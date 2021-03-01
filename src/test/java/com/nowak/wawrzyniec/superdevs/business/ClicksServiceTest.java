package com.nowak.wawrzyniec.superdevs.business;

import com.google.common.collect.Lists;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClicksServiceTest extends SparkIntegrationBaseTest {

    private final DataStoreProvider provider = mock(DataStoreProvider.class);

    private final ClicksService service = new ClicksService(provider);

    @Test
    public void shouldSumClicksPerDatasourceBetweenDates() {
        when(provider.raw()).thenReturn(prepareDataset());

        Optional<Object> clicks = service.calculateClicksPerDatasource("Google Ads", "2019-11-01", "2019-11-30");

        assertEquals(23L, clicks.get());
    }

    @Test
    public void shouldReturnZeroWhenThereAreNoClicksBetweenSuchDates() {
        when(provider.raw()).thenReturn(prepareDataset());

        Optional<Object> clicks = service.calculateClicksPerDatasource("Google Ads", "2020-11-01", "2020-11-30");

        assertFalse(clicks.isPresent());
    }

    @Test
    public void shouldReturnZeroWhenThereIsNoSuchDatasource() {
        when(provider.raw()).thenReturn(prepareDataset());

        Optional<Object> clicks = service.calculateClicksPerDatasource("Google", "2020-11-01", "2020-11-30");

        assertFalse(clicks.isPresent());
    }

    @Test
    public void shouldReturnDatasourcesAndCampaignsWithClicksAbove10() {
        when(provider.raw()).thenReturn(prepareDataset());

        List<Row> rows = service.collectDatastoresAndCampaignsAboveThreshold(10);

        assertEquals(2, rows.size());
        assertThat(rows.stream().map(row -> row.getString(0)).collect(Collectors.toList())).containsExactlyInAnyOrder("Google Ads", "Google Ads");
        assertThat(rows.stream().map(row -> row.getString(1)).collect(Collectors.toList())).containsExactlyInAnyOrder("Adventmarkt Touristik", "GDN_Retargeting");
        assertThat(rows.stream().map(row -> row.getLong(2)).collect(Collectors.toList())).containsExactlyInAnyOrder(16L, 32L);
    }

    private Dataset<Row> prepareDataset() {
        ArrayList<Raw> rows = Lists.newArrayList( //
                new Raw("Google Ads", "Adventmarkt Touristik", "11/12/19", 7, 22425), //
                new Raw("Google Ads", "Adventmarkt Touristik", "11/13/19", 16, 45452), //
                new Raw("Google Ads", "GDN_Retargeting", "01/01/19", 32, 17079) //
        );
        return spark().createDataFrame(rows, Raw.class);
    }
}