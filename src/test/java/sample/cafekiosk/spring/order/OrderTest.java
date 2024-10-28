package sample.cafekiosk.spring.order;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import sample.cafekiosk.spring.product.Product;
import sample.cafekiosk.spring.product.ProductSellingStatus;
import sample.cafekiosk.spring.product.ProductType;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static sample.cafekiosk.spring.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.product.ProductType.HANDMADE;

class OrderTest {

    @DisplayName("주문 생성 시 상품 리스트에서 주문의 총 금액을 계산한다.")
    @Test
    void calculateTotalPrice() {
        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();
        List<Product> products = List.of(
                createProduct("001", "아메리카노", 4000, HANDMADE, SELLING),
                createProduct("002", "라떼", 4500, HANDMADE, HOLD),
                createProduct("003", "팥빙수", 8000, HANDMADE, STOP_SELLING)
        );

        // when
        Order order = Order.create(products, registeredDateTime);

        // then
        assertThat(order.getTotalPrice()).isEqualTo(16500);

    }

    @DisplayName("주문 생성 시 주문 상태는 INIT이다.")
    @Test
    void init() {
        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();
        List<Product> products = List.of(
                createProduct("001", "아메리카노", 4000, HANDMADE, SELLING),
                createProduct("002", "라떼", 4500, HANDMADE, HOLD),
                createProduct("003", "팥빙수", 8000, HANDMADE, STOP_SELLING)
        );

        // when
        Order order = Order.create(products, registeredDateTime);

        // then
        assertThat(order.getOrderStatus()).isEqualByComparingTo(OrderStatus.INIT);

    }

    @DisplayName("주문 생성 시 주문 등록 시간을 기록한다.")
    @Test
    void registeredDateTime() {
        // given
        LocalDateTime registeredDateTime = LocalDateTime.now();
        List<Product> products = List.of(
                createProduct("001", "아메리카노", 4000, HANDMADE, SELLING),
                createProduct("002", "라떼", 4500, HANDMADE, HOLD),
                createProduct("003", "팥빙수", 8000, HANDMADE, STOP_SELLING)
        );

        // when
        Order order = Order.create(products, registeredDateTime);

        // then
        assertThat(order.getRegisteredDateTime()).isEqualTo(registeredDateTime);

    }

    private Product createProduct(String productNumber, String name,
                                  int price, ProductType type,
                                  ProductSellingStatus sellingStatus) {
        return Product.builder()
                .productNumber(productNumber)
                .name(name)
                .price(price)
                .type(type)
                .sellingStatus(sellingStatus)
                .build();
    }

}