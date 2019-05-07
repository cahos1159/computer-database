package com.excilys.cdb.controller.web;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.service.ComputerService;



public class DashBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int nbOrdiPage = 10;
    //private final ComputerService computerSer = ComputerService.getInstance();

    /**
     * @see HttpServlet#HttpServlet()
     */
 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			
			
			final int nbOrdiPage = getNbOrdiPage(request);
			final int page = getPage(request);
			
			if(request.getParameter("search") == "" || request.getParameter("search") == null) {
				int mode = (request.getParameter("mode") == null ||(request.getParameter("mode") == "")) ? 0 : Integer.valueOf(request.getParameter("mode"));
				int nbComputer =ComputerService.getInstance().computerOrder(request.getParameter("colonne"),mode).size();
				List<ComputerDto> ordi = ComputerService.getInstance().computerOrder(request.getParameter("colonne"),mode);
				ordi = Pagination.getInstance().MiseEnPage(ordi, nbOrdiPage, page);
				setListComputer(request,ordi);
				setPage(request,page,nbComputer,nbOrdiPage);
				setNumberOfComputer(request,nbComputer);
				setNbOrdiPage(request,nbOrdiPage);
				setField(request);
			}
			else {
				int mode = (request.getParameter("mode") == null ||(request.getParameter("mode") == "")) ? 0 : Integer.valueOf(request.getParameter("mode"));
				List<ComputerDto> ordi = ComputerService.getInstance().computerOrderSearch(request.getParameter("colonne"),mode,request.getParameter("search"));
				ordi = Pagination.getInstance().MiseEnPage(ordi, nbOrdiPage, page);
				int nbComputer =ComputerService.getInstance().computerOrderSearch(request.getParameter("colonne"),mode,request.getParameter("search")).size();
				setListComputer(request,ordi);
				setPage(request,page,nbComputer,nbOrdiPage);
				setNumberOfComputer(request,nbComputer);
				setNbOrdiPage(request,nbOrdiPage);
				setField(request);
				
				
			}
			
			this.getServletContext().getRequestDispatcher( "/WEB-INF/dashboard.jsp" ).forward( request, response );
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			System.out.print("Post");
			deleteComputer(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		doGet(request, response);
	}
	
	private void setListComputer(HttpServletRequest request, List<ComputerDto> ordi) {
		request.setAttribute("ordi", ordi);
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
		int nbPage =Integer.valueOf(nbOrdi/nbOrdiPage);
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
		System.out.println(idaggreg);
		List<String> ids = Arrays.asList(idaggreg.split(","));
		for(String id : ids) {
			ComputerService.getInstance().delete(ComputerService.getInstance().read(id));
		}
	}

}
