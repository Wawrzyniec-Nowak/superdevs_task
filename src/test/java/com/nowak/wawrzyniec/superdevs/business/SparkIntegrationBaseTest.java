package com.nowak.wawrzyniec.superdevs.business;

import org.apache.spark.SparkConf;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.AfterEach;

class SparkIntegrationBaseTest {

    private SparkConf sparkConf = null;

    private SparkSession sparkSession = null;

    SparkSession spark() {
        if (sparkSession == null) {
            sparkSession = createSparkSession();
        }
        return sparkSession;
    }

    private SparkConf conf() {
        if (sparkConf == null) {
            sparkConf = createConf();
        }
        return sparkConf;
    }

    private SparkConf createConf() {
        return new SparkConf()
                .setMaster("local[*]")
                .setAppName(getClass().getSimpleName());
    }

    private SparkSession createSparkSession() {
        return SparkSession.builder().config(conf())
                .getOrCreate();
    }

    @AfterEach
    public void stopSparkContext() {
        if (sparkSession != null) {
            sparkSession.close();
        }
    }

}

