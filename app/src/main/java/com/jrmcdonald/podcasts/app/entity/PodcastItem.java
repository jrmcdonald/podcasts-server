package com.jrmcdonald.podcasts.app.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jrmcdonald.podcasts.app.constants.PodcastConstants;
import com.jrmcdonald.podcasts.app.util.UrlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Podcast Item entity.
 * 
 * @author Jamie McDonald
 */
public class PodcastItem {

    private static final Logger logger = LoggerFactory.getLogger(PodcastItem.class);

    private String title;
    private String link;
    private String description;
    private long length;
    private String guid;

    @JsonFormat(pattern = PodcastConstants.OUTPUT_DATE_FORMAT)
    private Date pubDate;

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
     * @return the length
     */
    public long getLength() {
      return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(long length) {
      this.length = length;
    }

    /**
     * @return the pubDate
     */
    public Date getPubDate() {
        return pubDate;
    }

    /**
     * @param pubDate the pubDate to set
     */
    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    /**
     * @return the guid
     */
    public String getGuid() {
        return UrlHelper.prefixWithBaseUrl(guid);
    }

    /**
     * @param guid the guid to set
     */
    public void setGuid(String guid) {
      this.guid = guid;
    }

    public static class ItemBuilder {

        private String title;
        private String link;
        private String description;
        private long length;
        private Date pubDate;
        private String guid;

        public ItemBuilder withTitle(final String title) {
            this.title = title;
            return this;
        }

        public ItemBuilder withLink(final String link) {
            this.link = link;
            return this;
        }

        public ItemBuilder withDescription(final String description) {
            this.description = description;
            return this;            
        }

        public ItemBuilder withLength(final long length) {
            this.length = length;
            return this;
        }

        public ItemBuilder withPubDate(final String pubDate) {
            SimpleDateFormat sdf = new SimpleDateFormat(PodcastConstants.INPUT_DATE_FORMAT);

            try {
                this.pubDate = sdf.parse(pubDate);
            } catch (ParseException e) {
                logger.error("Unable to parse Item date: {}", pubDate);
            }

            return this;
        }

        public ItemBuilder withPubDate(final Date pubDate) {
            this.pubDate = pubDate;
            return this;
        }

        public ItemBuilder withGuid(final String guid) {
            this.guid = guid;
            return this;
        }

        public PodcastItem build() {
            PodcastItem item = new PodcastItem();

            item.setTitle(title);
            item.setLink(link);
            item.setDescription(description);
            item.setLength(length);
            item.setPubDate(pubDate);
            item.setGuid(guid);

            return item;
        }
    }
}