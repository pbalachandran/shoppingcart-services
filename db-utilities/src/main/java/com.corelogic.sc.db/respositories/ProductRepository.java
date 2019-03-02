package com.corelogic.sc.db.respositories;

import com.corelogic.sc.db.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Product findBySkuNumber(String skuNumber);
}
