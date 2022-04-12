package ru.iteco.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.exceptions.PointsOperationException;
import ru.iteco.repositories.CheckPositionRepository;
import ru.iteco.repositories.CheckRepository;
import ru.iteco.repositories.ClientRepository;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
@Slf4j
public class BonusPointsService {

    private CheckPositionRepository positionRepo;
    private CheckRepository checkRepo;
    private ClientRepository clientRepo;

    public int getPointsOfClient(BigInteger cardNumber) {
        //....
        return 0;
    }

    @Transactional
    public void scorePoints(BigInteger cardNumber) {
        try {
            //....
        } catch (Exception e) {
            log.error("Произошла ошибка во время начисления баллов", e);
            throw new PointsOperationException("Произошла ошибка во время начисления баллов", e);
        }
    }

    @Transactional
    public void withdrawPoints(BigInteger cardNumber, double checkSum, double positionSum) {
        try {
            //....
        } catch (Exception e) {
            log.error("Произошла ошибка во время снятия баллов", e);
            throw new PointsOperationException("Произошла ошибка во время снятия баллов", e);
        }
    }

}
