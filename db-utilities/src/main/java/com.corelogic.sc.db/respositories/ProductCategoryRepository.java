package com.corelogic.sc.db.respositories;

import com.corelogic.sc.db.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {
    ProductCategory findByProductCategoryName(String productCategoryName);
}
