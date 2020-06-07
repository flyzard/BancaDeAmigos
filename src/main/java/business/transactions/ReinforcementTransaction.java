package business.transactions;

import business.User;

/**
 * Represents a reinforcemente of one's account
 * 
 * @author grupo 5
 * @version 1
 * 
 */
public class ReinforcementTransaction extends AbstractTransaction {

    /**
     * 
     * @param from
     * @param amount
     */
    public ReinforcementTransaction(User from, double amount) {
        super(from, null, amount);
    }

    @Override
    public String toStringUser(User user) {
        return "Reforcou a conta com : " + this.getAmount();
    }

    @Override
    public Double getValueToUser(User user) {
        return this.getAmount();
    }
}