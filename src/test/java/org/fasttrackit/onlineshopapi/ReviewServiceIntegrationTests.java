package org.fasttrackit.onlineshopapi;

import org.fasttrackit.onlineshopapi.domain.Product;
import org.fasttrackit.onlineshopapi.domain.Review;
import org.fasttrackit.onlineshopapi.domain.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshopapi.service.ReviewService;
import org.fasttrackit.onlineshopapi.setps.ProductSteps;
import org.fasttrackit.onlineshopapi.transfer.review.CreateReviewRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.notNullValue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReviewServiceIntegrationTests {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private ProductSteps productSteps;

    @Test
    public void testCreateReview_WhenValidRequest_thenReturnCreatedReview() throws ResourceNotFoundException {

        // the first step to create an object to test is to create a REQUEST (which we have a class for)
        // then we will need assign this object's parameters to the request (which are in our example"Content,Id,Product)
        // and here we need to create a Product first that will have the Review object that we're creating... as follows
        Product product = productSteps.createProductMethod();


        CreateReviewRequest request = new CreateReviewRequest();

        // we need to set all that values which we sat that they cant accept a NULL value
        request.setContent("I like this product!");
        request.setProductId(product.getId());
        // ordering is important when we want to test, if we put created "review" object tow lines before (before we
        // set the parameters to the request), we will get a "Null Pointer Exception" error that is thrown when an
        // application attempts to use an object reference that has the null value (which will be the request that has
        // no parameters...
        Review review = reviewService.createReview(request);

        //Once we create the Object that we want to test, we start with the assertions as follows:
        // 1- The object that we're creating is not null
        assertThat(review, notNullValue());
        // 2- The object's Id that we're creating is greater than 0 (that it has been created successfully and it has
        // a Generated Id in the DB)
        assertThat(review.getId(), greaterThan(0L));
        // 3- we start testing the parameters that we're adding to it from the Request are matched(they should be the same)
        assertThat(review.getContent(), is(request.getContent()));
        // here we're testing if the Product is created or not, so we don't get "Null Pointer Exception" error.
        assertThat(review.getProduct(), notNullValue());
        assertThat(review.getProduct().getId(), is(request.getProductId()));

    }
}
