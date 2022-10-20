package ru.ramprox.service.product.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.ramprox.service.product.controller.dto.RegisterData;
import ru.ramprox.persist.model.User;

import java.util.Date;

@Configuration
public class Config {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);
        TypeMap<RegisterData, User> typeMap = mapper.createTypeMap(RegisterData.class, User.class);
        Converter<Date, Integer> converter = new DateToIntegerConverter();
        typeMap.addMappings(map1 -> map1.using(converter).map(RegisterData::getBirthDay, User::setAge));
        return mapper;
    }
}
