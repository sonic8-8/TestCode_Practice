package sample.cafekiosk.spring.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.IntegrationTestSupport;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static sample.cafekiosk.spring.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.product.ProductType.HANDMADE;

@Transactional
class ProductRepositoryTest extends IntegrationTestSupport {

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("원하는 판매상태를 가진 상품들을 조회한다.")
    @Test
    void findAllBySellingStatusIn() {
        // given
        Product product1 = Product.builder()
                .productNumber("001")
                .name("아메리카노")
                .price(4000)
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .build();
        Product product2 = Product.builder()
                .productNumber("002")
                .name("라떼")
                .price(4500)
                .type(HANDMADE)
                .sellingStatus(HOLD)
                .build();
        Product product3 = Product.builder()
                .productNumber("003")
                .name("팥빙수")
                .price(8000)
                .type(HANDMADE)
                .sellingStatus(STOP_SELLING)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllByProductNumberIn(List.of("001", "002"));

        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "price", "type", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", 4000, HANDMADE, SELLING),
                        tuple("002", "라떼", 4500, HANDMADE, HOLD)
                );
    }

    @DisplayName("상품 번호 리스트로 상품들을 조회한다.")
    @Test
    void findAllByProductNumberIn() {
        // given
        Product product1 = Product.builder()
                .productNumber("001")
                .name("아메리카노")
                .price(4000)
                .type(HANDMADE)
                .sellingStatus(SELLING)
                .build();
        Product product2 = Product.builder()
                .productNumber("002")
                .name("라떼")
                .price(4500)
                .type(HANDMADE)
                .sellingStatus(HOLD)
                .build();
        Product product3 = Product.builder()
                .productNumber("003")
                .name("팥빙수")
                .price(8000)
                .type(HANDMADE)
                .sellingStatus(STOP_SELLING)
                .build();

        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        List<Product> products = productRepository.findAllBySellingStatusIn(List.of(SELLING, HOLD));

        // then
        assertThat(products).hasSize(2)
                .extracting("productNumber", "name", "price", "type", "sellingStatus")
                .containsExactlyInAnyOrder(
                        tuple("001", "아메리카노", 4000, HANDMADE, SELLING),
                        tuple("002", "라떼", 4500, HANDMADE, HOLD)
                );
    }

    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어온다.")
    @Test
    void findLatestProduct() {
        // given
        String targetProductNumber = "003";
        Product product1 = createProduct("001", "아메리카노",4000, HANDMADE, SELLING);
        Product product2 = createProduct("002", "라떼",4500, HANDMADE, HOLD);
        Product product3 = createProduct(targetProductNumber, "팥빙수",8000, HANDMADE, STOP_SELLING);
        productRepository.saveAll(List.of(product1, product2, product3));

        // when
        String latestProductNumber = productRepository.findLatestProduct();

        // then
        assertThat(latestProductNumber).isEqualTo("003");
    }

    private Product createProduct(String productNumber, String name, int price, ProductType productType, ProductSellingStatus productSellingStatus) {
        return Product.builder()
                .productNumber(productNumber)
                .name(name)
                .price(price)
                .type(productType)
                .sellingStatus(productSellingStatus)
                .build();
    }

    @DisplayName("가장 마지막으로 저장한 상품의 상품번호를 읽어올 때 상품이 하나도 없을 경우는 null을 반환한다.")
    @Test
    void findLatestProductWhenProductIsEmpty() {
        // given

        // when
        String latestProductNumber = productRepository.findLatestProduct();

        // then
        assertThat(latestProductNumber).isNull();
    }

}