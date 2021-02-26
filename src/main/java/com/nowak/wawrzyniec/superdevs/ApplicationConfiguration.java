package com.nowak.wawrzyniec.superdevs;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Value("${application.name:superdevs task}")
    private String appName;

    @Value("${spark.home.directory}")
    private String sparkHome;

    @Value("${master.uri:local}")
    private String masterUri;

    @Bean
    public SparkSession sparkSession() {
        return SparkSession
                .builder()
                .sparkContext(javaSparkContext().sc())
                .appName(appName)
                .getOrCreate();
    }

    private SparkConf sparkConf() {
        return new SparkConf()
                .setAppName(appName)
                .setSparkHome(sparkHome)
                .setMaster(masterUri);
    }

    private JavaSparkContext javaSparkContext() {
        return new JavaSparkContext(sparkConf());
    }
}
