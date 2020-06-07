package business.transactions;

import java.time.LocalDateTime;
import business.GroupUser;
import business.User;

public class PaymentTransaction extends AbstractTransaction {

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

}