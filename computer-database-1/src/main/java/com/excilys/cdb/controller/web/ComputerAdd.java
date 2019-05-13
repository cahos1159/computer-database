package com.excilys.cdb.controller.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.dto.CompanyDto;
import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validateur.Validateur;




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
			final List<Company> company = CompanyService.getInstance().listAllElements();
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
	
	private void setListCompany(HttpServletRequest request, List<Company> company) {
		List<CompanyDto> res = new ArrayList<CompanyDto>();
		for(Iterator<Company> i=company.iterator();i.hasNext();) {
			res.add(CompanyMapper.getInstance().modelToDto(i.next()));
		}
		request.setAttribute("company", res);
	}
	
	private void createOrdi(HttpServletRequest request) throws Exception {
			
			CompanyDto comp = new CompanyDto(request.getParameter("companyId"));
			String intro = formatDate(request.getParameter("introduced"));
			String disc = formatDate(request.getParameter("discontinued"));
			ComputerDto elem = new ComputerDto("-10",request.getParameter("computerName"), intro, disc, comp);
			ComputerService.getInstance().create(ComputerMapper.getInstance().dtoToModel((ComputerDto) Validateur.getInstance().valide(elem)));
			request.setAttribute("titre", elem.toString());
			
		
	}
	
	private String formatDate(String date) {
		if(date == null) return null;
		date.replace("/", "-");
		String res = (date.length() <= 10 ) ? date.concat(" 12:00:00") : date;
		return res;
	}
}
