package com.nowak.wawrzyniec.superdevs.business;

import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.apache.spark.sql.functions.col;
import static org.apache.spark.sql.functions.sum;

@Service
public class ClickThroughRateService {

    private static final Logger LOGGER = Logger.getLogger(ClickThroughRateService.class.getName());

    private final DataStoreProvider provider;

    @Autowired
    public ClickThroughRateService(DataStoreProvider provider) {
        this.provider = provider;
    }

    public List<Row> calculateCTRPerDatasourceAndCampaign() {
        LOGGER.info("Calculating CTR");

        return provider.raw() //
                .groupBy(col("Datasource"), col("Campaign")) //
                .agg(sum(col("Impressions")).alias("sum_of_impressions"), sum(col("Clicks")).alias("sum_of_clicks")) //
                .withColumn("ctr", col("sum_of_clicks").divide(col("sum_of_impressions")).cast("Double")) //
                .drop("sum_of_impressions", "sum_of_clicks")
                .collectAsList();
    }
}