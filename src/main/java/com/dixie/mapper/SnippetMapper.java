package com.dixie.mapper;

import com.dixie.model.dto.SnippetDTO;
import com.dixie.model.entity.Snippet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SnippetMapper {

    SnippetDTO toSnippetDTO(Snippet snippet);

    Snippet toSnippet(SnippetDTO snippetDTO);
}
