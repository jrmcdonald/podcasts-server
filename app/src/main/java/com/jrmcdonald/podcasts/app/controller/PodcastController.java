package com.jrmcdonald.podcasts.app.controller;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;
import com.jrmcdonald.podcasts.app.entity.Podcast;
import com.jrmcdonald.podcasts.app.rss.PodcastFeedView;
import com.jrmcdonald.podcasts.app.service.PodcastService;
import com.jrmcdonald.podcasts.app.util.Views;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;


/**
 * Podcast controller.
 * 
 * @author Jamie McDonald
 */
@Controller
public class PodcastController {

    @Autowired
    private PodcastService service;

    /**
     * Fetch a list of all available podcasts. 
     * 
     * <p>Filters the Items from the returned podcasts.
     * 
     * @return a list of {@link Podcast} objects.
     */
    @JsonView(Views.Full.class)
    @GetMapping(value = "/podcasts", produces = "application/json")
    public @ResponseBody List<Podcast> getPodcasts() {
        return service.getPodcasts();
    }
     
    /**
     * Fetch a specific podcast with items.
     * 
     * @param podcastId the podcast to fetch
     * @return a {@link PodcastFeedView} rss feed
     */
    @GetMapping(value = "/podcasts/{podcastId}", produces = "application/rss+xml")
    public @ResponseBody View getPodcast(@PathVariable("podcastId") String podcastId) {
        return new PodcastFeedView(service.getPodcast(podcastId));
    }
    
    
}