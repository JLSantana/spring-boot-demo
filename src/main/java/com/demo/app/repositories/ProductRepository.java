package com.demo.app.repositories;

import com.demo.app.models.entities.Product;
import com.demo.app.models.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Optional<Product> findFirstByProductIdAndProductStatus (String productId, Status status);
}
