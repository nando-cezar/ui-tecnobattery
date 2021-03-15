package com.tecnobattery.tbsystem.controllers;

import java.util.List;

import javax.validation.Valid;

import com.tecnobattery.tbsystem.dto.request.ProductRequest;
import com.tecnobattery.tbsystem.dto.response.ProductResponse;
import com.tecnobattery.tbsystem.entities.Product;
import com.tecnobattery.tbsystem.services.ProductService;
import com.tecnobattery.tbsystem.tools.ToolModelMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

  @Autowired
  private ProductService productService;

  @Autowired
  private ToolModelMapper toolModelMapper;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public ProductResponse save(@Valid @RequestBody ProductRequest productInput) {
    return productService.save(toolModelMapper.toModel(productInput, Product.class), false);
  }

  @GetMapping
  public ResponseEntity<List<ProductResponse>> findAll() {
    return ResponseEntity.ok().body(productService.findAll());
  }

  @GetMapping("/{productId}")
  public ResponseEntity<ProductResponse> findById(@PathVariable Long productId) {
    return ResponseEntity.ok(productService.findById(productId));
  }

  @PutMapping("/{productId}")
  public ResponseEntity<ProductResponse> update(@Valid @PathVariable Long productId,
      @RequestBody ProductRequest productInput) {

    if (!productService.existsById(productId)) {
      return ResponseEntity.notFound().build();
    }

    Product product = new Product();
    product = toolModelMapper.toModel(productInput, Product.class);
    product.setId(productId);

    return ResponseEntity.ok(productService.save(product, true));
  }

  @DeleteMapping("/{productId}")
  public ResponseEntity<Void> deleteById(@PathVariable Long productId) {
    if (!productService.existsById(productId)) {
      return ResponseEntity.notFound().build();
    }
    productService.deleteById(productId);
    return ResponseEntity.noContent().build();
  }
}
