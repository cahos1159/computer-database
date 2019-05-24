package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.h2.tools.RunScript;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.config.spring.AppConfig;
import com.excilys.cdb.controller.web.Page;
import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.database.DataBaseAccess;
import com.excilys.cdb.exception.InvalidIdException;
import com.excilys.cdb.model.Computer;

class ComputerDaoTest {
	
	private static DataBaseAccess dataBase;
	 private static AnnotationConfigApplicationContext ctx;

	@BeforeAll
	static void context() throws SQLException, FileNotFoundException {
		ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();
		dataBase = new DataBaseAccess("test");
		

	}
	
	
	@BeforeEach
	 void prepTest() {
		try {
		ComputerDao dao = ctx.getBean(ComputerDao.class);
		Connection connection = dataBase.getConnection();
		RunScript.execute(connection, new FileReader("src/db/1.sql"));
		RunScript.execute(connection, new FileReader("src/db/3.sql"));
		}
		catch(Exception e) {}
		
	}
		
	@Test
	void testClassicCreateComputer() throws Exception {
		ComputerDao dao = ctx.getBean(ComputerDao.class);
		Computer compare = new Computer(10000,"Création test",null,null,1);
		dao.create(compare);
		Computer res = dao.read(compare.getId());
		dao.delete(compare);
		assertEquals(res,compare);
		}

	@Test
	void testIdNegatifCreateComputer() {
		try {
		ComputerDao dao = ctx.getBean(ComputerDao.class);
		Computer compare = new Computer(-1,"Création test",null,null,1);
		
		dao.create(compare);
		Computer res = dao.read(compare.getId());
		dao.delete(compare);
		}
		catch(Exception e){
			assertEquals(InvalidIdException.class,e.getClass());
		}
		
	}
//	
//	@Test
//	void testManufacturer0CreateComputer() throws Exception {
//		ComputerDao dao = ctx.getBean(ComputerDao.class);
//		Computer compare = new Computer(1000,"Création test",null,null,0);
//		dao.create(compare);
//		Computer res = dao.read(compare.getId());
//		dao.delete(compare);
//		assertEquals(res,compare);
//		}


	@Test
	void testUpdateComputer() throws Exception {
		ComputerDao dao = ctx.getBean(ComputerDao.class);
		Computer tmp = new Computer(1000,"Création test",null,null,1);
		dao.create(tmp);
		tmp = dao.read(1000);
		tmp.setManufacturer(2);
		Computer res = tmp;
		res.setManufacturer(2);
		dao.update(tmp);
		Computer compare = dao.read(tmp.getId());
		dao.delete(tmp);
		assertEquals(res,compare);
	}
	
	@Test
	void testClassicComputerSearchComputer() throws Exception {
		ComputerDao dao = ctx.getBean(ComputerDao.class);
		Computer tmp1 = new Computer(1000,"Création test1",null,null,1);
		Computer tmp2 = new Computer(1001,"Création test2",null,null,1);
		dao.create(tmp1);
		dao.create(tmp2);
		List<Computer> res = Arrays.asList(tmp1,tmp2);
		Page page = new Page(1,1);
		List<Computer> compare = dao.computerSearch(page,"Création test");
		dao.delete(tmp1);
		dao.delete(tmp2);
		assertEquals(res.get(0),compare.get(0));
		assertEquals(res.get(1),compare.get(1));
	}
	
	@Test
	void testSupposeToBeEmptyComputerSearchComputer() throws Exception {
		ComputerDao dao = ctx.getBean(ComputerDao.class);
		Computer tmp1 = new Computer(1000,"Création test1",null,null,1);
		Computer tmp2 = new Computer(1001,"Création test2",null,null,1);
		dao.create(tmp1);
		dao.create(tmp2);
		Page page = new Page(1,10);
		List<Computer> res = Arrays.asList(tmp1,tmp2);
		List<Computer> compare = dao.computerSearch(page,"ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
		dao.delete(tmp1);
		dao.delete(tmp2);
		assertTrue(compare.isEmpty());

	}
	
	@Test
	void testClassicComputerOrderSearchComputer() throws Exception {
		ComputerDao dao = ctx.getBean(ComputerDao.class);
		Computer tmp1 = new Computer(1000,"Création test1",null,null,1);
		Computer tmp2 = new Computer(1001,"Création test2",null,null,1);
		dao.create(tmp1);
		dao.create(tmp2);
		Page page = new Page(1,10);
		List<Computer> res = Arrays.asList(tmp1,tmp2);
		List<Computer> compare = dao.computerOrderSearch(page,"name",1,"Création test");
		dao.delete(tmp1);
		dao.delete(tmp2);
		assertEquals(res.get(0),compare.get(1));
		assertEquals(res.get(1),compare.get(0));
	}
	

}
