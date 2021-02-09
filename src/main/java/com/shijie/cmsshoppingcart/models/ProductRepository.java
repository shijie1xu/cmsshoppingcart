package com.shijie.cmsshoppingcart.models;

import com.shijie.cmsshoppingcart.models.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {


}
