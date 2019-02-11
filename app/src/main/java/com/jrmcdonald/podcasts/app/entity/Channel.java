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
public class Channel {

    private String channelId;
    private String title;
    private String link;
    private String description;
    private String image;
    private String author;

    @JsonView(View.Limited.class)
    private List<Item> items;

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
    public List<Item> getItems() {
        if (items == null) {
            items = new ArrayList<Item>();
        }

        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    /**
     * @param item the item to add
     */
    public void addItem(Item item) {
        if (items == null) {
            items = new ArrayList<Item>();
        }

        items.add(item);
    }

    public static class ChannelBuilder {

        private String channelId;
        private String title;
        private String link;
        private String description;
        private String image;
        private String author;

        public ChannelBuilder id(final String channelId) {
            this.channelId = channelId;
            return this;
        }

        public ChannelBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public ChannelBuilder link(final String link) {
            this.link = link;
            return this;
        }

        public ChannelBuilder description(final String description) {
            this.description = description;
            return this;
        }

        public ChannelBuilder image(final String image) {
            this.image = image;
            return this;
        }

        public ChannelBuilder author(final String author) {
            this.author = author;
            return this;
        }

        public Channel build() {
            Channel channel = new Channel();

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
