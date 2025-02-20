package com.dixie.service;

import com.dixie.exception.ImagerPostNotFoundException;
import com.dixie.mapper.ImagerPostMapper;
import com.dixie.model.dto.ImagerPostDTO;
import com.dixie.model.dto.ImagerPostUploadData;
import com.dixie.model.entity.ImagerPost;
import com.dixie.repository.ImagerPostRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ImagerPostService implements PostService {

    private final ImagerPostRepository imagerPostRepository;
    private final ImagerPostMapper mapper;

    @Override
    public String uploadImagerPost(String imagerPostUploadDataJson, MultipartFile image) throws URISyntaxException, IOException {
        var imagerPost = buildImagerPost(imagerPostUploadDataJson, image);
        imagerPostRepository.saveImagerPost(imagerPost);
        return "Post saved successfully! [ID:%s]".formatted(imagerPost.getId());
    }

    private ImagerPost buildImagerPost(@NonNull String imagerPostDataJson,
                                       @NonNull MultipartFile image) throws IOException, URISyntaxException {

        //TODO come up with a better way to generate unique IDs
        var postID = RandomStringUtils.random(8, true, true);
        var imagerPostUploadData = parseFromJson(imagerPostDataJson);
        var creationDateTime = LocalDateTime.now();
        var expirationDateTime = creationDateTime.plusMinutes(imagerPostUploadData.getTtl());
        var url = buildURL(postID);

        return ImagerPost.builder()
                .id(postID)
                .user("STUB-USER") //TODO remove stub
                .image(image.getBytes())
                .message(imagerPostUploadData.getMessage())
                .creationTime(creationDateTime)
                .expirationTime(expirationDateTime)
                .link(url)
                .build();
    }

    private ImagerPostUploadData parseFromJson(String json) {
        return new Gson().fromJson(json, ImagerPostUploadData.class);
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
        var imagerPost = imagerPostRepository.getImagerPost(id);
        return mapper.toDTO(imagerPost.orElseThrow(ImagerPostNotFoundException::new));
    }

    @Override
    public List<ImagerPostDTO> getImagerPostsByUsername(String username) throws ImagerPostNotFoundException {
        var optional = imagerPostRepository.getImagerPostsByUsername(username);
        return optional
                .orElseThrow(ImagerPostNotFoundException::new)
                .stream().map(mapper::toDTO).toList();
    }

    @Override
    public ImagerPostDTO editImagerPost(@NonNull String id,
                                        @Nullable String payloadJson,
                                        @Nullable MultipartFile image) throws ImagerPostNotFoundException, IOException {
        var payload = parseFromJson(payloadJson);
        var entity = imagerPostRepository.getImagerPost(id)
                .orElseThrow(ImagerPostNotFoundException::new);
        var updatedEntity = updateEntity(entity, payload, image);
        var optional = imagerPostRepository.editImagerPost(updatedEntity);
        return optional
                .map(mapper::toDTO)
                .orElseThrow(ImagerPostNotFoundException::new);
    }

    private ImagerPost updateEntity(@NonNull ImagerPost entity,
                                    @Nullable ImagerPostUploadData uploadData,
                                    @Nullable MultipartFile image) throws IOException {

        var message = (uploadData != null) ? uploadData.getMessage() : null;
        var ttl = (uploadData != null) ? uploadData.getTtl() : null;
        var expirationTime = (ttl != null) ? entity.getCreationTime().plusMinutes(ttl) : null;
        var imageBytes = (image != null) ? image.getBytes() : null;

        entity.setMessage(Objects.requireNonNullElseGet(message, entity::getMessage));
        entity.setExpirationTime(Objects.requireNonNullElseGet(expirationTime, entity::getExpirationTime));
        entity.setImage(Objects.requireNonNullElseGet(imageBytes, entity::getImage));

        return entity;
    }

    @Override
    public String deleteImagerPost(String id) throws ImagerPostNotFoundException {
        var imagerPost = imagerPostRepository.getImagerPost(id)
                .orElseThrow(ImagerPostNotFoundException::new);
        imagerPostRepository.deleteImagerPost(imagerPost);
        return "Post removed successfully [ID:%s]".formatted(id);
    }

    @Scheduled(fixedRate = 5000)
    private void deleteExpiredImagerPosts() {
        imagerPostRepository.deleteExpiredImagerPosts();
    }
}
