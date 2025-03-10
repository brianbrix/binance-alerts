package com.me.binancealerts.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CoinPair {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    private String coin1;
    private String coin2;
    private Double highPrice;
    private Double lastPrice;


    public String getCoin1() {
        return coin1;
    }
    public void setCoin1(String coin1) {
        this.coin1 = coin1;
    }
    public String getCoin2() {
        return coin2;
    }
    public void setCoin2(String coin2) {
        this.coin2 = coin2;
    }

    public Double getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(Double highPrice) {
        this.highPrice = highPrice;
    }

    public Double getLastPrice() {
        return lastPrice;
    }

    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    @Override
    public String toString() {
        return "CoinPair{" +
                "id=" + id +
                ", coin1='" + coin1 + '\'' +
                ", coin2='" + coin2 + '\'' +
                ", highPrice=" + highPrice +
                ", lastPrice=" + lastPrice +
                '}';
    }
}
