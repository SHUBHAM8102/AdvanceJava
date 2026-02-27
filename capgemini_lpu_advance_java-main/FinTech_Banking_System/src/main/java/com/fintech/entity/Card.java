package com.fintech.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "card_id")
    private Long cardId;

    @Column(name = "card_number", nullable = false, unique = true, length = 16)
    private String cardNumber;

    @Column(name = "card_type", nullable = false, length = 20)
    private String cardType; 

    @Column(name = "card_network", length = 20)
    private String cardNetwork; 

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private BankAccount linkedAccount;

    
    @ManyToMany(mappedBy = "cards")
    private List<Customer> customers = new ArrayList<>();

    
    public Card() {
    }

    public Card(String cardNumber, String cardType, String cardNetwork, LocalDate expiryDate, Boolean isActive) {
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.cardNetwork = cardNetwork;
        this.expiryDate = expiryDate;
        this.isActive = isActive;
    }

    
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNetwork() {
        return cardNetwork;
    }

    public void setCardNetwork(String cardNetwork) {
        this.cardNetwork = cardNetwork;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public BankAccount getLinkedAccount() {
        return linkedAccount;
    }

    public void setLinkedAccount(BankAccount linkedAccount) {
        this.linkedAccount = linkedAccount;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }

    
    public void addCustomer(Customer customer) {
        if (!this.customers.contains(customer)) {
            this.customers.add(customer);
            customer.getCards().add(this);
        }
    }

    public void removeCustomer(Customer customer) {
        if (this.customers.contains(customer)) {
            this.customers.remove(customer);
            customer.getCards().remove(this);
        }
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardId=" + cardId +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardType='" + cardType + '\'' +
                ", cardNetwork='" + cardNetwork + '\'' +
                ", expiryDate=" + expiryDate +
                ", isActive=" + isActive +
                '}';
    }
}

