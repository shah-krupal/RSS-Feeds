package com.example.tryrss.repository;

import com.example.tryrss.model.ItemRSS;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRSSRepo extends MongoRepository<ItemRSS, Integer> {
//    Iterable<ItemRSS> findTop10ByFeedIdOrderByCreatedDateDesc(Integer feedId);

    Iterable<ItemRSS> findTop10ByFeedId(Integer feedId);
}
