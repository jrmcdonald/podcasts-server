package com.jrmcdonald.podcasts.app.controller;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;
import com.jrmcdonald.podcasts.app.entity.Podcast;
import com.jrmcdonald.podcasts.app.rss.ChannelFeedView;
import com.jrmcdonald.podcasts.app.service.ChannelService;
import com.jrmcdonald.podcasts.app.util.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * FeedController
 */
@Controller
public class ChannelController {

    @Autowired
    private ChannelService service;

    @JsonView(View.Full.class)
    @GetMapping(value = "/channels", produces = "application/json")
    public @ResponseBody List<Podcast> getChannels() {
        return service.getChannels();
    }
     

    @GetMapping(value = "/channels/{channelId}", produces = "application/rss+xml")
    public @ResponseBody org.springframework.web.servlet.View getChannel(@PathVariable("channelId") String channelId) {
        ChannelFeedView view = new ChannelFeedView(service.getChannel(channelId));
        
        return view;
    }
    
    
}