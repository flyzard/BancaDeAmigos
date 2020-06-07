package business;

import static org.junit.Assert.*;

// cf. API at http://junit.org/junit4/javadoc/latest/
import org.junit.BeforeClass;
import org.junit.Before; 
import org.junit.Test;

import junit.framework.TestCase;

import org.junit.AfterClass;

public class BancaDAmigosTest {
	
	/**
	 * Controller
	 */
	private BancaDeAmigos banca;

	/**
	 * Nome e utilizador para registo
	 */
	private final String userName = "Joao Felizardo";

	/**
	 * Email do primeiro utilizador
	 */
	private final String email = "joao@fcul.pt";

	/**
	 * Email do segundo utilizador
	 */
	private final String email2 = "mariana@fcul.pt";

	/**
	 * Password para ambos os utilizadores
	 */
	private final String password = "password";

	/**
	 * NIB para ambos os utilizadores
	 */
	private final String nib = "nib q232323rqwrwqeqw";

	@Before // run before each test
	public void setup() {
		// Cria o Controller
		banca = new BancaDeAmigos();

		// Regista Joao
		assertEquals("Registado com sucesso!", banca.registar(userName, email, password, nib));
		// Faz loggin de joao
		assertEquals("Logged in with success!", banca.login(email, password));
		// Verifica se o login não funciona com credenciais erradas
		assertEquals("Wrong email or password!", banca.login(email, "pasd"));
		// Regista Mariana
		assertEquals("Registado com sucesso!", banca.registar(userName, email2, password, nib));
	}

	@Test
	public void test_the_whole_flow() {
		// Nome do grupo 
		final String groupName = "Novo Grupo";

		// Joao cria um grupo
		assertEquals("Grupo " + groupName + " criado com sucesso!", banca.createGroup(groupName, 20));

		// Mariana faz pedido para aderir ao grupo
		banca.login(email2, password);
		assertEquals("O seu pedido foi submetido com sucesso! Por favor aguarde aceitação.", 
			banca.joinGroup(groupName));

		// Responsible aproves or not the requests to join group
		banca.login(email, password);
		assertEquals("Joao Felizardo (mariana@fcul.pt) deseja juntar-se ao grupo!", banca.getMembershipRequests(groupName));
		assertEquals("O seu voto foi registado!", banca.acceptGroupRequests(groupName, email2));

		// // Reforça a conta virtual
		banca.login(email2, password);
		assertEquals("O pedido de reforço de conta foi feito com sucesso!", banca.reinforceAccount(groupName, 20.0));

		// Mariana faz reforço de conta
		banca.login(email, password);
		assertEquals("Reforço de conta confirmado com sucesso!", 
				banca.acknoledgeReinforcement(groupName, email2, 20.0));
		
		// Fiel depositário pede para mudar e passa a ser o francisco o responsável
		assertEquals(
			"O pedido para mudar fiel depositário foi feito com sucesso!", 
			banca.changeResponsible(groupName)
		);

		// Joao vota na Mariana para fiel depositário
		assertEquals(
			"O seu voto para fiel depositário foi registado com sucesso!", 
			banca.voteForGroupResponsible(groupName, email2)
		);

		// Mariana vota em Joao para fiel depositário
		banca.login(email2, password);
		assertEquals(
			"O seu voto para fiel depositário foi registado com sucesso!",
			banca.voteForGroupResponsible(groupName, email)
		);

		// Joao obtem o resultado da votação para fiel depositário
		banca.login(email, password);
		assertEquals("A votação não foi unanime!", banca.getVotingForGroupResponsibleResult(groupName));

		// Joao Paga a Mariana e Mariana faz pagamento a Francisco
		assertEquals("Pagamento efectuado com sucesso!", banca.payToOnGroup(email2, groupName, 5.80));

		// Mariana obtem o extracto de actividades da conta
		banca.login(email2, password);
		assertEquals(
			"Deposito inicial em conta: 20.0\nReforcou a conta com : 20.0\nRecebeu: 5.8 de joao@fcul.pt\nSaldo atual: 20.0", 
			banca.getBalance(groupName)
		);

		// Propoe mudança de fiel depositário
		banca.login(email, password);
		assertEquals(
				"O pedido para mudar fiel depositário foi feito com sucesso!", 
				banca.changeResponsible(groupName)
			);

		// Joao vota Mariana como fiel depositario
		assertEquals(
			"O seu voto para fiel depositário foi registado com sucesso!", 
			banca.voteForGroupResponsible(groupName, email2)
		);

		// Mariana vota Mariana como fiel depositário
		banca.login(email2, password);
		assertEquals(
			"O seu voto para fiel depositário foi registado com sucesso!",
			banca.voteForGroupResponsible(groupName, email2)
		);

		// Mariana obtem o resultado da eleição para fiel depositário
		assertEquals("The new responsible is: " + email2, banca.getVotingForGroupResponsibleResult(groupName));

		// Joao abandona o grupo
		banca.login(email, password);
		assertEquals(
			"O seu pedido para abandonar grupo foi realizado com sucesso!", 
			banca.abandonGroup(groupName)
		);

		// Mariana fecha o grupo
		banca.login(email2, password);
		assertEquals("O pedido para fechar grupo foi feito com sucesso!", banca.closeGroup(groupName));
	}

}
