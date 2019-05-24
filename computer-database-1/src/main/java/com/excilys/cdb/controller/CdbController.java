package com.excilys.cdb.controller;

import java.util.*;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.excilys.cdb.dto.*;
import com.excilys.cdb.enums.CommandEnum;
import com.excilys.cdb.enums.CreateOptionEnum;
import com.excilys.cdb.exception.*;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Model;
import com.excilys.cdb.service.*;


@Scope(value="singleton")
@Controller
public class CdbController {
	private String[] splitStr;
	private static final String DATEFORMAT = "yyyy-MM-dd/HH:mm:ss";
	private static final String COMPUTER = "computer";
	private static final String COMPANY = "company";
	
	
	final ComputerMapper cuterMap;
	
	final CompanyMapper canyMap;
	
	final CompanyService canyServ;
	
	final ComputerService cuterServ;
	
	
	
	
	
	public CdbController(ComputerMapper cuterMap,CompanyMapper canyMap,CompanyService canyServ,ComputerService cuterServ)
	{
		this.canyMap = canyMap;
		this.canyServ = canyServ;
		this.cuterMap = cuterMap;
		this.cuterServ = cuterServ; 
	}
	

	public String treatMessage(String msg) throws Exception {
		// Parse message based on whitespace : Any amount might be placed beside and inbetween
		this.splitStr = msg.trim().split("\\s+");
		
		CommandEnum cmd = CommandEnum.getCommandEnum(splitStr[0].toLowerCase());
		switch(cmd) {
			case CREATE:
				return this.create();
			case READ:
				return this.read();
			case UPDATE:
				return this.update();
			case DELETE:
				return this.delete();
			case HELP:
				return help();
			case LISTALL:
				return this.listAll();
			case LIST:
				return this.list();
			case EMPTY:
				return "";
			case UNKNOWN:
			default:
				throw new UnknownCommandException(splitStr[0]);
		}
	}
	
	private static String help() {
		return "Please use custom format for dates: "+DATEFORMAT+"\n"
			+ "create|update company <id> <new_name>\n"
			+ "create computer <id> <name> <intro | _> <disc | _> <company_id | _>\n"
			+ "update computer <id> <[-n:new_name] [-i:new_intro] [-d:new_disc] [-c:new_cid]>\n"
			+ "read|delete <table> <id>\n"
			+ "listall <table>\n"
			+ "list <table> <page> <size>\n"
			+ "help";
	}
	
	private static String castDate(String s) throws InvalidDateFormatException {
		if (s.length() == 19) {
			// Check Date Format
			if (s.charAt(4) == '-' && s.charAt(7) == '-' && s.charAt(10) == '/' && s.charAt(13) == ':' && s.charAt(16) == ':') {
				return s.replace("/", " ");
			} else {
				throw new InvalidDateFormatException(DATEFORMAT,s);
			}
		} else if (s.contentEquals("_")) {
			return null;
		} else {
			throw new InvalidDateFormatException(DATEFORMAT,s);
		}
	}
	
