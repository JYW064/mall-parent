package com.jyw.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jyw.portal.domain.Good;
import com.jyw.portal.dto.GoodContentDTO;
import com.jyw.portal.dto.GoodDTO;
import com.jyw.portal.mapper.GoodMapper;
import com.jyw.portal.service.SearchService;
import com.jyw.portal.util.DTOUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @program: mall-parent
 * @description:
 * @author: ji yi wei
 * @create: 2022-04-21
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    RestHighLevelClient restHighLevelClient;

    @Autowired
    GoodMapper goodMapper;

    @Override
    public void addDocument() throws IOException {
        CreateIndexRequest indexRequest = new CreateIndexRequest("good");
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {

                builder.startObject("productId");
                {
                    builder.field("type", "long");
                }
                builder.endObject();

                builder.startObject("productName");
                {
                    builder.field("type", "text")
                            //插入时分词
                            .field("analyzer", "ik_max_word")
                            //搜索时分词
                            .field("search_analyzer", "ik_smart");
                }
                builder.endObject();

                builder.startObject("image");
                {
                    builder.field("type", "text");
                }
                builder.endObject();

                builder.startObject("price");
                {
                    builder.field("type", "float");
                }
                builder.endObject();

                builder.startObject("subTitle");
                {
                    builder.field("type", "text")
                            //插入时分词
                            .field("analyzer", "ik_max_word")
                            //搜索时分词
                            .field("search_analyzer", "ik_smart");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        indexRequest.mapping("_doc", builder);
        restHighLevelClient.indices().create(indexRequest, RequestOptions.DEFAULT);

        List<Good> goodList = goodMapper.selectAll(-1, -1);
        //创建请求
        BulkRequest request = new BulkRequest();
        //将数据加入请求
        for (Good good : goodList) {
            request.add(
                    new IndexRequest("good")
                            .id("" + good.getId())
                            .source(JSON.toJSONString(DTOUtil.goodTOGoodContentDTO(good)), XContentType.JSON));
        }
        restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
    }

    @Override
    public GoodDTO SearchDocument(String key, int currentPage, int pageSize, int priceMax, int priceMin, String sort,int quick) throws IOException {

        if (pageSize == 0) {
            pageSize = 1;
        }
        int cur = (currentPage - 1) * pageSize;
        SearchRequest searchRequest = new SearchRequest("good");
        //设置查询方法
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("productName", key);

        //设置搜索高亮
        if (quick != 1) {
            HighlightBuilder highlightBuilder = new HighlightBuilder()
                    .field("productName")
                    .preTags("<a style=\"color: #e4393c\">")
                    .postTags("</a>")
                    .requireFieldMatch(false);
            searchSourceBuilder.highlighter(highlightBuilder);
        }

        searchSourceBuilder.from(cur)
                .size(pageSize)
                .query(matchQueryBuilder);

        if (priceMin >= 0 && priceMax >= 0 && sort.equals("0")) {
            searchSourceBuilder.postFilter(QueryBuilders.rangeQuery("price").gt(priceMin).lt(priceMax));
        } else if (priceMin >= 0 && priceMax >= 0 && sort.equals("1")) {
            searchSourceBuilder.postFilter(QueryBuilders.rangeQuery("price").gt(priceMin).lt(priceMax))
                    .sort("price", SortOrder.ASC);
        } else if (priceMin >= 0 && priceMax >= 0 && sort.equals("-1")) {
            searchSourceBuilder.postFilter(QueryBuilders.rangeQuery("price").gt(priceMin).lt(priceMax))
                    .sort("price", SortOrder.DESC);
        } else if ((priceMin < 0 || priceMax < 0) && sort.equals("1")) {
            searchSourceBuilder.sort("price", SortOrder.ASC);
        } else if ((priceMin < 0 || priceMax < 0) && sort.equals("-1")) {
            searchSourceBuilder.sort("price", SortOrder.DESC);
        }

        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);


        //解析查询结果
        List<GoodContentDTO> goodContentDTOList = new ArrayList<>();
        int total = 0;
        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            HighlightField productName = searchHit.getHighlightFields().get("productName");
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            //解析高亮字符串，将原来的字符串替换成高亮字符串
            if(quick != 1){
                sourceAsMap.put("productName", productName.getFragments()[0].toString());
            }
            GoodContentDTO goodContentDTO = JSONObject.parseObject(JSON.toJSONString(sourceAsMap), GoodContentDTO.class);
            goodContentDTOList.add(goodContentDTO);
            total++;
        }
        GoodDTO goodDTO = new GoodDTO();
        goodDTO.setGoodContentDTOList(goodContentDTOList);
        goodDTO.setTotal(total);
        return goodDTO;
    }
}
