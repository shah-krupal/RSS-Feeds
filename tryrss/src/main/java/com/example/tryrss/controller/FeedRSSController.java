package com.example.tryrss.controller;
import com.example.tryrss.model.FeedRSS;
import com.example.tryrss.service.FeedRSSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeedRSSController {
    @Autowired
    FeedRSSService feedRSSService ;
    @PostMapping("/feed")
    public void createFeedRSS(@RequestBody FeedRSS feedRSS){
        feedRSSService.createFeedRSS(feedRSS) ;
    }
}
