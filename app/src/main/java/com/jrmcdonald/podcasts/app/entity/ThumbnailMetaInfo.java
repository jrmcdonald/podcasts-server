package com.jrmcdonald.podcasts.app.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Thumbnail JSON Entity
 * 
 * @author Jamie McDonald
 */
public class ThumbnailMetaInfo {

    private long id;
    private String url;

    @JsonCreator
    public ThumbnailMetaInfo(@JsonProperty(value = "id", required = true) final long id,
                             @JsonProperty(value = "url", required = true) final String url) {
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