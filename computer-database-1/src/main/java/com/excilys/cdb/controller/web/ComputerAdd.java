package com.excilys.cdb.controller.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;


/**
 * Servlet implementation class ComputerAdd
 */

public class ComputerAdd extends HttpServlet {
	private static final long serialVersionUID = 1L;  
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ComputerAdd() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			final List<CompanyDto> company = CompanyService.getInstance().listAllElements();
			setListCompany(request,company);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		this.getServletContext().getRequestDispatcher( "/WEB-INF/addComputer.jsp" ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			createOrdi(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doGet(request, response);
	}
	
	private void setListCompany(HttpServletRequest request, List<CompanyDto> company) {
		request.setAttribute("company", company);
	}
	
	private void createOrdi(HttpServletRequest request) throws Exception {
			int nbComputer = ComputerService.getInstance().listAllElements().size() + 100;
			CompanyDto comp = new CompanyDto(request.getParameter("companyId"));
			String intro = formatDate(request.getParameter("introduced"));
			String disc = formatDate(request.getParameter("discontinued"));
			ComputerDto elem = new ComputerDto(""+nbComputer+"",request.getParameter("computerName"), intro, disc, comp);
			ComputerService.getInstance().create(elem);
			request.setAttribute("titre", elem.toString());
			
		
	}
	
	private String formatDate(String date) {
		date.replace("/", "-");
		String res = (date.length() <= 10 ) ? date.concat(" 12:00:00") : date;
		return res;
	}
}
