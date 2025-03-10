package com.me.binancealerts.repos;

import com.me.binancealerts.entities.CoinPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;


@Repository
public interface CoinPairRepo extends JpaRepository<CoinPair, Long> {
    Optional<CoinPair> findCoinPairByCoin1AndCoin2(String coin1, String coin2);
    Optional<Set<CoinPair>> findAllByCoin1OrCoin2(String coin1, String coin2);
    void deleteCoinPairByCoin1AndCoin2(String coin1, String coin2);
    Set<CoinPair> findAllBy();

}
