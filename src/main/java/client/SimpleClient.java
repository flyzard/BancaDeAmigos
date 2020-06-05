package client;

import java.security.acl.Group;

// descomentar caso opte por usar handlers:

// import use_cases.HandlerEntrarNoGrupo;
// import use_cases.HandlerFazerPagamento;

import business.*;

/**
 * Cliente simples
 *	
 * @author ...
 * @version ...
 * 
 */
public class SimpleClient {

	/**
	 * A simple interaction with the application services
	 * 
	 * @param args Command line parameters
	 */
	public static void main(String[] args) {

		BancaDeAmigos banca = new BancaDeAmigos();
		System.out.println(banca.registar("joao", "joao@fc.pt", "password", "nib 12341242342343"));
		System.out.println(banca.registar("carlos", "carlos@fc.pt", "password", "nib 12341242342343"));
		System.out.println(banca.registar("Mariana", "mariana@fc.pt", "password", "nib 12341242342343"));
		System.out.println(banca.registar("Francisco", "francisco@fc.pt", "password", "nib 12341242342343"));
		
		// Element creates and friends join
		System.out.println(banca.login("joao@fc.pt", "password"));
		System.out.println(banca.createGroup("Novo groupo", "NIB 1231231242343", 20.0));

		System.out.println(banca.login("carlos@fc.pt", "password"));
		System.out.println(banca.joinGroup("Novo groupo"));

		System.out.println(banca.login("mariana@fc.pt", "password"));
		System.out.println(banca.joinGroup("Novo groupo"));

		System.out.println(banca.login("francisco@fc.pt", "password"));
		System.out.println(banca.joinGroup("Novo groupo"));

		// Responsible aproves or not the requests to join group
		System.out.println(banca.login("joao@fc.pt", "password"));
		System.out.println(banca.getMembershipRequests("Novo groupo"));
		System.out.println(banca.acceptGroupRequests("Novo groupo", "mariana@fc.pt"));
		System.out.println(banca.acceptGroupRequests("Novo groupo", "francisco@fc.pt"));
		System.out.println(banca.refuseGroupRequests("Novo groupo", "carlos@fc.pt"));

		// Reforça a conta virtual
		System.out.println(banca.login("mariana@fc.pt", "password"));
		System.out.println(banca.reinforceAccount("Novo groupo", 20.0));

		System.out.println(banca.login("joao@fc.pt", "password"));
		System.out.println(banca.acknoledgeReinforcement("Novo groupo", "mariana@fc.pt", 20.0));

		// Fiel depositário pede para mudar e passa a ser o francisco o responsável
		System.out.println(banca.login("joao@fc.pt", "password"));
		System.out.println(banca.changeResponsible("Novo groupo"));
		System.out.println(banca.voteForGroupResponsible("Novo groupo", "francisco@fc.pt"));

		System.out.println(banca.login("mariana@fc.pt", "password"));
		System.out.println(banca.voteForGroupResponsible("Novo groupo", "francisco@fc.pt"));

		System.out.println(banca.login("francisco@fc.pt", "password"));
		System.out.println(banca.voteForGroupResponsible("Novo groupo", "mariana@fc.pt"));

		System.out.println(banca.getVotingForGroupResponsibleResult("Novo groupo"));
		System.out.println(banca.getVotingForGroupResponsibleResult("Novo groupo"));

		// Joao Paga a Mariana e Mariana faz pagamento a Francisco
		System.out.println(banca.login("joao@fc.pt", "password"));
		System.out.println(banca.payToOnGroup("mariana@fc.pt", "Novo groupo", 5.80));

		System.out.println(banca.login("mariana@fc.pt", "password"));
		System.out.println(banca.payToOnGroup("francisco@fc.pt", "Novo groupo", 5.80));

		// Mariana obtem o extracto de actividades da conta
		System.out.println(banca.getBalance("Novo groupo"));

		// Propoe mudança de fiel depositário
		System.out.println(banca.login("francisco@fc.pt", "password"));
		System.out.println(banca.changeResponsible());
		System.out.println(banca.voteForGroupResponsible("Novo groupo", "mariana@fc.pt"));

		System.out.println(banca.login("mariana@fc.pt", "password"));
		System.out.println(banca.voteForGroupResponsible("Novo groupo", "joao@fc.pt"));

		System.out.println(banca.login("joao@fc.pt", "password"));
		System.out.println(banca.voteForGroupResponsible("Novo groupo", "francisco@fc.pt"));
		System.out.println(banca.getVotingForGroupResponsibleResult("Novo groupo"));
		System.out.println(banca.abandonGroup("Novo groupo"));

		System.out.println(banca.login("francisco@fc.pt", "password"));
		System.out.println(banca.closeGroup("Novo groupo"));

		// A guy registeres in the system

		// A guy makes login in the system

		// A guy creates as group in the system

		// Another guy registers in the system

		// This other guy joins the group

		// This other guy makes the initial deposit

		// The first guy accetps the initial deposit

		// The last guy makes a payment

		// 


		
		Grupo grupo = new Grupo("Grupo do camelo");



		System.out.println(grupo.getName());

	}
}