package com.jrmcdonald.podcasts.app.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrmcdonald.podcasts.app.entity.Podcast;
import com.jrmcdonald.podcasts.app.entity.Podcast.PodcastBuilder;
import com.jrmcdonald.podcasts.app.entity.PodcastItem;
import com.jrmcdonald.podcasts.app.entity.PodcastItem.ItemBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service class to build Podcasts and PodcastItems from metadata files.
 * 
 * @author Jamie McDonald
 */
@Service
public class PodcastService {

    Logger logger = LoggerFactory.getLogger(PodcastService.class);

    @Value("${app.file.source}")
    private String fileSource;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Build a list of Podcast entities from the filesystem.
     * 
     * @return the list of {@link Podcast} entities
     */
    public List<Podcast> getPodcasts() {
        List<Podcast> podcasts = new ArrayList<Podcast>();

        try {
            Files.list(Paths.get(fileSource))
                .filter(Files::isDirectory)
                .map(this::parsePodcastDir)
                .forEachOrdered(podcasts::add);
        } catch (IOException e) {
            logger.error("Unable to read source directory: {}", e);
        }

        return podcasts;
    }

    /**
     * Build a single Podcast entity.
     * 
     * @param podcastId the Podcast to build.
     * @return the {@link Podcast}
     */
    public Podcast getPodcast(String podcastId) {
       Path path = Paths.get(fileSource + '/' + podcastId); 

       return parsePodcastDir(path);
    }

    /**
     * Parse the meta files for a specified Podcast.
     * 
     * @param podcastDir the audio directory to parse
     * @return the {@link Podcast}
     */
    private Podcast parsePodcastDir(Path podcastDir) {
        Podcast podcast = null;

        try {
            List<Path> metaFiles = Files.list(podcastDir)
                    .sorted()
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString()
                    .endsWith(".json"))
                    .collect(Collectors.toList());
            
            if (metaFiles.size() > 0) {
                podcast = buildPodcastFromFirstMetaFile(metaFiles.get(0));

                metaFiles.stream()
                        .filter(Files::isRegularFile)
                        .filter(p -> p.toString().endsWith(".json"))
                        .map(this::buildItemFromMetaFile)
                        .forEachOrdered(podcast::addItem);
            }
        } catch (IOException e) {
            logger.debug("An exception occurred reading the filesystem: {}", e);
        }

        return podcast;
    }

    /**
     * Parse the first meta data file in the directory to build 
     * the Podcast level details.
     * 
     * @param metaFile the json meta file to parse.
     * @return the {@link Podcast}
     */
    private Podcast buildPodcastFromFirstMetaFile(Path metaFile) {
        Podcast channel = null;

        try {
            JsonNode rootNode = objectMapper.readTree(metaFile.toFile());

            String title = rootNode.get("playlist_title").asText();
            String channelId = rootNode.get("playlist_id").asText();

            StringBuilder sb = new StringBuilder();
            sb.append("/podcasts/");
            sb.append(channelId);
            sb.append("/");

            String link = sb.toString();
            String image = rootNode.get("thumbnails").get(0).get("url").asText();
            String author = rootNode.get("playlist_uploader").asText();

            PodcastBuilder builder = new PodcastBuilder();

            channel = builder.id(channelId)
                    .title(title)
                    .link(link)
                    .description(title)
                    .image(image)
                    .author(author)
                    .build();
        } catch (IOException e) {
            logger.error("Unable to build channel: {}", e);
        }

        return channel;
    }
    
    /**
     * Build a single PodcastItem from a json meta data file.
     * 
     * @param metaFile the json meta data file to parse
     * @return the {@link PodcastItem}
     */
    private PodcastItem buildItemFromMetaFile(Path metaFile) {
        PodcastItem item = null;

        try {
            JsonNode rootNode = objectMapper.readTree(metaFile.toFile());

            String title = rootNode.get("title").asText();
            String pubDate = rootNode.get("upload_date").asText();
            String description = rootNode.get("description").asText();
            
            String fileName = metaFile.getFileName().toString().replace("info.json", "mp3");
            String resourcePath = "/files/" + rootNode.get("playlist_id").asText();
            String url = resourcePath + "/" + fileName;

            long length = rootNode.get("duration").asLong();

            ItemBuilder builder = new ItemBuilder();

            item = builder.title(title)
                    .pubDate(pubDate)
                    .link(url)
                    .description(description)
                    .length(length)
                    .guid(url)
                    .build();
        } catch (IOException e) {
            logger.error("Unable to build item: {}", e);
        }

        return item;
    }
}