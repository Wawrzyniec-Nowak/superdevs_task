package com.nowak.wawrzyniec.superdevs.business;

import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.sum;

@Service
public class ImpressionsService {

    private static final Logger LOGGER = Logger.getLogger(ImpressionsService.class.getName());

    private final DataStoreProvider provider;

    @Autowired
    public ImpressionsService(DataStoreProvider provider) {
        this.provider = provider;
    }

    public List<Row> calculateImpressionsOverTime() {
        LOGGER.info("Calculating impressions over time");

        return provider.raw() //
                .groupBy(col("Daily")) //
                .agg(sum(col("Impressions")).cast("Long")) //
                .collectAsList();
    }
}
