package com.excilys.cdb.controller.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validateur.Validateur;

@Controller
@SessionAttributes("page")
@RequestMapping("/")
public class DashBoardSpring {
	
	private static Logger logger = LoggerFactory.getLogger(DashBoardSpring.class);

	
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
	
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView dashGet(@ModelAttribute("page") Page page) {

    	ModelAndView mv = new ModelAndView("dashboard");
    	try {
			int mode = (page.getMode() == null ||"".equals((page.getMode()))) ? 0 : Integer.valueOf( page.getMode());
		 	if("".equals(page.getSearch()) || null == (page.getSearch())) {
		 		setPage(page,cuterServ.count(page.getSearch(),0));
				page.setNbElem(cuterServ.count(page.getSearch(),0));
				List<Computer> ordi = cuterServ.computerOrder(page,page.getColonne(),mode);
				setListComputer(mv,ordi);

				System.out.println(page.getNumero());
				mv.addObject("numberOfComputer",cuterServ.count(page.getSearch(),0));
				
			}
			else {
				setPage(page,cuterServ.count(page.getSearch(),1));
				List<Computer> ordi = cuterServ.computerOrderSearch(page,(String)page.getColonne(),mode,(String)page.getSearch());
				setListComputer(mv,ordi);

				mv.addObject("numberOfComputer",cuterServ.count(page.getSearch(),1));
				
				
			}

			
			
		} catch (Exception e) {
			logger.error("",e);
			throw new  ResourceNotFound();
		}
    	mv.addObject("page",page);
		return mv;
    }
    
    @PostMapping("/")
    public ModelAndView dashPost(@ModelAttribute("page") Page page,@RequestParam Map<String,String> params, ModelMap model) {
    	deleteComputer(model,params);
    	return dashGet(page);
    }
    
   
    private void setListComputer(ModelAndView model,List<Computer> ordi) throws Exception {
		

		List<ComputerDto> res = new ArrayList<>();
		for(Iterator<Computer> i=ordi.iterator();i.hasNext();) {
			res.add(cuterMap.modelToDto(i.next()));
		}
		model.addObject("ordi", res);
	}

	

	
	private void setPage(Page page,int nbOrdi) {
		if(page.getNumero() <=0) page.setNumero(1);
		int nbPage =nbOrdi/page.getNbOrdiPage();
		if((page.getNbOrdiPage())>= nbPage) page.setNumero(nbPage);
	}
	
	private int getPage(ModelMap model, Map<String, String> params) {
		return Integer.parseInt((params.get("page") == null) ? "1" : (String) params.get("page"));
	}
	

	private void deleteComputer(ModelMap model,Map<String, String> params){
		String idaggreg = params.get("selection");
		List<String> ids = Arrays.asList(idaggreg.split(","));
		for(String id : ids) {
			try {
				cuterServ.delete(cuterServ.read(Integer.parseInt(id)));
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