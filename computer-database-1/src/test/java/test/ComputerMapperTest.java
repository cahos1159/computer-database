package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.config.spring.AppConfig;
import com.excilys.cdb.database.DataBaseAccess;
import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.exception.InvalidDateValueException;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;

class ComputerMapperTest {
	
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
	void testNullDtoToModelComputerDto() {

		ComputerDto objTest = new ComputerDto("3","CM-200",null,null,"2",null);
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

		ComputerDto objTest = new ComputerDto("3","CM-200","1990-1-1 01:00:00","1991-1-1 01:00:00","2",null);
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

		ComputerDto objTest = new ComputerDto("coucou","CM-200",null,null,"2",null);
		ComputerMapper mappTest = ctx.getBean(ComputerMapper.class);
		try 
		{
			Computer res = mappTest.dtoToModel(objTest);
		}
		catch(Exception e)
		{
			assertEquals(NumberFormatException.class,e.getClass());
		}

		objTest = new ComputerDto("2","CM-200",null,null,"coucou",null);
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

		ComputerDto objTest = new ComputerDto("2","CM-200","coucou",null,"2",null);
		ComputerMapper mappTest = ctx.getBean(ComputerMapper.class);
		try 
		{
			Computer res = mappTest.dtoToModel(objTest);
		}
		catch(Exception e)
		{
			assertEquals(InvalidDateValueException.class,e.getClass());
		}
		objTest = new ComputerDto("2","CM-200",null,"coucou","2",null);
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

		Computer objTest = new Computer(3,"CM-200",null,null,2);
		ComputerDto res = mappTest.modelToDto(objTest);
		ComputerDto compare = new ComputerDto("3","CM-200","_","_","2","Thinking Machines");
		assertEquals(compare,res);
	}
	void testTimestampModelToDtoComputer() throws Exception {
		ComputerMapper mappTest = ctx.getBean(ComputerMapper.class);

		int myYear = 1990;
		int myMonth = 1;
		int myDay = 1;
		Timestamp intro = Timestamp.valueOf(String.format("%04d-%02d-%02d 01:00:00",myYear, myMonth, myDay));
		myYear = 1991;
		Timestamp disc = Timestamp.valueOf(String.format("%04d-%02d-%02d 01:00:00",myYear, myMonth, myDay));
		Computer objTest = new Computer(3,"CM-200",intro,disc,2);
		ComputerDto res = mappTest.modelToDto(objTest);
		ComputerDto compare = new ComputerDto("3","CM-200","1990-1-1 01:00:00","1991-1-1 01:00:00","2",null);
		assertEquals(compare,res);
	}

}
