package ru.ramprox.service.order.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.ramprox.service.order.database.entity.Order;
import ru.ramprox.service.order.database.entity.OrderDetail;
import ru.ramprox.service.order.database.entity.OrderDetailID;
import ru.ramprox.service.order.database.repository.OrderDetailRepository;
import ru.ramprox.service.order.database.repository.OrderRepository;
import ru.ramprox.service.order.dto.LineItem;
import ru.ramprox.service.order.dto.ProductCartDto;
import ru.ramprox.service.order.dto.ProductDto;
import ru.ramprox.service.order.dto.UserReadOrderDto;
import ru.ramprox.service.order.service.cart.CartService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final WebClient productWebClient;

    private final CartService cartService;

    private final String productGetUrl;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderDetailRepository orderDetailRepository,
                            WebClient productWebClient,
                            CartService cartService,
                            @Value("${productService.productGetUrl}") String productGetUrl) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productWebClient = productWebClient;
        this.cartService = cartService;
        this.productGetUrl = productGetUrl;
    }

    @Override
    public Mono<UserReadOrderDto> save(Long userId) {
        return Flux.fromIterable(cartService.getProducts())
                .flatMap(this::getLineItem)
                .collectList()
                .map(listProductDto -> {
                    Order order = saveOrder(listProductDto, userId);
                    saveOrderDetails(order, listProductDto);
                    return order;
                }).map(order -> new UserReadOrderDto(order.getUserId(), order.getCreatedAt(),
                        order.getPrice().toString(), order.getStatus()));
    }

    private Mono<LineItem> getLineItem(ProductCartDto productCartDto) {
        return productWebClient.get()
                .uri(uriBuilder -> uriBuilder.path(productGetUrl).build(productCartDto.getProductId()))
                .retrieve()
                .bodyToMono(ProductDto.class)
                .map(productDto ->
                        new LineItem(productDto.getId(),
                                productDto.getPrice(),
                                productCartDto.getQty()));
    }

    private Order saveOrder(List<LineItem> listProductDto, Long userId) {
        BigDecimal price = listProductDto
                .stream()
                .map(lineItem -> new BigDecimal(lineItem.getPrice())
                        .multiply(new BigDecimal(lineItem.getQty())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = new Order(price, LocalDateTime.now(), userId);
        return orderRepository.save(order);
    }

    private void saveOrderDetails(Order order, List<LineItem> lineItems) {
        List<OrderDetail> orderDetails = lineItems
                .stream()
                .map(lineItem -> {
                    OrderDetailID orderDetailID = new OrderDetailID(lineItem.getProductId(), order.getId());
                    return new OrderDetail(orderDetailID, lineItem.getQty(), new BigDecimal(lineItem.getPrice()));
                }).collect(Collectors.toList());
        orderDetailRepository.saveAll(orderDetails);
    }
}
