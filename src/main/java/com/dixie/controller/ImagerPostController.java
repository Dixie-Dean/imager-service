package com.dixie.controller;

import com.dixie.exception.ImagerPostNotFoundException;
import com.dixie.model.dto.ImagerPostDTO;
import com.dixie.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/imager")
public class ImagerPostController {

    private final PostService postService;

    @PostMapping(value = "/upload")
    public ResponseEntity<String> uploadImagerPost(@RequestPart("data") String payloadJson,
                                                   @RequestPart("image") MultipartFile image) throws URISyntaxException, IOException, ExecutionException, InterruptedException {
        var response = postService.uploadImagerPost(payloadJson, image);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/post")
    public ResponseEntity<ImagerPostDTO> getImagerPost(@RequestParam String id) throws ImagerPostNotFoundException {
        var imagerPost = postService.getImagerPost(id);
        return new ResponseEntity<>(imagerPost, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<ImagerPostDTO>> getImagerPostsByUsername(@RequestParam String username) throws ImagerPostNotFoundException {
        var imagerPosts = postService.getImagerPostsByUsername(username);
        return new ResponseEntity<>(imagerPosts, HttpStatus.OK);
    }

    @PatchMapping("/edit")
    public ResponseEntity<ImagerPostDTO> editImagerPost(@RequestPart("id") String id,
                                                        @RequestPart(value = "data", required = false) String payloadJson,
                                                        @RequestPart(value = "image", required = false) MultipartFile image) throws ImagerPostNotFoundException, IOException, URISyntaxException {
        var imagerPost = postService.editImagerPost(id, payloadJson, image);
        return new ResponseEntity<>(imagerPost, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteImagerPost(@RequestParam String id) throws ImagerPostNotFoundException {
        var response = postService.deleteImagerPost(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}