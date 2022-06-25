package com.alok.mongodb.demo.codec;

import org.bson.Document;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.YearMonth;

@Component
@ReadingConverter
public class DocumentToYearMonthConverter implements Converter<Document, YearMonth> {

    @Override
    public YearMonth convert(@Nullable Document document) {
        if (document == null) return null;

        String[] yMArr = document.getString(YearMonthToStringConverter.Y_M).split("-");

        return YearMonth.of(Integer.parseInt(yMArr[0]), Integer.parseInt(yMArr[1]));
    }
}