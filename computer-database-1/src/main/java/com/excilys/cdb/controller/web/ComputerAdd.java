package com.excilys.cdb.controller.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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




public class ComputerAdd extends AbstractServlet {
	private static final long serialVersionUID = 1L;  
	
    /**
     * @throws ServletException 
     * @see HttpServlet#HttpServlet()
     */
	private static Logger logger = LoggerFactory.getLogger(ComputerAdd.class);
	

	
	public ComputerAdd() throws ServletException 
	{
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			final List<Company> company = canyServ.listAllElements();
			setListCompany(request,company);
			
		} catch (Exception e) {
			
			logger.error("", e);
		}
		
		try {
		this.getServletContext().getRequestDispatcher( "/WEB-INF/addComputer.jsp" ).forward( request, response );
		}
		catch(Exception e){
			logger.error("", e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			createOrdi(request);
			doGet(request, response);
		} catch (Exception e) {
			
			logger.error("", e);
		}
		
	}
	
	private void setListCompany(HttpServletRequest request, List<Company> company) {
		List<CompanyDto> res = new ArrayList<>();
		for(Iterator<Company> i=company.iterator();i.hasNext();) {
			res.add(canyMap.modelToDto(i.next()));
		}
		request.setAttribute("company", res);
	}
	
	private void createOrdi(HttpServletRequest request) throws Exception {
			
			CompanyDto comp = new CompanyDto(request.getParameter("companyId"));
			String intro = formatDate(request.getParameter("introduced"));
			String disc = formatDate(request.getParameter("discontinued"));
			ComputerDto elem = new ComputerDto("-10",request.getParameter("computerName"), intro, disc, comp);
			cuterServ.create(cuterMap.dtoToModel((ComputerDto) val.valide(elem)));
			request.setAttribute("titre", elem.toString());
			
		
	}
	
	private String formatDate(String date) {
		if(date == null) return null;
		date = date.replace("/", "-");
		return  (date.length() <= 10 ) ? date.concat(" 12:00:00") : date;
	}
}
