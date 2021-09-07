package com.currency.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.currency.client.GifServiceProxy;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URL;

@Service
public class GiphyService {

    private final String RICH_TAG = "rich";
    private final String BROKE_TAG = "broke";

    private RateService rateService;
    private GifServiceProxy ratesServiceProxy;

    @Value("${gif.app.id}")
    private String appId;

    @Autowired
    public GiphyService(RateService rateService, GifServiceProxy ratesServiceProxy) {
        this.rateService = rateService;
        this.ratesServiceProxy = ratesServiceProxy;
    }

    public byte[] getGif(String rateCode) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (rateService.isCurrentRateBigger(rateCode)) {
            try {
                var node = objectMapper.readTree(ratesServiceProxy.getGif(appId, RICH_TAG));
                return convertGifUrlToByteArray(node.get("data").get("image_url").asText());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return new byte[0];
            }
        } else {
            try {
                var node = objectMapper.readTree(ratesServiceProxy.getGif(appId, BROKE_TAG));
                return convertGifUrlToByteArray(node.get("data").get("image_url").asText());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return new byte[0];
            }
        }
    }

    private byte[] convertGifUrlToByteArray(String url) {
        try {
            URL imageUrl = new URL(url.replaceAll("media\\d+", "i"));
            InputStream inputStream = imageUrl.openStream();
            byte[] bytes = IOUtils.toByteArray(inputStream);
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
