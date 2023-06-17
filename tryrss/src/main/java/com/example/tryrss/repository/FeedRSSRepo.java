package com.example.tryrss.repository;

import com.example.tryrss.model.FeedRSS;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface FeedRSSRepo extends MongoRepository<FeedRSS, Integer> {

    FeedRSS findByFeedId(int feedId);
}
