package com.jrmcdonald.podcasts.app.rss;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.jrmcdonald.podcasts.app.constants.PodcastConstants;
import com.jrmcdonald.podcasts.app.entity.Podcast;
import com.jrmcdonald.podcasts.app.entity.PodcastItem;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Content;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Enclosure;
import com.rometools.rome.feed.rss.Guid;
import com.rometools.rome.feed.rss.Image;
import com.rometools.rome.feed.rss.Item;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.view.feed.AbstractRssFeedView;

/**
 * Implementation of {@link AbstractRssFeedView} to present an RSS feed of Podcasts.
 * 
 * @author Jamie McDonald
 */
public class PodcastFeedView extends AbstractRssFeedView {

    private Podcast podcast;

    public PodcastFeedView(final Podcast podcast) {
        super();
        this.podcast = podcast;
    }

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Channel feed,
            HttpServletRequest request) {
        feed.setTitle(podcast.getTitle());
        feed.setDescription(podcast.getDescription());
        feed.setLink(podcast.getLink());
        
        if (podcast.getImage() != null) {
            feed.setImage(createImage(podcast));
        }
    }

    @Override
    protected List<Item> buildFeedItems(Map<String, Object> model, HttpServletRequest request,
            HttpServletResponse response) {
        return podcast.getItems()
                .stream()
                .map(this::createItem)
                .collect(Collectors.toList());
    }

    /**
     * Create the /rss/channel/image element from a Podcast.
     * 
     * @param podcast the {@link Podcast} to get data from
     * @return the {@link Image}
     */
    private Image createImage(Podcast podcast) {
        Image image = new Image();
        image.setTitle(podcast.getTitle());
        image.setUrl(podcast.getImage());
        image.setLink(podcast.getLink());
        return image;
    }

    /**
     * Create the /item/ element from a PodcastItem.
     * 
     * @param podcastItem the {@link PodcastItem} to get data from
     * @return the {@link Item}
     */
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

    /**
     * Create the /item/description element from a PodcastItem.
     * 
     * @param podcastItem the {@link PodcastItem} to get data from
     * @return the {@link Description}
     */
    private Description createDescription(PodcastItem podcastItem) {
        Description description = new Description();
        description.setType(Content.HTML);

        if (StringUtils.isNotEmpty(podcastItem.getDescription())) {
            description.setValue(podcastItem.getDescription());
        } else {
            description.setValue(PodcastConstants.NO_DESCRIPTION);
        }

        return description;
    }
    
    /**
     * Create the /item/guid element from a PodcastItem.
     * 
     * @param podcastItem the {@link PodcastItem} to get data from
     * @return the {@link Guid}
     */
    private Guid createGuid(PodcastItem podcastItem) {
        Guid guid = new Guid();
        guid.setValue(podcastItem.getGuid());
        return guid;
    }

    /**
     * Create the /item/enclosure list from a PodcastItem.
     * 
     * @param podcastItem the {@link PodcastItem} to get data from
     * @return the list of {@link Enclosure}
     */
    private List<Enclosure> createEnclosures(PodcastItem podcastItem) {
        Enclosure enclosure = new Enclosure();
        enclosure.setUrl(podcastItem.getLink());
        enclosure.setLength(podcastItem.getLength());
        enclosure.setType(PodcastConstants.MIME_TYPE_MPEG);
        return new ArrayList<Enclosure>(Arrays.asList(enclosure));
    }
}
