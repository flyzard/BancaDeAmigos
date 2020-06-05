package business.transactions;

import java.time.LocalDateTime;
import business.User;

public interface Transaction {
    
    public User getFrom();

    public User getTo();

    public double getAmount();

    public LocalDateTime getInitDate();

    public LocalDateTime getAcceptedDate();
    
    public LocalDateTime getCanceledDate();
    
    public Transaction accept();

    public Transaction cancel();
}