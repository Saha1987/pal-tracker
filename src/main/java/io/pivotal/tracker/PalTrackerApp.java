package io.pivotal.tracker;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.sql.DataSource;

@SpringBootApplication
@EnableSwagger2
public class PalTrackerApp {

    public static void main(String[] arin) {
        SpringApplication.run(PalTrackerApp.class, arin);
    }

    @Bean
    TimeEntryRepository timeEntryRepository() {
        return new InMemoryTimeEntryRepository();
    }

    @Bean
    TimeEntryRepository timeEntryRepository(DataSource dataSource) {
        return new JdbcTimeEntryRepository(dataSource);
    }

    @Bean
    public ObjectMapper jsonObjectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .serializationInclusion(JsonInclude.Include.NON_NULL) // Don’t include null values
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) //ISODate
                .modules(new JavaTimeModule())
                .build();
    }
}