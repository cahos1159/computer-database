package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.excilys.cdb.dao.CompanyDao;
import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.exception.InvalidIdException;
import com.excilys.cdb.model.Computer;

class ComputerDaoTest {

	@BeforeEach
	 void prepTest() {
		try {
		ComputerDao dao = ComputerDao.getInstance();
		dao.read(1000).getClass();
		dao.deleteById(1000);
		dao.read(1001).getClass();
		dao.deleteById(1001);
		}
		catch(Exception e) {}
		
	}
		
	@Test
	void testClassicCreateComputer() throws Exception {
		ComputerDao dao = ComputerDao.getInstance();
		Computer compare = new Computer(1000,"Création test",null,null,1);
		Computer res = dao.create(compare);
		dao.delete(compare);
		assertEquals(res,compare);
		}

	@Test
	void testIdNegatifCreateComputer() {
		try {
		ComputerDao dao = ComputerDao.getInstance();
		Computer compare = new Computer(-1,"Création test",null,null,1);
		Computer res = dao.create(compare);
		dao.delete(compare);
		}
		catch(Exception e){
			assertEquals(InvalidIdException.class,e.getClass());
		}
		
	}
	
	@Test
	void testManufacturer0CreateComputer() throws Exception {
		ComputerDao dao = ComputerDao.getInstance();
		Computer compare = new Computer(1000,"Création test",null,null,0);
		Computer res = dao.create(compare);
		dao.delete(compare);
		assertEquals(res,compare);
		}


	@Test
	void testUpdateComputer() throws Exception {
		ComputerDao dao = ComputerDao.getInstance();
		Computer tmp = new Computer(1000,"Création test",null,null,1);
		Computer res = new Computer(1000,"Création test",null,null,2);
		tmp.setManufacturer(2);
		dao.create(tmp);
		Computer compare = dao.update(tmp);
		dao.delete(tmp);
		assertEquals(res,compare);
	}
	
	@Test
	void testClassicComputerSearchComputer() throws Exception {
		ComputerDao dao = ComputerDao.getInstance();
		Computer tmp1 = new Computer(1000,"Création test1",null,null,1);
		Computer tmp2 = new Computer(1001,"Création test2",null,null,1);
		dao.create(tmp1);
		dao.create(tmp2);
		List<Computer> res = Arrays.asList(tmp1,tmp2);
		List<Computer> compare = dao.computerSearch(1,10,"Création test");
		dao.delete(tmp1);
		dao.delete(tmp2);
		assertEquals(res.get(0),compare.get(0));
		assertEquals(res.get(1),compare.get(1));
	}
	
	@Test
	void testSupposeToBeEmptyComputerSearchComputer() throws Exception {
		ComputerDao dao = ComputerDao.getInstance();
		Computer tmp1 = new Computer(1000,"Création test1",null,null,1);
		Computer tmp2 = new Computer(1001,"Création test2",null,null,1);
		dao.create(tmp1);
		dao.create(tmp2);
		List<Computer> res = Arrays.asList(tmp1,tmp2);
		List<Computer> compare = dao.computerSearch(1,10,"ZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZZ");
		dao.delete(tmp1);
		dao.delete(tmp2);
		assertTrue(compare.isEmpty());

	}
	
	@Test
	void testClassicComputerOrderSearchComputer() throws Exception {
		ComputerDao dao = ComputerDao.getInstance();
		Computer tmp1 = new Computer(1000,"Création test1",null,null,1);
		Computer tmp2 = new Computer(1001,"Création test2",null,null,1);
		dao.create(tmp1);
		dao.create(tmp2);
		List<Computer> res = Arrays.asList(tmp1,tmp2);
		List<Computer> compare = dao.computerOrderSearch(1,10,"name",1,"Création test");
		dao.delete(tmp1);
		dao.delete(tmp2);
		assertEquals(res.get(0),compare.get(0));
		assertEquals(res.get(1),compare.get(1));
	}
	


}
