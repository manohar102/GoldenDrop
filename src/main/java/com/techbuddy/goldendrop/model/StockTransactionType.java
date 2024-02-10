package com.techbuddy.goldendrop.model;

public enum StockTransactionType {
    IN,
    OUT;

    public static boolean isOutStock(StockTransactionType stockTransactionType) {
        return OUT.equals(stockTransactionType);
    }
}
