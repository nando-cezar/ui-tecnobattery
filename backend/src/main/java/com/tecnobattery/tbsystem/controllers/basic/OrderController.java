package com.tecnobattery.tbsystem.controllers.basic;

import java.time.OffsetDateTime;
import java.util.List;

import javax.validation.Valid;

import com.tecnobattery.tbsystem.auth.model.User;
import com.tecnobattery.tbsystem.auth.service.UserService;
import com.tecnobattery.tbsystem.dto.request.OrderRequest;
import com.tecnobattery.tbsystem.dto.response.OrderResponse;
import com.tecnobattery.tbsystem.entities.Client;
import com.tecnobattery.tbsystem.entities.Order;
import com.tecnobattery.tbsystem.entities.Product;
import com.tecnobattery.tbsystem.entities.enumerated.OrderStatus;
import com.tecnobattery.tbsystem.services.ClientService;
import com.tecnobattery.tbsystem.services.OrderService;
import com.tecnobattery.tbsystem.services.ProductService;
import com.tecnobattery.tbsystem.tools.ToolConvertIdObject;
import com.tecnobattery.tbsystem.tools.ToolModelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/api/v1/orders")
@AllArgsConstructor
public class OrderController {

  private final OrderService orderService;
  private final ClientService clientService;
  private final ProductService productService;
  private final UserService userService;
  private final ToolModelMapper toolModelMapper;
  private final ToolConvertIdObject toolConvertIdObject;

  @PostMapping
  @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
  @ResponseStatus(HttpStatus.CREATED)
  public OrderResponse save(@Valid @RequestBody OrderRequest orderInput) {

    Order order = toolModelMapper.toModel(orderInput, Order.class);
    order.setClient(toolModelMapper.toModel(clientService.findById(orderInput.getClientId()), Client.class));
    order.setStatus(OrderStatus.PENDING);
    order.setOpening(OffsetDateTime.now());
    order.setProducts(toolConvertIdObject.getObjectId(orderInput.getProducts(), productService, Product.class));
    order.setUsers(toolConvertIdObject.getObjectId(orderInput.getUsers(), userService, User.class));

    return orderService.save(order);
  }

  @GetMapping
  @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
  public ResponseEntity<List<OrderResponse>> findAll() {
    return ResponseEntity.ok().body(orderService.findAll());
  }

  @GetMapping("/{orderId}")
  @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
  public ResponseEntity<OrderResponse> findById(@PathVariable Long orderId) {
    return ResponseEntity.ok(orderService.findById(orderId));
  }

  @PutMapping("finish/{orderId}")
  @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
  public ResponseEntity<OrderResponse> finish(@Valid @PathVariable Long orderId) {

    if (!orderService.existsById(orderId)) {
      return ResponseEntity.notFound().build();
    }

    Order order = toolModelMapper.toModel(orderService.findById(orderId), Order.class);

    if (order.getDeadline() == null) {

      order.setStatus(OrderStatus.DELIVERED);
      order.setDeadline(OffsetDateTime.now());
      order.setId(orderId);
      return ResponseEntity.ok(orderService.save(order));
    }
    return ResponseEntity.badRequest().build();
  }
}
