package business.transactions;

import java.time.LocalDateTime;
import business.User;

/**
 * Abstract class representing the functionalities of a transaction
 * 
 * @author grupo 5
 * @version 1
 * 
 */
public abstract class AbstractTransaction implements Transaction {
 
    /**
     * User initiating the transaction
     */
    private User from;

    /**
     * User to whom the transactions is destinied to
     */
    private User to;

    /**
     * The amount of the transaction
     */
    private double amount;

    /**
     * The date the transaction was initiated
     */
    private LocalDateTime initiatedAt;

    /**
     * The date the transaction was accepted
     */
    private LocalDateTime acceptedAt;

    /**
     * The date the transaction was canceled
     */
    private LocalDateTime caceledAt;
    

    /**
     * Constructor
     * 
     * @param from   - User initiating the transaction
     * @param to     - User to whom the transactions is destinied to
     * @param amount - The amount of the transaction
     */
    public AbstractTransaction(User from, User to, double amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.initiatedAt = LocalDateTime.now();
    }
    
    
    /**
     * Get the user that initiating the transaction
     * 
     * @return User
     */
    @Override
    public User getFrom() {
        return this.from;
    }

    /**
     * Get the user to whom the transactions is destinied to
     * 
     * @return User
     */
    @Override
    public User getTo() {
        return this.to;
    }

    /**
     * Get the amount of the transaction
     * 
     * @return Double
     */
    @Override
    public double getAmount() {
        return this.amount;
    }

    /**
     * Get the date the transaction was initiated
     * 
     * @return LocalDateTime
     */
    @Override
    public LocalDateTime getInitDate() {
        return this.initiatedAt;
    }

    /**
     * Get the date the transaction was accepted
     * 
     * @return LocalDateTime
     */
    @Override
    public LocalDateTime getAcceptedDate() {
        return this.acceptedAt;
    }

    /**
     * Accept the transaction
     * 
     * @return this
     */
    @Override
    public Transaction accept() {
        this.acceptedAt = LocalDateTime.now();
        return this;
    }

    /**
     * Cancel the transaction
     * 
     * @return this
     */
    @Override
    public Transaction cancel() {
        this.caceledAt = LocalDateTime.now();
        return this;
    }

    /**
     * Returns the identificator of the destiny user
     * 
     * @return String
     */
    @Override
    public abstract String toStringUser(User user);

    /**
     * Returns the date it was canceled
     * 
     * @return String
     */
    @Override
    public LocalDateTime getCanceledDate() {
        return this.caceledAt;
    }

}