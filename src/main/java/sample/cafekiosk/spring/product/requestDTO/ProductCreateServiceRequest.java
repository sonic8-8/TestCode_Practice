package sample.cafekiosk.spring.product.requestDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sample.cafekiosk.spring.product.Product;
import sample.cafekiosk.spring.product.ProductSellingStatus;
import sample.cafekiosk.spring.product.ProductType;

@Getter
@NoArgsConstructor
public class ProductCreateServiceRequest {


    private ProductType type;
    private ProductSellingStatus sellingStatus;
    private String name;
    private int price;

    @Builder
    public ProductCreateServiceRequest(ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        this.type = type;
        this.sellingStatus = sellingStatus;
        this.name = name;
        this.price = price;
    }

    public Product toEntity(String nextProductNumber) {
        return Product.builder()
                .productNumber(nextProductNumber)
                .name(name)
                .type(type)
                .sellingStatus(sellingStatus)
                .price(price)
                .build();
    }

}

