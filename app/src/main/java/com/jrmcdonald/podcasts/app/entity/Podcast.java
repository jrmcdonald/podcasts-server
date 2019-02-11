package com.jrmcdonald.podcasts.app.entity;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;
import com.jrmcdonald.podcasts.app.util.UrlHelper;
import com.jrmcdonald.podcasts.app.util.View;

/**
 * Channel
 */
@JsonView(View.Full.class)
public class Podcast {

    private String channelId;
    private String title;
    private String link;
    private String description;
    private String image;
    private String author;

    @JsonView(View.Limited.class)
    private List<PodcastItem> items;

    /**
     * @return the channelId
     */
    public String getChannelId() {
        return channelId;
    }

    /**
     * @param channelId the channelId to set
     */
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return UrlHelper.prefixWithBaseUrl(link);
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the items
     */
    public List<PodcastItem> getItems() {
        if (items == null) {
            items = new ArrayList<PodcastItem>();
        }

        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<PodcastItem> items) {
        this.items = items;
    }

    /**
     * @param item the item to add
     */
    public void addItem(PodcastItem item) {
        if (items == null) {
            items = new ArrayList<PodcastItem>();
        }

        items.add(item);
    }

    public static class PodcastBuilder {

        private String channelId;
        private String title;
        private String link;
        private String description;
        private String image;
        private String author;

        public PodcastBuilder id(final String channelId) {
            this.channelId = channelId;
            return this;
        }

        public PodcastBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public PodcastBuilder link(final String link) {
            this.link = link;
            return this;
        }

        public PodcastBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public PodcastBuilder image(final String image) {
            this.image = image;
            return this;
        }

        public PodcastBuilder author(final String author) {
            this.author = author;
            return this;
        }

        public Podcast build() {
            Podcast channel = new Podcast();

            channel.setChannelId(channelId);
            channel.setTitle(title);
            channel.setLink(link);
            channel.setDescription(description);
            channel.setImage(image);
            channel.setAuthor(author);

            return channel;
        }
    }
}
