package co.newlabs.service;

import co.newlabs.client.account.AccountClient;
import co.newlabs.client.tracking.TrackingClient;
import co.newlabs.dto.OrderDTO;
import co.newlabs.repository.lineitem.LineItemRepository;
import co.newlabs.repository.order.OrderRepository;
import co.newlabs.repository.product.ProductRepository;
import ma.glasnost.orika.MapperFacade;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTests {

    @Mock
    OrderRepository orderRepository;

    @Mock
    LineItemRepository lineItemRepository;

    @Mock
    ProductRepository productRepository;

    @Mock
    AccountClient accountClient;

    @Mock
    TrackingClient trackingClient;

    @Spy
    MapperFacade mapper;

    @InjectMocks
    OrderService systemUnderTest;

    @Test
    public void testGetOrderTest_ScenarioA() {
        // arrange

        // act
        OrderDTO actual = systemUnderTest.getOrderTest(1L);

        // assert
        Assert.assertThat(actual.getOrderNumber(), is(1L));

        // verify
    }
}
