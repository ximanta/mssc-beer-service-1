package guru.springframework.msscbeerservice.services.order;

import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.BeerOrderLineDto;
import guru.springframework.msscbeerservice.domain.Beer;
import guru.springframework.msscbeerservice.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidator {

    private final BeerRepository beerRepository;

    public Boolean validateOrder(BeerOrderDto beerOrderDto) {

//        for (BeerOrderLineDto beerOrderLineDto : beerOrderDto.getBeerOrderLines()) {
//            if (beerRepository.findByUpc(beerOrderLineDto.getUpc()) == null) {
//                return false;
//            }
//        }
//
//        return true;

        AtomicInteger beersNotFound = new AtomicInteger();
        for (BeerOrderLineDto beerOrderLineDto : beerOrderDto.getBeerOrderLines()) {

            if (beerRepository.findByUpc(beerOrderLineDto.getUpc()) == null) {
                beersNotFound.incrementAndGet();
            }
        }

        return beersNotFound.get() == 0;
    }
}
