package com.auroralibrary.library.configuration;

import java.time.format.DateTimeFormatter;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DateConfig {

  public static final String DATE_FORMAT = "dd/MM/yyyy";
  private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

  @Bean
  public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
    return builder -> {
      builder.simpleDateFormat(DATE_FORMAT);
      builder.serializers(new LocalDateSerializer(formatter));
      builder.deserializers(new LocalDateDeserializer(formatter));
    };
  }
}
