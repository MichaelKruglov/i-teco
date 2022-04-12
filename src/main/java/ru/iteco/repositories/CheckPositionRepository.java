package ru.iteco.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.iteco.entities.CheckPosition;

import java.util.UUID;

@Repository
public interface CheckPositionRepository extends JpaRepository<CheckPosition, UUID> {
}
