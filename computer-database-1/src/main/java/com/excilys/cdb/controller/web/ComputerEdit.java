package com.excilys.cdb.controller.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validateur.Validateur;



/**
 * Servlet implementation class ComputerAdd
 */


public class ComputerEdit extends AbstractServlet {
	private static final long serialVersionUID = 1L;  
	
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
			List<Company> company;
			final int id = Integer.valueOf(request.getParameter("id"));
			Computer computer = c_uterServ.read(id);
			
			

			try {

				company = c_anyServ.listAllElements();
				setListCompany(request,company);
				setIdComputer(request,id);
				setComputer(request,c_uterMap.modelToDto(computer));
				
			} catch (Exception e) {
				
			}

		this.getServletContext().getRequestDispatcher( "/WEB-INF/editComputer.jsp" ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			updateOrdi(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		doGet(request, response);
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
		
		c_uterServ.update(c_uterMap.dtoToModel(elem));
		
	
}
	private void setComputer(HttpServletRequest request, ComputerDto computer) {
		request.setAttribute("name", computer.getName());
		request.setAttribute("intro", computer.getIntroduction());
		request.setAttribute("disc", computer.getDiscontinued());
		request.setAttribute("compName", computer.getCompany().getName());
		
	}
	
	private String formatDate(String date) {
		if(date == null) return null;
		date.replace("/", "-");
		String res = (date.length() <= 10 ) ? date.concat(" 12:00:00") : date;
		return res;
	}
}
