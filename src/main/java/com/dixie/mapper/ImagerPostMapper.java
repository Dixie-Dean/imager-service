package com.dixie.mapper;

import com.dixie.model.dto.ImagerPostDTO;
import com.dixie.model.entity.ImagerPost;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImagerPostMapper {

    ImagerPostDTO toDTO(ImagerPost imagerPost);
}
