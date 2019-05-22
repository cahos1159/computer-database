package com.excilys.cdb.controller.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validateur.Validateur;


@Controller
public class ControllerAddEdit {
	
	private static Logger logger = LoggerFactory.getLogger(ControllerAddEdit.class);
	@Autowired
	ComputerService cuterServ;
	@Autowired
	ComputerMapper	cuterMap;
	@Autowired
	CompanyService canyServ;
	@Autowired
	CompanyMapper canyMap;
	@Autowired
	Validateur val;
	
	@GetMapping("/computer-add")
    public String ComputerAddGet(@RequestParam Map<String,String> params, ModelMap model) {
    	try {
			final List<Company> company = canyServ.listAllElements();
			setListCompany(model,company);
			
		} catch (Exception e) {
			
			logger.error("", e);
			
		}
    	return "addComputer";
    }
    
    @PostMapping("/computer-add")
    public String  ComputerAddPost(@RequestParam Map<String,String> params, ModelMap model) {
    	try {
			createOrdi(model,params);
			ComputerAddGet(params, model);
		} catch (Exception e) {
			
			logger.error("", e);
		}
    	return "addComputer";
    }
    @GetMapping("/computer-edit")
    protected String computerEditGet(@RequestParam Map<String,String> params, ModelMap model) {

		try {
			List<Company> company;
			final int id = Integer.parseInt(params.get("id"));
			Computer computer = null;
			computer = cuterServ.read(id);
		
			company = canyServ.listAllElements();
			setListCompany(model,company);
			setIdComputer(model,id);
			setComputer(model,cuterMap.modelToDto(computer));
		} catch (Exception e) {
			logger.error("",e);
		}
		return "editComputer";
}

    @PostMapping("/computer-edit")
	protected String computerEditPost(@RequestParam Map<String,String> params, ModelMap model) {
		try {
			updateOrdi(model,params);
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
	
	private void createOrdi(ModelMap model,Map<String, String> params) throws Exception {
			
			String intro = formatDate(params.get("introduced"));
			String disc = formatDate(params.get("discontinued"));
			ComputerDto elem = new ComputerDto("-10",params.get("computerName"), intro, disc, params.get("companyId"),null);
			cuterServ.create(cuterMap.dtoToModel((ComputerDto) val.valide(elem)));
			model.addAttribute("titre", elem.toString());
			
		
	}
	
	private String formatDate(String date) {
		if(date == null) return null;
		date = date.replace("/", "-");
		return  (date.length() <= 10 ) ? date.concat(" 12:00:00") : date;
	}
	
	private void setIdComputer(HttpServletRequest request, int id) {
		request.setAttribute("id", id);
	}
	
	private void updateOrdi(ModelMap model, Map<String, String> params) throws Exception {
		
		String intro = formatDate(params.get("introduced"));
		String disc = formatDate(params.get("discontinued"));
		ComputerDto elem = new ComputerDto(params.get("id"),params.get("name"), intro, disc,  params.get("companyId"),null);
		
		cuterServ.update(cuterMap.dtoToModel(elem));
		
	
}
	private void setComputer(ModelMap model, ComputerDto computer) {
		model.addAttribute("name", computer.getName());
		model.addAttribute("intro", computer.getIntroduction());
		model.addAttribute("disc", computer.getDiscontinued());
		model.addAttribute("compName", computer.getCompanyName());
		
	}
	
	
}
