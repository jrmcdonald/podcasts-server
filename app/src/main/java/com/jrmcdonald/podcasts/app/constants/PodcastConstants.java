package com.jrmcdonald.podcasts.app.constants;

/**
 * PodcastConstants
 */
public class PodcastConstants {

    /**
     * Propoerties
     */
    public static final String PODCAST_FILE_SOURCE = "${podcast.file.source}";

    /**
     * Formats
     */
    public static final String INPUT_DATE_FORMAT = "yyyyMMdd";
    public static final String OUTPUT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Mime Types
     */
    public static final String MIME_TYPE_MPEG = "audio/mpeg";

    /**
     * JSON Fields
     */
    public static final String JSON_PLAYLIST_ID = "playlist_id";
    public static final String JSON_PLAYLIST_TITLE = "playlist_title";
    public static final String JSON_PLAYLIST_UPLOADER = "playlist_uploader";
    public static final String JSON_TITLE = "title";
    public static final String JSON_DURATION = "duration";
    public static final String JSON_DESCRIPTION = "description";
    public static final String JSON_UPLOAD_DATE = "upload_date";
    public static final String JSON_THUMBNAILS = "thumbnails";
    public static final String JSON_ID = "id";
    public static final String JSON_URL = "url";

    /**
     * File Handling
     */
    public static final String PATH_SEP = "/";
    public static final String FILES_BASE = "/files/";
    public static final String PODCASTS_BASE = "/podcasts/";
    public static final String EXT_JSON = ".json";
    public static final String EXT_MP3 = ".mp3";
    public static final String EXT_INFO_JSON = ".info.json";

    /**
     * Defaults
     */
    public static final String NO_DESCRIPTION = "No description provided.";
}