	private String create() throws Exception {
		int sizeComputerExpected = 7;
		int sizeCompanyExpected = 4;
		
		switch (splitStr.length) {
			case 1:
				// Requires at least <table>
				throw new MissingArgumentException(2,splitStr.length);
			case 2:
			case 3:
				if (splitStr[1].toLowerCase().equals(COMPUTER)) {
					throw new MissingArgumentException(sizeComputerExpected,splitStr.length);
				} else if (splitStr[1].toLowerCase().equals(COMPANY)) {
					throw new MissingArgumentException(sizeCompanyExpected,splitStr.length);
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
			case 4:
				if (splitStr[1].toLowerCase().equals(COMPUTER)) {
					throw new MissingArgumentException(sizeComputerExpected,splitStr.length);
				} else if (splitStr[1].toLowerCase().equals(COMPANY)) {
					CompanyDto c = new CompanyDto(splitStr[2],splitStr[3]);
					int ret = canyServ.create(canyMap.dtoToModel(c));
					return (ret <= 0) ? "No company has been created" : "Create "+ret;
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
			case 5:
				if (splitStr[1].toLowerCase().equals(COMPUTER)) {
					throw new MissingArgumentException(sizeComputerExpected,splitStr.length);
				} else if (splitStr[1].toLowerCase().equals(COMPANY)) {
					throw new TooManyArgumentsException(splitStr[4]);
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
			case 6:
				if (splitStr[1].toLowerCase().equals(COMPUTER)) {
					throw new MissingArgumentException(sizeComputerExpected,splitStr.length);
				} else if (splitStr[1].toLowerCase().equals(COMPANY)) {
					throw new TooManyArgumentsException(splitStr[4]);
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
			case 7:
				if (splitStr[1].toLowerCase().equals(COMPUTER)) {
					ComputerDto c = new ComputerDto(splitStr[2],splitStr[3],castDate(splitStr[4]),castDate(splitStr[5]),(splitStr[6].contentEquals("_") ? "0" : splitStr[6]),null);
					int ret = cuterServ.create(cuterMap.dtoToModel(c));
					return (ret <= 0) ? "No computer has been created" : "Create "+ret;
				} else if (splitStr[1].toLowerCase().equals(COMPANY)) {
					throw new TooManyArgumentsException(splitStr[5]);
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
			default:
			case 8:
				if (splitStr[1].toLowerCase().equals(COMPUTER)) {
					throw new TooManyArgumentsException(splitStr[7]);
				} else if (splitStr[1].toLowerCase().equals(COMPANY)) {
					throw new TooManyArgumentsException(splitStr[5]);
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
				
		}
	}
	
	private String read() throws Exception {
		Dto c;
		
		int sizeExpected = 3;
		switch (splitStr.length) {
			case 1:
			case 2:
				throw new MissingArgumentException(sizeExpected,splitStr.length);
			case 3:
				// Load dto by id
				if (splitStr[1].toLowerCase().equals(COMPUTER)) {
					c = cuterMap.modelToDto(cuterServ.read(Integer.parseInt(splitStr[2])));
				} else if (splitStr[1].toLowerCase().equals(COMPANY)) {
					c = canyMap.modelToDto(canyServ.read(Integer.parseInt(splitStr[2])));
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
				// Display dto
				return (c == null) ? "["+splitStr[2]+"] Not Found" : c.toString();
			default:
				throw new TooManyArgumentsException(splitStr[3]);
		}
	}
	
	private String delete() throws Exception {		
		int sizeExpected = 3;
		
		switch (splitStr.length) {
			case 1:
			case 2:
				throw new MissingArgumentException(sizeExpected,splitStr.length);
			case 3:
				int ret;
				if (splitStr[1].toLowerCase().equals(COMPUTER)) {
					ret = cuterServ.delete(cuterMap.dtoToModel(new ComputerDto(splitStr[2])));
				} else if (splitStr[1].toLowerCase().equals(COMPANY)) {
					ret = canyServ.delete(canyMap.dtoToModel(new CompanyDto(splitStr[2])));
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
				return (ret <= 0) ? "[" +splitStr[2]+"] Not Found" : "Delete "+ret;
			default:
				throw new TooManyArgumentsException(splitStr[3]);
		}
	}
	
	private void updateTreatOption(ComputerDto c, String s) throws InvalidComputerOptionException, InvalidDateFormatException {
		if (s.charAt(0) != '-' || s.charAt(2) != ':' || s.length() == 3) {
			throw new InvalidComputerOptionException(s);
		} else {
			CreateOptionEnum opt = CreateOptionEnum.getCommandEnum(Character.toLowerCase(s.charAt(1)));
			switch(opt) {
				case NAME:
					c.setName(s.substring(3));
					break;
				case INTRODUCTION:
					c.setIntroduction(CdbController.castDate(s.substring(3)));
					break;
				case DISCONTINUED:
					c.setDiscontinued(CdbController.castDate(s.substring(3)));
					break;
				case COMPANY:
					c.setCompanyId(s.substring(3).contentEquals("_") ? "-1" : s.substring(3));
					break;
				case UNKNOWN:
				default:
					throw new InvalidComputerOptionException(s);
			}
		}
	}
	
	private String update() throws Exception {
		int sizeExpected = 4;
		
		switch (splitStr.length) {
		case 1:
		case 2:
		case 3:
			throw new MissingArgumentException(sizeExpected,splitStr.length);
		default:
			int ret;
			if (splitStr[1].toLowerCase().equals(COMPUTER)) {
				ComputerDto c = new ComputerDto(splitStr[2]);
				for (String s : Arrays.copyOfRange(splitStr, 3, splitStr.length)) {
					this.updateTreatOption(c,s);
				}
				ret = cuterServ.update(cuterMap.dtoToModel(c));
			} else if (splitStr[1].toLowerCase().equals(COMPANY)) {
				if(splitStr.length == 4) {
					CompanyDto c = new CompanyDto(splitStr[2],splitStr[3]);
					ret = canyServ.update(canyMap.dtoToModel(c));
				} else {
					throw new TooManyArgumentsException(splitStr[4]);
				}
			} else {
				throw new InvalidTableException(splitStr[1]);
			}
			return "Update "+ret;
		}
	}
	
	private String listAll() throws Exception {
		switch (splitStr.length) {
			case 1:
				throw new MissingArgumentException(2, splitStr.length);
			case 2:
				List<? extends Model> mList;
				if (splitStr[1].toLowerCase().equals(COMPUTER)) {
					mList = cuterServ.listAllElements();
				} else if (splitStr[1].toLowerCase().equals(COMPANY)) {
					mList = (List<? extends Model>) canyServ.listAllElements();
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
				
				String ret = "";
				for (Model d : mList) {
					ret += d.toString() + "\n";
				}
				return ret;
			default:
				throw new TooManyArgumentsException(splitStr[2]);
		}
	}
	
	private String list() throws Exception {
		int sizeExpected = 4;
		
		switch (splitStr.length) {
			case 1:
			case 2:
			case 3:
				throw new MissingArgumentException(sizeExpected, splitStr.length);
			case 4:
				List<? extends Model> mList;
				if (splitStr[1].toLowerCase().equals(COMPUTER)) {
					mList = cuterServ.list(splitStr[2], splitStr[3]);
				} else if (splitStr[1].toLowerCase().equals(COMPANY)) {
					mList = canyServ.list(splitStr[2], splitStr[3]);
				} else {
					throw new InvalidTableException(splitStr[1]);
				}
				
				String ret = "";
				for (Model d : mList) {
					ret += d.toString() + "\n";
				}
				return ret;
			default:
				throw new TooManyArgumentsException(splitStr[4]);
		}
	}
	
}
