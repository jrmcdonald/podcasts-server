package com.jrmcdonald.podcasts.app.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jrmcdonald.podcasts.app.util.UrlHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Item
 */
public class Item {

    private static final Logger logger = LoggerFactory.getLogger(Item.class);

    private String title;
    private String link;
    private String description;
    private Enclosure enclosure;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date pubDate;

    private String guid;

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
     * @return the enclosure
     */
    public Enclosure getEnclosure() {
        return enclosure;
    }

    /**
     * @param enclosure the enclosure to set
     */
    public void setEnclosure(Enclosure enclosure) {
        this.enclosure = enclosure;
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
        private Enclosure enclosure;
        private Date pubDate;
        private String guid;

        public ItemBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public ItemBuilder link(final String link) {
            this.link = link;
            return this;
        }

        public ItemBuilder description(final String description) {
            this.description = description;
            return this;            
        }

        public ItemBuilder enclosure(final Enclosure enclosure) {
            this.enclosure = enclosure;
            return this;
        }

        public ItemBuilder pubDate(final String pubDate) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            try {
                this.pubDate = sdf.parse(pubDate);
            } catch (ParseException e) {
                logger.error("Unable to parse Item date: {}", pubDate);
            }

            return this;
        }

        public ItemBuilder pubDate(final Date pubDate) {
            this.pubDate = pubDate;
            return this;
        }

        public ItemBuilder guid(final String guid) {
            this.guid = guid;
            return this;
        }

        public Item build() {
            Item item = new Item();

            item.setTitle(title);
            item.setLink(link);
            item.setDescription(description);
            item.setEnclosure(enclosure);
            item.setPubDate(pubDate);
            item.setGuid(guid);

            return item;
        }
    }
}