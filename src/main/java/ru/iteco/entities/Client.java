package ru.iteco.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;

@Data
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @Column(name = "card_number", length = 20)
    private BigInteger cardNumber;

    @Column(name = "points_sum", nullable = false)
    private long pointsSum;

}
