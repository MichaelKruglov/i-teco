package ru.iteco.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.iteco.services.BonusPointsService;

import java.math.BigInteger;

@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
public class BonusPointsController {

    private BonusPointsService service;

    @GetMapping(value = "/{cardNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public int getBonusPoints(@PathVariable("cardNumber") BigInteger cardNumber) {
        return service.getPointsOfClient(cardNumber);
    }

    @PostMapping(value = "/{cardNumber}/score")
    public void scorePoints(@PathVariable("cardNumber") BigInteger cardNumber) {
        service.scorePoints(cardNumber);
    }

    @PostMapping(value = "/{cardNumber}/withdraw")
    public void withdrawPoints(@PathVariable("cardNumber") BigInteger cardNumber,
                               @RequestParam("checkSum") double checkSum,
                               @RequestParam("positionSum") double positionSum) {
        service.withdrawPoints(cardNumber, checkSum, positionSum);
    }

}
