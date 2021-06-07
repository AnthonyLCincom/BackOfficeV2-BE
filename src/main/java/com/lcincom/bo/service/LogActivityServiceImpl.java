package com.lcincom.bo.service;

import com.lcincom.bo.DTO.LogFilter;
import com.lcincom.bo.enums.LogType;
import com.lcincom.bo.model.LogActivity;
import com.lcincom.bo.repository.LogActivityRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class LogActivityServiceImpl implements LogActivityService {

    @Autowired
    LogActivityRepository logActivityRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Override
    public Map<String, Object> filter(LogFilter log) {

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        if(!ObjectUtils.isEmpty(log.getKeyword())){
            boolQueryBuilder.must(QueryBuilders.multiMatchQuery(log.getKeyword(), new String[]{ "newData", "oldData" }));
        }

        if(!ObjectUtils.isEmpty(log.getUserName())){
            boolQueryBuilder.must(QueryBuilders.termQuery("userName", log.getUserName()));
        }

        if(!ObjectUtils.isEmpty(log.getType())){
            boolQueryBuilder.must(QueryBuilders.termQuery("type", LogType.valueOf(log.getType())));
        }

        if(log.getFromDate() != null && log.getToDate() != null) {
            // search from 0h fromDate to 24h toDate
            Calendar cal = Calendar.getInstance();
            cal.setTime(log.getToDate());
            cal.add(Calendar.DATE, 1);
            Date toDate = cal.getTime();
            boolQueryBuilder.must(QueryBuilders.rangeQuery("createdTime").gte(log.getFromDate()).lte(toDate));
        }

        FieldSortBuilder sort = SortBuilders.fieldSort(log.getSortByCols()).order(SortOrder.valueOf(log.getOrderBy()));
        PageRequest page = PageRequest.of(log.getPageNo() - 1, log.getRecordsPerPage());

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("log_activity")
                .withQuery(boolQueryBuilder)
                .withSort(sort)
                .withPageable(page)
                .build();

        Page<LogActivity> logActivities = elasticsearchTemplate.queryForPage(searchQuery, LogActivity.class);

        Map<String, Object> result = new HashMap<>();
        result.put("content", logActivities.getContent());
        result.put("totalElements", logActivities.getTotalElements());
        result.put("totalPages", logActivities.getTotalPages());
        return result;
    }
}
