package sample.cafekiosk.spring.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sample.cafekiosk.spring.product.requestDTO.ProductCreateRequest;
import sample.cafekiosk.spring.product.requestDTO.ProductCreateServiceRequest;
import sample.cafekiosk.spring.product.responseDTO.ProductResponse;

import java.util.List;
import java.util.stream.Collectors;

import static sample.cafekiosk.spring.product.ProductSellingStatus.SELLING;
import static sample.cafekiosk.spring.product.ProductType.HANDMADE;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse createProduct(ProductCreateServiceRequest request) {

        String nextProductNumber = createNextProductNumber();

        Product product = request.toEntity(nextProductNumber);
        Product savedProduct = productRepository.save(product);

        return ProductResponse.of(savedProduct);
    }

    public List<ProductResponse> getSellingProducts() {
        List<Product> products = productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return products.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }

    private String createNextProductNumber() {
        String latestProductNumber = productRepository.findLatestProduct();

        if (latestProductNumber == null) {
            return "001";
        }

        int latestProductNumberInt = Integer.parseInt(latestProductNumber);
        int nextProductNumberInt = latestProductNumberInt + 1;

        return String.format("%03d", nextProductNumberInt);
    }



}
