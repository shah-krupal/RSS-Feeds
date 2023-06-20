package com.example.rssgenerator.services.serviceImpls;

import com.example.rssgenerator.models.RssFeed;
import com.example.rssgenerator.models.RssItem;
import com.example.rssgenerator.repositories.RssFeedRepo;
import com.example.rssgenerator.repositories.RssItemRepo;
import com.example.rssgenerator.services.RssChannelService;
import com.example.rssgenerator.services.RssItemService;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RssChannelImpl implements RssChannelService {
    @Autowired
    RssFeedRepo rssFeedRepo ;
    @Autowired
    RssItemRepo rssItemRepo ;
    @Autowired
    RssItemService rssItemService ;
    @Override
    public Channel getRss(Integer feedId) {
        Channel channel = new Channel() ;
        channel.setFeedType("rss_2.0");
        RssFeed rssFeed = rssFeedRepo.findByFeedId(feedId) ;
        channel.setTitle(rssFeed.getTitle());
        channel.setGenerator(rssFeed.getGenerator());
        channel.setDescription(rssFeed.getDescription());
        channel.setUri(rssFeed.getUrl());
        Iterable<RssItem>rssItemIterable = rssItemRepo.findTop10ByFeedId(feedId) ; // returns last 10 items in feed
        List<Item>itemList = new ArrayList<>() ;

        for(RssItem items:rssItemIterable)
        {
            Item item = rssItemService.convertToItem(items) ;
            itemList.add(item) ;
        }
        System.out.println(itemList);
        channel.setItems(itemList) ;
        channel.setPubDate(new Date());
        channel.setLink("http://localhost.com");  // Not sure what should come
        return channel ;
    }
}