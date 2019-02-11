package com.jrmcdonald.podcasts.app.controller;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;
import com.jrmcdonald.podcasts.app.entity.Channel;
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
    @GetMapping(value="/channels")
    public @ResponseBody List<Channel> getChannels() {
        return service.getChannels();
    }
     

    @GetMapping(value="/channels/{channelId}")
    public @ResponseBody Channel getChannel(@PathVariable("channelId") String channelId) {
        return service.getChannel(channelId);
    }
    
    
}