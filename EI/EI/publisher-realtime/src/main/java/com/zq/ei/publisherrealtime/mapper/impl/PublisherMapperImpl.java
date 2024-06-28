package com.zq.ei.publisherrealtime.mapper.impl;

import com.google.gson.Gson;
import com.zq.ei.publisherrealtime.mapper.PublisherMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchCorruptionException;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Slf4j
@Repository
public class PublisherMapperImpl implements PublisherMapper {

    @Autowired
    RestHighLevelClient esClient;

    private String indexNamePrefix = "ei_dau_info";

    @Override
    public String searchDau() {
//        Map<String, Object> dauResults = new HashMap<>();
        // 构建JSON格式的结果
        Map<String, Object> jsonResult = new HashMap<>();

        // 招聘总数
        long dauTotal = searchDauTotal();
        jsonResult.put("dauTotal", dauTotal);

        //当日招聘城市名称集合
        List<String> dauCity = searchDauCity("groupbycity", "city");
//        dauResults.put("dauCity", dauCity);
        Map<String, List<String>> data = new HashMap<>();
        data.put("data", dauCity);
        jsonResult.put("yAxis", data);


        // 当日各城市招聘数量集合
        List<Long> dauCityNums = searchDauCityNums("groupbycity", "city");
//        dauResults.put("dauCityNums", dauCityNums);
        Map<String, List<Long>> seriesData = new HashMap<>();
        seriesData.put("data", dauCityNums);

        List<Map<String, List<Long>>> seriesList = new ArrayList<>();
        seriesList.add(seriesData);

        jsonResult.put("series", seriesList);


        // 构建存储公司规模数据的结果
        Map<String, Object> CompanyScaleResult = new HashMap<>();

        // 招聘公司规模聚合
        List<String> CompanyScale = searchDauCity("groupbycompanysize", "company_size");
//        dauResults.put("dauCity", dauCity);
        Map<String, List<String>> data2 = new HashMap<>();
        data2.put("data", CompanyScale);
        CompanyScaleResult.put("xAxis", data2);

        // 招聘公司规模数量聚合
        List<Long> CompanyScaleNums = searchDauCityNums("groupbycompanysize", "company_size");
//        dauResults.put("dauCityNums", dauCityNums);
        Map<String, List<Long>> seriesData2 = new HashMap<>();
        seriesData2.put("data", CompanyScaleNums);

        List<Map<String, List<Long>>> seriesList2 = new ArrayList<>();
        seriesList2.add(seriesData2);

        CompanyScaleResult.put("series", seriesList2);
        // 规模数据存储至接口返回json
        jsonResult.put("CompanyScale", CompanyScaleResult);


        // 将结果转换为JSON字符串
        String jsonString = new Gson().toJson(jsonResult);

//        Map<String, Object> dauResults = new HashMap<>();
//        //日活总数
//        dauResults.put("dauTotal", 456);
//
//        HashMap<String, Long> dauTd = new HashMap<>();
//        dauTd.put("08", 100L);
//        dauTd.put("12", 55L);
//        dauTd.put("13", 66L);
//        dauTd.put("15", 44L);
//        dauTd.put("18", 88L);
//        dauResults.put("dauTd", dauTd);
//
//        HashMap<String, Long> dauYd = new HashMap<>();
//        dauYd.put("09", 130L);
//        dauYd.put("11", 77L);
//        dauYd.put("12", 66L);
//        dauYd.put("14", 50L);
//        dauYd.put("16", 88L);
//        dauYd.put("18", 888L);
//        dauResults.put("dauYd", dauYd);

        return jsonString;
    }


