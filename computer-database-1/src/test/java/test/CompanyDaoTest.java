package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.h2.tools.RunScript;
import org.h2.tools.Script;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.config.spring.AppConfig;
import com.excilys.cdb.dao.CompanyDao;
import com.excilys.cdb.database.DataBaseAccess;
import com.excilys.cdb.exception.FailedSQLQueryException;
import com.excilys.cdb.exception.InvalidIdException;
import com.excilys.cdb.exception.PrimaryKeyViolationException;
import com.excilys.cdb.model.Company;




class CompanyDaoTest {
	
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
		Connection connection = dataBase.getConnection();
		RunScript.execute(connection, new FileReader("src/db/1.sql"));
		RunScript.execute(connection, new FileReader("src/db/3.sql"));

		}
		catch(Exception e) {}
		
	}
	
	@Test
	void testCreateCompany() throws Exception {
		CompanyDao dao = ctx.getBean(CompanyDao.class);
		Company compare = new Company(1002,"Création test");
		dao.create(compare);
		Company res = dao.read(compare.getId());
		dao.delete(compare);
		assertEquals(res,compare);
	}
	
	@Test
	void testCreateIdNegatifCompany() throws Exception {
		try {
		CompanyDao dao = ctx.getBean(CompanyDao.class);
		Company compare = new Company(-12,"Création test");
		dao.create(compare);
		Company res = dao.read(compare.getId());
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
		dao.create(compare);
		dao.create(compare);
		dao.delete(compare);
		}
		catch(Exception  e) {
			assertEquals(PrimaryKeyViolationException.class,e.getClass());
		}
	}

	@Test
	void testUpdateClassicNameCompany() throws Exception {
		CompanyDao dao = ctx.getBean(CompanyDao.class);
		Company compare = new Company(102,"Création test");
		Company tmp = new Company(1002,"Création");
		dao.create(compare);
		tmp.setName("Création test");
		dao.update(tmp);
		Company res = dao.read(compare.getId());
		dao.delete(compare);
		assertEquals(res,compare);
	}
	
	@Test
	void testUpdateClassicIdCompany() throws Exception {
		CompanyDao dao = ctx.getBean(CompanyDao.class);
		Company compare = new Company(108,"Création test");
		Company tmp = new Company(1009,"Création test");
		dao.create(compare);
		tmp.setId(109);
		dao.update(tmp);
		Company res = dao.read(compare.getId());
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
		Company tmp = new Company(5666,"Création test");
		dao.create(tmp);
		Company res = dao.read(tmp.getId());
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