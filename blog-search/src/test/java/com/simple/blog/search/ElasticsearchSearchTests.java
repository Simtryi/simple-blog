package com.simple.blog.search;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;

/**
 * Elasticsearch 搜索测试
 */
public class ElasticsearchSearchTests extends BlogSearchApplicationTests {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 搜索
     */
    @Test
    public void testQuery() throws IOException {
        //  搜索所有
        System.out.println("---------- 搜索所有 ----------");
        query(QueryBuilders.matchAllQuery());

        //  关键词搜索
        System.out.println("---------- 关键词搜索 ----------");
        query(QueryBuilders.termQuery("description", "苹果"));

        //  范围搜索
        System.out.println("---------- 范围搜索 ----------");
        query(QueryBuilders.rangeQuery("price").gte(1).lte(10));

        //  前缀搜索
        System.out.println("---------- 前缀搜素 ----------");
        query(QueryBuilders.prefixQuery("title", "苹果"));

        //  通配符搜索 ?: 任意一个字符 *: 任意多个字符
        System.out.println("---------- 通配符搜索 ----------");
        query(QueryBuilders.wildcardQuery("title", "苹果*"));

        //  多个指定Id搜索
        System.out.println("---------- 多个指定Id搜索 ----------");
        query(QueryBuilders.idsQuery().addIds("1").addIds("2"));

        //  多字段搜索
        System.out.println("---------- 多字段搜索 ----------");
        query(QueryBuilders.multiMatchQuery("非常", "title", "description"));
    }

    private void query(QueryBuilder queryBuilder) throws IOException {
        //  搜素请求对象
        SearchRequest searchRequest = new SearchRequest("products");
        //  搜索条件对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //  搜索条件
        searchSourceBuilder.query(queryBuilder);
        //  指定搜索条件
        searchRequest.source(searchSourceBuilder);

        //  搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("hits.total:" + searchResponse.getHits().getTotalHits().value);
        System.out.println("hits.max_score:" + searchResponse.getHits().getMaxScore());
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println("_id:" + hit.getId());
            System.out.println("_index:" + hit.getIndex());
            System.out.println("_score:" + hit.getScore());
            System.out.println("_source:" + hit.getSourceAsString());
        }
    }

    /**
     * 分页搜索
     */
    @Test
    public void testPage() throws IOException {
        //  搜素请求对象
        SearchRequest searchRequest = new SearchRequest("products");
        //  搜索条件对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //  搜索条件
        searchSourceBuilder.query(QueryBuilders.matchAllQuery())
                .from(0)    //  起始位置
                .size(10);  //  每页显示条数

        //  指定搜索条件
        searchRequest.source(searchSourceBuilder);

        //  搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("hits.total:" + searchResponse.getHits().getTotalHits().value);
        System.out.println("hits.max_score:" + searchResponse.getHits().getMaxScore());
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println("_id:" + hit.getId());
            System.out.println("_index:" + hit.getIndex());
            System.out.println("_score:" + hit.getScore());
            System.out.println("_source:" + hit.getSourceAsString());
        }
        restHighLevelClient.close();
    }

    /**
     * 排序搜索
     */
    @Test
    public void testSort() throws IOException {
        //  搜素请求对象
        SearchRequest searchRequest = new SearchRequest("products");
        //  搜索条件对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //  搜索条件
        searchSourceBuilder.query(QueryBuilders.matchAllQuery())
                .sort("price", SortOrder.ASC);

        //  指定搜索条件
        searchRequest.source(searchSourceBuilder);

        //  搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("hits.total:" + searchResponse.getHits().getTotalHits().value);
        System.out.println("hits.max_score:" + searchResponse.getHits().getMaxScore());
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println("_id:" + hit.getId());
            System.out.println("_index:" + hit.getIndex());
            System.out.println("_score:" + hit.getScore());
            System.out.println("_source:" + hit.getSourceAsString());
        }
        restHighLevelClient.close();
    }

    /**
     * 返回指定字段
     */
    @Test
    public void testSource() throws IOException {
        //  搜素请求对象
        SearchRequest searchRequest = new SearchRequest("products");
        //  搜索条件对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //  搜索条件
        searchSourceBuilder.query(QueryBuilders.matchAllQuery())
                .fetchSource(new String[]{"title", "price"}, new String[]{}); //  参数1：包含字段数组，参数2：排除字段数组

        //  指定搜索条件
        searchRequest.source(searchSourceBuilder);

        //  搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("hits.total:" + searchResponse.getHits().getTotalHits().value);
        System.out.println("hits.max_score:" + searchResponse.getHits().getMaxScore());
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println("_id:" + hit.getId());
            System.out.println("_index:" + hit.getIndex());
            System.out.println("_score:" + hit.getScore());
            System.out.println("_source:" + hit.getSourceAsString());
        }
        restHighLevelClient.close();
    }

    /**
     * 高亮搜索
     */
    @Test
    public void testHighLight() throws IOException {
        //  搜素请求对象
        SearchRequest searchRequest = new SearchRequest("products");
        //  搜索条件对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //  高亮对象
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.requireFieldMatch(false)
                .field("description").field("title")
                .preTags("<span style='color:red;'>").postTags("</span>");

        //  搜索条件
        searchSourceBuilder.query(QueryBuilders.termQuery("description", "好吃"))
                .highlighter(highlightBuilder);

        //  指定搜索条件
        searchRequest.source(searchSourceBuilder);

        //  搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("hits.total:" + searchResponse.getHits().getTotalHits().value);
        System.out.println("hits.max_score:" + searchResponse.getHits().getMaxScore());
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println("_id:" + hit.getId());
            System.out.println("_index:" + hit.getIndex());
            System.out.println("_score:" + hit.getScore());
            System.out.println("_source:" + hit.getSourceAsString());
            //  获取高亮字段
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields.containsKey("description")) {
                System.out.println("description:" + highlightFields.get("description").fragments()[0]);
            }
            if (highlightFields.containsKey("title")) {
                System.out.println("title:" + highlightFields.get("title").fragments()[0]);
            }
        }
        restHighLevelClient.close();
    }

    /**
     * 过滤搜索
     */
    @Test
    public void testFilter() throws IOException {
        //  搜素请求对象
        SearchRequest searchRequest = new SearchRequest("products");
        //  搜索条件对象
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        //  搜索条件
        searchSourceBuilder.query(QueryBuilders.matchAllQuery())
                .postFilter(QueryBuilders.termQuery("description", "苹果"));

        //  指定搜索条件
        searchRequest.source(searchSourceBuilder);

        //  搜索
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("hits.total:" + searchResponse.getHits().getTotalHits().value);
        System.out.println("hits.max_score:" + searchResponse.getHits().getMaxScore());
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println("_id:" + hit.getId());
            System.out.println("_index:" + hit.getIndex());
            System.out.println("_score:" + hit.getScore());
            System.out.println("_source:" + hit.getSourceAsString());
        }
        restHighLevelClient.close();
    }

}
