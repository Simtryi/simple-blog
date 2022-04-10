package com.simple.blog.admin;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Elasticsearch 索引测试
 */
public class ElasticsearchIndexTests extends BlogAdminApplicationTests {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     */
    @Test
    public void testCreateIndex() throws IOException {
        //  创建索引请求对象
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("products");
        //  设置映射
        createIndexRequest.mapping("{\n" +
                "  \"properties\": {\n" +
                "    \"title\": {\n" +
                "      \"type\": \"keyword\"\n" +
                "    },\n" +
                "    \"price\": {\n" +
                "      \"type\": \"double\"\n" +
                "    },\n" +
                "    \"created_at\": {\n" +
                "      \"type\": \"date\"\n" +
                "    },\n" +
                "    \"description\": {\n" +
                "      \"type\": \"text\",\n" +
                "      \"analyzer\": \"ik_max_word\"\n" +
                "    }\n" +
                "  }\n" +
                "}", XContentType.JSON);
        //  创建索引
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println("创建状态：" + createIndexResponse.isAcknowledged());
        restHighLevelClient.close();
    }

    /**
     * 删除索引
     */
    @Test
    public void testDeleteIndex() throws IOException {
        //  删除索引请求对象
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("products");
        //  删除索引
        AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        System.out.println("删除状态：" + acknowledgedResponse.isAcknowledged());
        restHighLevelClient.close();
    }

    /**
     * 查询索引
     */
    @Test
    public void testGetIndex() throws IOException {
        //  查询索引请求对象
        GetIndexRequest getIndexRequest = new GetIndexRequest("products");
        //  查询索引
        GetIndexResponse getIndexResponse = restHighLevelClient.indices().get(getIndexRequest, RequestOptions.DEFAULT);
        for (String index : getIndexResponse.getIndices()) {
            System.out.println(index);
        }
        restHighLevelClient.close();
    }

}
