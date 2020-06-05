package business.transactions;

import java.time.LocalDateTime;
import business.User;

public class ReinforcementTransaction extends AbstractTransaction {
    public ReinforcementTransaction(User from, double amount) {
        super(from, null, amount);
    }

}