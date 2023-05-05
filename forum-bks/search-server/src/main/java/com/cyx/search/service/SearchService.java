package com.cyx.search.service;

import java.io.IOException;
import java.util.List;

public interface SearchService {
    boolean createIndex(String indexName) throws IOException;
    <T> void importData(String indexName, List<T> data) throws IOException;
    <T> List<T>  search(String indexName,List<String> mustQueries,List<String> mustValues, List<String> shouldQueries,List<String> shouldValues, int size,Class<T> clazz) throws IOException;
}
