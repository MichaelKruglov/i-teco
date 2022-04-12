package ru.iteco.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.entities.Check;

import java.util.UUID;

@Repository
public interface CheckRepository extends JpaRepository<Check, UUID> {
}
