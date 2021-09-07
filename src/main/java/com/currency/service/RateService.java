package com.currency.service;

import com.currency.client.CurrencyExchangeServiceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Service
public class RateService {

    @Value("${rates.app.id}")
    private String apiId;

    @Value("${rate.code.rub}")
    private String rubRateCode;

    @Value("${time.zone.name}")
    private String timeZoneName;

    private CurrencyExchangeServiceProxy service;

    @Autowired
    public RateService(CurrencyExchangeServiceProxy service) {
        this.service = service;
    }

    public Boolean isCurrentRateBigger(String rateCode) {

        var currentDate = LocalDate.now(ZoneId.of(timeZoneName));

        Double currentRubRate = getRate(currentDate, rubRateCode);
        Double currentMainRate = getRate(currentDate, rateCode);
        Double previousRubRate = getRate(currentDate.minusDays(1), rubRateCode);
        Double previousMainRate = getRate(currentDate.minusDays(1), rateCode);

        return (currentRubRate / currentMainRate) > (previousRubRate / previousMainRate);
    }

    private Double getRate(LocalDate date, String rateCode) {
        return service
                .retrieveExchangeValue(DateTimeFormatter.ISO_LOCAL_DATE.format(date), apiId)
                .getRates()
                .get(rateCode);
    }
}
