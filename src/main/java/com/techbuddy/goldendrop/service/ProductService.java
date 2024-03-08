package com.techbuddy.goldendrop.service;

import com.techbuddy.goldendrop.configuration.AwsS3Config;
import com.techbuddy.goldendrop.dto.ProductDTO;
import com.techbuddy.goldendrop.exception.InvalidProductException;
import com.techbuddy.goldendrop.mapper.ProductMapper;
import com.techbuddy.goldendrop.model.Product;
import com.techbuddy.goldendrop.model.ProductStockView;
import com.techbuddy.goldendrop.model.Store;
import com.techbuddy.goldendrop.repository.ProductRepository;
import com.techbuddy.goldendrop.repository.ProductStockViewRepository;
import com.techbuddy.goldendrop.request.ProductRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {
    private final ProductRepository productRepository;
    private final StoreService storeService;
    private final ProductMapper productMapper;
    private final ProductStockViewRepository productStockViewRepository;
    private final AwsS3Service awsS3Service;
    private final AwsS3Config awsS3Config;

    public ProductDTO createOrUpdate(ProductRequest productRequest) throws IOException {

        Store store = storeService.validateAndFetchStoreId(productRequest.getStoreId());
        Product productToBeSavedOrUpdated;

        if (productRequest.getProductId() == null) {
            productToBeSavedOrUpdated = productMapper.map(productRequest, store);
        } else {
            Product existingProduct =
                    fetchProductByProductIdAndStoreId(productRequest.getProductId(), productRequest.getStoreId());
            productToBeSavedOrUpdated = productMapper.map(productRequest, store, existingProduct);
        }
        setProductImageName(productRequest, productToBeSavedOrUpdated);
        Product product = productRepository.save(productToBeSavedOrUpdated);
        storeProductImage(productRequest);
        return productMapper.map(product);
    }

    public Product fetchProductByProductIdAndStoreId(Long productId, Long storeId) {
        Optional<Product> product = productRepository.findProductByIdAndAndStoreId(productId, storeId);
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
        if(productRequest.getImageFile() != null) {
            String fileName = parseFileName(productRequest.getImageFile());
            product.setImageName(fileName);
        }
    }

    public void storeProductImage(ProductRequest productRequest) throws IOException {
        if(productRequest.getImageFile() != null) {
            File tempLocalFile = copyUploadToTempFile(productRequest.getImageFile());
            String fileName = parseFileName(productRequest.getImageFile());
            awsS3Service.putObject(getS3ImagePath(fileName),  new FileInputStream(tempLocalFile));
        }
    }

    private String getS3ImagePath(String fileName) {
        return  awsS3Config.getBucketName() + AwsS3Service.S3_FILE_SEPERATOR + fileName;
    }

    private String parseFileName(MultipartFile uploadedFile) {
        return  ObjectUtils.defaultIfNull(uploadedFile.getOriginalFilename(), "tmp")
                .replaceAll("\\s+", "_").replaceAll("/", ":").replaceAll(",","");
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
