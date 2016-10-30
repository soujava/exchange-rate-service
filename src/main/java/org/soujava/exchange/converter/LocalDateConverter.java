package org.soujava.exchange.converter;

import javax.persistence.AttributeConverter;
import java.time.LocalDate;
import java.util.Date;

public class LocalDateConverter implements AttributeConverter<LocalDate, Date> {
    @Override
    public Date convertToDatabaseColumn(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    @Override
    public LocalDate convertToEntityAttribute(Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }
}
