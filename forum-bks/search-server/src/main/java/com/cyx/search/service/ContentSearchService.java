package com.cyx.search.service;

import com.cyx.common.entity.Content;

import java.io.IOException;
import java.util.List;

public interface ContentSearchService {
    List<Content> searchByWord(String word) throws IOException;

    List<Content> searchByIdAndWord(String id, String word) throws IOException;
}
