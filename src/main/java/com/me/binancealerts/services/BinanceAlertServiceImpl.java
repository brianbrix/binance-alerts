package com.me.binancealerts.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.me.binancealerts.entities.CoinPair;
import com.me.binancealerts.repos.CoinPairRepo;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class BinanceAlertServiceImpl implements BinanceAlertService {
    private static final Logger logger = LoggerFactory.getLogger(BinanceAlertServiceImpl.class);
    private final CoinPairRepo coinPairRepo;
    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BINANCE_API_URL = "https://api.binance.com/api/v3/ticker/24hr?symbol=";

    public BinanceAlertServiceImpl(CoinPairRepo CoinPairRepo) {
        this.coinPairRepo = CoinPairRepo;
    }

    @Override
    public CoinPair addCoinPair(String coin1, String coin2) {
        if (coinPairRepo.findCoinPairByCoin1AndCoin2(coin1, coin2).isEmpty()) {
            CoinPair coinPair = new CoinPair();
            coinPair.setCoin1(coin1);
            coinPair.setCoin2(coin2);
            coinPairRepo.save(coinPair);
            checkPricesForCoinPair(coinPair);
            return coinPair;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Already exists");
    }


    @Override
    public Optional<CoinPair> findCoinPair(String coin1, String coin2) {
        return coinPairRepo.findCoinPairByCoin1AndCoin2(coin1, coin2);

    }

    @Override
    public Optional<Set<CoinPair>> findAllDuosContainingCoin(String coin) {
        return coinPairRepo.findAllByCoin1OrCoin2(coin, coin);
    }

    @Override
    public void checkPricesForAllDuos() {

        for (CoinPair coinDouo: getAllCoinPairs()) {
            checkPricesForCoinPair(coinDouo);
        }
    }

    @Override
    public void checkPricesForCoinPair(CoinPair coinPair) {
        ObjectMapper objectMapper = new ObjectMapper();

        String symbol= coinPair.getCoin1()+ coinPair.getCoin2();
        try {
            String response = restTemplate.getForObject(BINANCE_API_URL + symbol, String.class);
            JsonNode jsonNode = objectMapper.readTree(response);
            logger.info("Fetched data for {}: {}", symbol, response);

            double lastPrice = jsonNode.get("lastPrice").asDouble();
            double highPrice = jsonNode.get("highPrice").asDouble();
            double lowPrice = jsonNode.get("lowPrice").asDouble();
            if (coinPair.getLastPrice()!=null)
            {
                coinPair.setPriceChange(lastPrice - coinPair.getLastPrice());

            }
            coinPair.setLastPrice(lastPrice);
            coinPair.setHighPrice(highPrice);
            coinPair.setLowPrice(lowPrice);
            coinPairRepo.save(coinPair);
            logger.info("Saved data for {}: lastPrice={}, highPrice={}", symbol, lastPrice, highPrice);
            if (lastPrice >= highPrice) {
                logger.info(" ALERT: {} hit its daily high of ${}", symbol, highPrice);
            }

        } catch (Exception e) {
            logger.error("Error fetching data for {}: {}", symbol, e.getMessage(), e);
        }
    }

    @Override
    public Set<CoinPair> getAllCoinPairs() {
        return new HashSet<>(coinPairRepo.findAll());
    }

    @Transactional
    @Override
    public void deleteCoinPair(String coin1, String coin2) {
        coinPairRepo.deleteCoinPairByCoin1AndCoin2(coin1,coin2);
    }
}
