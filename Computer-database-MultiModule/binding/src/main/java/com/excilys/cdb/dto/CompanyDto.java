package com.excilys.cdb.dto;

public class CompanyDto extends Dto {
	private String name;
	
	public CompanyDto(String id) {
		this(id,"");
	}
	
	public CompanyDto(String id, String name) {
		super(id);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Company ["+this.id+"] "+this.name;
	}
	public int hashCode() {
		int result = 31*17 + this.getId().hashCode();
		result = 31*result + ((this.getName() == null) ? 0 : this.getName().hashCode());		
		
		return result;
		}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null) return false;
		if(!(obj instanceof CompanyDto)) return false;
		CompanyDto other = (CompanyDto) obj;
		return this.hashCode() == other.hashCode();
		
}
}