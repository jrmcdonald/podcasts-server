package com.jrmcdonald.podcasts.app.controller;

import java.util.List;
import com.jrmcdonald.podcasts.app.entity.Channel;
import com.jrmcdonald.podcasts.app.service.ChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * FeedController
 */
@Controller
public class ChannelController {

    @Autowired
    private ChannelService channelService;
    
    @GetMapping(value="/channels")
    public @ResponseBody List<Channel> getChannels() {
        return channelService.getChannels();
    }
     

    // @GetMapping(value="/channels/{channel}")
    // public Channel getFeed(@PathVariable("channel") String channel) {


    // }
    
    
}