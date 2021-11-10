package ru.geekbrains;

import ru.geekbrains.controller.dto.OrderDto;
import ru.geekbrains.persist.model.Order;
import ru.geekbrains.persist.model.OrderDetail;
import ru.geekbrains.service.dto.LineItem;

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
            assertEquals(expectedLineItem.getProductDto().getCost(), resultLineItem.getProductDto().getCost());
            assertEquals(expectedLineItem.getQty(), resultLineItem.getQty());
            assertEquals(expectedLineItem.getColor(), resultLineItem.getColor());
            assertEquals(expectedLineItem.getMaterial(), resultLineItem.getMaterial());
        }
    }

    public static void assertEqualsOrderDtos(List<OrderDto> expectedOrderDtos,
                                             List<OrderDto> resultOrderDtos) {
        for (int i = 0; i < expectedOrderDtos.size(); i++) {
            OrderDto expectedOrderDto = expectedOrderDtos.get(i);
            OrderDto resultOrderDto = resultOrderDtos.get(i);
            assertEquals(expectedOrderDto.getId(), resultOrderDto.getId());
            assertEquals(expectedOrderDto.getCreatedAt(), resultOrderDto.getCreatedAt());
            assertEquals(expectedOrderDto.getPrice(), resultOrderDto.getPrice());
            assertEquals(expectedOrderDto.getStatus(), resultOrderDto.getStatus());
        }
    }

    public static void assertEqualsOrders(List<Order> expectedOrders, List<Order> resultOrders) {
        for (int i = 0; i < expectedOrders.size(); i++) {
            Order expectedOrder = expectedOrders.get(i);
            Order resultOrder = resultOrders.get(i);
            assertEquals(expectedOrder.getId(), resultOrder.getId());
            assertEquals(expectedOrder.getUser().getId(), resultOrder.getUser().getId());
            assertEquals(expectedOrder.getStatus(), resultOrder.getStatus());
            assertEquals(expectedOrder.getCreatedAt(), resultOrder.getCreatedAt());
            List<OrderDetail> expectedDetails = expectedOrder.getProducts();
            List<OrderDetail> resultDetails = resultOrder.getProducts();
            assertEqualsOrderDetails(expectedDetails, resultDetails);
        }
    }

    private static void assertEqualsOrderDetails(List<OrderDetail> expectedDetails,
                                                 List<OrderDetail> resultDetails) {
        for (int i = 0; i < expectedDetails.size(); i++) {
            OrderDetail expectedDetail = expectedDetails.get(i);
            OrderDetail resultDetail = resultDetails.get(i);
            assertEquals(expectedDetail.getId(), resultDetail.getId());
            assertEquals(expectedDetail.getOrder().getId(), resultDetail.getOrder().getId());
            assertEquals(expectedDetail.getProduct().getId(), resultDetail.getProduct().getId());
            assertEquals(expectedDetail.getQty(), resultDetail.getQty());
            assertEquals(expectedDetail.getCost(), resultDetail.getCost());
        }
    }
}
