package com.nowak.wawrzyniec.superdevs.business;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.junit.jupiter.api.AfterEach;

import java.util.Optional;

public class SparkIntegrationBaseTest {

    private Optional<SparkConf> sparkConf = Optional.empty();

    private Optional<JavaSparkContext> javaSparkContext = Optional.empty();

    private Optional<SparkSession> sparkSession = Optional.empty();

    protected SparkSession spark() {
        if (!sparkSession.isPresent()) {
            sparkSession = Optional.of(createSparkSession());
        }
        return sparkSession.get();
    }

    protected JavaSparkContext jsc() {
        if (!javaSparkContext.isPresent()) {
            javaSparkContext = Optional.of(createJavaSparkContext());
        }
        return javaSparkContext.get();
    }

    protected SparkConf conf() {
        if (!sparkConf.isPresent()) {
            sparkConf = Optional.of(createConf());
        }
        return sparkConf.get();
    }

    protected SparkConf createConf() {
        return new SparkConf()
                .setMaster("local[*]")
                .setAppName(getClass().getSimpleName());
    }

    protected SparkSession createSparkSession() {
        return SparkSession.builder().config(conf())
                .getOrCreate();
    }

    protected JavaSparkContext createJavaSparkContext() {
        return new JavaSparkContext(spark().sparkContext());
    }

    @AfterEach
    public void stopSparkContext() {
        sparkSession.ifPresent(SparkSession::close);
    }

}

