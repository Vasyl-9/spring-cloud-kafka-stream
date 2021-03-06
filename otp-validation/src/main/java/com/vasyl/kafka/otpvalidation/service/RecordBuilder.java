package com.vasyl.kafka.otpvalidation.service;

import com.vasyl.kafka.otpvalidation.model.PaymentConfirmation;
import com.vasyl.kafka.otpvalidation.model.PaymentRequest;
import com.vasyl.kafka.otpvalidation.model.TransactionStatus;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class RecordBuilder {
    public TransactionStatus getTransactionStatus(PaymentRequest request, PaymentConfirmation confirmation){
        String status = "Failure";
        if(request.getOTP().equals(confirmation.getOTP()))
            status = "Success";

        TransactionStatus transactionStatus = new TransactionStatus();
        transactionStatus.setTransactionID(request.getTransactionID());
        transactionStatus.setStatus(status);
        return transactionStatus;
    }
}