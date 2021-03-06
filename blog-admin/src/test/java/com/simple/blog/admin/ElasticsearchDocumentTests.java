package com.simple.blog.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Elasticsearch 文档测试
 */
public class ElasticsearchDocumentTests extends BlogAdminApplicationTests {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 索引文档
     */
    @Test
    public void testIndexDocument() throws IOException {
        //  索引请求对象
        IndexRequest indexRequest = new IndexRequest("products");
        //  指定文档Id
        indexRequest.id("1");
        //  指定文档数据
        indexRequest.source("{\n" +
                "  \"title\": \"苹果\",\n" +
                "  \"price\": 2.5,\n" +
                "  \"created_at\": \"2022-04-09\",\n" +
                "  \"description\": \"苹果非常好吃！\"\n" +
                "}", XContentType.JSON);
        //  索引文档
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println("索引文档状态：" + indexResponse.status());
        restHighLevelClient.close();
    }

    /**
     * 批量索引文档
     */
    @Test
    public void testBulkDocument() throws IOException {
        Product product1 = new Product();
        product1.setId(5);
        product1.setTitle("橘子");
        product1.setPrice(3.0);
        product1.setDescription("橘子非常好吃！");

        Product product2 = new Product();
        product2.setId(6);
        product2.setTitle("橙子");
        product2.setPrice(5.0);
        product2.setDescription("橙子非常好吃！");

        IndexRequest indexRequest1 = new IndexRequest("products");
        indexRequest1.id("5")
                .source(new ObjectMapper().writeValueAsString(product1), XContentType.JSON);

        IndexRequest indexRequest2 = new IndexRequest("products");
        indexRequest2.id("6")
                .source(new ObjectMapper().writeValueAsString(product2), XContentType.JSON);

        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.add(indexRequest1).add(indexRequest2);

        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        System.out.println(bulkResponse.status());
        restHighLevelClient.close();
    }

    /**
     * 删除文档
     */
    @Test
    public void testDeleteDocument() throws IOException {
        //  删除请求对象
        DeleteRequest deleteRequest = new DeleteRequest("products", "1");
        //  删除文档
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println("删除文档状态：" + deleteResponse.status());
        restHighLevelClient.close();
    }

    /**
     * 更新文档
     */
    @Test
    public void testUpdateDocument() throws IOException {
        //  更新请求对象
        UpdateRequest updateRequest = new UpdateRequest("products", "1");
        //  指定更新的数据
        updateRequest.doc("{\n" +
                "  \"price\": 4.5\n" +
                "}", XContentType.JSON);
        //  更新文档
        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println("更新文档状态：" + updateResponse.status());
        restHighLevelClient.close();
    }

    /**
     * 查询文档
     */
    @Test
    public void testGetDocument() throws IOException {
        //  查询请求对象
        GetRequest getRequest = new GetRequest("products", "1");
        //  查询文档
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(getResponse.getId() + ":" + getResponse.getSource());
        restHighLevelClient.close();
    }

}