    @Override
    public String searchDau3() {
        // 构建JSON格式的结果
        Map<String, Object> jsonResult = new HashMap<>();

        //招聘公司规模聚合
        List<String> dauCity = searchDauCity("groupbycompanysize", "company_size");
//        dauResults.put("dauCity", dauCity);
        Map<String, List<String>> data = new HashMap<>();
        data.put("data", dauCity);
        jsonResult.put("xAxis", data);


        // 招聘公司规模数量聚合
        List<Long> dauCityNums = searchDauCityNums("groupbycompanysize", "company_size");
//        dauResults.put("dauCityNums", dauCityNums);
        Map<String, List<Long>> seriesData = new HashMap<>();
        seriesData.put("data", dauCityNums);

        List<Map<String, List<Long>>> seriesList = new ArrayList<>();
        seriesList.add(seriesData);

        jsonResult.put("series", seriesList);

        // 将结果转换为JSON字符串
        String jsonString = new Gson().toJson(jsonResult);

        return jsonString;
    }


    // 名称聚合结果
    // 返回索引名集合
    public List<String> searchDauCity(String termsName, String fieldName){
        HashMap<String, Long> dauCity = new HashMap<>();
        // 构建城市列表
        List<String> resultList = new ArrayList<>();

        String indexName = indexNamePrefix;
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 不要明细
        searchSourceBuilder.size(0);
        //聚合
        TermsAggregationBuilder termsAggregationBuilder =
                AggregationBuilders.terms(termsName).field(fieldName).size(24);
        searchSourceBuilder.aggregation(termsAggregationBuilder);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
            Aggregations aggregations = searchResponse.getAggregations();
            ParsedTerms parsedTerms = aggregations.get(termsName);
            List<? extends Terms.Bucket> buckets = parsedTerms.getBuckets();

            for (Terms.Bucket bucket : buckets) {
                String city = bucket.getKeyAsString();
                long cityTotal = bucket.getDocCount();
                dauCity.put(city, cityTotal);
                // 城市列表
                resultList.add(city);
            }

            return resultList;

        } catch (ElasticsearchStatusException ese){
            if( ese.status() == RestStatus.NOT_FOUND ){
                log.warn( indexName + "不存在...");
            }
        }
        catch (IOException e) {
            throw new RuntimeException("es查询失败...");
        }
        return resultList;
    }


    // 数量聚合结果
    // 返回聚合的数量结果
    public List<Long> searchDauCityNums(String termsName, String fieldName){
        HashMap<String, Long> dauCity = new HashMap<>();
        // 构建城市列表
        List<Long> resultList = new ArrayList<>();

        String indexName = indexNamePrefix;
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 不要明细
        searchSourceBuilder.size(0);
        //聚合
        TermsAggregationBuilder termsAggregationBuilder =
                AggregationBuilders.terms(termsName).field(fieldName).size(24);
        searchSourceBuilder.aggregation(termsAggregationBuilder);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
            Aggregations aggregations = searchResponse.getAggregations();
            ParsedTerms parsedTerms = aggregations.get(termsName);
            List<? extends Terms.Bucket> buckets = parsedTerms.getBuckets();

            for (Terms.Bucket bucket : buckets) {
                String city = bucket.getKeyAsString();
                long cityTotal = bucket.getDocCount();
                dauCity.put(city, cityTotal);
                // 城市列表
                resultList.add(cityTotal);
            }

            return resultList;

        } catch (ElasticsearchStatusException ese){
            if( ese.status() == RestStatus.NOT_FOUND ){
                log.warn( indexName + "不存在...");
            }
        }
        catch (IOException e) {
            throw new RuntimeException("es查询失败...");
        }
        return resultList;
    }


    // 招聘总数
    public long searchDauTotal(){
        String indexName = indexNamePrefix;
        SearchRequest searchRequest = new SearchRequest(indexName);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 不要明细
        searchSourceBuilder.size(0);
        searchRequest.source(searchSourceBuilder);
        try {
            SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
            long dauTotals = searchResponse.getHits().getTotalHits().value;
            return dauTotals;
        } catch (ElasticsearchStatusException ese){
            if( ese.status() == RestStatus.NOT_FOUND ){
                log.warn( indexName + "不存在...");
            }
        }
        catch (IOException e) {
            throw new RuntimeException("es查询失败...");
        }
        return 0L;
    }
}
