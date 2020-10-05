package co.newlabs.controller;

import co.newlabs.dto.OrderDTO;
import co.newlabs.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private OrderService service;

    @GetMapping("/{id}/test")
    public ResponseEntity<OrderDTO> getOrderDetailsTest(@PathVariable long id) {
        return ResponseEntity.ok(service.getOrderTest(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderDetails(@PathVariable long id) {
        return ResponseEntity.ok(service.getOrder(id));
    }

    @GetMapping("/{id}/full")
    public ResponseEntity<OrderDTO> getOrderFullDetails(@PathVariable long id) {
        return ResponseEntity.ok(service.getOrderFullDetails(id));
    }
}
