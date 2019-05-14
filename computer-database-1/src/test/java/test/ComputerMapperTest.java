package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.config.spring.AppConfig;
import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.InvalidDateFormatException;
import com.excilys.cdb.exception.InvalidDateValueException;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;

class ComputerMapperTest {
	
	private static AnnotationConfigApplicationContext ctx;

	@BeforeAll
	static void context() {
		ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();
	}
	
	@Test
	void testNullDtoToModelComputerDto() {
		CompanyDto cmp = new CompanyDto("2");
		ComputerDto objTest = new ComputerDto("3","CM-200",null,null,cmp);
		ComputerMapper mappTest = ctx.getBean(ComputerMapper.class);
		Computer res = mappTest.dtoToModel(objTest);
		Computer compare = new Computer(3,"CM-200",null,null,2);
		assertEquals(res,compare);
		
		objTest = null;
		compare = null;
		res = mappTest.dtoToModel(objTest);
		assertEquals(res,compare);
		
	}
	
	@Test
	void testTimeDtoToModelComputerDto() {
		CompanyDto cmp = new CompanyDto("2");
		ComputerDto objTest = new ComputerDto("3","CM-200","1990-1-1 01:00:00","1991-1-1 01:00:00",cmp);
		ComputerMapper mappTest = ctx.getBean(ComputerMapper.class);
		Computer res = mappTest.dtoToModel(objTest);
		int myYear = 1990;
		int myMonth = 1;
		int myDay = 1;
		Timestamp intro = Timestamp.valueOf(String.format("%04d-%02d-%02d 01:00:00",myYear, myMonth, myDay));
		myYear = 1991;
		Timestamp disc = Timestamp.valueOf(String.format("%04d-%02d-%02d 01:00:00",myYear, myMonth, myDay));
		Computer compare = new Computer(3,"CM-200",intro,disc,2);
		assertEquals(res,compare);
	}
	@Test
	void testExceptionNumberFormatDtoToModelComputerDto() {
		CompanyDto cmp = new CompanyDto("2");
		ComputerDto objTest = new ComputerDto("coucou","CM-200",null,null,cmp);
		ComputerMapper mappTest = ctx.getBean(ComputerMapper.class);
		try 
		{
			Computer res = mappTest.dtoToModel(objTest);
		}
		catch(Exception e)
		{
			assertEquals(NumberFormatException.class,e.getClass());
		}
		CompanyDto cmp2 = new CompanyDto("coucou");
		objTest = new ComputerDto("2","CM-200",null,null,cmp2);
		mappTest = ctx.getBean(ComputerMapper.class);
		try 
		{
			Computer res = mappTest.dtoToModel(objTest);
			fail("IllegalArgumentException not thrown");
		}
		catch(Exception e)
		{
			assertEquals(NumberFormatException.class,e.getClass());
		}
		
	}
	
	@Test
	void testExceptionDateFormatDtoToModelComputerDto() {
		CompanyDto cmp = new CompanyDto("2");
		ComputerDto objTest = new ComputerDto("2","CM-200","coucou",null,cmp);
		ComputerMapper mappTest = ctx.getBean(ComputerMapper.class);
		try 
		{
			Computer res = mappTest.dtoToModel(objTest);
		}
		catch(Exception e)
		{
			assertEquals(InvalidDateValueException.class,e.getClass());
		}
		objTest = new ComputerDto("2","CM-200",null,"coucou",cmp);
		mappTest = ctx.getBean(ComputerMapper.class);
		try 
		{
			Computer res = mappTest.dtoToModel(objTest);
			fail("IllegalArgumentException not thrown");
		}
		catch(Exception e)
		{
			assertEquals(InvalidDateValueException.class,e.getClass());
		}
		
	}
	
	
	@Test
	void testNullModelToDtoComputer() throws Exception {
		ComputerMapper mappTest = ctx.getBean(ComputerMapper.class);
		CompanyDto cmp = new CompanyDto("2","Thinking Machines");
		Computer objTest = new Computer(3,"CM-200",null,null,2);
		ComputerDto res = mappTest.modelToDto(objTest);
		ComputerDto compare = new ComputerDto("3","CM-200","_","_",cmp);
		assertEquals(compare,res);
	}
	void testTimestampModelToDtoComputer() throws Exception {
		ComputerMapper mappTest = ctx.getBean(ComputerMapper.class);
		CompanyDto cmp = new CompanyDto("2");
		int myYear = 1990;
		int myMonth = 1;
		int myDay = 1;
		Timestamp intro = Timestamp.valueOf(String.format("%04d-%02d-%02d 01:00:00",myYear, myMonth, myDay));
		myYear = 1991;
		Timestamp disc = Timestamp.valueOf(String.format("%04d-%02d-%02d 01:00:00",myYear, myMonth, myDay));
		Computer objTest = new Computer(3,"CM-200",intro,disc,2);
		ComputerDto res = mappTest.modelToDto(objTest);
		ComputerDto compare = new ComputerDto("3","CM-200","1990-1-1 01:00:00","1991-1-1 01:00:00",cmp);
		assertEquals(compare,res);
	}

}
