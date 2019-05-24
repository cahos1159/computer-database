package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.config.spring.AppConfig;
import com.excilys.cdb.database.DataBaseAccess;
import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.InvalidComputerOptionException;
import com.excilys.cdb.validateur.Validateur;

class ValidateurTest {

	private static DataBaseAccess dataBase;
	 private static AnnotationConfigApplicationContext ctx;

	@BeforeAll
	static void context() throws SQLException, FileNotFoundException {
		ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();
		dataBase = new DataBaseAccess("test");
		Connection connection = dataBase.getConnection();
		RunScript.execute(connection, new FileReader("src/db/1.sql"));
		RunScript.execute(connection, new FileReader("src/db/3.sql"));

	}
	
	@Test
	void testFonctionnementNormaleMoitierDate() throws InvalidComputerOptionException {

		ComputerDto ordi = new ComputerDto("100","Cou","2018-12-10","2018-12-11","1","coucou");
		ComputerDto res = (ComputerDto) ctx.getBean(Validateur.class).valide(ordi);
		assertEquals(res,ordi);
	}
	
	@Test
	void testFonctionnementNormale() throws InvalidComputerOptionException {

		ComputerDto ordi = new ComputerDto("100","Cou","2018-12-10 13:45:24","2018-12-11 13:45:24","1","coucou");
		ComputerDto res = (ComputerDto) ctx.getBean(Validateur.class).valide(ordi);
		assertEquals(res,ordi);
	}
	
	@Test
	void testFonctionnementEchecValideNullName() throws InvalidComputerOptionException {
		try {
		ComputerDto ordi = new ComputerDto("100",null,"2018-12-10 13:45:24","2018-12-11 13:45:24","1","coucou");
		ComputerDto res = (ComputerDto) ctx.getBean(Validateur.class).valide(ordi);
		
		}
		catch(Exception e) {
			assertEquals(e.getClass(),NullPointerException.class);
		}
	}

}
