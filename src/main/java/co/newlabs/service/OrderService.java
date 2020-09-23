package co.newlabs.service;

import co.newlabs.client.account.AccountClient;
import co.newlabs.client.account.AccountDTO;
import co.newlabs.client.tracking.TrackingClient;
import co.newlabs.client.tracking.TrackingDTO;
import co.newlabs.dto.LineItemDTO;
import co.newlabs.dto.OrderDTO;
import co.newlabs.dto.ProductDTO;
import co.newlabs.repository.lineitem.LineItemEntity;
import co.newlabs.repository.lineitem.LineItemRepository;
import co.newlabs.repository.order.OrderEntity;
import co.newlabs.repository.order.OrderRepository;
import co.newlabs.repository.product.ProductRepository;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private OrderRepository orderRepository;
    private LineItemRepository lineItemRepository;
    private ProductRepository productRepository;
    private AccountClient account;
    private TrackingClient tracking;
    private MapperFacade mapper;

    public OrderDTO getOrder(final long id) {
        OrderEntity orderEntity = orderRepository.getOrderById(id);
        return getOrder(orderEntity);
    }

    public OrderDTO getOrderFullDetails(final long id) {
        OrderEntity orderEntity = orderRepository.getOrderById(id);
        OrderDTO order = getOrder(orderEntity);
        order.setAccount(mapper.map(account.getAccountById(orderEntity.getAccountId()), AccountDTO.class));
        order.setTracking(mapper.map(tracking.getTrackingDetailsByOrderId(id), TrackingDTO.class));

        return order;
    }

    private OrderDTO getOrder(OrderEntity orderEntity) {
        OrderDTO order = mapper.map(orderEntity, OrderDTO.class);
        order.setLineItems(new ArrayList<LineItemDTO>());
        List<LineItemEntity> lineItemEntities = lineItemRepository.getLineItemsByOrder(orderEntity.getOrderId());
        for (LineItemEntity lineItem: lineItemEntities) {
            ProductDTO product = mapper.map(productRepository.getProductById(lineItem.getProductId()), ProductDTO.class);
            LineItemDTO lineItemDTO = mapper.map(lineItem, LineItemDTO.class);
            lineItemDTO.setProduct(product);
            order.getLineItems().add(lineItemDTO);
        }
        return order;
    }
}
