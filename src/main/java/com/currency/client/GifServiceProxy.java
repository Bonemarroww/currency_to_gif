package com.currency.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "gif-service", url = "${gif.api.url}")
public interface GifServiceProxy {

    @GetMapping("")
    String getGif(
            @RequestParam(value = "api_key") String apiId,
            @RequestParam(value = "tag") String tag
            );
}
