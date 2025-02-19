package com.dixie.controller;

import com.dixie.model.dto.ImagerPostDTO;
import com.dixie.model.dto.ImagerPostUpdateInfo;
import com.dixie.model.dto.ImagerPostUploadInfo;
import com.dixie.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pastebin")
public class ImagerPostController {

    private final PostService postService;

    @PostMapping("/upload")
    public String uploadPost(@RequestBody ImagerPostUploadInfo imagerPostUploadInfo) throws MalformedURLException, URISyntaxException {
        return postService.uploadPost(imagerPostUploadInfo);
    }

    @GetMapping("/posts")
    public List<ImagerPostDTO> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/get/{id}")
    public ImagerPostDTO getPost(@PathVariable String id) {
        return postService.getPost(id);
    }

    @PatchMapping("/edit/{id}")
    public String editPost(@PathVariable String id, @RequestBody ImagerPostUpdateInfo imagerPostUpdateInfo) {
        return postService.editPost(id, imagerPostUpdateInfo);
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable String id) {
        return postService.deletePost(id);
    }
}