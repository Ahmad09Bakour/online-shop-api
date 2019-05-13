package org.fasttrackit.onlineshopapi.persistence;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// we're inheriting from JavaPersistenceApiRepository class which has a lot a lot of methods that we'll need to use instead of creating them by ourselves(<name of the object, the type of Id that object>)
public interface ProductRepository extends JpaRepository<Product, Long> {

    // queries derived from method names.... spring boot knowing the name of the method "conaitns the word Name.. get string...)
    // then spring boot will send the query to the DB (GET FROM *** CONTAINS ***....)
    Page<Product> findByNameContaining(String partialName, Pageable pageable);

    Page<Product> findByNameContainingAndQuantityGreaterThanEqual(String partialName, int minimumQuantity, Pageable pageable);

    // an example of named queries, use JPQL queries if nativeQuery is fault (by default)
//    @Query(value = "SELECT * FROM product", nativeQuery = true)
//    Product findBySomeComplexLogic();
}
