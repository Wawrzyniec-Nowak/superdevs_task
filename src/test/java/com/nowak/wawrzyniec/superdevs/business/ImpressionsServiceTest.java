package com.nowak.wawrzyniec.superdevs.business;

import com.google.common.collect.Lists;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ImpressionsServiceTest extends SparkIntegrationBaseTest {

    private final DataStoreProvider provider = mock(DataStoreProvider.class);

    private final ImpressionsService service = new ImpressionsService(provider);

    @Test
    public void shouldSumAllImpressionsPerEachDay() {
        when(provider.raw()).thenReturn(prepareDataset());
        List<Row> rows = service.calculateImpressionsOverTime();

        assertEquals(3, rows.size());

        List<Long> impressions = rows.stream().map(row -> row.getLong(1)).collect(Collectors.toList());

        assertThat(impressions).containsExactlyInAnyOrder(67877L, 3988L, 18813L);
    }

    @Test
    public void shouldNotFailOnEmptyDataStore() {
        when(provider.raw()).thenReturn(spark().createDataFrame(Lists.newArrayList(), Raw.class));
        List<Row> rows = service.calculateImpressionsOverTime();

        assertEquals(0, rows.size());
    }

    private Dataset<Row> prepareDataset() {
        ArrayList<Raw> rows = Lists.newArrayList( //
                new Raw("Google Ads", "Adventmarkt Touristik", "11/12/19", 7, 22425), //
                new Raw("Google Ads", "Adventmarkt Touristik", "11/12/19", 16, 45452), //
                new Raw("Google Ads", "GDN_Retargeting", "01/01/19", 32, 17079), //
                new Raw("Twitter Ads", "Pickerl-Erinnerung", "11/18/19", 104, 3988), //
                new Raw("Twitter Ads", "Pickerl-Erinnerung", "01/01/19", 79, 1020), //
                new Raw("Twitter Ads", "Rechtsschutz", "01/01/19", 25, 714)

        );
        return spark().createDataFrame(rows, Raw.class);
    }
}