package com.cyx.search.service;

import com.cyx.common.entity.Bar;

import java.io.IOException;
import java.util.List;

public interface BarSearchService {
    List<Bar> searchByWord(String word) throws IOException;
}
