package com.example.tryrss.service;

import com.example.tryrss.model.FeedRSS;
import com.example.tryrss.model.ItemRSS;
import com.example.tryrss.repository.FeedRSSRepo;
import com.example.tryrss.repository.ItemRSSRepo;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class ItemRSSService {
    @Autowired
    FeedRSSRepo feedRSSRepo ;
    @Autowired
    ItemRSSRepo itemRSSRepo ;
    public void generateItem(ItemRSS itemRSS) {
        itemRSSRepo.save(itemRSS) ;  // save to mongo for future reference
        Item item = RSStoItem(itemRSS) ;
        Channel channel = toFeed(item,itemRSS) ;
        notifyHook(channel) ;
    }

    private Item RSStoItem(ItemRSS itemRSS) {
        Item item = new Item() ;   // ROME RSS item
        item.setAuthor(itemRSS.getCreator());
        item.setTitle(itemRSS.getTitle());
        Description description = new Description();
        description.setType("text/plain");
        description.setValue(itemRSS.getDescription());
        item.setDescription(description);
//        item.setPubDate(itemRSS.getPubDate());
        return item ;
    }

    private Channel toFeed(Item item, ItemRSS itemRSS) {
        Channel channel = new Channel() ;
        channel.setFeedType("rss_2.0");
        channel.setTitle("");
        FeedRSS feedRSS = feedRSSRepo.findByFeedId(itemRSS.getFeedId()) ;
        channel.setGenerator(feedRSS.getGenerator());
        channel.setDescription(itemRSS.getDescription());
        channel.setUri(feedRSS.getUrl());
        channel.setItems(Collections.singletonList(item));
        channel.setPubDate(new Date());
        channel.setLink("http://localhost.com");
        return channel ;
    }

    private void notifyHook(Channel channel) {
        System.out.println("Notifying");
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_RSS_XML);

// Create a new RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

// Add the necessary message converters
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

// Build the request entity
        HttpEntity<Channel> requestEntity = new HttpEntity<>(channel, requestHeaders);

// Make the HTTP POST request, marshaling the request to JSON, and the response to a String
        URI url = URI.create("http://localhost:8081/data");
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        String result = responseEntity.getBody();
    }


    public Channel getRSS(Integer feedId) {
        Channel channel = new Channel() ;
        channel.setFeedType("rss_2.0");
        channel.setTitle("");
        Iterable<ItemRSS>itemRSSIterable = itemRSSRepo.findTop10ByFeedId(feedId) ;
        List<Item> itemList = new ArrayList<>() ;
        for(ItemRSS tmpitem : itemRSSIterable)
        {
            Item item = RSStoItem(tmpitem) ;
            itemList.add(item) ;
        }
        FeedRSS feedRSS = feedRSSRepo.findByFeedId(feedId) ;
        channel.setGenerator(feedRSS.getGenerator());
        channel.setDescription("Feed Description");
        channel.setUri(feedRSS.getUrl());
        channel.setItems(itemList);
        channel.setPubDate(new Date());
        channel.setLink("http://localhost.com");
        return channel ;
    }
}
