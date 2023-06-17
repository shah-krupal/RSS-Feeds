package com.example.tryrss.controller;

import com.example.tryrss.model.ItemRSS;
import com.example.tryrss.service.ItemRSSService;
import com.rometools.rome.feed.rss.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ItemRSSController {
    @Autowired
    ItemRSSService itemRSSService ;
    @PostMapping("/generate")
    public void generateItem(@RequestBody ItemRSS itemRSS)
    {
        itemRSSService.generateItem(itemRSS) ;
    }
    @GetMapping("/rss/{feedId}")
    public Channel getRSS(@PathVariable Integer feedId){
        return itemRSSService.getRSS(feedId) ;
    }
}
