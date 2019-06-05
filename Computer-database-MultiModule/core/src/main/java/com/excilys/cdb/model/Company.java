package com.excilys.cdb.model;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="company")
public class Company {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Basic(optional = false)
	@Column(nullable = false)
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy ="company", cascade = CascadeType.REMOVE)
	private List<Computer> computers;
	
	public Company() {}
	
	public Company(Integer id, String name) {
		this.setId(id);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object == this)
			return true;
        if (!(object instanceof Company))
            return false;
        
        Company model = (Company) object;
        return model.getId() == this.getId();
	}
	
	@Override
	public int hashCode() {
		return  31*17 + this.getId();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}