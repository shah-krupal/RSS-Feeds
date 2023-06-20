package com.example.rssgenerator.services.serviceImpls;

import com.example.rssgenerator.models.RssFeed;
import com.example.rssgenerator.models.RssItem;
import com.example.rssgenerator.models.Stats;
import com.example.rssgenerator.repositories.RssFeedRepo;
import com.example.rssgenerator.services.RssFeedService;
import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class RssFeedServiceImpl implements RssFeedService {
    @Autowired
    MongoOperations mongoOperations ;
    @Autowired
    RssFeedRepo rssFeedRepo ;

    @Value("${spring.data.statName}")
    String statName ;

    @Override
    @Transactional
    public String createFeed(RssFeed rssFeed) {
        try{
            Query query = new Query();
            query.addCriteria(Criteria.where("name").is(statName));
            Update update = new Update();
            update.inc("noOfFeeds", 1);
            Stats newstats = mongoOperations.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), Stats.class);
            assert newstats != null;
            rssFeed.setFeedId(newstats.getNoOfFeeds());
            rssFeed.setLastBuildDate(new Date());
            rssFeedRepo.save(rssFeed) ;
            System.out.println("Feed Added Successfully");
            return "Feed Added Successfully";
        }catch(Exception e){
            System.out.println("Feed Not Added");
            System.out.println(e.getMessage());
            e.printStackTrace();
            return "Feed Not Added" ;
        }
    }

    @Override
    public void trying(RssItem rssItem) throws FeedException, IOException {
        Writer writer = new FileWriter("xyz.txt");
        SyndFeed feed = new SyndFeedImpl() ;
        SyndEntry entry = new SyndEntryImpl() ;
        feed.setEntries(Arrays.asList(entry));
        SyndContent description = new SyndContentImpl();
        description.setType("text/html");
        description.setValue(rssItem.getDescription());
        entry.setDescription(description);
        SyndFeedOutput syndFeedOutput= new SyndFeedOutput() ;
        syndFeedOutput.output(feed, writer);
    }
}
