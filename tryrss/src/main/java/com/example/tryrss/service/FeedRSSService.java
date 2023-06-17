package com.example.tryrss.service;

import com.example.tryrss.model.FeedRSS;
import com.example.tryrss.repository.FeedRSSRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedRSSService {
    @Autowired
    private FeedRSSRepo feedRSSRepo ;


    public void createFeedRSS(FeedRSS feedRSS) {
        feedRSSRepo.save(feedRSS) ;
    }
}
