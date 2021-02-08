package guru.springframework.msscbeerservice.services.order;

import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.events.ValidateOrderRequest;
import guru.sfg.brewery.model.events.ValidateOrderResult;
import guru.springframework.msscbeerservice.config.JMSConfig;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;


@RequiredArgsConstructor
@Component
public class BeerOrderValidationListener {

    private final JmsTemplate jmsTemplate;
    private final BeerOrderValidator beerOrderValidator;

    @JmsListener(destination = JMSConfig.VALIDATE_ORDER_QUEUE)
    public void listen(@Payload ValidateOrderRequest validateOrderRequest, @Headers MessageHeaders headers, Message message) {
        System.out.println("#### Got ValidateOrderRequest");

        BeerOrderDto beerOrderDto = validateOrderRequest.getBeerOrderDto();
        Boolean isValid = beerOrderValidator.validateOrder(beerOrderDto);
        sendValidateOrderResponse(beerOrderDto.getId(), isValid);
    }

    private void sendValidateOrderResponse(UUID orderId, boolean isValid) {
        ValidateOrderResult response = ValidateOrderResult.builder()
                .isValid(isValid)
                .orderId(orderId)
                .build();

        jmsTemplate.convertAndSend(JMSConfig.VALIDATE_ORDER_RESPONSE_QUEUE, response);
    }


}
