package com.alok.mongodb.demo.codec;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

@Component
@WritingConverter
public class YearMonthToStringConverter implements Converter<YearMonth, String> {
    static final String Y_M = "yM";

    @Override
    public String convert(@Nullable YearMonth yearMonth) {
        if (yearMonth == null) return null;

        return String.format("%d-%02d", yearMonth.getYear(), yearMonth.getMonthValue());
    }
}