package com.excilys.cdb.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.excilys.cdb.exception.InvalidDateOrderException;

@Entity
@Table(name="computer")
public class Computer  {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(nullable = false,name = "name")
	private String name;
	@Column(name = "introduced")
	private Timestamp introduced;
	@Column(name = "discontinued")
	private Timestamp discontinued;
	@ManyToOne(fetch = FetchType.EAGER, optional = true, targetEntity = Company.class)
	@JoinColumn(name = "company_id",
	referencedColumnName = "id")
	private Company company;
	
	public Computer() {}
	
	public Computer(int id, String name, Timestamp dateIntro, Timestamp dateDisc, Company manufacturer) {
		this.setId(id);
		this.setName(name);
		this.setDateIntro(dateIntro);
		this.setDateDisc(dateDisc);
		this.setCompany(manufacturer);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getDateIntro() {
		return introduced;
	}

	public void setDateIntro(Timestamp dateIntro) {
		if (dateIntro != null && this.discontinued != null && dateIntro.after(this.discontinued)) {
			throw new InvalidDateOrderException(dateIntro, this.discontinued);
		}
		this.introduced = dateIntro;
	}

	public Timestamp getDateDisc() {
		return discontinued;
	}

	public void setDateDisc(Timestamp dateDisc) {
		if (dateDisc != null && this.introduced != null && dateDisc.before(this.introduced)) {
			throw new InvalidDateOrderException(this.introduced, dateDisc);
		}
		this.discontinued = dateDisc;
	}

	public Company getCompany() {
		return this.company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
        if (!(object instanceof Computer))
            return false;
        
        Computer model = (Computer) object;
        return model.getId() == this.getId();
	}
	
	@Override
	public int hashCode() {
		return 31*17 + this.getId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
	
}
