package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.config.spring.AppConfig;
import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.InvalidComputerOptionException;
import com.excilys.cdb.validateur.Validateur;

class ValidateurTest {

	private static AnnotationConfigApplicationContext ctx;

	@BeforeAll
	static void context() {
		ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();
	}	
	
	@Test
	void testFonctionnementNormaleMoitierDate() throws InvalidComputerOptionException {
		CompanyDto comp = new CompanyDto("1","coucou");
		ComputerDto ordi = new ComputerDto("100","Cou","2018-12-10","2018-12-11",comp);
		ComputerDto res = (ComputerDto) ctx.getBean(Validateur.class).valide(ordi);
		assertEquals(res,ordi);
	}
	
	@Test
	void testFonctionnementNormale() throws InvalidComputerOptionException {
		CompanyDto comp = new CompanyDto("1","coucou");
		ComputerDto ordi = new ComputerDto("100","Cou","2018-12-10 13:45:24","2018-12-11 13:45:24",comp);
		ComputerDto res = (ComputerDto) ctx.getBean(Validateur.class).valide(ordi);
		assertEquals(res,ordi);
	}

}
