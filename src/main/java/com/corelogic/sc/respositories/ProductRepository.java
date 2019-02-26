package com.corelogic.sc.respositories;

import com.corelogic.sc.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Product findBySkuNumber(String skuNumber);
}
