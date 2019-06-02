package org.fasttrackit.onlineshopapi.transfer.review;

import javax.validation.constraints.NotNull;

public class CreateReviewRequest {

    @NotNull
    private String content;
    @NotNull
    private Long productId;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "CreateReviewRequest{" +
                "content='" + content + '\'' +
                ", productId=" + productId +
                '}';
    }
}
