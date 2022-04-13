package ru.iteco.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.iteco.entities.Check;
import ru.iteco.entities.CheckPosition;
import ru.iteco.entities.Client;
import ru.iteco.exceptions.PointsOperationException;
import ru.iteco.repositories.CheckPositionRepository;
import ru.iteco.repositories.CheckRepository;
import ru.iteco.repositories.ClientRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class BonusPointsService {

    private final CheckPositionRepository positionRepo;
    private final CheckRepository checkRepo;
    private final ClientRepository clientRepo;

    public long getPointsOfClient(BigInteger cardNumber) {
        return clientRepo.getCurrentPointsByCardNum(cardNumber);
    }

    @Transactional
    public long updatePoints(BigInteger cardNumber, double checkSum, List<Double> positions) {
        try {
            Client client = clientRepo.getClientWithLock(cardNumber);
            withdrawPoints(checkSum, positions, client);
            scorePoints(cardNumber, checkSum, client);
            clientRepo.save(client);
            saveCheck(checkSum,  positions, cardNumber);
            return client.getPointsSum();
        } catch (Exception e) {
            log.error("Произошла ошибка во время снятия баллов", e);
            throw e;
        }
    }

    private void withdrawPoints(double checkSum, List<Double> positions, Client client) {
        double positionSum = positions.stream().mapToDouble(i -> i).sum();
        if (positionSum < checkSum) throw new PointsOperationException("Сумма чека больше суммы позиций!");
        long currentPoints = client.getPointsSum() - Math.round((positionSum - checkSum) * 0.1);
        if (currentPoints < 0) throw new PointsOperationException("Недостаточно баллов для списания!");
        client.setPointsSum(currentPoints);
    }

    private void scorePoints(BigInteger cardNumber, double checkSum, Client client) {
        double allCheckSum = checkRepo.findCheckSumByCard(cardNumber);
        double coef;
        if (allCheckSum <= 50000) coef = 50;
        else if (allCheckSum <= 100000) coef = 40;
        else coef = 30;
        client.setPointsSum(client.getPointsSum() + Math.round(checkSum / coef));
    }

    private void saveCheck(double checkSum, List<Double> positions, BigInteger cardNumber) {
        Check savedCheck = checkRepo.save(makeCheck(checkSum, cardNumber));
        positions.forEach(p->positionRepo.save(makePosition(p, savedCheck.getId())));
    }

    private Check makeCheck(double checkSum, BigInteger cardNumber) {
        return Check.builder()
                .checkSum(checkSum)
                .cardNumber(cardNumber)
                .build();
    }

    private CheckPosition makePosition(double positionSum, UUID checkId) {
        return CheckPosition.builder()
                .checkId(checkId)
                .positionSum(positionSum)
                .build();
    }

}
