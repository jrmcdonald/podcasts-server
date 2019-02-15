package com.jrmcdonald.podcasts.app.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    public PodcastMetaInfo(@JsonProperty(value = "playlist_id", required = true) final String id,
                               @JsonProperty(value = "playlist_title", required = true) final String title,
                               @JsonProperty(value = "thumbnails", required = false) final List<ThumbnailMetaInfo> thumbnails,
                               @JsonProperty(value = "playlist_uploader", required = false) final String uploader) {
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