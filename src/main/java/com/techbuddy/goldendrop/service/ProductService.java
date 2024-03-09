package com.techbuddy.goldendrop.service;

import com.techbuddy.goldendrop.configuration.AwsS3Config;
import com.techbuddy.goldendrop.dto.ProductDTO;
import com.techbuddy.goldendrop.exception.InvalidProductException;
import com.techbuddy.goldendrop.mapper.ProductMapper;
import com.techbuddy.goldendrop.model.Product;
import com.techbuddy.goldendrop.model.ProductStockView;
import com.techbuddy.goldendrop.model.Store;
import com.techbuddy.goldendrop.model.User;
import com.techbuddy.goldendrop.repository.ProductRepository;
import com.techbuddy.goldendrop.repository.ProductStockViewRepository;
import com.techbuddy.goldendrop.request.ProductRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {
    private final ProductRepository repository;
    private final StoreService storeService;
    private final ProductMapper productMapper;
    private final ProductStockViewRepository productStockViewRepository;
    private final AwsS3Service awsS3Service;
    private final AwsS3Config awsS3Config;
    private final UserService userService;

    public Page<Product> findAll(Specification<Product> specification, Pageable pageable) {
        return repository.findAll(specification, pageable);
    }

    public List<Product> setPresignedUrlsForProduct(List<Product> products) {
        return products.stream().map(product -> {
            product.setUrl(getPresignedProductUrl(product));
            return product;
        }).collect(Collectors.toList());
    }

    public ProductDTO createOrUpdate(ProductRequest productRequest) throws IOException {

        User user = userService.fetchUser();
        Long storeId = user.getStore().getId();
        Store store = storeService.validateAndFetchStoreId(storeId);
        Product productToBeSavedOrUpdated;

        if (productRequest.getId() == null) {
            productToBeSavedOrUpdated = productMapper.map(productRequest, store);
        } else {
            Product existingProduct = fetchProductByProductIdAndStoreId(productRequest.getId(), storeId);
            productToBeSavedOrUpdated = productMapper.map(productRequest, store, existingProduct);
        }
        setProductImageName(productRequest, productToBeSavedOrUpdated);
        Product product = repository.save(productToBeSavedOrUpdated);
        storeProductImage(product, productRequest);
        return productMapper.map(product);
    }

    public Product fetchProductByProductIdAndStoreId(Long productId, Long storeId) {
        Optional<Product> product = repository.findProductByIdAndAndStoreId(productId, storeId);
        if (product.isEmpty()) {
            throw new InvalidProductException("Invalid productId/StoreId");
        }
        return product.get();
    }

    public List<ProductDTO> getProductsByStoreId(Long storeId, int pageNumber, int pageSize) {
        storeService.validateAndFetchStoreId(storeId);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        List<ProductStockView> products = productStockViewRepository.findProductStockViewByStoreId(storeId, pageable);
        return products.stream().map(productMapper::map).toList();
    }

    public List<ProductDTO> getTopFiveProducts(Long storeId) {
        if (storeId != null) {
            storeService.validateAndFetchStoreId(storeId);
        }

        List<ProductStockView> products = productStockViewRepository.findTop5ByStoreIdOrderByOutQuantityDesc(storeId);
        return products.stream().map(productMapper::map).toList();
    }

    private void setProductImageName(ProductRequest productRequest, Product product) {
        if (productRequest.getImageFile() != null) {
            String fileName = parseFileName(productRequest.getImageFile());
            product.setImageName(fileName);
        }
    }

    public void storeProductImage(Product product, ProductRequest productRequest) throws IOException {
        if (productRequest.getImageFile() != null) {
            File tempLocalFile = copyUploadToTempFile(productRequest.getImageFile());
            awsS3Service.putObject(getS3ImagePath(product), new FileInputStream(tempLocalFile));
        }
    }

    private URL getPresignedProductUrl(Product product) {
        return awsS3Service.generatePreSignedURL(getS3ImagePath(product));
    }

    private String getS3ImagePath(Product product) {
        return "Products" + AwsS3Service.S3_FILE_SEPERATOR + product.getId() +
                AwsS3Service.S3_FILE_SEPERATOR + product.getImageName();
    }

    private String parseFileName(MultipartFile uploadedFile) {
        return ObjectUtils.defaultIfNull(uploadedFile.getOriginalFilename(), "tmp")
                .replaceAll("\\s+", "_")
                .replaceAll("/", ":")
                .replaceAll(",", "");
    }

    private File copyUploadToTempFile(MultipartFile uploadedFile) throws IOException {

        String fileName = uploadedFile.getOriginalFilename().replaceAll("\\s+", "_");
        String fileBaseName = FilenameUtils.getBaseName(fileName);
        String extension = FilenameUtils.getExtension(fileName);
        if (StringUtils.isNotBlank(extension)) {
            extension = "." + extension;
        }
        File tempFile = null;
        tempFile = File.createTempFile(fileBaseName + "_" + extension.substring(1), extension);
        uploadedFile.transferTo(tempFile);
        return tempFile;
    }
}
