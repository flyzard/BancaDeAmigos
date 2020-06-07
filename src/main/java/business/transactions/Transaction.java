package business.transactions;

import java.time.LocalDateTime;
import business.User;

/**
 * Represents a contract for a object of type Transaction
 * 
 * @author grupo 5
 * @version 1
 * 
 */
public interface Transaction {
    
    /**
     * Get the user that initiating the transaction
     * 
     * @return User
     */
    public User getFrom();

    /**
     * Get the user to whom the transactions is destinied to
     * 
     * @return User
     */
    public User getTo();

    /**
     * Get the amount of the transaction
     * 
     * @return Double
     */
    public double getAmount();

    /**
     * Get the date the transaction was initiated
     * 
     * @return LocalDateTime
     */
    public LocalDateTime getInitDate();

    /**
     * Get the date the transaction was accepted
     * 
     * @return LocalDateTime
     */
    public LocalDateTime getAcceptedDate();
    
    /**
     * Returns the date it was canceled
     * 
     * @return String
     */
    public LocalDateTime getCanceledDate();
    
    /**
     * Accept the transaction
     * 
     * @return this
     */
    public Transaction accept();

    
    /**
     * Cancel the transaction
     * 
     * @return this
     */
    public Transaction cancel();

    /**
     * Returns the identificator of the destiny user
     * 
     * @return String
     */
    public String toStringUser(User user);
    
    /**
     * 
     * @param user
     * @return Double - The added value to the destiny account
     */
    public Double getValueToUser(User user);
    
}