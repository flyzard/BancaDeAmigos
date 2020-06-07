package business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import business.Exceptions.GroupNotFoundException;
import business.Exceptions.NoUserLoggedInException;
import services.Auth;

public class BancaDeAmigos {
    
    private final Map<String, User> users;
    private final Map<String, Grupo> groups;
    private final Auth authService;
    private User loggedUser;
    private static BancaDeAmigos INSTANCE;

    public BancaDeAmigos() {
        this.authService = new Auth();
        this.users = new HashMap<String, User>();
        this.groups = new HashMap<String, Grupo>();
    }

    public static BancaDeAmigos getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BancaDeAmigos();
        }

        return INSTANCE;
    }

    /**
     * Methodo para registar novos utilizadores.
     * 
     * @param name
     * @param email
     * @param password
     * @return
     */
    public String registar(final String name, final String email, final String password, final String nib) {

        if (!authService.validateName(name)) {
            return "Nome invalido! Verifique que o nome tem 5 ou mais digitos.";
        }

        if (!authService.validateEmail(email)) {
            return "Email invalido";
        }

        if (!authService.validatePassword(password)) {
            return "Password invalida! Verifique que a password tem 5 ou mais digitos.";
        }

        users.put(email, new User(name, email, password, nib));

        return "Registado com sucesso!";
    }

    public User getUser(final String email) {
        return null;
    }

    public String login(final String email, final String password) {
        final User user = users.get(email);

        if (user != null && user.checkPassword(password)) {
            loggedUser = user;
            return "Logged in with success!";
        }

        return "Wrong email or password!";
    }

    public String createGroup(final String groupName, final double initialCredit) {
        if (this.groups.containsKey(groupName)) {
            return "Já existe um grupo com o nome " + groupName + "!";
        }

        final Grupo grupo = new Grupo(groupName, loggedUser, initialCredit);
        loggedUser.addGroup(grupo);
        groups.put(groupName, grupo);

        return "Grupo " + groupName + " criado com sucesso!";
    }

    public String joinGroup(final String groupName) {
        final Grupo group = groups.get(groupName);

        if (group == null) {
            return "Não existe um grupo com o nome " + groupName;
        }

        if (group.requestMembership(loggedUser)) {
            return "O seu pedido foi submetido com sucesso! Por favor aguarde aceitação.";
        }

        return "O seu pedido não foi aceite.";
    }

    public String acceptGroupRequests(final String groupName, final String newUserEmail) {
        try {
            if (this.getGroupByName(groupName).acceptNewUser(loggedUser, newUserEmail)) {
                return "O seu voto foi registado!";
            }
        } catch(GroupNotFoundException e) {
            return e.getMessage();
        } catch (NoUserLoggedInException e) {
            return e.getMessage();
        }
        
        return "Não foi possível registar o seu voto!";
    }

    public String refuseGroupRequests(final String groupName, final String newUserEmail) {
        try {
            if (this.getGroupByName(groupName).refuseNewUser(loggedUser, newUserEmail)) {
                return "O seu voto foi registado!";
            }
        } catch(GroupNotFoundException e) {
            return e.getMessage();
        } catch (NoUserLoggedInException ex) {
            return ex.getMessage();
        }

        return "Não foi possível registar o seu voto!";
    }

    public String reinforceAccount(final String groupName, final double amount) {
        try {
            if (this.getGroupByName(groupName).reinforceAccount(loggedUser, amount)) {
                return "O pedido de reforço de conta foi feito com sucesso!";
            }
        } catch(GroupNotFoundException e) {
            return e.getMessage();
        } catch (NoUserLoggedInException ex) {
            return ex.getMessage();
        }

        return "Não foi possível fazer pedido de reforço!";
    }

    public String acknoledgeReinforcement(final String groupName, final String userEmail, final double amount) {
        try {
            if (this.getGroupByName(groupName).acknoledgeReinforcement(loggedUser, userEmail, amount)) {
                return "Reforço de conta confirmado com sucesso!";
            }
        } catch(GroupNotFoundException e) {
            return e.getMessage();
        } catch (NoUserLoggedInException ex) {
            return ex.getMessage();
        }
        
        return "Não foi possível reforçar a sua conta!";
    }

    public String changeResponsible(final String groupName) {
        try {
            if (this.getGroupByName(groupName).changeResponsible(loggedUser)) {
                return "O pedido para mudar fiel depositário foi feito com sucesso!";
            }
        } catch(GroupNotFoundException e) {
            return e.getMessage();
        } catch (NoUserLoggedInException ex) {
            return ex.getMessage();
        }

        return "Não foi possível fazer pedido para mudar fiel depositário!";
    }

    public String voteForGroupResponsible(final String groupName, final String newResponsibleEmail) {
        try {
            if (this.getGroupByName(groupName).voteForGroupResponsible(loggedUser, newResponsibleEmail)) {
                return "O seu voto para fiel depositário foi registado com sucesso!";
            }
        } catch(GroupNotFoundException e) {
            return e.getMessage();
        } catch (NoUserLoggedInException ex) {
            return ex.getMessage();
        }
        
        return "Não foi possível registar o seu voto para fiel depositário!";
    }

    public String getVotingForGroupResponsibleResult(final String groupName) {
        String result = null;
        try {
            result = this.getGroupByName(groupName).getChangeResponsibleVoting(loggedUser);
        } catch(GroupNotFoundException e) {
            result = e.getMessage();
        } catch (NoUserLoggedInException ex) {
            result = ex.getMessage();
        }
        
        return result;
    }

    public String payToOnGroup(final String destinyEmail, final String groupName, final double amount) {
        try {
            if (this.getGroupByName(groupName).makePayment(loggedUser, destinyEmail, amount)) {
                return "Pagamento efectuado com sucesso!";
            }
        } catch(GroupNotFoundException e) {
            return e.getMessage();
        } catch (NoUserLoggedInException ex) {
            return ex.getMessage();
        }
        
        return "Não foi possível efectuar pagamento!";
    }

    public String getBalance(final String groupName) {
        try {
            double balance = this.getGroupByName(groupName).getMyBalance(loggedUser);
            return "O balanço atual é de " + balance + "!";
        } catch(GroupNotFoundException e) {
            return e.getMessage();
        } catch (NoUserLoggedInException ex) {
            return ex.getMessage();
        }
    }

    public String abandonGroup(final String groupName) {
        try {
            if (this.getGroupByName(groupName).abandonGroup(loggedUser)) {
                return "O seu pedido para abandonar grupo foi realizado com sucesso!";
            }
        } catch(GroupNotFoundException e) {
            return e.getMessage();
        } catch (NoUserLoggedInException ex) {
            return ex.getMessage();
        }

        return "Não foi possível abandonar grupo!";
    }

    public String closeGroup(final String groupName) {
        try {
            if (this.getGroupByName(groupName).closeGroup(loggedUser)) {
                return "O pedido para fechar grupo foi feito com sucesso!";
            }
        } catch(GroupNotFoundException e) {
            return e.getMessage();
        } catch (NoUserLoggedInException ex) {
            return ex.getMessage();
        }

        return "Não foi possível fechar grupo!";
	}

	public String getMembershipRequests(String groupName) {
        List<String> result = null;

        try {
            result = this.getGroupByName(groupName)
                .getPendingMembershipRequests()
                .stream()
                .map(req -> req.toString())
                .collect(Collectors.toList());   
        } catch (GroupNotFoundException e) {
            return e.getMessage();
        } catch (NoUserLoggedInException ex) {
            return ex.getMessage();
        } 
        
        return String.join("\n", result);
    }
    
    private Grupo getGroupByName(String groupName) throws GroupNotFoundException, NoUserLoggedInException {
        final Grupo group = groups.get(groupName);

        if (group == null) {
            throw new GroupNotFoundException(groupName);
        }

        if (! (loggedUser instanceof User)) {
            throw new NoUserLoggedInException();
        }

        return group;
    }
}