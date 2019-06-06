package com.excilys.cdb.controller.web;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.ComputerDto;
import com.excilys.cdb.model.Computer;

@RestController
@RequestMapping(path="/api",produces="application/json")
public class Api extends WebControl {
	
	private static Logger logger = LoggerFactory.getLogger(ControllerAddEdit.class);
	
	@GetMapping("/computer{id}")
	public Computer getCpt(@PathVariable("id") int id){
		try {
		if(Objects.isNull(id) || id < 0) {return cuterServ.read(1); }
		else return cuterServ.read(id);
		}
		catch(Exception e) {
			logger.error("Cette id:"+id+" n'éxiste pas",e);
		}
		return null;
	}
	
	@GetMapping("/computer-page{numero}")
	public Iterable<Computer> getPageCpt(@PathVariable("numero") int numero){
		try {
			System.out.println("Page "+numero);
		if(Objects.isNull(numero) || numero < 0 ) {
			Pageable tmp = PageRequest.of(1,10);
			return cuterServ.listComputer("",tmp);
			}
		else {
			Pageable tmp = PageRequest.of(numero,10);
			return cuterServ.listComputer("",tmp);
		}
		}
		catch(Exception e) {
			logger.warn("Les parametres sont invalides:"+numero+" n'éxiste pas",e);
		}
		return null;
	}
	@PostMapping(path="/create",consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public void postComputer(@RequestBody Computer computer) throws Exception {
		ComputerDto temp = (ComputerDto) val.valide(cuterMap.modelToDto(computer));
		System.out.println(temp.toString());
		Computer tmp = cuterMap.dtoToModel(temp);

		cuterServ.create((tmp));
	}
	
	@GetMapping("/computer-delete{id}")
	@ResponseStatus(code=HttpStatus.NO_CONTENT)
	public void deleteComputer(@PathVariable("id") int id) {
		try {
			cuterServ.delete(id);
		}
		catch(Exception e) {
			logger.warn("L'élément que vous essayez de supprimer n'éxiste pas",e);
		}
	}
	
	 	   	
}

