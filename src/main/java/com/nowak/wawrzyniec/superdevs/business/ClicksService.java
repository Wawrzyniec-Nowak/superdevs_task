package com.nowak.wawrzyniec.superdevs.business;

import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import static org.apache.spark.sql.functions.*;

@Service
public class ClicksService {

    private static final Logger LOGGER = Logger.getLogger(ClicksService.class.getName());

    private final DataStoreProvider provider;

    @Autowired
    public ClicksService(DataStoreProvider provider) {
        this.provider = provider;
    }

    public Optional<Object> calculateClicksPerDatasource(String datasource, String since, String till) {
        LOGGER.info("Calculating clicks per datasource " + datasource + " for date period (" + since + ", " + till + ")");

        return Optional.ofNullable(provider.raw() //
                .filter(col("Datasource").equalTo(datasource)) //
                .filter(to_date(col("Daily"), "MM/dd/yy").gt(format(since))) //
                .filter(to_date(col("Daily"), "MM/dd/yy").lt(format(till))) //
                .select(col("Clicks")) //
                .agg(sum("Clicks").cast("Long")) //
                .first() //
                .get(0));
    }

    private LocalDate format(String date) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public List<Row> collectDatastoresAndCampaignsAboveThreshold(int threshold) {
        LOGGER.info("Collecting datastores and campaigns with clicks above " + threshold);

        return provider.raw() //
                .filter(col("Clicks").gt(threshold)) //
                .orderBy(col("Clicks"))
                .select(col("Datasource"), col("Campaign"), col("Clicks").cast("Long")) //
                .collectAsList();

    }
}
