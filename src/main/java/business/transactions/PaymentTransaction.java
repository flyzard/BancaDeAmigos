package business.transactions;

import java.time.LocalDateTime;
import business.GroupUser;

public class PaymentTransaction extends AbstractTransaction {

    public PaymentTransaction(GroupUser from, GroupUser to, float amount) {
        super(from, to, amount);
    }
    
}