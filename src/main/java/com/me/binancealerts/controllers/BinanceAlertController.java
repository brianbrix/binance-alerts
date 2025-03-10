package com.me.binancealerts.controllers;

import com.me.binancealerts.entities.CoinPair;
import com.me.binancealerts.services.BinanceAlertService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
public class BinanceAlertController {
    private final BinanceAlertService binanceAlertService;

    public BinanceAlertController(BinanceAlertService binanceAlertService) {
        this.binanceAlertService = binanceAlertService;
    }

    @GetMapping("/")
    public String showTrackedCoins(Model model) {
        Set<CoinPair> trackedCoins = binanceAlertService.getAllCoinPairs();
        model.addAttribute("trackedCoins", trackedCoins);
        return "dashboard";
    }
}
