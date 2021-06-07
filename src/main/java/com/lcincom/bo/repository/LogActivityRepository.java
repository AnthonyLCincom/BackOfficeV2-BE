package com.lcincom.bo.repository;

import com.lcincom.bo.model.LogActivity;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogActivityRepository extends ElasticsearchRepository<LogActivity, String> {

    Page<LogActivity> search(SearchQuery nativeSearchQueryBuilder);

}