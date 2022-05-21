package com.vasyl.kafka.bindings;

import org.apache.kafka.streams.kstream.KTable;
import org.springframework.cloud.stream.annotation.Input;

public interface StockListenerBinding {

    @Input("stock-input-channel")
    KTable<String, String> stockInputStream();

}
