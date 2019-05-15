package org.fasttrackit.onlineshopapi.persistence;

import org.fasttrackit.onlineshopapi.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
