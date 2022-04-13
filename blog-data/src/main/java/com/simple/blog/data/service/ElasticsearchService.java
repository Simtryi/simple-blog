package com.simple.blog.data.service;

import com.simple.blog.data.base.BaseEntity;

import java.util.List;
import java.util.Map;

/**
 * Elasticsearch 服务
 */
public interface ElasticsearchService {

    /**
     * 索引文档
     */
    <T extends BaseEntity> String indexDocument(String indexName, T document);

    /**
     * 批量索引文档
     */
    <T extends BaseEntity> String bulkDocument(String indexName, List<T> documents);

    /**
     * 删除文档
     */
    String deleteDocument(String indexName, Long id);

    /**
     * 更新文档
     */
    <T extends BaseEntity> String updateDocument(String indexName, T document);

    /**
     * 查询文档
     */
    Map<String, Object> getDocument(String indexName, Long id);

}
