package com.currency.controller;

import com.currency.service.GiphyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyConversionController {

    private GiphyService giphyService;

    @Autowired
    public CurrencyConversionController(GiphyService giphyService) {
        this.giphyService = giphyService;
    }

    @GetMapping(value = "/values/{rateCode}", produces = MediaType.IMAGE_GIF_VALUE)
    public @ResponseBody byte[] convertCurrency(@PathVariable String rateCode) {
        return giphyService.getGif(rateCode);
    }
}
