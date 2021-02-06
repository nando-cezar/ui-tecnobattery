package com.tecnobattery.tbsystem.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tb_product")
public class Product implements Serializable {

  /**
   *
   */
  private static final long serialVersionUID = -6255181520948924390L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Double price;
  private String description;
  private String imageUrl;

  public Product() {
  }

  public Product(Long id, String name, Double price, String description, String imageUrl) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.description = description;
    this.imageUrl = imageUrl;
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPrice() {
    return this.price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getImageUrl() {
    return this.imageUrl;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    if (!(o instanceof Product)) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(price, product.price)
        && Objects.equals(description, product.description) && Objects.equals(imageUrl, product.imageUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, price, description, imageUrl);
  }

  @Override
  public String toString() {
    return "{" + " id='" + getId() + "'" + ", name='" + getName() + "'" + ", price='" + getPrice() + "'"
        + ", description='" + getDescription() + "'" + ", imageUrl='" + getImageUrl() + "'" + "}";
  }

}
