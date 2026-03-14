package pom.capgemini.bookservice.dto;

public class OrderEvent {
    private Long orderId;
    private Long bookId;
    private Integer quantity;
    private String status;

    public OrderEvent() {
    }

    public OrderEvent(Long orderId, Long bookId, Integer quantity, String status) {
        this.orderId = orderId;
        this.bookId = bookId;
        this.quantity = quantity;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

