package business.transactions;

import business.User;

/**
 * Represents the initial transaction one has to do to get in the group
 * 
 * @author grupo 5
 * @version 1
 * 
 */
public class InitialTransaction extends AbstractTransaction {

    /**
     * 
     * @param from   - The User that initiates the transaction
     * @param amount - the amount of the transaction
     */
    public InitialTransaction(User from, double amount) {
        super(from, null, amount);
    }

    @Override
    public String toStringUser(User user) {
        return "Deposito inicial em conta: " + this.getAmount();
    }

    @Override
    public Double getValueToUser(User user) {
        return this.getAmount();
    }
    
}