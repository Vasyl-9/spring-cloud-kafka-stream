package com.vasyl.kafka.avroposfanout.services;

import com.vasyl.kafka.avroposfanout.model.HadoopRecord;
import com.vasyl.kafka.avroposfanout.model.Notification;
import com.vasyl.kafka.model.LineItem;
import com.vasyl.kafka.model.PosInvoice;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RecordBuilder {

    public Notification getNotification(PosInvoice invoice){
        Notification notification = new Notification();
        notification.setInvoiceNumber(invoice.getInvoiceNumber());
        notification.setCustomerCardNo(invoice.getCustomerCardNo());
        notification.setTotalAmount(invoice.getTotalAmount());
        notification.setEarnedLoyaltyPoints(invoice.getTotalAmount() * 0.02);
        return notification;
    }

    public PosInvoice getMaskedInvoice(PosInvoice invoice){
        invoice.setCustomerCardNo(null);
        if (invoice.getDeliveryType().equalsIgnoreCase("HOME-DELIVERY")) {
            invoice.getDeliveryAddress().setAddressLine(null);
            invoice.getDeliveryAddress().setContactNumber(null);
        }
        return invoice;
    }

    public List<HadoopRecord> getHadoopRecords(PosInvoice invoice){
        List<HadoopRecord> records = new ArrayList<>();

        for (LineItem i : invoice.getInvoiceLineItems()) {
            HadoopRecord record = new HadoopRecord();
            record.setInvoiceNumber(invoice.getInvoiceNumber());
            record.setCreatedTime(invoice.getCreatedTime());
            record.setStoreID(invoice.getStoreID());
            record.setPosID(invoice.getPosID());
            record.setCustomerType(invoice.getCustomerType());
            record.setPaymentMethod(invoice.getPaymentMethod());
            record.setDeliveryType(invoice.getDeliveryType());
            record.setItemCode(i.getItemCode());
            record.setItemDescription(i.getItemDescription());
            record.setItemPrice(i.getItemPrice());
            record.setItemQty(i.getItemQty());
            record.setTotalValue(i.getTotalValue());
            if (invoice.getDeliveryType().toString().equalsIgnoreCase("HOME-DELIVERY")) {
                record.setCity(invoice.getDeliveryAddress().getCity());
                record.setState(invoice.getDeliveryAddress().getState());
                record.setPinCode(invoice.getDeliveryAddress().getPinCode());
            }
            records.add(record);
        }
        return records;
    }
}