package Registration.Api;

import Registration.IO.ProspectiveDriverFileParser;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;

@SpringBootApplication
public class RegistrationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(RegistrationServiceApplication.class, args);
    }

    @Bean
    Clock evaluationClock(@Value("${app.evaluation-date:}") String date) {
        return date.isBlank() ? Clock.systemUTC()
                : Clock.fixed(LocalDate.parse(date).atStartOfDay().toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
    }

    @Bean
    ProspectiveDriverFileParser applicationParser() {
        return new ProspectiveDriverFileParser();
    }

    @Bean
    Jackson2ObjectMapperBuilderCustomizer strictJson() {
        return builder -> builder.featuresToEnable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION,
                DeserializationFeature.FAIL_ON_TRAILING_TOKENS);
    }
}
