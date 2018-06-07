package com.virtuslab.rx_intro.task;

class Confirmation {

    final String transactionId;
    final boolean isConfirmed;

    Confirmation(String transactionId, boolean isConfirmed) {
        this.transactionId = transactionId;
        this.isConfirmed = isConfirmed;
    }
}
