package com.corelogic.sc.db.respositories;

import com.corelogic.sc.db.entities.Cart;
import com.corelogic.sc.db.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findItemsByCart(Cart cart);

    @Query("SELECT item FROM Item item WHERE item.product.skuNumber = :skuNumber")
    Item findBySkuNumber(@Param("skuNumber") String skuNumber);
}
