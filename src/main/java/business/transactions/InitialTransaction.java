package business.transactions;

import java.time.LocalDateTime;

import business.User;

public class InitialTransaction extends AbstractTransaction {

    public InitialTransaction(User from, double amount) {
        super(from, null, amount);
    }

    @Override
    public LocalDateTime getCanceledDate() {
        // TODO Auto-generated method stub
        return null;
    }
    
}