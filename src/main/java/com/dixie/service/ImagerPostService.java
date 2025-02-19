package com.dixie.service;

import com.dixie.mapper.ImagerPostMapper;
import com.dixie.model.dto.ImagerPostDTO;
import com.dixie.model.dto.ImagerPostUpdateInfo;
import com.dixie.model.dto.ImagerPostUploadInfo;
import com.dixie.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ImagerPostService implements PostService {

    private final PostRepository postRepository;
    private final ImagerPostMapper imagerPostMapper;

    @Override
    public String uploadPost(ImagerPostUploadInfo imagerPostUploadInfo) throws URISyntaxException, MalformedURLException {
//        var postID = RandomStringUtils.random(8, true, true);
//        var url = buildURL(postID);
//        var creationDateTime = LocalDateTime.now();
//        var expirationDateTime = creationDateTime.plusMinutes(imagerPostUploadInfo.getTtl());
        return null;
    }

    @Override
    public List<ImagerPostDTO> getPosts() {
//        List<ImagerPost> imagerPosts = postRepository.getAllSnippets();
//        List<ImagerPostDTO> imagerPostDTOs = new ArrayList<>();
//
//        for (ImagerPost imagerPost : imagerPosts) {
//            ImagerPostDTO imagerPostDTO = imagerPostMapper.toDTO(imagerPost);
//            imagerPostDTOs.add(imagerPostDTO);
//        }
//        return imagerPostDTOs;
        return null;
    }

    @Override
    public ImagerPostDTO getPost(String id) {
//        ImagerPost imagerPost = postRepository.getSnippetById(id);
//        return imagerPostMapper.toDTO(imagerPost);
        return null;
    }

    @Override
    public String editPost(String id, ImagerPostUpdateInfo imagerPostUpdateInfo) {
//        return postRepository.update(id, imagerPostUpdateInfo.getBody());
        return null;
    }

    @Override
    public String deletePost(String id) {
//        return postRepository.delete(id);
        return null;
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
}
