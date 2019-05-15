package com.excilys.cdb.controller.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;



/**
 * Servlet implementation class ComputerAdd
 */


public class ComputerEdit extends AbstractServlet {
	private static final long serialVersionUID = 1L;  
	private static Logger logger = LoggerFactory.getLogger(ComputerEdit.class);
    /**
     * @throws ServletException 
     * @see HttpServlet#HttpServlet()
     */
	
	
	

	
	public ComputerEdit() throws ServletException 
	{
		super();
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
				
			
			
			

			try {
				List<Company> company;
				final int id = Integer.parseInt(request.getParameter("id"));
				Computer computer = null;
				computer = cuterServ.read(id);
			
				company = canyServ.listAllElements();
				setListCompany(request,company);
				setIdComputer(request,id);
				setComputer(request,cuterMap.modelToDto(computer));
				this.getServletContext().getRequestDispatcher( "/WEB-INF/editComputer.jsp" ).forward( request, response );
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
			updateOrdi(request);
			doGet(request, response);
		} catch (Exception e) {
			logger.error("",e);
		}
		
	}
	
	private void setListCompany(HttpServletRequest request, List<Company> company) {
		request.setAttribute("company", company);
	}
	
	private void setIdComputer(HttpServletRequest request, int id) {
		request.setAttribute("id", id);
	}
	
	private void updateOrdi(HttpServletRequest request) throws Exception {
		CompanyDto comp = new CompanyDto(request.getParameter("companyId"));
		
		String intro = formatDate(request.getParameter("introduced"));
		String disc = formatDate(request.getParameter("discontinued"));
		
		ComputerDto elem = new ComputerDto(request.getParameter("id"),request.getParameter("name"), intro, disc, comp);
		
		cuterServ.update(cuterMap.dtoToModel(elem));
		
	
}
	private void setComputer(HttpServletRequest request, ComputerDto computer) {
		request.setAttribute("name", computer.getName());
		request.setAttribute("intro", computer.getIntroduction());
		request.setAttribute("disc", computer.getDiscontinued());
		request.setAttribute("compName", computer.getCompany().getName());
		
	}
	
	private String formatDate(String date) {
		if(date == null) return null;
		date = date.replace("/", "-");
		return (date.length() <= 10 ) ? date.concat(" 12:00:00") : date;
	}
}
