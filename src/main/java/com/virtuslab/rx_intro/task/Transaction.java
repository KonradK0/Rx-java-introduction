package com.virtuslab.rx_intro.task;

import java.math.BigDecimal;

class Transaction {

    final String transactionId;
    final BigDecimal value;

    Transaction(String transactionId, BigDecimal value) {
        this.transactionId = transactionId;
        this.value = value;
    }
}
