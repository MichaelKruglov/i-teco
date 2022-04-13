package ru.iteco.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.iteco.entities.Check;

import java.math.BigInteger;
import java.util.UUID;

@Repository
public interface CheckRepository extends JpaRepository<Check, UUID> {

    @Query("select coalesce(sum(e.checkSum), 0) from Check e where e.cardNumber = :cardNum")
    double findCheckSumByCard(@Param("cardNum") BigInteger cardNum);

}
