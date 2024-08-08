package sample.cafekiosk.spring.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.order.OrderRepository;
import sample.cafekiosk.spring.order.OrderService;
import sample.cafekiosk.spring.order.requestDTO.OrderCreateRequest;
import sample.cafekiosk.spring.order.responseDTO.OrderResponse;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static sample.cafekiosk.spring.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.product.ProductType.*;

@ActiveProfiles("test")
@SpringBootTest
class ProductTypeTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;


    @DisplayName("재고 속성 상품인지 확인할 수 있다.")
    @Test
    void containsStockType() {
        // given
        ProductType givenType = HANDMADE;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isFalse();

    }

    @DisplayName("재고 속성 상품인지 확인할 수 있다.")
    @Test
    void containsStockType2() {
        // given
        ProductType givenType = BAKERY;

        // when
        boolean result = ProductType.containsStockType(givenType);

        // then
        assertThat(result).isTrue();

    }

    private Product createProduct(String productNumber, ProductType productType, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(productType)
                .price(price)
                .sellingStatus(SELLING)
                .name("상품")
                .build();
    }

}