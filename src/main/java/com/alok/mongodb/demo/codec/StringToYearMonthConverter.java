package com.alok.mongodb.demo.codec;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

@Component
@ReadingConverter
public class StringToYearMonthConverter implements Converter<String, YearMonth> {

    @Override
    public YearMonth convert(@Nullable String strYearMonth) {
        if (strYearMonth == null) return null;

        String[] yMArr = strYearMonth.split("-");

        return YearMonth.of(Integer.parseInt(yMArr[0]), Integer.parseInt(yMArr[1]));
    }
}