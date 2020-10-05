package co.newlabs;

import co.newlabs.client.account.AccountDTO;
import co.newlabs.client.tracking.PositionDTO;
import co.newlabs.client.tracking.TrackingDTO;
import co.newlabs.dto.LineItemDTO;
import co.newlabs.dto.OrderDTO;
import co.newlabs.dto.ProductDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
public class ApplicationIT {
    @LocalServerPort
    private int REST_ASSURED_PORT_NUMBER;

    @Autowired
    private WireMockServer wireMockServer;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        wireMockServer.resetAll();
        RestAssured.port = REST_ASSURED_PORT_NUMBER;
        RestAssured.baseURI = "http://localhost:" + REST_ASSURED_PORT_NUMBER;
    }

    @Test
    public void getOrder_ScenarioA() throws Exception {
        // arrange

        // act
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiZXhwIjoxNjAxOTMyOTg2LCJhdWQiOiJST0xFX0FETUlOIn0.0NfqTGwzun_OgUidhkCjPRojLp8AC9E8Zy38QRGuYcU");
        Response response = request.get("/orders/1");

        // assert
        ProductDTO expectedProduct = ProductDTO.builder()
                .productId(1)
                .name("Product 1")
                .price(1.00)
                .build();

        List<LineItemDTO> expectedList = new ArrayList<>();
        expectedList.add(LineItemDTO.builder().lineItemId(1).product(expectedProduct).quantity(1).build());

        OrderDTO expectedOrder = OrderDTO.builder()
                .orderNumber(1L)
                .lineItems(expectedList)
                .build();

        String expected = objectMapper.writeValueAsString(expectedOrder);
        String actual = response.getBody().print();

        Assert.assertThat(actual, is(equalTo(expected)));
        Assert.assertThat(response.getStatusCode(), is(200));
        Assert.assertThat(wireMockServer.findAllUnmatchedRequests().size(), is(0));

        // verify
    }

    @Test
    public void getOrder_ScenarioB() throws Exception {
        // arrange

        // act
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiZXhwIjoxNjAxOTMyOTg2LCJhdWQiOiJST0xFX0FETUlOIn0.0NfqTGwzun_OgUidhkCjPRojLp8AC9E8Zy38QRGuYcU");
        Response response = request.get("/orders/2");

        // assert
        Assert.assertThat(response.getStatusCode(), is(404));
        Assert.assertThat(wireMockServer.findAllUnmatchedRequests().size(), is(0));

        // verify
    }

    @Test
    public void getOrderFullDetails_ScenarioA() throws Exception {
        // arrange
        AccountDTO mockAccount = AccountDTO.builder()
                .name("Account 1")
                .build();

        wireMockServer.stubFor(get(urlMatching("/accounts/1"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(objectMapper.writeValueAsString(mockAccount))
            )
        );

        PositionDTO mockPosition1 = PositionDTO.builder()
                .lat(34.00)
                .lon(-97.00)
                .build();
        PositionDTO mockPosition2 = PositionDTO.builder()
                .lat(35.00)
                .lon(-98.00)
                .build();

        List<PositionDTO> mockPositionList = new ArrayList<>();
        mockPositionList.add(mockPosition1);
        mockPositionList.add(mockPosition2);

        TrackingDTO mockTracking = TrackingDTO.builder()
                .status("enroute")
                .positions(mockPositionList)
                .build();

        wireMockServer.stubFor(get(urlMatching("/tracking/order/1"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(objectMapper.writeValueAsString(mockTracking))
            )
        );

        // act
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiZXhwIjoxNjAxOTMyOTg2LCJhdWQiOiJST0xFX0FETUlOIn0.0NfqTGwzun_OgUidhkCjPRojLp8AC9E8Zy38QRGuYcU");
        Response response = request.get("/orders/1/full");

        // assert
        AccountDTO expectedAccount = AccountDTO.builder()
                .name("Account 1")
                .build();

        PositionDTO expectedPosition1 = PositionDTO.builder()
                .lat(34.00)
                .lon(-97.00)
                .build();
        PositionDTO expectedPosition2 = PositionDTO.builder()
                .lat(35.00)
                .lon(-98.00)
                .build();
        List<PositionDTO> expectedPositionList = new ArrayList<>();
        expectedPositionList.add(expectedPosition1);
        expectedPositionList.add(expectedPosition2);

        TrackingDTO expectedTracking = TrackingDTO.builder()
                .status("enroute")
                .positions(expectedPositionList)
                .build();

        ProductDTO expectedProduct = ProductDTO.builder()
                .productId(1)
                .name("Product 1")
                .price(1.00)
                .build();

        List<LineItemDTO> expectedList = new ArrayList<>();
        expectedList.add(LineItemDTO.builder().lineItemId(1).product(expectedProduct).quantity(1).build());

        OrderDTO expectedOrder = OrderDTO.builder()
                .orderNumber(1L)
                .lineItems(expectedList)
                .account(expectedAccount)
                .tracking(expectedTracking)
                .build();

        OrderDTO actualOrder = objectMapper.readValue(response.getBody().print(), OrderDTO.class);

        Assert.assertThat(actualOrder, is(equalTo(expectedOrder)));
        Assert.assertThat(response.getStatusCode(), is(200));
        Assert.assertThat(wireMockServer.findAllUnmatchedRequests().size(), is(0));

        // verify
        wireMockServer.verify(1, getRequestedFor(urlMatching("/accounts/1")));
        wireMockServer.verify(1, getRequestedFor(urlMatching("/tracking/order/1")));
    }

    @Test
    public void getOrderFullDetails_ScenarioB() throws Exception {
        // arrange
        wireMockServer.stubFor(get(urlMatching("/accounts/1"))
            .willReturn(aResponse()
                .withStatus(417)
                .withBody("It broke, oops.")));

        PositionDTO mockPosition1 = PositionDTO.builder()
                .lat(34.00)
                .lon(-97.00)
                .build();
        PositionDTO mockPosition2 = PositionDTO.builder()
                .lat(35.00)
                .lon(-98.00)
                .build();

        List<PositionDTO> mockPositionList = new ArrayList<>();
        mockPositionList.add(mockPosition1);
        mockPositionList.add(mockPosition2);

        TrackingDTO mockTracking = TrackingDTO.builder()
                .status("enroute")
                .positions(mockPositionList)
                .build();

        wireMockServer.stubFor(get(urlMatching("/tracking/order/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(mockTracking))
                )
        );

        // act
        RequestSpecification request = RestAssured.given();
        request.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiZXhwIjoxNjAxOTMyOTg2LCJhdWQiOiJST0xFX0FETUlOIn0.0NfqTGwzun_OgUidhkCjPRojLp8AC9E8Zy38QRGuYcU");
        Response response = request.get("/orders/1/full");

        // assert
        PositionDTO expectedPosition1 = PositionDTO.builder()
                .lat(34.00)
                .lon(-97.00)
                .build();
        PositionDTO expectedPosition2 = PositionDTO.builder()
                .lat(35.00)
                .lon(-98.00)
                .build();
        List<PositionDTO> expectedPositionList = new ArrayList<>();
        expectedPositionList.add(expectedPosition1);
        expectedPositionList.add(expectedPosition2);

        TrackingDTO expectedTracking = TrackingDTO.builder()
                .status("enroute")
                .positions(expectedPositionList)
                .build();

        ProductDTO expectedProduct = ProductDTO.builder()
                .productId(1)
                .name("Product 1")
                .price(1.00)
                .build();

        List<LineItemDTO> expectedList = new ArrayList<>();
        expectedList.add(LineItemDTO.builder().lineItemId(1).product(expectedProduct).quantity(1).build());

        OrderDTO expectedOrder = OrderDTO.builder()
                .orderNumber(1L)
                .lineItems(expectedList)
                .tracking(expectedTracking)
                .build();

        OrderDTO actualOrder = objectMapper.readValue(response.getBody().print(), OrderDTO.class);

        Assert.assertThat(actualOrder, is(equalTo(expectedOrder)));
        Assert.assertThat(response.getStatusCode(), is(200));
        Assert.assertThat(wireMockServer.findAllUnmatchedRequests().size(), is(0));

        // verify
        wireMockServer.verify(1, getRequestedFor(urlMatching("/accounts/1")));
        wireMockServer.verify(1, getRequestedFor(urlMatching("/tracking/order/1")));
    }
}
