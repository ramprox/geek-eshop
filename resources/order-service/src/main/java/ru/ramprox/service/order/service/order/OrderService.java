package ru.ramprox.service.order.service.order;

import reactor.core.publisher.Mono;
import ru.ramprox.service.order.dto.UserReadOrderDto;

public interface OrderService {

    Mono<UserReadOrderDto> save(Long userId);

}
