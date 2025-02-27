package com.dixie.service;

import com.dixie.exception.ImagerPostNotFoundException;
import com.dixie.model.dto.ImagerPostDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PostService {

    String uploadImagerPost(String imagerPostUploadInfo, MultipartFile image) throws URISyntaxException, IOException, ExecutionException, InterruptedException;

    ImagerPostDTO getImagerPost(String id) throws ImagerPostNotFoundException;

    List<ImagerPostDTO> getImagerPostsByUsername(String username) throws ImagerPostNotFoundException;

    ImagerPostDTO editImagerPost(String id, String payloadJson, MultipartFile image) throws IOException, URISyntaxException, ImagerPostNotFoundException;

    String deleteImagerPost(String id) throws ImagerPostNotFoundException;
}
