package com.jrmcdonald.podcasts.app.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jrmcdonald.podcasts.app.constants.PodcastConstants;

/**
 * Podcast Metadata JSON Entity
 * 
 * @author Jamie McDonald
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PodcastMetaInfo {

    private String id;
    private String title;
    private List<ThumbnailMetaInfo> thumbnails;
    private String uploader;

    @JsonCreator
    public PodcastMetaInfo(@JsonProperty(value = PodcastConstants.JSON_PLAYLIST_ID, required = true) final String id,
                           @JsonProperty(value = PodcastConstants.JSON_PLAYLIST_TITLE, required = true) final String title,
                           @JsonProperty(value = PodcastConstants.JSON_THUMBNAILS, required = false) final List<ThumbnailMetaInfo> thumbnails,
                           @JsonProperty(value = PodcastConstants.JSON_PLAYLIST_UPLOADER, required = false) final String uploader) {
        this.id = id;
        this.title = title;
        this.thumbnails = thumbnails;
        this.uploader = uploader;
    }

    /**
     * @return the id
     */
    public String getId() {
      return id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
      return title;
    }

    /**
     * @return the thumbnails
     */
    public List<ThumbnailMetaInfo> getThumbnails() {
      return thumbnails;
    }

    /**
     * @return the uploader
     */
    public String getUploader() {
      return uploader;
    }
}