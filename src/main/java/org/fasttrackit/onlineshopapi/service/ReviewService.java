package org.fasttrackit.onlineshopapi.service;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.domain.Review;
import org.fasttrackit.onlineshopapi.domain.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.persistence.ReviewRepository;
import org.fasttrackit.onlineshopapi.transfer.review.CreateReviewRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReviewService.class);

    private final ReviewRepository reviewRepository;
    private final ProductService productService; // we're adding Product dependency here because without a Product,
    // no review can be made. We don't need ObjectMapper because we don't have long logic here to save time typing it

    public ReviewService(ReviewRepository reviewRepository, ProductService productService) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
    }

    @Transactional
    public Review createReview(CreateReviewRequest request) throws ResourceNotFoundException {

        LOGGER.info("Creating review {}", request);

        Review review = new Review();
        review.setContent(request.getContent());

        Product product = productService.getProduct(request.getProductId());
        review.setProduct(product);

        return reviewRepository.save(review);

    }
}
