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
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validateur.Validateur;

@Controller
public class DashBoardSpring {
	
	private static int nbOrdiPage = 10;
	private static final String SEARCH = "search";
	private static Logger logger = LoggerFactory.getLogger(DashBoardSpring.class);
	private static final String COLONNE = "colonne";
	private static final String NBORDIPARPAGE = "nbOrdiPage";
	
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
	
    @GetMapping("/")
    public String dashGet(@RequestParam Map<String,String> params, ModelMap model) {
//    	if(!value.isEmpty()) throw new ResourceNotFound();
    	try {

			Page page = new Page(getPage(model,params),getNbOrdiPage(model,params));
			int mode = (params.get("mode") == null ||"".equals((params.get("mode")))) ? 0 : Integer.valueOf( params.get("mode"));
		 	if("".equals(params.get(SEARCH)) || null == (params.get(SEARCH))) {
				
				
				List<Computer> ordi = cuterServ.computerOrder(page,(String)params.get(COLONNE),mode);
				setListComputer(model,ordi);
				setPage(model,page.getNumero(),cuterServ.count((String)params.get(SEARCH),1),page.getNbElem());
				setNumberOfComputer(model,cuterServ.count(params.get(SEARCH),0));
				
			}
			else {
				List<Computer> ordi = cuterServ.computerOrderSearch(page,(String)params.get(COLONNE),mode,(String)params.get(SEARCH));
				setListComputer(model,ordi);
				setPage(model,page.getNumero(),cuterServ.count((String) params.get(SEARCH),1),page.getNbElem());
				setNumberOfComputer(model,cuterServ.count((String) params.get(SEARCH),1));
				
				
			}
			
			setNbOrdiPage(model,nbOrdiPage);
			setField(model, params);
			
			
		} catch (Exception e) {
			logger.error("",e);
			throw new  ResourceNotFound();
		}
        return "dashboard";
    }
    
    @PostMapping("/")
    public String dashPost(@RequestParam Map<String,String> params, ModelMap model) {
    	try {
			deleteComputer(model,params);
			dashGet(params, model);
		} catch (Exception e) {
			logger.error("",e);
		}
    	return "dashboard";
    }
    
   
    private void setListComputer(ModelMap model, List<Computer> ordi) throws Exception {
		if(ordi==null)model.addAttribute("ordi", null);
		else {
		List<ComputerDto> res = new ArrayList<>();
		for(Iterator<Computer> i=ordi.iterator();i.hasNext();) {
			res.add(cuterMap.modelToDto(i.next()));
		}
		model.addAttribute("ordi", res);
		}
	}
	
	private void setNumberOfComputer(ModelMap model, int nbComputer) {
		model.addAttribute("numberOfComputer", nbComputer);
	}
	private void setField(ModelMap model, Map<String, String> params) {
		model.addAttribute(SEARCH, params.get(SEARCH));
		model.addAttribute("mode", params.get("mode"));
		model.addAttribute(COLONNE, params.get(COLONNE));
	}
	
	private void setNbOrdiPage(ModelMap model, int nbOrdiPage) {
		model.addAttribute(NBORDIPARPAGE, nbOrdiPage);
	}
	
	private void setPage(ModelMap model, int page,int nbOrdi,int nbOrdiPage) {
		model.addAttribute("page", page);
		int prev = page==1?1:page-1;
		int nbPage =nbOrdi/nbOrdiPage;
		if((nbOrdi%nbOrdiPage)!=0) nbPage++;
		int next = page==nbPage?nbPage:page+1;
		model.addAttribute("previous", prev);
		model.addAttribute("next", next);
	}
	
	private int getPage(ModelMap model, Map<String, String> params) {
		return Integer.parseInt((params.get("page") == null) ? "1" : (String) params.get("page"));
	}
	
	private static int getNbOrdiPage(ModelMap model ,Map<String, String> params) {
		nbOrdiPage =(params.get(NBORDIPARPAGE) == null) ? nbOrdiPage : Integer.valueOf((String) params.get(NBORDIPARPAGE));
		return nbOrdiPage;
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
	
	
	
	
 
}