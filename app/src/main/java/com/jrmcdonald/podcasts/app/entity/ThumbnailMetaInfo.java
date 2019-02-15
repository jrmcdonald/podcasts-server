package com.jrmcdonald.podcasts.app.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jrmcdonald.podcasts.app.constants.PodcastConstants;

/**
 * Thumbnail JSON Entity
 * 
 * @author Jamie McDonald
 */
public class ThumbnailMetaInfo {

    private long id;
    private String url;

    @JsonCreator
    public ThumbnailMetaInfo(@JsonProperty(value = PodcastConstants.JSON_ID, required = true) final long id,
                             @JsonProperty(value = PodcastConstants.JSON_URL, required = true) final String url) {
        this.id = id;
        this.url = url;
    }

    /**
     * @return the id
     */
    public long getId() {
      return id;
    }

    /**
     * @return the url
     */
    public String getUrl() {
      return url;
    }
}