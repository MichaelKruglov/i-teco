package ru.iteco.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.iteco.services.BonusPointsService;

import java.math.BigInteger;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
@Api(tags = "BonusPointsController", description = "API")
public class BonusPointsController {

    private final BonusPointsService service;

    /**
     * Метод для получения суммы баллов номеру карты.
     *
     * @param: номер карты.
     * @return: данные сумме баллов на карте.
     */
    @ApiOperation(value = "getBonusPoints", nickname = "getBonusPoints")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 200, message = "Successful")})
    @GetMapping(value = "/{cardNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public long getBonusPoints(@PathVariable("cardNumber") BigInteger cardNumber) {
        return service.getPointsOfClient(cardNumber);
    }

    /**
     * Метод для обновления количества баллов на карте.
     *
     * @param: номер карты.
     * @param: сумма чека.
     * @param: список позиций.
     * @return: сумма баллов на карте.
     */
    @ApiOperation(value = "withdrawPoints", nickname = "withdrawPoints")
    @ApiResponses(value = {
            @ApiResponse(code = 500, message = "Server error"),
            @ApiResponse(code = 200, message = "Successful")})
    @PostMapping(value = "/{cardNumber}/update", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public long updatePoints(@PathVariable("cardNumber") BigInteger cardNumber,
                             @RequestParam("checkSum") double checkSum,
                             @RequestBody @NonNull List<Double> positions) {
        return service.updatePoints(cardNumber, checkSum, positions);
    }
}
