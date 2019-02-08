package com.jrmcdonald.podcasts.app.entity;

import java.util.Date;;

/**
 * Item
 */
public class Item {

    private String title;
    private String link;
    private String enclosure;
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
      return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
      this.link = link;
    }

    /**
     * @return the enclosure
     */
    public String getEnclosure() {
      return enclosure;
    }

    /**
     * @param enclosure the enclosure to set
     */
    public void setEnclosure(String enclosure) {
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

    public static class ItemBuilder {

        private String title;
        private String link;
        private String enclosure;
        private Date pubDate;
        
        public ItemBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public ItemBuilder link(final String link) {
            this.link = link;
            return this;
        }

        public ItemBuilder enclosure(final String enclosure) {
            this.enclosure = enclosure;
            return this;
        }

        public ItemBuilder pubDate(final Date pubDate) {
            this.pubDate = pubDate;
            return this;
        }

        public Item build() {
            Item item = new Item();

            item.setTitle(title);
            item.setLink(link);
            item.setEnclosure(enclosure);
            item.setPubDate(pubDate);

            return item;
        }
    }
}