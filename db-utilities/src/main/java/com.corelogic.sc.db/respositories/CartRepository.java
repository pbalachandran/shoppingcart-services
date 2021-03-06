package com.corelogic.sc.db.respositories;

import com.corelogic.sc.db.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {
    Cart findByCartName(String cartName);
}
