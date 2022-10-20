package ru.ramprox;

import ru.ramprox.repository.service.dto.LineItem;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class Util {

    public static void assertEqualsLineItems(List<LineItem> expectedLineItems, List<LineItem> resultLineItems) {
        for (int i = 0; i < expectedLineItems.size(); i++) {
            LineItem expectedLineItem = expectedLineItems.get(i);
            LineItem resultLineItem = resultLineItems.get(i);
            assertEquals(expectedLineItem.getProductId(), resultLineItem.getProductId());
            assertNotNull(resultLineItem.getProductDto());
            assertEquals(expectedLineItem.getProductDto().getId(), resultLineItem.getProductDto().getId());
            assertEquals(expectedLineItem.getProductDto().getName(), resultLineItem.getProductDto().getName());
            assertEquals(expectedLineItem.getProductDto().getPrice(), resultLineItem.getProductDto().getPrice());
            assertEquals(expectedLineItem.getQty(), resultLineItem.getQty());
            assertEquals(expectedLineItem.getColor(), resultLineItem.getColor());
            assertEquals(expectedLineItem.getMaterial(), resultLineItem.getMaterial());
        }
    }
}
