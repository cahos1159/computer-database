package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.config.spring.AppConfig;
import com.excilys.cdb.dao.CompanyDao;
import com.excilys.cdb.exception.FailedSQLQueryException;
import com.excilys.cdb.exception.InvalidIdException;
import com.excilys.cdb.exception.PrimaryKeyViolationException;
import com.excilys.cdb.model.Company;




class CompanyDaoTest {
	
	 private static AnnotationConfigApplicationContext ctx;

	@BeforeAll
	static void context() {
		ctx = new AnnotationConfigApplicationContext();
		ctx.register(AppConfig.class);
		ctx.refresh();
	}
	
	@BeforeEach
	 void prepTest() {
		try {
		CompanyDao dao = ctx.getBean(CompanyDao.class);
		dao.read(1000).getClass();
		dao.deleteById(1000);
		}
		catch(Exception e) {}
		
	}
	
	@Test
	void testCreateCompany() throws Exception {
		CompanyDao dao = ctx.getBean(CompanyDao.class);
		Company compare = new Company(1000,"Création test");
		Company res = dao.create(compare);
		dao.delete(compare);
		assertEquals(res,compare);
	}
	
	@Test
	void testCreateIdNegatifCompany() throws Exception {
		try {
		CompanyDao dao = ctx.getBean(CompanyDao.class);
		Company compare = new Company(-12,"Création test");
		Company res = dao.create(compare);
		dao.delete(compare);
		}
		catch(Exception  e) {
		assertEquals(InvalidIdException.class,e.getClass());
		}
	}
	
	@Test
	void testCreateInvalidKeyfCompany() throws Exception {
		try {
		CompanyDao dao = ctx.getBean(CompanyDao.class);
		Company compare = new Company(1000,"Création test");
		Company tmp = dao.create(compare);
		Company res = dao.create(compare);
		dao.delete(compare);
		}
		catch(Exception  e) {
			assertEquals(PrimaryKeyViolationException.class,e.getClass());
		}
	}

	@Test
	void testUpdateClassicNameCompany() throws Exception {
		CompanyDao dao = ctx.getBean(CompanyDao.class);
		Company compare = new Company(1000,"Création test");
		Company tmp = new Company(1000,"Création");
		dao.create(compare);
		tmp.setName("Création test");
		Company res = dao.update(tmp);
		dao.delete(compare);
		assertEquals(res,compare);
	}
	
	@Test
	void testUpdateClassicIdCompany() throws Exception {
		CompanyDao dao = ctx.getBean(CompanyDao.class);
		Company compare = new Company(1000,"Création test");
		Company tmp = new Company(1001,"Création test");
		dao.create(compare);
		tmp.setId(1000);
		Company res = dao.update(tmp);
		dao.delete(compare);
		assertEquals(res,compare);
	}
	
	@Test
	void testDeleteCompany() throws Exception {
		try {
			CompanyDao dao = ctx.getBean(CompanyDao.class);
		Company tmp = new Company(1000,"Création test");
		dao.create(tmp);
		dao.delete(tmp);
		}
		catch(Exception e) {
			assertEquals(e.getClass(),FailedSQLQueryException.class);
		}
	}
	
	@Test
	void testDeleteExceptionCompany() throws Exception {
		try {
		CompanyDao dao = ctx.getBean(CompanyDao.class);
		Company tmp = new Company(1000,"Création test");
		dao.create(tmp);
		dao.delete(tmp);
		dao.delete(tmp);
		}
		catch(Exception e) {
			assertEquals(e.getClass(),InvalidIdException.class);
		}
	}
	
	@Test
	void testReadClassique() throws Exception {
		CompanyDao dao = ctx.getBean(CompanyDao.class);
		Company tmp = new Company(1000,"Création test");
		dao.create(tmp);
		Company res = dao.read(1000);
		assertEquals(res,tmp);
	}
	
	@Test
	void testReadInvalidIdException(){
		try {
		CompanyDao dao = ctx.getBean(CompanyDao.class);
		Company res = dao.read(2000);
		}
		catch(Exception e) {
			assertEquals(InvalidIdException.class,e.getClass());
		}
	}


}