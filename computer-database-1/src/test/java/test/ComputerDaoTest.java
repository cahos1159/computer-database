package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.exception.InvalidIdException;
import com.excilys.cdb.model.Computer;

class ComputerDaoTest {

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


}
