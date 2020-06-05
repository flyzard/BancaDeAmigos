package business.transactions;

import java.time.LocalDateTime;

import business.GroupUser;
import business.User;

public abstract class AbstractTransaction implements Transaction {
 
    private User from;
    private User to;
    private double amount;
    private LocalDateTime initiatedAt;
    private LocalDateTime acceptedAt;
    private LocalDateTime caceledAt;
    

    public AbstractTransaction(User from, User to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.initiatedAt = LocalDateTime.now();
    }
    
    @Override
    public User getFrom() {
        return this.from;
    }

    @Override
    public User getTo() {
        return this.to;
    }

    @Override
    public double getAmount() {
        return this.amount;
    }

    @Override
    public LocalDateTime getInitDate() {
        return this.initiatedAt;
    }

    @Override
    public LocalDateTime getAcceptedDate() {
        return this.acceptedAt;
    }

    @Override
    public Transaction accept() {
        this.acceptedAt = LocalDateTime.now();
        return this;
    }

    @Override
    public Transaction cancel() {
        this.caceledAt = LocalDateTime.now();
        return this;
    }

}