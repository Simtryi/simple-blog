package com.simple.blog.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Elasticsearch 操作对象测试
 */
public class ElasticsearchObjectTests extends BlogAdminApplicationTests {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 索引
     */
    @Test
    public void testIndex() throws IOException {
        Product product = new Product();
        product.setId(1);
        product.setTitle("苹果");
        product.setPrice(5.0);
        product.setDescription("苹果非常好吃！");

        IndexRequest indexRequest = new IndexRequest("products");
        indexRequest.id(product.getId().toString())
                .source(new ObjectMapper().writeValueAsString(product), XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(indexResponse.status());
        restHighLevelClient.close();
    }

    /**
     * 搜索
     */
    @Test
    public void testSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest("products");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("description");

        searchSourceBuilder.query(QueryBuilders.termQuery("description", "非常"))
                        .highlighter(highlightBuilder);

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        List<Product> productList = new ArrayList<>();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            Product product = new ObjectMapper().readValue(hit.getSourceAsString(), Product.class);
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            if (highlightFields.containsKey("description")) {
                product.setDescription(highlightFields.get("description").fragments()[0].toString());
            }
            productList.add(product);
        }
        productList.forEach(System.out::println);
        restHighLevelClient.close();
    }

}

class Product {

    private Integer id;
    private String title;
    private Double price;
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }

}
