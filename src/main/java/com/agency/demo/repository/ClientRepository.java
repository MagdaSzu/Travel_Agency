package com.agency.demo.repository;


import com.agency.demo.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByLastName(String surname);

    Optional<Client> findByPassport(String passport);

}
