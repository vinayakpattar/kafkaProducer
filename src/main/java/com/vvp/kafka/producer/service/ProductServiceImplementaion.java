package com.vvp.kafka.producer.service;

import com.vvp.kafka.producer.model.ProductCreatedEvent;
import com.vvp.kafka.producer.model.CreateProductModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
public class ProductServiceImplementaion implements ProductService{
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public ProductServiceImplementaion(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductModel createProductModel) throws ExecutionException, InterruptedException {

        String productId = UUID.randomUUID().toString();
        //TO DO : Persist in Database before publishing as Event

        ProductCreatedEvent productCreatedEvent = new ProductCreatedEvent(productId, createProductModel.getTitle(), createProductModel.getPrice(), createProductModel.getQuantity());
//        CompletableFuture<SendResult<String, ProductCreatedEvent>> future =
//                kafkaTemplate.send("product-created-events-topic", productId, productCreatedEvent);

//        future.whenComplete((result, exception)-> {
//           if (exception != null){
//               logger.error("Failed to send event:" + exception.getLocalizedMessage());
//           } else {
//               logger.info("Message sent successfully:" + result.getRecordMetadata());
//           }
//        });
        logger.info("Before publishing event");
        SendResult<String, ProductCreatedEvent> result =
                kafkaTemplate.send("product-created-events-topic", productId, productCreatedEvent).get();
        logger.info("Partition : " + result.getRecordMetadata().partition());
        logger.info("Topic : " + result.getRecordMetadata().topic());
        logger.info("Offset : " + result.getRecordMetadata().offset());
//        future.join();
        logger.info("Returning Product ID");
        return productId;
    }
}
