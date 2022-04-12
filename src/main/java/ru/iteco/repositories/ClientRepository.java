package ru.iteco.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.entities.Client;

import java.math.BigDecimal;

@Repository
public interface ClientRepository extends JpaRepository<Client, BigDecimal> {
}
