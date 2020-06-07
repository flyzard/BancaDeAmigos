package business;

import static org.junit.Assert.*;

// cf. API at http://junit.org/junit4/javadoc/latest/
import org.junit.BeforeClass;
import org.junit.Before; 
import org.junit.Test;

import junit.framework.TestCase;

import org.junit.AfterClass;

public class BancaDAmigosTest {
	
	private BancaDeAmigos banca;
	private String userName = "Joao Felizardo";
	private String email = "joao@fcul.pt";
	private String password = "password";
	private String nib = "nib q232323rqwrwqeqw";

	@Before // run before each test
	public void setup() {
		banca = new BancaDeAmigos();
		assertEquals("Registado com sucesso!", banca.registar(userName, email, password, nib));
		assertEquals("Logged in with success!", banca.login(email, password));
		assertEquals("Wrong email or password!", banca.login(email, "pasd"));
	}

	@Test
	public void test_a_user_can_create_a_group() {
		String grupo = banca.createGroup("Novo Grupo",  20);

		// banca.

		// assertEquals(grupo.getFielDepositario(), userName);

	}

	@Test
	public void test_a_user_can_adhere_to_a_grupo() {
		
		// Grupo grupo = banca.createGroup("Novo Grupo", "NIB1234123423423");
		// assertEquals(grupo.getFielDepositario(), name);

	}

	
		
}
