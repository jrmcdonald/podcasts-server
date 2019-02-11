package com.jrmcdonald.podcasts.app.entity;

/**
 * Enclosure
 */
public class Enclosure {

    private String url;
    private long length;
    private String type;

    public Enclosure(final String url, final long length) {
        this.url = url;
        this.length = length;
        this.type = "audio";
    }

    public Enclosure(final String url, final long length, final String type) {
        this.url = url;
        this.length = length;
        this.type = type;
    }

    /**
     * @return the url
     */
    public String getUrl() {
      return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
      this.url = url;
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
     * @return the type
     */
    public String getType() {
      return type;
    }
    
    /**
     * @param type the type to set
     */
    public void setType(String type) {
      this.type = type;
    }
}