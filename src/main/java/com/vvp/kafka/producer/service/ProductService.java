package com.vvp.kafka.producer.service;

import com.vvp.kafka.producer.model.CreateProductModel;

import java.util.concurrent.ExecutionException;

public interface ProductService {
    String createProduct(CreateProductModel createProductModel) throws ExecutionException, InterruptedException;
}
