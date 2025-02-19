package com.dixie.service;

import com.dixie.model.dto.ImagerPostUploadInfo;
import com.dixie.model.dto.ImagerPostDTO;
import com.dixie.model.dto.ImagerPostUpdateInfo;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

public interface PostService {

    String uploadPost(ImagerPostUploadInfo imagerPostUploadInfo) throws URISyntaxException, MalformedURLException;

    List<ImagerPostDTO> getPosts();

    ImagerPostDTO getPost(String id);

    String editPost(String id, ImagerPostUpdateInfo imagerPostUpdateInfo);

    String deletePost(String id);
}
