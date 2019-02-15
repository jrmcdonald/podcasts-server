package com.jrmcdonald.podcasts.app.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Podcast Item Metadata JSON Entity
 * 
 * @author Jamie McDonald
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PodcastItemMetaInfo extends PodcastMetaInfo {

    private String uploadDate;
    private String description;
    private long duration;

    @JsonCreator
    public PodcastItemMetaInfo(@JsonProperty(value = "playlist_id", required = true) final String id,
                               @JsonProperty(value = "title", required = true) final String title,
                               @JsonProperty(value = "thumbnails", required = false) final List<ThumbnailMetaInfo> thumbnails,
                               @JsonProperty(value = "playlist_uploader", required = false) final String uploader, 
                               @JsonProperty(value = "upload_date", required = true) final String uploadDate,
                               @JsonProperty(value = "description", required = true) final String description,
                               @JsonProperty(value = "duration", required = true) final long duration) {
      super(id, title, thumbnails, uploader);

      this.uploadDate = uploadDate;
      this.description = description;
      this.duration = duration;
    }

    /**
     * @return the uploadDate
     */
    public String getUploadDate() {
      return uploadDate;
    }

    /**
     * @return the description
     */
    public String getDescription() {
      return description;
    }

    /**
     * @return the duration
     */
    public long getDuration() {
      return duration;
    }
}