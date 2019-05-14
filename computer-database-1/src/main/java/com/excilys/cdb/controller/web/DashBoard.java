package com.excilys.cdb.controller.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.model.Computer;


public class DashBoard extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	private static int nbOrdiPage = 10;
	private static Logger logger = LoggerFactory.getLogger(DashBoard.class);
    //private final ComputerService computerSer = ComputerService.getInstance();

    /**
     * @see HttpServlet#HttpServlet()
     */


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
	
	public DashBoard() throws ServletException 
	{
		super();
	}
	

    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			

			Page page = new Page(getPage(request),getNbOrdiPage(request));
			int mode = (request.getParameter("mode") == null ||"".equals((request.getParameter("mode")))) ? 0 : Integer.valueOf(request.getParameter("mode"));
			
		 	if("".equals(request.getParameter("search")) || null == (request.getParameter("search"))) {
				
				
				List<Computer> ordi = c_uterServ.computerOrder(page,request.getParameter("colonne"),mode);
				int nbComputer = ordi == null ? 0 : ordi.size();
				ordi = Pagination.getInstance().MiseEnPage(ordi,page);
				setListComputer(request,ordi);
				setPage(request,page.getNumero(),c_uterServ.count(request.getParameter("search"),1),page.getNbElem());
				setNumberOfComputer(request,c_uterServ.count(request.getParameter("search"),0));
				
			}
			else {
				List<Computer> ordi = c_uterServ.computerOrderSearch(page,request.getParameter("colonne"),mode,request.getParameter("search"));
	
				ordi = Pagination.getInstance().MiseEnPage(ordi,page);
				setListComputer(request,ordi);
				setPage(request,page.getNumero(),c_uterServ.count(request.getParameter("search"),1),page.getNbElem());
				setNumberOfComputer(request,c_uterServ.count(request.getParameter("search"),1));
				
				
			}
			
			setNbOrdiPage(request,nbOrdiPage);
			setField(request);
			this.getServletContext().getRequestDispatcher( "/WEB-INF/dashboard.jsp" ).forward( request, response );
			
		} catch (Exception e) {
			logger.error("",e);
		}
		
	}




	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			deleteComputer(request);
			doGet(request, response);
		} catch (Exception e) {
			logger.error("",e);
		}
		
	}
	
	private void setListComputer(HttpServletRequest request, List<Computer> ordi) throws Exception {
		if(ordi==null)request.setAttribute("ordi", null);
		else {
		List<ComputerDto> res = new ArrayList<ComputerDto>();
		for(Iterator<Computer> i=ordi.iterator();i.hasNext();) {
			res.add(c_uterMap.modelToDto(i.next()));
		}
		request.setAttribute("ordi", res);
		}
	}
	
	private void setNumberOfComputer(HttpServletRequest request, int nbComputer) {
		request.setAttribute("numberOfComputer", nbComputer);
	}
	private void setField(HttpServletRequest request) {
		request.setAttribute("search", request.getParameter("search"));
		request.setAttribute("mode", request.getParameter("mode"));
		request.setAttribute("colonne", request.getParameter("colonne"));
	}
	
	private void setNbOrdiPage(HttpServletRequest request, int nbOrdiPage) {
		request.setAttribute("nbOrdiPage", nbOrdiPage);
	}
	
	private void setPage(HttpServletRequest request, int Page,int nbOrdi,int nbOrdiPage) {
		request.setAttribute("page", Page);
		int prev = Page==1?1:Page-1;
		int nbPage =nbOrdi/nbOrdiPage;
		if((nbOrdi%nbOrdiPage)!=0) nbPage++;
		int next = Page==nbPage?nbPage:Page+1;
		request.setAttribute("previous", prev);
		request.setAttribute("next", next);
	}
	
	private int getPage(HttpServletRequest request) {
		
		int pageActuel = Integer.valueOf((request.getParameter("page") == null) ? "1" : request.getParameter("page"));
		return pageActuel;
	}
	
	private int getNbOrdiPage(HttpServletRequest request) {
		DashBoard.nbOrdiPage =(request.getParameter("nbOrdiPage") == null) ? DashBoard.nbOrdiPage : Integer.valueOf(request.getParameter("nbOrdiPage"));
		return DashBoard.nbOrdiPage;
	}
	private void deleteComputer(HttpServletRequest request) throws RuntimeException, Exception {
		String idaggreg = (String) request.getParameter("selection");
		List<String> ids = Arrays.asList(idaggreg.split(","));
		for(String id : ids) {
			c_uterServ.delete(c_uterServ.read(Integer.parseInt(id)));
		}
	}

}
