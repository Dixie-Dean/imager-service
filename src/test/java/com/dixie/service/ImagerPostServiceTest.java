package com.dixie.service;

import com.dixie.exception.ImagerPostNotFoundException;
import com.dixie.exception.ImagerPostValidationException;
import com.dixie.mapper.ImagerPostMapper;
import com.dixie.model.dto.ImagerPostDTO;
import com.dixie.model.dto.ImagerPostUploadData;
import com.dixie.model.entity.ImagerPost;
import com.dixie.repository.ImagerPostRepository;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ImagerPostServiceTest {

    private static final String ID = "ABCDEFGH";
    private static final String EMAIL = "user@gmail.com";

    private static final ImagerPost IMAGER_POST =
            new ImagerPost(
                    ID,
                    "user@gmail.com",
                    "image".getBytes(),
                    "message",
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    "link");

    private static final ImagerPost UPDATED_IMAGER_POST =
            new ImagerPost(
                    ID,
                    "user@gmail.com",
                    "image".getBytes(),
                    "updated-message",
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    "link");

    private static final ImagerPostDTO IMAGER_POST_DTO =
            new ImagerPostDTO(
                    ID,
                    "user@gmail.com",
                    "image".getBytes(),
                    "message",
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    "link");

    private static final ImagerPostDTO UPDATED_IMAGER_POST_DTO =
            new ImagerPostDTO(
                    ID,
                    "user@gmail.com",
                    "image".getBytes(),
                    "updated-message",
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    "link");

    @Mock
    private ImagerPostRepository imagerPostRepository;

    @Mock
    private ImagerPostMapper mapper;

    @Mock
    private Gson gson;

    @Mock
    private Validator validator;

    @InjectMocks
    private ImagerPostService imagerPostService;

    @Test
    void getImagerPost_Success() throws ImagerPostNotFoundException {
        when(imagerPostRepository.getImagerPost(anyString())).thenReturn(Optional.of(IMAGER_POST));
        when(mapper.toDTO(IMAGER_POST)).thenReturn(IMAGER_POST_DTO);

        var result = imagerPostService.getImagerPost(ID);

        assertEquals(ID, result.getId());
        verify(imagerPostRepository, atLeastOnce()).getImagerPost(anyString());
        verify(mapper, atLeastOnce()).toDTO(any(ImagerPost.class));
    }

    @Test
    void getImagerPost_ThrowsImagerPostNotFoundException() {
        when(imagerPostRepository.getImagerPost(anyString())).thenReturn(Optional.empty());
        assertThrows(ImagerPostNotFoundException.class, () -> imagerPostService.getImagerPost(anyString()));
    }

    @Test
    void getImagerPostsByEmail_Success() throws ImagerPostNotFoundException {
        when(imagerPostRepository.getImagerPostsByEmail(EMAIL)).thenReturn(Optional.of(List.of(IMAGER_POST)));
        when(mapper.toDTO(IMAGER_POST)).thenReturn(IMAGER_POST_DTO);

        var result = imagerPostService.getImagerPostsByEmail(EMAIL);

        assertEquals(List.of(IMAGER_POST_DTO), result);
        verify(imagerPostRepository, atLeastOnce()).getImagerPostsByEmail(anyString());
        verify(mapper, atLeastOnce()).toDTO(any(ImagerPost.class));
    }

    @Test
    void getImagerPostsByEmail_ThrowsImagerPostNotFoundException() {
        when(imagerPostRepository.getImagerPostsByEmail(anyString())).thenReturn(Optional.empty());
        assertThrows(ImagerPostNotFoundException.class, () -> imagerPostService.getImagerPostsByEmail(anyString()));
    }

    @Test
    void editImagerPost_Success() throws IOException, ImagerPostNotFoundException {
        var json = "{\"email\":\"user@gmail.com\",\"message\":\"updated-message\",\"ttl\":1}";
        var image = mock(MultipartFile.class);
        when(image.getBytes()).thenReturn("mock-image".getBytes());

        var imagerPostUploadData = new ImagerPostUploadData(EMAIL, "updated-message", 1);
        when(gson.fromJson(json, ImagerPostUploadData.class)).thenReturn(imagerPostUploadData);

        when(imagerPostRepository.getImagerPost(anyString())).thenReturn(Optional.of(IMAGER_POST));
        when(imagerPostRepository.editImagerPost(any(ImagerPost.class))).thenReturn(Optional.of(UPDATED_IMAGER_POST));
        when(mapper.toDTO(any(ImagerPost.class))).thenReturn(UPDATED_IMAGER_POST_DTO);

        var errors = mock(Errors.class);
        when(validator.validateObject(imagerPostUploadData)).thenReturn(errors);
        when(errors.getAllErrors()).thenReturn(Collections.emptyList());

        var resultImagerPostDTO = imagerPostService.editImagerPost(ID, json, image);

        assertEquals(ID, resultImagerPostDTO.getId());
        assertEquals(EMAIL, resultImagerPostDTO.getUser());
        assertEquals("updated-message", resultImagerPostDTO.getMessage());
        verify(imagerPostRepository, atLeastOnce()).getImagerPost(anyString());
        verify(imagerPostRepository, atLeastOnce()).editImagerPost(any(ImagerPost.class));
    }

    @Test
    void editImagerPost_ThrowsImagerPostValidationException() {
        var json = "{\"email\":\"user@gmail.com\",\"message\":\"updated-message\",\"ttl\":1}";
        var image = mock(MultipartFile.class);

        var imagerPostUploadData = new ImagerPostUploadData(EMAIL, "updated-message", 1);
        when(gson.fromJson(json, ImagerPostUploadData.class)).thenReturn(imagerPostUploadData);

        var errors = mock(Errors.class);
        var objectError = mock(ObjectError.class);
        when(validator.validateObject(imagerPostUploadData)).thenReturn(errors);
        when(errors.getAllErrors()).thenReturn(List.of(objectError));

        assertThrows(ImagerPostValidationException.class, () -> imagerPostService.editImagerPost(ID, json, image));
    }

    @Test
    void deleteImagerPost_Success() throws ImagerPostNotFoundException {
        when(imagerPostRepository.getImagerPost(anyString())).thenReturn(Optional.of(IMAGER_POST));

        var result = imagerPostService.deleteImagerPost(ID);

        assertEquals("Post removed successfully [ID:%s]".formatted(ID), result);
        verify(imagerPostRepository, atLeastOnce()).getImagerPost(anyString());
        verify(imagerPostRepository, atLeastOnce()).deleteImagerPost(any(ImagerPost.class));
    }

    @Test
    void deleteImagerPost_ThrowsImagerPostNotFoundException() {
        when(imagerPostRepository.getImagerPost(anyString())).thenReturn(Optional.empty());
        assertThrows(ImagerPostNotFoundException.class, () -> imagerPostService.deleteImagerPost(anyString()));
    }
}
