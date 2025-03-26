package com.dixie.service;

import com.dixie.exception.ImagerPostNotFoundException;
import com.dixie.exception.ImagerPostValidationException;
import com.dixie.mapper.ImagerPostMapper;
import com.dixie.model.dto.ImagerPostDTO;
import com.dixie.model.dto.ImagerPostUploadData;
import com.dixie.model.entity.ImagerPost;
import com.dixie.repository.ImagerPostRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImagerPostService implements PostService {

    private static final String SEND_TO_TOPIC_NAME = "request-id-topic";
    private static final String LISTEN_TO_TOPIC_NAME = "provide-id-topic";
    private static final String MESSAGE = "request-id";

    private final ImagerPostRepository imagerPostRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ImagerPostMapper mapper;
    private final Gson gson;
    private final Validator validator;

    private CompletableFuture<String> responseFromIdService;

    @KafkaListener(topics = LISTEN_TO_TOPIC_NAME, groupId = "imager")
    public void getUniqueId(String id) {
        log.info("GetUniqueID | ID:{}, topic:{}", id, LISTEN_TO_TOPIC_NAME);
        responseFromIdService.complete(id);
    }

    @Override
    public String uploadImagerPost(String imagerPostUploadDataJson, MultipartFile image) throws URISyntaxException, IOException, ExecutionException, InterruptedException {
        log.info("UploadImagerPost | JSON:{}, Image:{}", imagerPostUploadDataJson, image);
        var imagerPost = buildImagerPost(imagerPostUploadDataJson, image);
        log.info("ImagerPost to persist:{}", imagerPost);
        imagerPostRepository.saveImagerPost(imagerPost);
        return "Post saved successfully! [ID:%s]".formatted(imagerPost.getId());
    }

    private ImagerPost buildImagerPost(@NonNull String imagerPostDataJson,
                                       @NonNull MultipartFile image) throws IOException, URISyntaxException, ExecutionException, InterruptedException {

        this.responseFromIdService = new CompletableFuture<>();
        kafkaTemplate.send(SEND_TO_TOPIC_NAME, MESSAGE);
        log.info("BuildImagerPost | Request to ID-Service, topic:{}, message:{}", SEND_TO_TOPIC_NAME, MESSAGE);

        var imagerPostUploadData = parseFromJson(imagerPostDataJson);
        var creationDateTime = LocalDateTime.now();
        var expirationDateTime = creationDateTime.plusMinutes(imagerPostUploadData.getTtl());
        var postID = responseFromIdService.get();
        var url = buildURL(postID);

        return ImagerPost.builder()
                .id(postID)
                .user(imagerPostUploadData.getEmail())
                .image(image.getBytes())
                .message(imagerPostUploadData.getMessage())
                .creationTime(creationDateTime)
                .expirationTime(expirationDateTime)
                .link(url)
                .build();
    }

    private ImagerPostUploadData parseFromJson(String json) {
        var imagerPostUploadData = gson.fromJson(json, ImagerPostUploadData.class);
        var errors = validator.validateObject(imagerPostUploadData).getAllErrors();
        if (!errors.isEmpty()) {
            String message = errors.stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining("\n"));
            log.error("ParseFromJson | message:{}", message);
            throw new ImagerPostValidationException(message);
        }
        return imagerPostUploadData;
    }

    private String buildURL(String snippetID) throws URISyntaxException, MalformedURLException {
        URIBuilder builder = new URIBuilder();
        builder.setScheme("http");
        builder.setHost("localhost");
        builder.setPort(8080);
        builder.setPath("/imager/post/" + snippetID);
        URL url = builder.build().toURL();
        return url.toString();
    }

    @Override
    public ImagerPostDTO getImagerPost(String id) throws ImagerPostNotFoundException {
        log.info("GetImagerPost | ID:{}", id);
        var optional = imagerPostRepository.getImagerPost(id);
        var imagerPost = mapper.toDTO(optional.orElseThrow(ImagerPostNotFoundException::new));
        log.info("GetImagerPost | ImagerPost:{}", imagerPost);
        return imagerPost;
    }

    @Override
    public List<ImagerPostDTO> getImagerPostsByEmail(String email) throws ImagerPostNotFoundException {
        log.info("GetImagerPostsByEmail | Username:{}", email);
        var optional = imagerPostRepository.getImagerPostsByEmail(email);
        var posts = optional
                .orElseThrow(ImagerPostNotFoundException::new)
                .stream().map(mapper::toDTO).toList();
        log.info("GetImagerPostsByEmail | DTOs:{}", posts);
        return posts;
    }

    @Override
    public ImagerPostDTO editImagerPost(@NonNull String id,
                                        @Nullable String payloadJson,
                                        @Nullable MultipartFile image) throws ImagerPostNotFoundException, IOException {
        log.info("EditImagerPost | ID:{}, JSON:{}, Image:{}", id, payloadJson, image);
        var payload = parseFromJson(payloadJson);
        var entity = imagerPostRepository.getImagerPost(id)
                .orElseThrow(ImagerPostNotFoundException::new);
        var updatedEntity = updateEntity(entity, payload, image);
        log.info("EditImagerPost | Old:{}, Updated:{}", entity, updatedEntity);
        var optional = imagerPostRepository.editImagerPost(updatedEntity);
        return optional
                .map(mapper::toDTO)
                .orElseThrow(ImagerPostNotFoundException::new);
    }

    private ImagerPost updateEntity(@NonNull ImagerPost entity,
                                    @Nullable ImagerPostUploadData uploadData,
                                    @Nullable MultipartFile image) throws IOException {

        var message = (uploadData != null) ? uploadData.getMessage() : null;
        var ttl = (uploadData != null) ? uploadData.getTtl() : 0;
        var expirationTime = (ttl != 0) ? entity.getCreationTime().plusMinutes(ttl) : null;
        var imageBytes = (image != null) ? image.getBytes() : null;

        entity.setMessage(Objects.requireNonNullElseGet(message, entity::getMessage));
        entity.setExpirationTime(Objects.requireNonNullElseGet(expirationTime, entity::getExpirationTime));
        entity.setImage(Objects.requireNonNullElseGet(imageBytes, entity::getImage));

        return entity;
    }

    @Override
    public String deleteImagerPost(String id) throws ImagerPostNotFoundException {
        log.info("DeleteImagerPost | ID:{}", id);
        var imagerPost = imagerPostRepository.getImagerPost(id)
                .orElseThrow(ImagerPostNotFoundException::new);
        log.info("DeleteImagerPost | ImagerPost:{}", imagerPost);
        imagerPostRepository.deleteImagerPost(imagerPost);
        return "Post removed successfully [ID:%s]".formatted(id);
    }

    @Scheduled(fixedRate = 5000)
    private void deleteExpiredImagerPosts() {
        imagerPostRepository.deleteExpiredImagerPosts();
    }
}
