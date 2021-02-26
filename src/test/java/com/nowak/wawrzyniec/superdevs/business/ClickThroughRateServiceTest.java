package com.nowak.wawrzyniec.superdevs.business;

import com.google.common.collect.Lists;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClickThroughRateServiceTest extends SparkIntegrationBaseTest {

    private final DataStoreProvider provider = mock(DataStoreProvider.class);

    private final ClickThroughRateService service = new ClickThroughRateService(provider);

    @Test
    public void shouldSumAllClicksPerDatasourceAndCampaignAndDivideThemBySumOfAllImpressionsPerDatasourceAndCampaign() {
        when(provider.raw()).thenReturn(prepareDataset());
        List<Row> rows = service.calculateCTRPerDatasourceAndCampaign();

        assertEquals(4, rows.size());

        List<Double> ctr = rows.stream() //
                .map(row -> row.getDouble(2)) //
                .map(number -> BigDecimal.valueOf(number) //
                        .setScale(10, RoundingMode.HALF_UP) //
                        .doubleValue()) //
                .collect(Collectors.toList());

        assertThat(ctr).containsExactlyInAnyOrder(0.0003388482, 0.001873646, 0.0365415335, 0.0350140056);
    }

    private Dataset<Row> prepareDataset() {
        ArrayList<Raw> rows = Lists.newArrayList( //
                new Raw("Google Ads", "Adventmarkt Touristik", "11/12/19", 7, 22425), //
                new Raw("Google Ads", "Adventmarkt Touristik", "11/13/19", 16, 45452), //
                new Raw("Google Ads", "GDN_Retargeting", "01/01/19", 32, 17079), //
                new Raw("Twitter Ads", "Pickerl-Erinnerung", "11/18/19", 104, 3988), //
                new Raw("Twitter Ads", "Pickerl-Erinnerung", "11/19/19", 79, 1020), //
                new Raw("Twitter Ads", "Rechtsschutz", "02/17/19", 25, 714)

        );
        return spark().createDataFrame(rows, Raw.class);
    }
}