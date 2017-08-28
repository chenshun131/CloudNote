package com.chenshun.studyapp.jms.common;

import java.io.Serializable;

/**
 * User: mew <p />
 * Time: 17/8/25 13:44  <p />
 * Version: V1.0  <p />
 * Description:  <p />
 */
public class TradeData implements Serializable {

    private static final long serialVersionUID = -5429609469903861818L;

    private long tradeId;

    private String side;

    private String symbol;

    private long shares;

    public TradeData() {
    }

    public TradeData(long tradeId, String side, String symbol, long shares) {
        setTradeId(tradeId);
        setSide(side);
        setSymbol(symbol);
        setShares(shares);
    }

    public long getTradeId() {
        return tradeId;
    }

    public void setTradeId(long tradeId) {
        this.tradeId = tradeId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public long getShares() {
        return shares;
    }

    public void setShares(long shares) {
        this.shares = shares;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String toString() {
        return tradeId + "," + side + "," + symbol + "," + shares + " shares";
    }

}
