package com.jrmcdonald.podcasts.app.rss;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.jrmcdonald.podcasts.app.entity.Podcast;
import com.jrmcdonald.podcasts.app.entity.PodcastItem;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Enclosure;
import com.rometools.rome.feed.rss.Guid;
import com.rometools.rome.feed.rss.Item;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

/**
 * ChannelFeedView
 */
public class ChannelFeedView extends AbstractRssFeedView {

    private Podcast podcastChannel;

    public ChannelFeedView(final Podcast podcast) {
        super();
        this.podcastChannel = podcast;
    }

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed,
            HttpServletRequest request) {
        feed.setTitle(podcastChannel.getTitle());
        feed.setDescription(podcastChannel.getDescription());
        feed.setLink(podcastChannel.getLink());
    }

    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) {
        return podcastChannel.getItems()
                .stream()
                .map(this::createItem)
                .collect(Collectors.toList());
    }

    private Item createItem(PodcastItem podcastItem) {
        Item item = new Item();

        item.setTitle(podcastItem.getTitle());
        item.setLink(podcastItem.getLink());
        item.setDescription(createDescription(podcastItem));
        item.setPubDate(podcastItem.getPubDate());
        item.setGuid(createGuid(podcastItem));
        item.setEnclosures(createEnclosures(podcastItem));

        return item;
    }

    private Description createDescription(PodcastItem podcastItem) {
        Description description = new Description();
        description.setType(Content.HTML);
        description.setValue(podcastItem.getDescription());
        return description;
    }
    
    private Guid createGuid(PodcastItem podcastItem) {
        Guid guid = new Guid();
        guid.setValue(podcastItem.getGuid());
        return guid;
    }

    private List<Enclosure> createEnclosures(PodcastItem podcastItem) {
        Enclosure enclosure = new Enclosure();
        enclosure.setUrl(podcastItem.getLink());
        enclosure.setLength(podcastItem.getLength());
        enclosure.setType("audio/mpeg");
        return new ArrayList<Enclosure>(Arrays.asList(enclosure));
    }
}
