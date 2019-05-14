package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.config.spring.AppConfig;
import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

class CompanyMapperTest {

	
	 private static AnnotationConfigApplicationContext ctx;

		@BeforeAll
		static void context() {
			ctx = new AnnotationConfigApplicationContext();
			ctx.register(AppConfig.class);
			ctx.refresh();
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
