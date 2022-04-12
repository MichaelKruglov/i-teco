package ru.iteco.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.iteco.services.BonusPointsService;

import java.math.BigInteger;

@Slf4j
@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
@Api(tags = "BonusPointsController", description = "API")
public class BonusPointsController {

    private BonusPointsService service;

    /**
     Метод для получения суммы баллов номеру карты.
     @param: номер карты.
     @return: данные сумме баллов на карте.
     */
    @ApiOperation(value = "getBonusPoints", nickname = "getBonusPoints")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 200, message = "Successful")})
    @GetMapping(value = "/{cardNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public int getBonusPoints(@PathVariable("cardNumber") BigInteger cardNumber) {
        return service.getPointsOfClient(cardNumber);
    }

    /**
     Метод для увеличения баллов на карту.
     @param: номер карты.
     */
    @ApiOperation(value = "scorePoints", nickname = "scorePoints")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 200, message = "Successful")})
    @PostMapping(value = "/{cardNumber}/score")
    public void scorePoints(@PathVariable("cardNumber") BigInteger cardNumber) {
        service.scorePoints(cardNumber);
    }

    /**
     Метод для списания баллов на карту.
     @param: номер карты.
     @param: сумма чека.
     @param: сумма позиции.
     */
    @ApiOperation(value = "withdrawPoints", nickname = "withdrawPoints")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 200, message = "Successful")})
    @PostMapping(value = "/{cardNumber}/withdraw")
    public void withdrawPoints(@PathVariable("cardNumber") BigInteger cardNumber,
                               @RequestParam("checkSum") double checkSum,
                               @RequestParam("positionSum") double positionSum) {
        service.withdrawPoints(cardNumber, checkSum, positionSum);
    }
}
