package ru.iteco.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.iteco.entities.Client;

import java.math.BigDecimal;
import java.math.BigInteger;

@Repository
public interface ClientRepository extends JpaRepository<Client, BigDecimal> {

    @Query("select e.pointsSum from Client e where e.cardNumber = :cardNum")
    Long getCurrentPointsByCardNum(@Param("cardNum") BigInteger cardNum);

    @Query(value = "select e.* from clients e where e.card_number = :cardNum for update", nativeQuery = true)
    Client getClientWithLock(@Param("cardNum") BigInteger cardNum);

    @Query(value = "select e.* from clients e where e.card_number = :cardNum", nativeQuery = true)
    Client getClientByCardNumber(@Param("cardNum")BigInteger cardNumber);
}
