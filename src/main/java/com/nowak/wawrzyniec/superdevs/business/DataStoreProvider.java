package com.nowak.wawrzyniec.superdevs.business;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class DataStoreProvider {

    private final SparkSession session;

    @Value("${input.data.store}")
    private String dataStore;

    @Autowired
    DataStoreProvider(SparkSession session) {
        this.session = session;
    }

    Dataset<Row> raw() {
        return session.read() //
                .format("csv") //
                .option("header", "true") //
                .load(dataStore);
    }
}
