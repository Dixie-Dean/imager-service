package com.dixie.service;

import com.dixie.model.dto.SnippetCreationDTO;
import com.dixie.model.dto.SnippetDTO;
import com.dixie.model.dto.SnippetUpdateDTO;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

public interface SnippetService {

    String create(SnippetCreationDTO snippetCreationDTO) throws URISyntaxException, MalformedURLException;

    List<SnippetDTO> viewAll();

    SnippetDTO getSnippet(String id);

    String update(String id, SnippetUpdateDTO snippetUpdateDTO);

    String delete(String id);
}
