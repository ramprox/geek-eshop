package ru.ramprox.service.product.config;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class DateToIntegerConverter implements Converter<Date, Integer> {
    @Override
    public Integer convert(MappingContext<Date, Integer> mappingContext) {
        LocalDate now = LocalDate.now();
        LocalDate birthDay = mappingContext.getSource()
                .toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        long years = birthDay.until(now, ChronoUnit.YEARS);
        return (int) years;
    }
}
