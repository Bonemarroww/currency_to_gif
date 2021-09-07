package com.currency;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.currency.service.RateService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static com.github.jenspiegsa.wiremockextension.ManagedWireMockServer.with;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.currency.RatesMocks.setupMockRatesResponse;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {WireMockConfig.class})
public class MainApplicationTests {

    @Autowired
    WireMockServer server = with(wireMockConfig().dynamicHttpsPort());

    @Autowired
    private RateService rateService;

    @Before
    public void init() throws IOException {
        setupMockRatesResponse(server);
    }

    @Test
    public void currentRateBiggerThanPrevious() {
        assertTrue(rateService.isCurrentRateBigger("EUR"));
    }

    @Test
    public void currentRateLessThanPrevious() {
        assertFalse(rateService.isCurrentRateBigger("AED"));
    }

    @Test(expected = NullPointerException.class)
    public void errorCheck() {
        rateService.isCurrentRateBigger("AAA");
    }
}
