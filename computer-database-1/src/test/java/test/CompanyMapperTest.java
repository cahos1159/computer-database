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
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;

class CompanyMapperTest {

	
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
	void testClassicDtoToModelCompanyDto() {
		CompanyDto objTest = new CompanyDto("3","Coucou");
		CompanyMapper mappTest = ctx.getBean(CompanyMapper.class);
		Company res = mappTest.dtoToModel(objTest);
		Company compare = new Company(3,"Coucou");
		assertEquals(res,compare);
		
	}
	
	
	@Test
	void testDifferenceReconnueDtoToModelCompanyDto2() {
		CompanyDto objTest = new CompanyDto("4","Coucou");
		CompanyMapper mappTest = ctx.getBean(CompanyMapper.class);
		Company res = mappTest.dtoToModel(objTest);
		Company compare = new Company(3,"Coucou");
		assertTrue(!res.equals(compare));
		
	}
	
	
	
	@Test
	void testClassicModelToDtoCompany() {
		CompanyMapper mappTest = ctx.getBean(CompanyMapper.class);
		Company objTest = new Company(3,"CM-200");
		CompanyDto res = mappTest.modelToDto(objTest);
		CompanyDto compare = new CompanyDto("3","CM-200");
		assertEquals(compare,res);
	}
	
	@Test
	void testDifferenceModelToDtoCompany() {
		CompanyMapper mappTest = ctx.getBean(CompanyMapper.class);
		Company objTest = new Company(3,"CM-200");
		CompanyDto res = mappTest.modelToDto(objTest);
		CompanyDto compare = new CompanyDto("2","CM-200");
		assertTrue(!res.equals(compare));
	}
	
	@Test
	void testDifferenceModelToDtoCompany2() {
		CompanyMapper mappTest = ctx.getBean(CompanyMapper.class);
		Company objTest = new Company(3,"CM-200");
		CompanyDto res = mappTest.modelToDto(objTest);
		CompanyDto compare = new CompanyDto("3","CM-20");
		assertTrue(!res.equals(compare));
	}
	
	@Test
	void testDifferenceNullModelToDtoCompany2() {
		CompanyMapper mappTest = ctx.getBean(CompanyMapper.class);
		Company objTest = new Company(3,null);
		CompanyDto res = mappTest.modelToDto(objTest);
		CompanyDto compare = new CompanyDto("3","CM-20");
		assertTrue(!res.equals(compare));
	}
	
	void testComparaisonNullModelToDtoCompany3() {
		CompanyMapper mappTest = ctx.getBean(CompanyMapper.class);
		Company objTest = new Company(3,null);
		CompanyDto res = mappTest.modelToDto(objTest);
		CompanyDto compare = new CompanyDto("3",null);
		assertEquals(res,compare);
	}
}
