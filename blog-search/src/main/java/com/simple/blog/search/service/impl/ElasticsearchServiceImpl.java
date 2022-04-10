package com.simple.blog.search.service.impl;

import com.simple.blog.common.api.ResultCode;
import com.simple.blog.common.base.BaseEntity;
import com.simple.blog.common.exception.ApiException;
import com.simple.blog.common.util.JsonUtil;
import com.simple.blog.search.service.ElasticsearchService;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * ElasticsearchService 实现类
 */
@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Override
    public <T extends BaseEntity> String indexDocument(String indexName, T document) {
        //  索引请求对象
        IndexRequest indexRequest = new IndexRequest(indexName);
        //  指定文档数据
        indexRequest.id(document.getId().toString())
                .source(JsonUtil.toJsonStr(document), XContentType.JSON);

        try {
            //  索引文档
            IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            return indexResponse.status().toString();
        } catch (IOException e) {
            throw new ApiException(ResultCode.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public <T extends BaseEntity> String bulkDocument(String indexName, List<T> documents) {
        //  批量请求对象
        BulkRequest bulkRequest = new BulkRequest();

        documents.forEach(document -> {
            bulkRequest.add(new IndexRequest(indexName)
                    .id(document.getId().toString())
                    .source(JsonUtil.toJsonStr(document), XContentType.JSON));
        });

        try {
            //  批量索引文档
            BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            return bulkResponse.status().toString();
        } catch (IOException e) {
            throw new ApiException(ResultCode.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public String deleteDocument(String indexName, Long id) {
        //  删除请求对象
        DeleteRequest deleteRequest = new DeleteRequest(indexName, id.toString());

        try {
            //  删除文档
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            return deleteResponse.status().toString();
        } catch (IOException e) {
            throw new ApiException(ResultCode.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public <T extends BaseEntity> String updateDocument(String indexName, T document) {
        //  更新请求对象
        UpdateRequest updateRequest = new UpdateRequest(indexName, document.getId().toString());
        //  指定更新的数据
        updateRequest.doc(JsonUtil.toJsonStr(document), XContentType.JSON);

        try {
            //  更新文档
            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            return updateResponse.status().toString();
        } catch (IOException e) {
            throw new ApiException(ResultCode.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Map<String, Object> getDocument(String indexName, Long id) {
        //  查询请求对象
        GetRequest getRequest = new GetRequest(indexName, id.toString());

        try {
            //  查询文档
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            return getResponse.getSource();
        } catch (IOException e) {
            throw new ApiException(ResultCode.BAD_REQUEST, e.getMessage());
        }
    }

}
