package com.jrmcdonald.podcasts.app.entity;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jrmcdonald.podcasts.app.constants.PodcastConstants;

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
  public PodcastItemMetaInfo(@JsonProperty(value = PodcastConstants.JSON_PLAYLIST_ID, required = true) final String id,
                             @JsonProperty(value = PodcastConstants.JSON_TITLE, required = true) final String title,
                             @JsonProperty(value = PodcastConstants.JSON_THUMBNAILS, required = false) final List<ThumbnailMetaInfo> thumbnails,
                             @JsonProperty(value = PodcastConstants.JSON_PLAYLIST_UPLOADER, required = false) final String uploader,
                             @JsonProperty(value = PodcastConstants.JSON_UPLOAD_DATE, required = true) final String uploadDate,
                             @JsonProperty(value = PodcastConstants.JSON_DESCRIPTION, required = true) final String description,
                             @JsonProperty(value = PodcastConstants.JSON_DURATION, required = true) final long duration) {
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