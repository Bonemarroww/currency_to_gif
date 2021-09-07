package com.currency.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class CurrencyConversionBean {
    private String disclaimer;
    private String license;
    private String timestamp;
    private String base;
    private Map<String, Double> rates;

    public CurrencyConversionBean() {
    }

    public CurrencyConversionBean(String disclaimer, String license, String timestamp, String base, Map<String, Double> rates) {
        this.disclaimer = disclaimer;
        this.license = license;
        this.timestamp = timestamp;
        this.base = base;
        this.rates = rates;
    }
}
