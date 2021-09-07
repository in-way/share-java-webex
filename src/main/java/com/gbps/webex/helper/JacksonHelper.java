

package com.gbps.webex.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.gbps.webex.core.WebExConstant;

import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by  ivy on 2018/1/17.
 */
public final class JacksonHelper {

    private static final DateTimeFormatter DATE_FMT =
        DateTimeFormatter.ofPattern(WebExConstant.STAND_DATE_FMT_STR);
    private static final DateTimeFormatter TIME_FMT =
        DateTimeFormatter.ofPattern(WebExConstant.STAND_TIME_FMT_STR);
    private static final DateTimeFormatter DATE_TIME_FMT =
        DateTimeFormatter.ofPattern(WebExConstant.STAND_DATE_TIME_FMT_STR);

    public static ObjectMapper buildJsr310ObjectMapper() {
        ObjectMapper objectMapper = Jackson2ObjectMapperBuilder.json().createXmlMapper(false).build();

        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new SimpleDateFormat(WebExConstant.STAND_DATE_TIME_FMT_STR));

        // JavaTimeModule LocalDateTime不行
        JSR310Module javaTimeModule = new JSR310Module();
        // JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DATE_FMT));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DATE_FMT));

        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(TIME_FMT));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(TIME_FMT));

        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FMT));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FMT));

        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }
}

