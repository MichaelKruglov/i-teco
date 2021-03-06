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

    public Long getPointsOfClient(BigInteger cardNumber) {
        return clientRepo.getCurrentPointsByCardNum(cardNumber);
    }

    @Transactional
    public long updatePoints(BigInteger cardNumber, double checkSum, List<Double> positions) {
        try {
            double positionSum = positions.stream().mapToDouble(i -> i).sum();
            checkVerification(checkSum, positionSum, positions);
            clientVerification(cardNumber);

            Client client = clientRepo.getClientWithLock(cardNumber);
            withdrawPoints(checkSum, positionSum, client);
            scorePoints(cardNumber, checkSum, client);
            clientRepo.save(client);
            saveCheck(checkSum, positions, cardNumber);
            return client.getPointsSum();
        } catch (Exception e) {
            log.error("Произошла ошибка во время снятия баллов", e);
            throw e;
        }
    }

    private void clientVerification(BigInteger cardNumber) {
        if (cardNumber == null || cardNumber.toString().length() != 20)
            throw new PointsOperationException("Неверный формат номера карты");
        var optionalClient = clientRepo.getClientByCardNumber(cardNumber);
        if (optionalClient == null) {
            Client client = new Client();
            client.setCardNumber(cardNumber);
            client.setPointsSum(0);
            clientRepo.save(client);
        }
    }

    private void checkVerification(double checkSum, double positionSum, List<Double> positions) {
        if (checkSum == 0 && positionSum == 0) throw new PointsOperationException("Нулевая сумма в позициях и в чеке");
        if (positions.stream().anyMatch(p->p<0)) throw new PointsOperationException("Некорректная сумма позиции");
    }

    private void withdrawPoints(double checkSum, double positionSum, Client client) {
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
        positions.forEach(p -> positionRepo.save(makePosition(p, savedCheck.getId())));
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
