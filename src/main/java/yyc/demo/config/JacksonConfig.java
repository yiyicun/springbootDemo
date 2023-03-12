package yyc.demo.config;


import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator.Feature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@AutoConfigureBefore({JacksonAutoConfiguration.class})
@Configuration
public class JacksonConfig {
    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    public String dateTimeValue;

    @Value("${spring.jackson.time-zone:GMT+08:00")
    public String timeZone;

    public static final String localDateValue = "yyyy-MM-dd";

    public JacksonConfig(){

    }

    @Bean(name={"format"})
    public DateTimeFormatter dateTimeFormat(){
        return DateTimeFormatter.ofPattern(this.dateTimeValue);
    }

    private DateTimeFormatter dateFormat(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    @Bean
    public JavaTimeModule jdk8TimeModule(){
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDate.class,new LocalDateSerializer(this.dateFormat()));
        javaTimeModule.addDeserializer(LocalDate.class,new LocalDateDeserializer(this.dateFormat()));
        javaTimeModule.addSerializer(LocalDateTime.class,new LocalDateTimeSerializer(this.dateTimeFormat()));
        javaTimeModule.addDeserializer(LocalDateTime.class,new LocalDateTimeDeserializer(this.dateTimeFormat()));
        return javaTimeModule;
    }

    @Bean(name={"baseObjectMapper"})
    public ObjectMapper JacksonObjectMapper(Jackson2ObjectMapperBuilder builder){
        ObjectMapper om = builder.createXmlMapper(false).build();
        om.setSerializationInclusion(Include.ALWAYS);
        om.setDateFormat(new SimpleDateFormat(this.dateTimeValue));
        if(StringUtils.isEmpty(this.timeZone.trim())){
            om.setTimeZone(TimeZone.getDefault());
        } else {
            om.setTimeZone(TimeZone.getTimeZone(this.timeZone.trim()));
        }
        om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        om.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
        om.enable(new Feature[]{Feature.WRITE_BIGDECIMAL_AS_PLAIN});
        om.registerModule(new Jdk8Module());
        om.registerModule(this.jdk8TimeModule());
        return om;
    }
}
