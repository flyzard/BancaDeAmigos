package business.transactions;

import java.time.LocalDateTime;
import business.GroupUser;
import business.User;

public class PaymentTransaction extends AbstractTransaction {

    public PaymentTransaction(User from, User to, double amount) {
        super(from, to, amount);
    }

    @Override
    public LocalDateTime getCanceledDate() {
        // TODO Auto-generated method stub
        return null;
    }

}