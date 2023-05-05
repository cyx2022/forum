package com.cyx.search.service.impl;

import com.cyx.common.utils.JsonUtil;
import com.cyx.search.service.SearchService;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.bytes.BytesReference;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {
    @Autowired
    private RestHighLevelClient client;

    public boolean createIndex(String indexName) throws IOException {
        GetIndexRequest request=new GetIndexRequest(indexName);
        boolean exist = client.indices().exists(request, RequestOptions.DEFAULT);
        if(!exist){
            CreateIndexRequest createIndexRequest=new CreateIndexRequest(indexName);
            client.indices().create(createIndexRequest,RequestOptions.DEFAULT);
            return true;
        }else{
            return false;
        }
    }

    public <T> void importData(String indexName, List<T> data) throws IOException {
        BulkRequest bulkRequest=new BulkRequest();
        for (T sd : data) {
            String s = JsonUtil.object2JsonStr(sd);
            IndexRequest request=new IndexRequest(indexName).source(s, XContentType.JSON);
            bulkRequest.add(request);
        }
        client.bulk(bulkRequest,RequestOptions.DEFAULT);
    }


    public <T> List<T>  search(String indexName,List<String> mustQueries,List<String> mustValues, List<String> shouldQueries,List<String> shouldValues,int size,Class<T> clazz) throws IOException {

        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//        must参数
        for(int i=0;mustQueries!=null&&i<mustQueries.size();i++){
            boolQueryBuilder.must(QueryBuilders.matchQuery(mustQueries.get(i),mustValues.get(i)));
        }
//        should参数
        for (int i = 0; shouldQueries!=null&&i < shouldQueries.size(); i++) {
            boolQueryBuilder.should(QueryBuilders.matchQuery(shouldQueries.get(i), shouldValues.get(i)));
        }


        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(size);

        searchRequest.source(searchSourceBuilder);
        SearchResponse response=client.search(searchRequest,RequestOptions.DEFAULT);
        SearchHits hits=response.getHits();
        List<T> results=new ArrayList<>();
        for (SearchHit hit : hits) {
            String s = hit.getSourceAsString();
            T t = JsonUtil.jsonStr2Object(s, clazz);
            results.add(t);
        }
        return results;
    }
}
