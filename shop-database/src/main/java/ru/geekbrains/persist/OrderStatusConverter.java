package ru.geekbrains.persist;

import ru.geekbrains.persist.model.Order;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<Order.Status, String> {
    @Override
    public String convertToDatabaseColumn(Order.Status status) {
        if(status == null) {
            return null;
        }
        return status.getDescription();
    }

    @Override
    public Order.Status convertToEntityAttribute(String description) {
        if(description == null) {
            return null;
        }
        return Stream.of(Order.Status.values())
                .filter(s -> s.getDescription().equals(description))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
