package com.nowak.wawrzyniec.superdevs;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class ApplicationConfiguration {

    @Value("${application.name:superdevs task}")
    private String appName;

    @Value("${master.uri:local}")
    private String masterUri;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nowak.wawrzyniec.superdevs.api"))
                .build();
    }

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
                .setMaster(masterUri);
    }

    private JavaSparkContext javaSparkContext() {
        return new JavaSparkContext(sparkConf());
    }
}
