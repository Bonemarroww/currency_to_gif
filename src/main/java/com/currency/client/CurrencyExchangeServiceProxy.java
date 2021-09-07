package com.currency.client;

import com.currency.model.CurrencyConversionBean;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "rates-service", url = "${rates.api.url}")
public interface CurrencyExchangeServiceProxy {

    @GetMapping("/historical/{date}.json")
    CurrencyConversionBean retrieveExchangeValue (
            @PathVariable(value = "date") String date,
            @RequestParam(name = "app_id") String id
    );
}
