package business.GroupRequests;

import java.util.Set;

import business.User;

/**
 * Pedido para integração de grupo
 * 
 * @author grupo 5
 * @version 1
 * 
 */
public class IntegrateGroupRequest extends AbstractGroupRequest {

    public IntegrateGroupRequest(User from, Set<User> voters) {
        super(from, voters);
    }

    @Override
    public String toString() {
        final User user = getFrom();
        return user.getName() + " (" + user.getEmail() + ") deseja juntar-se ao grupo!";
    }
}