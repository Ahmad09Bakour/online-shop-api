package org.fasttrackit.onlineshopapi.persistence;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

// we're inheriting from JavaPersistenceApiRepository class which has a lot a lot of methods that we'll need to use instead of creating them by ourselves(<name of the object, the type of Id that object>)
public interface ProductRepository extends JpaRepository<Product, Long> {
}
