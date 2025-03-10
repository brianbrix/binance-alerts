package com.me.binancealerts;

import com.me.binancealerts.services.BinanceAlertService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PriceCheckScheduler {
    private final BinanceAlertService binanceAlertService;

    public PriceCheckScheduler(BinanceAlertService binanceAlertService) {
        this.binanceAlertService = binanceAlertService;
    }

    @Scheduled(fixedRate = 60000)
    public void schedulePriceCheck() {
        binanceAlertService.checkPricesForAllDuos();
    }
}
