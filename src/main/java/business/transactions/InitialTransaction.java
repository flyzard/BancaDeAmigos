package business.transactions;

import java.time.LocalDateTime;

import business.User;

public class InitialTransaction extends AbstractTransaction {

    public InitialTransaction(User from, double amount) {
        super(from, null, amount);
    }

    @Override
    public String toStringUser(User user) {
        return "Deposito inicial em conta: " + this.getAmount();
    }
    
}