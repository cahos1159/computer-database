package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

class CompanyMapperTest {

	@Test
	void testClassicDtoToModelCompanyDto() {
		CompanyDto objTest = new CompanyDto("3","Coucou");
		CompanyMapper mappTest = CompanyMapper.getInstance();
		Company res = mappTest.dtoToModel(objTest);
		Company compare = new Company(3,"Coucou");
		assertEquals(res,compare);
		
	}
	
	
	@Test
	void testDifferenceReconnueDtoToModelCompanyDto2() {
		CompanyDto objTest = new CompanyDto("4","Coucou");
		CompanyMapper mappTest = CompanyMapper.getInstance();
		Company res = mappTest.dtoToModel(objTest);
		Company compare = new Company(3,"Coucou");
		assertTrue(!res.equals(compare));
		
	}
	
	
	
	@Test
	void testClassicModelToDtoCompany() {
		CompanyMapper mappTest = CompanyMapper.getInstance();
		Company objTest = new Company(3,"CM-200");
		CompanyDto res = mappTest.modelToDto(objTest);
		CompanyDto compare = new CompanyDto("3","CM-200");
		assertEquals(compare,res);
	}
	
	@Test
	void testDifferenceModelToDtoCompany() {
		CompanyMapper mappTest = CompanyMapper.getInstance();
		Company objTest = new Company(3,"CM-200");
		CompanyDto res = mappTest.modelToDto(objTest);
		CompanyDto compare = new CompanyDto("2","CM-200");
		assertTrue(!res.equals(compare));
	}
	
	@Test
	void testDifferenceModelToDtoCompany2() {
		CompanyMapper mappTest = CompanyMapper.getInstance();
		Company objTest = new Company(3,"CM-200");
		CompanyDto res = mappTest.modelToDto(objTest);
		CompanyDto compare = new CompanyDto("3","CM-20");
		assertTrue(!res.equals(compare));
	}
	
	@Test
	void testDifferenceNullModelToDtoCompany2() {
		CompanyMapper mappTest = CompanyMapper.getInstance();
		Company objTest = new Company(3,null);
		CompanyDto res = mappTest.modelToDto(objTest);
		CompanyDto compare = new CompanyDto("3","CM-20");
		assertTrue(!res.equals(compare));
	}
	
	void testComparaisonNullModelToDtoCompany3() {
		CompanyMapper mappTest = CompanyMapper.getInstance();
		Company objTest = new Company(3,null);
		CompanyDto res = mappTest.modelToDto(objTest);
		CompanyDto compare = new CompanyDto("3",null);
		assertEquals(res,compare);
	}
}
