package com.excilys.cdb.validateur;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.excilys.cdb.dao.ComputerDao;
import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.dto.Dto;
import com.excilys.cdb.exception.InvalidComputerOptionException;
import com.excilys.cdb.exception.InvalidDateValueException;

@Scope(value="singleton")
@Component
public class Validateur {
	private static Logger logger = LoggerFactory.getLogger(ComputerDao.class);
	
		
		private Validateur() {}	

		
	public Dto valide(Dto elem) throws InvalidComputerOptionException {
		boolean verif;
		if(elem.getClass().equals(CompanyDto.class)) {
			CompanyDto comp = (CompanyDto) elem; 
			if(valideName(comp.getName())) return comp;
			else {
				logger.error("",new InvalidComputerOptionException("name"));
				throw new InvalidComputerOptionException("name");
			}
		}
		else {
			ComputerDto comp = (ComputerDto) elem;
			if(!valideName(comp.getName())) {
				logger.error("",new InvalidComputerOptionException(comp.getName()));
				throw new InvalidDateValueException(comp.getIntroduction());
			}
			if(valideDate(comp.getIntroduction(),comp.getDiscontinued())) return comp;
			else {
				logger.error("",new InvalidComputerOptionException("date"));
				throw new InvalidComputerOptionException("date");
			}
			
		}
		
		
	}
	
	private boolean valideName(String name) throws InvalidComputerOptionException{
		if(name.isEmpty() || name.isBlank() || name.length()>100) {
			logger.error("",new InvalidComputerOptionException("name"));
			return false;
		}
		return true;
		
	}
	
	private boolean valideDate(String intro, String disc) throws InvalidComputerOptionException{
		boolean verifIntro;
		boolean verifDisc;
		try {
			LocalDateTime introF = castLocalDateTime(intro);
			LocalDateTime discF = castLocalDateTime(disc);
			verifIntro = introF.isBefore(discF) == true ? introF.isBefore(LocalDateTime.now()) : false;
			verifDisc = discF.isBefore(LocalDateTime.now());
			
		}
		catch (Exception e) {
			logger.error("",new InvalidComputerOptionException(intro));
			throw new InvalidDateValueException(intro);
		}
		if(verifIntro && verifDisc) return true;
		else return false;
		
		
	}
	
	private LocalDateTime castLocalDateTime(String s) throws RuntimeException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		if(s.matches("[0-9]{4}-(0[1-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1]) (2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]")){
			try {
				LocalDateTime dateTime = LocalDateTime.parse(s, formatter);
				
				return dateTime;
			} catch (Exception e) {
				logger.error("", new InvalidDateValueException(s));
				throw new InvalidDateValueException(s);
			}
		}
		else {
			try {
				String tmp = s.concat(" 12:00:00");
				LocalDateTime dateTime = LocalDateTime.parse(tmp, formatter);
				
				return  dateTime;
			} catch (Exception e) {
				logger.error("",e);
				throw e;
				
			}
		}
		
		
	}
		
}
