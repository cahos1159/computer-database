package com.excilys.cdb.dto;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

public class ComputerDto extends Dto {
	@NotEmpty(message = "Name incorrect or empty")
	@NotBlank(message = "Name incorrect or empty")
	private String name;
	@NotEmpty(message = "date incorrect or empty")
	@NotBlank(message = "date incorrect or empty")
	@DateTimeFormat(pattern = "dd-mm-yyyy")
	private String introduction;
	@NotEmpty(message = "date incorrect or empty")
	@NotBlank(message = "date incorrect or empty")
	@DateTimeFormat(pattern = "dd-mm-yyyy")
	private String discontinued;
	@Min(value = 0, message = "Negative values doesn't exist")
	private String companyId;
	private String companyName;
	
	public ComputerDto(String id) {
		this(id,"",null,null,null,null);
	}
	
	public ComputerDto(String id, String name, String i, String d, String idc,String namec) {
		super(id);
		this.setName(name);
		this.setIntroduction(i);
		this.setDiscontinued(d);
		this.setCompanyId(idc);
		this.setCompanyName(namec);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public String getCompanyId() {
		return companyId;
	}
	
	public void setCompanyId(String compId) {
		this.companyId = compId;
	}
	
	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String compName) {
		this.companyName = compName;
	}
	
	
	
	@Override
	public String toString() {
		return "Computer ["+this.getId()+"] " + this.getName() + " (" + this.getIntroduction() + ") (" + this.getDiscontinued() + ") " + this.getCompanyId()+ this.getCompanyName();
	}
	

	public int hashCode() {
		int result = 31*17 + this.getId().hashCode();
		result = 31*result + ((this.getName() == null) ? 0 : this.getName().hashCode());
		result = 31*result + ((this.getIntroduction() == null) ? 0 : this.getIntroduction().hashCode());
		result = 31*result + ((this.getDiscontinued()== null) ? 0 : this.getDiscontinued().hashCode());
		result = 31*result + this.getName().hashCode();
		
		
		return result;
		}
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof ComputerDto)) return false;
		ComputerDto other = (ComputerDto) obj;
		return this.hashCode() == other.hashCode();
		
}
}