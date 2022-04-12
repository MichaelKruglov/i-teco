package ru.iteco.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.UUID;

@Data
@Entity
@Table(name = "checks")
public class Check {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private UUID id;

    @Column(name = "card_number", length = 20, nullable = false)
    private BigInteger cardNumber;

    @Column(name = "check_sum", nullable = false)
    private double checkSum;

}
