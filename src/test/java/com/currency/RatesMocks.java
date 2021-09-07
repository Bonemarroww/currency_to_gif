package com.currency;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.nio.charset.Charset.defaultCharset;
import static org.springframework.util.StreamUtils.copyToString;

public class RatesMocks {

    public static void setupMockRatesResponse(WireMockServer mockServer) throws IOException {

        var currentDate = LocalDate.now(ZoneId.of("Asia/Yekaterinburg"));

        mockServer.stubFor(get(urlPathEqualTo("/historical/" + DateTimeFormatter.ISO_LOCAL_DATE.format(currentDate) + ".json"))
                .withQueryParam("app_id", equalTo("cb7cb3cf1783405d9fe590953e8dbb50"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(copyToString(
                                MainApplicationTests.class.getClassLoader().getResourceAsStream("payload/test.json"),
                                defaultCharset()))));
        mockServer.stubFor(get(urlPathEqualTo("/historical/" + DateTimeFormatter.ISO_LOCAL_DATE.format(currentDate.minusDays(1)) + ".json"))
                .withQueryParam("app_id", equalTo("cb7cb3cf1783405d9fe590953e8dbb50"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                        .withBody(copyToString(
                                MainApplicationTests.class.getClassLoader().getResourceAsStream("payload/test2.json"),
                                defaultCharset()))));
        mockServer.getStubMappings().forEach(System.out::println);
    }
}
