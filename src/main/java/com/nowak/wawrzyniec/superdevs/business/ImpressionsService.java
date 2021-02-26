package com.nowak.wawrzyniec.superdevs.business;

import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.spark.sql.functions.*;

@Service
public class ImpressionsService {

    private final DataStoreProvider provider;

    @Autowired
    public ImpressionsService(DataStoreProvider provider) {
        this.provider = provider;
    }

    public List<Row> calculateImpressionsOverTime() {
        return provider.raw() //
                .groupBy(col("Daily")) //
                .agg(sum(col("Impressions"))) //
                .collectAsList();
    }
}
