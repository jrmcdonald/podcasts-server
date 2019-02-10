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
import com.jrmcdonald.podcasts.app.entity.Channel;
import com.jrmcdonald.podcasts.app.entity.Channel.ChannelBuilder;
import com.jrmcdonald.podcasts.app.entity.Item;
import com.jrmcdonald.podcasts.app.entity.Item.ItemBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * FeedService
 */
@Service
public class ChannelService {

    Logger logger = LoggerFactory.getLogger(ChannelService.class);

    @Value("${app.file.source}")
    private String fileSource;

    @Autowired
    private ObjectMapper objectMapper;

    public List<Channel> getChannels() {
        List<Channel> channels = new ArrayList<Channel>();

        try {
            Files.list(Paths.get(fileSource))
                .filter(Files::isDirectory)
                .map(this::parseChannelDir)
                .forEachOrdered(channels::add);
        } catch (IOException e) {
            logger.error("Unable to read source directory: {}", e);
        }

        return channels;
    }

    public Channel getChannel(String channelId) {
       Path path = Paths.get(fileSource + '/' + channelId); 

       return parseChannelDir(path);
    }

    private Channel parseChannelDir(Path channelDir) {
        Channel channel = null;

        try {
            List<Path> metaFiles = Files.list(channelDir)
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString()
                    .endsWith(".json"))
                    .collect(Collectors.toList());
            
            if (metaFiles.size() > 0) {
                channel = buildChannelFromFirstMetaFile(metaFiles.get(0));

                metaFiles.stream()
                        .filter(Files::isRegularFile)
                        .filter(p -> p.toString().endsWith(".json"))
                        .map(this::buildItemFromMetaFile)
                        .forEachOrdered(channel::addItem);
            }
        } catch (IOException e) {
            logger.debug("An exception occurred reading the filesystem: {}", e);
        }

        return channel;
    }


    private Channel buildChannelFromFirstMetaFile(Path metaFile) {
        Channel channel = null;

        try {
            JsonNode rootNode = objectMapper.readTree(metaFile.toFile());

            String title = rootNode.get("playlist_title").asText();

            StringBuilder sb = new StringBuilder();
            sb.append("/channels/");
            sb.append(rootNode.get("playlist_id").asText());
            sb.append("/");

            String link = sb.toString();
            String image = rootNode.get("thumbnails").get(0).get("url").asText();
            String author = rootNode.get("playlist_uploader").asText();

            ChannelBuilder builder = new ChannelBuilder();

            channel = builder.title(title)
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
    private Item buildItemFromMetaFile(Path metaFile) {
        Item item = null;

        try {
            JsonNode rootNode = objectMapper.readTree(metaFile.toFile());

            String title = rootNode.get("title").asText();
            String pubDate = rootNode.get("upload_date").asText();

            ItemBuilder builder = new ItemBuilder();

            item = builder.title(title)
                    .pubDate(pubDate)
                    .build();
        } catch (IOException e) {
            logger.error("Unable to build item: {}", e);
        }

        return item;

    }
}