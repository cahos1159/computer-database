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
	private static final String SEARCH = "search";
	private static Logger logger = LoggerFactory.getLogger(DashBoard.class);
	private static final String COLONNE = "colonne";
	private static final String NBORDIPARPAGE = "nbOrdiPage";
    

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
			
		 	if("".equals(request.getParameter(SEARCH)) || null == (request.getParameter(SEARCH))) {
				
				
				List<Computer> ordi = cuterServ.computerOrder(page,request.getParameter(COLONNE),mode);
				ordi = Pagination.getInstance().miseEnPage(ordi,page);
				setListComputer(request,ordi);
				setPage(request,page.getNumero(),cuterServ.count(request.getParameter(SEARCH),1),page.getNbElem());
				setNumberOfComputer(request,cuterServ.count(request.getParameter(SEARCH),0));
				
			}
			else {
				List<Computer> ordi = cuterServ.computerOrderSearch(page,request.getParameter(COLONNE),mode,request.getParameter(SEARCH));
	
				ordi = Pagination.getInstance().miseEnPage(ordi,page);
				setListComputer(request,ordi);
				setPage(request,page.getNumero(),cuterServ.count(request.getParameter(SEARCH),1),page.getNbElem());
				setNumberOfComputer(request,cuterServ.count(request.getParameter(SEARCH),1));
				
				
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
    @Override
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
		List<ComputerDto> res = new ArrayList<>();
		for(Iterator<Computer> i=ordi.iterator();i.hasNext();) {
			res.add(cuterMap.modelToDto(i.next()));
		}
		request.setAttribute("ordi", res);
		}
	}
	
	private void setNumberOfComputer(HttpServletRequest request, int nbComputer) {
		request.setAttribute("numberOfComputer", nbComputer);
	}
	private void setField(HttpServletRequest request) {
		request.setAttribute(SEARCH, request.getParameter(SEARCH));
		request.setAttribute("mode", request.getParameter("mode"));
		request.setAttribute(COLONNE, request.getParameter(COLONNE));
	}
	
	private void setNbOrdiPage(HttpServletRequest request, int nbOrdiPage) {
		request.setAttribute(NBORDIPARPAGE, nbOrdiPage);
	}
	
	private void setPage(HttpServletRequest request, int page,int nbOrdi,int nbOrdiPage) {
		request.setAttribute("page", page);
		int prev = page==1?1:page-1;
		int nbPage =nbOrdi/nbOrdiPage;
		if((nbOrdi%nbOrdiPage)!=0) nbPage++;
		int next = page==nbPage?nbPage:page+1;
		request.setAttribute("previous", prev);
		request.setAttribute("next", next);
	}
	
	private int getPage(HttpServletRequest request) {
		return Integer.parseInt((request.getParameter("page") == null) ? "1" : request.getParameter("page"));
	}
	
	private static int getNbOrdiPage(HttpServletRequest request) {
		DashBoard.nbOrdiPage =(request.getParameter(NBORDIPARPAGE) == null) ? DashBoard.nbOrdiPage : Integer.valueOf(request.getParameter(NBORDIPARPAGE));
		return DashBoard.nbOrdiPage;
	}
	private void deleteComputer(HttpServletRequest request){
		String idaggreg = request.getParameter("selection");
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
