package com.excilys.cdb.controller.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.model.Computer;

@Controller
@SessionAttributes("page")
@RequestMapping("/")
public class DashBoardSpring extends WebControl{
	
	private static Logger logger = LoggerFactory.getLogger(DashBoardSpring.class);
	
	static Map<String,String>  att = new HashMap<>();
	static {		
		att.put("name", "name");
		att.put("introduced", "introduced");
		att.put("discontinued", "discontinued");
		att.put("company", "company");
	}
	

	

	@GetMapping
    public ModelAndView dashGet(@ModelAttribute("page") Page page) {

    	ModelAndView mv = new ModelAndView("dashboard");
    	try {
    		page.setNbElem(cuterServ.count(page.getSearch()));
    		setPage(page);
		 	if("".equals(page.getSearch()) || null == (page.getSearch())) page.setSearch("");
		 		
				page.setNbElem(cuterServ.count(page.getSearch()));
				Pageable requete = buildPageable(page);
				List<Computer> ordi = cuterServ.listComputer(page.getSearch(),requete);
				
				setListComputer(mv,ordi);

				
				mv.addObject("numberOfComputer",cuterServ.count(page.getSearch()));	
			
		} catch (Exception e) {
			logger.error("",e);
			throw new  ResourceNotFound();
		}
    	
    	mv.addObject("page",page);
		return mv;
    }
    

    
    @PostMapping("/")
    public ModelAndView dashPost(@ModelAttribute("page") Page page,@RequestParam Map<String,String> params, ModelMap model,SessionStatus status) {
    	deleteComputer(params);
    	 status.setComplete();
    	return dashGet(page);
    }
    
    private Pageable buildPageable(Page page) {
    	int mode = (page.getMode() == null ||"".equals((page.getMode()))) ? 0 : Integer.valueOf( page.getMode());
    	Pageable tmp = PageRequest.of(page.numero-1,page.getNbOrdiPage());

    		if(att.containsKey(page.getColonne())) {
	    		if(mode == 0){
	    			if(page.getNbOrdiPage()> page.getNbElem()-(page.getNbOrdiPage()*page.getNumero())) {tmp = PageRequest.of(page.numero-1,page.getNbOrdiPage(),Sort.by(att.get(page.getColonne())).ascending());}
	    			return PageRequest.of(page.numero-1,page.getNbOrdiPage(),Sort.by(att.get(page.getColonne())).ascending());
	    		}
	    		else {
	    			return PageRequest.of(page.numero-1,page.getNbOrdiPage(),Sort.by(att.get(page.getColonne())).descending());
	    		}
    		}
    	return tmp;
    }
   
    private void setListComputer(ModelAndView model,List<Computer> ordi) throws Exception {
		

		List<ComputerDto> res = new ArrayList<>();
		for(Iterator<Computer> i=ordi.iterator();i.hasNext();) {
			res.add(cuterMap.modelToDto(i.next()));

		}

		model.addObject("ordi", res);
	}

	

	
	private void setPage(Page page) {
		if(page.getNumero() <1) page.setNumero(1);
		int nbPage =page.getNbElem()/page.getNbOrdiPage();
		if((page.getNumero()-1)> nbPage && nbPage > 0) page.setNumero(nbPage);
	}
	
	private void deleteComputer(Map<String, String> params){
		String idaggreg = params.get("selection");
		List<String> ids = Arrays.asList(idaggreg.split(","));
		for(String id : ids) {
			try {
				cuterServ.delete(Integer.parseInt(id));
			} catch (Exception e) {
				logger.error("",e);
			}
		}
	}
	@ModelAttribute("page")
	private Page getPage() {
		return new Page();
	}
	
	
 
}