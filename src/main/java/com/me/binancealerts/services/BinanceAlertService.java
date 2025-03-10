package com.me.binancealerts.services;

import com.me.binancealerts.entities.CoinPair;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public interface BinanceAlertService {
    CoinPair addCoinPair(String coin1, String coin2);
    Optional<CoinPair> findCoinPair(String coin1, String coin2);
    Optional<Set<CoinPair>> findAllDuosContainingCoin(String coin);
    void checkPricesForAllDuos();
    void checkPricesForCoinPair(CoinPair coinPair);
    Set<CoinPair> getAllCoinPairs();
}
