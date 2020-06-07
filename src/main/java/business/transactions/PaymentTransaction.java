package business.transactions;

import business.User;

/**
 * Represents a payment from one element of the group to another element
 * 
 * @author grupo 5
 * @version 1
 * 
 */
public class PaymentTransaction extends AbstractTransaction {

    /**
     * 
     * @param from
     * @param to
     * @param amount
     */
    public PaymentTransaction(User from, User to, double amount) {
        super(from, to, amount);
    }

    @Override
    public String toStringUser(User user) {
        if (user.equals(this.getFrom())) {
            return "Pagamento de : " + this.getAmount() + " a " + this.getTo().getEmail();
        }

        return "Recebeu: " + this.getAmount() + " de " + this.getFrom().getEmail();
    }

    @Override
    public Double getValueToUser(User user) {
        if (this.getTo().equals(user)) {
            return this.getAmount();
        }
        return -this.getAmount();
    }

}