package com.excilys.cdb.dto;

public class ComputerDto extends Dto {
	private String name;
	private String introduction;
	private String discontinued;
	private CompanyDto company;
	
	public ComputerDto(String id) {
		this(id,"",null,null,null);
	}
	
	public ComputerDto(String id, String name, String i, String d, CompanyDto c) {
		super(id);
		this.setName(name);
		this.setIntroduction(i);
		this.setDiscontinued(d);
		this.setCompany(c);
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

	public CompanyDto getCompany() {
		return company;
	}

	public void setCompany(CompanyDto companyName) {
		this.company = companyName;
	}
	
	
	
	@Override
	public String toString() {
		return "Computer ["+this.getId()+"] " + this.getName() + " (" + this.getIntroduction() + ") (" + this.getDiscontinued() + ") " + this.getCompany();
	}
	

	public int hashCode() {
		int result = 31*17 + this.getId().hashCode();
		result = 31*result + ((this.getName() == null) ? 0 : this.getName().hashCode());
		result = 31*result + ((this.getIntroduction() == null) ? 0 : this.getIntroduction().hashCode());
		result = 31*result + ((this.getDiscontinued()== null) ? 0 : this.getDiscontinued().hashCode());
		result = 31*result + this.getCompany().hashCode();
		
		
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