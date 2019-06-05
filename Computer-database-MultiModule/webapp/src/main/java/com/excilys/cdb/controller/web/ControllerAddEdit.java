package com.excilys.cdb.controller.web;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.User;


@Controller
public class ControllerAddEdit extends WebControl  {
	
	ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	Validator validator = factory.getValidator();
	
	private static Logger logger = LoggerFactory.getLogger(ControllerAddEdit.class);

	
	@GetMapping("/computer-add")
    public String computerAddGet(@RequestParam Map<String,String> params, ModelMap model) {
    	try {
			final List<Company> company = canyServ.listAll();
			setListCompany(model,company);
			
		} catch (Exception e) {
			
			logger.error("", e);
			
		}
    	return "addComputer";
    }
    
    @PostMapping("/computer-add")
    public String  computerAddPost(@RequestParam Map<String,String> params, ModelMap model) {
    	try {
			createOrdi(model,params);
			computerAddGet(params, model);
		} catch (Exception e) {
			
			logger.error("", e);
		}
    	return "addComputer";
    }
    @GetMapping("/computer-edit")
    public String computerEditGet(@RequestParam Map<String,String> params, ModelMap model) {

		try {
			List<Company> company;
			final int id = Integer.parseInt(params.get("id"));
			Computer computer = null;
			computer = cuterServ.read(id);
		
			company = canyServ.listAll();
			setListCompany(model,company);
			setIdComputer(model,id);
			setComputer(model,cuterMap.modelToDto(computer));
		} catch (Exception e) {
			logger.error("",e);
		}
		return "editComputer";
}

    @PostMapping("/computer-edit")
	public String computerEditPost(@RequestParam Map<String,String> params, ModelMap model) {
		try {
			updateOrdi(params);
		} catch (Exception e) {
			logger.error("",e);
		}
		return "redirect:/";
	}
    

    private void setIdComputer(ModelMap model, int id) {
		model.addAttribute("id", id);
	}
    
    private void setListCompany(ModelMap model, List<Company> company) {
		List<CompanyDto> res = new ArrayList<>();
		for(Iterator<Company> i=company.iterator();i.hasNext();) {
			res.add(canyMap.modelToDto(i.next()));
		}
		model.addAttribute("company", res);
	}
	
    @PostMapping("/logine")
    public String  loginePost(@RequestParam Map<String,String> params, ModelMap model) {
    	userServ.addUser(new User(params.get("username"),params.get("password")));
    	return "login";
    }
    
	private void createOrdi(ModelMap model,Map<String, String> params) throws Exception {

			String intro = formatDate(params.get("introduced"));
			String disc = formatDate(params.get("discontinued"));
			String name = params.get("computerName");
			String canyId = params.get("companyId");
			ComputerDto elem = new ComputerDto("-10",name, intro, disc, canyId,null);
			Set<ConstraintViolation<ComputerDto>> violations = validator.validate(elem);
			for (ConstraintViolation<ComputerDto> violation : violations) {
			    logger.error(violation.getMessage()); 
			}
			cuterServ.create(cuterMap.dtoToModel((ComputerDto) val.valide(elem)));
			model.addAttribute("titre", elem.toString());
			
		
	}
	
	private String formatDate(String date) {
		if(date == null) return null;
		date = date.replace("/", "-");
		return  (date.length() <= 10 ) ? date.concat(" 12:00:00") : date;
	}
	

	
	private void updateOrdi( Map<String, String> params) throws Exception {
		
		String intro = formatDate(params.get("introduced"));
		String disc = formatDate(params.get("discontinued"));
		String name =params.get("name");
		String canyId = params.get("companyId");
		ComputerDto elem = new ComputerDto(params.get("id"),name, intro, disc,canyId,null);
		Set<ConstraintViolation<ComputerDto>> violations = validator.validate(elem);
		for (ConstraintViolation<ComputerDto> violation : violations) {
		    logger.error(violation.getMessage()); 
		}
		cuterServ.update(cuterMap.dtoToModel((ComputerDto) val.valide(elem)));
		
	
}
	private void setComputer(ModelMap model, ComputerDto computer) {
		model.addAttribute("name", computer.getName());
		model.addAttribute("intro", computer.getIntroduction());
		model.addAttribute("disc", computer.getDiscontinued());
		model.addAttribute("compName", computer.getCompanyName());
		
	}
	
	
}
