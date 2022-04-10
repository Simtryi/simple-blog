package com.simple.blog.search;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedDoubleTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.ParsedAvg;
import org.elasticsearch.search.aggregations.metrics.ParsedMax;
import org.elasticsearch.search.aggregations.metrics.ParsedMin;
import org.elasticsearch.search.aggregations.metrics.ParsedSum;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

/**
 * Elasticsearch 聚合测试
 */
public class ElasticsearchAggregationTests extends BlogSearchApplicationTests {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 分组
     */
    @Test
    public void testGroup() throws IOException {
        SearchRequest searchRequest = new SearchRequest("products");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder
                .query(QueryBuilders.matchAllQuery())
                .aggregation(AggregationBuilders.terms("price_group").field("price"))
                .size(0);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Aggregations aggregations = searchResponse.getAggregations();
        ParsedDoubleTerms parsedDoubleTerms = aggregations.get("price_group");

        List<? extends Terms.Bucket> buckets = parsedDoubleTerms.getBuckets();
        buckets.forEach(bucket -> {
            System.out.println(bucket.getKey() + ":" + bucket.getDocCount());
        });
        restHighLevelClient.close();
    }

    /**
     * 求最大值
     */
    @Test
    public void testMax() throws IOException {
        SearchRequest searchRequest = new SearchRequest("products");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder
                .query(QueryBuilders.matchAllQuery())
                .aggregation(AggregationBuilders.max("price_max").field("price"))
                .size(0);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Aggregations aggregations = searchResponse.getAggregations();
        ParsedMax parsedMax = aggregations.get("price_max");
        System.out.println(parsedMax.getValue());
        restHighLevelClient.close();
    }

    /**
     * 求最小值
     */
    @Test
    public void testMin() throws IOException {
        SearchRequest searchRequest = new SearchRequest("products");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder
                .query(QueryBuilders.matchAllQuery())
                .aggregation(AggregationBuilders.min("price_min").field("price"))
                .size(0);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Aggregations aggregations = searchResponse.getAggregations();
        ParsedMin parsedMin = aggregations.get("price_min");
        System.out.println(parsedMin.getValue());
        restHighLevelClient.close();
    }

    /**
     * 求和
     */
    @Test
    public void testSum() throws IOException {
        SearchRequest searchRequest = new SearchRequest("products");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder
                .query(QueryBuilders.matchAllQuery())
                .aggregation(AggregationBuilders.sum("price_sum").field("price"))
                .size(0);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Aggregations aggregations = searchResponse.getAggregations();
        ParsedSum parsedSum = aggregations.get("price_sum");
        System.out.println(parsedSum.getValue());
        restHighLevelClient.close();
    }

    /**
     * 求平均值
     */
    @Test
    public void testAvg() throws IOException {
        SearchRequest searchRequest = new SearchRequest("products");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder
                .query(QueryBuilders.matchAllQuery())
                .aggregation(AggregationBuilders.avg("price_avg").field("price"))
                .size(0);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        Aggregations aggregations = searchResponse.getAggregations();
        ParsedAvg parsedAvg = aggregations.get("price_avg");
        System.out.println(parsedAvg.getValue());
        restHighLevelClient.close();
    }

}
