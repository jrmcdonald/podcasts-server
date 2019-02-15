package com.jrmcdonald.podcasts.app.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jrmcdonald.podcasts.app.entity.Podcast;
import com.jrmcdonald.podcasts.app.entity.Podcast.PodcastBuilder;
import com.jrmcdonald.podcasts.app.entity.PodcastItem;
import com.jrmcdonald.podcasts.app.entity.PodcastItem.ItemBuilder;
import com.jrmcdonald.podcasts.app.entity.PodcastItemMetaInfo;
import com.jrmcdonald.podcasts.app.entity.PodcastMetaInfo;
import com.jrmcdonald.podcasts.app.entity.ThumbnailMetaInfo;
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

    @Value("${podcast.file.source}")
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
        Podcast podcast = null;

        try {
            PodcastMetaInfo metaInfo = objectMapper.readValue(metaFile.toFile(), PodcastMetaInfo.class);

            PodcastBuilder builder = new PodcastBuilder();

            builder.id(metaInfo.getId())
                .title(metaInfo.getTitle())
                .link(buildPodcastUrl(metaInfo.getId()))
                .description(metaInfo.getTitle())
                .author(metaInfo.getUploader());
            
            if (metaInfo.getThumbnails() != null && !metaInfo.getThumbnails().isEmpty()) {
                ThumbnailMetaInfo thumbnail = metaInfo.getThumbnails().get(0);
                builder.image(thumbnail.getUrl());
            }

            podcast = builder.build();
        } catch (JsonMappingException e) {
            logger.error("Unable to parse {}: {}", metaFile.toString(), e);
        } catch (IOException e) {
            logger.error("Unable to build podcast from {}: {}", metaFile.toString(), e);
        }

        return podcast;
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
            PodcastItemMetaInfo metaInfo = objectMapper.readValue(metaFile.toFile(), PodcastItemMetaInfo.class);

            String resourceUrl = buildResourceUrl(metaFile.getFileName().toString(), metaInfo.getId());

            ItemBuilder builder = new ItemBuilder();

            builder.title(metaInfo.getTitle())
                    .pubDate(metaInfo.getUploadDate())
                    .link(resourceUrl)
                    .description(metaInfo.getDescription())
                    .length(metaInfo.getDuration())
                    .guid(resourceUrl);

            item = builder.build();
        } catch (JsonMappingException e) {
            logger.error("Unable to parse {}: {}", metaFile.toString(), e);
        } catch (IOException e) {
            logger.error("Unable to build item: {}", e);
        }

        return item;
    }

    /**
     * Build a URL pointing to a specific podcast.
     * 
     * @param id the podcast id
     * @return the podcast URL
     */
    private String buildPodcastUrl(String id) {
        StringBuilder sb = new StringBuilder();
        sb.append("/podcasts/");
        sb.append(id);
        sb.append("/");
        return sb.toString();
    }

    /**
     * Build a URL pointing to a specific resource on the filesystem.
     * 
     * @param fileName the name of the resource
     * @param id the podcast id that the resource belongs to
     * @return the resource URL
     */
    private String buildResourceUrl(final String fileName, final String id) {
        StringBuilder sb = new StringBuilder();
        sb.append("/files/");
        sb.append(id);
        sb.append("/");
        sb.append(fileName.replace("info.json", "mp3"));
        return sb.toString();
    }
}