package com.me.binancealerts.controllers;

import com.me.binancealerts.entities.CoinPair;
import com.me.binancealerts.services.BinanceAlertService;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/coins")
public class BinanceAlertAPIController {
    private final BinanceAlertService binanceAlertService;

    public BinanceAlertAPIController(BinanceAlertService binanceAlertService) {
        this.binanceAlertService = binanceAlertService;
    }

    @PostMapping("/add/{coin1}/{coin2}")
    public String addPair(@PathVariable String coin1, @PathVariable String coin2) {
        return binanceAlertService.addCoinPair(coin1, coin2).toString();
    }

    @DeleteMapping("/remove/{coin1}/{coin2}")
    public String removePair(@PathVariable String coin1, @PathVariable String coin2) {
        return binanceAlertService.findCoinPair(coin1, coin2)
                .map(CoinPair -> {
                    binanceAlertService.getAllCoinPairs().remove(CoinPair);
                    return "Removed " + coin1 + "/" + coin2;
                })
                .orElse("Pair not found.");
    }

    @GetMapping("/filter/{coin}")
    public Set<CoinPair> getPairsContaining(@PathVariable String coin) {
        if (coin == null || coin.length() < 3) {
            return binanceAlertService.getAllCoinPairs();
        }
        return binanceAlertService.findAllDuosContainingCoin(coin).orElse(new HashSet<>());
    }
}
