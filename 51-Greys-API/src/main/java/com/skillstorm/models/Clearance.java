package com.skillstorm.models;

import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "clearance")
public class Clearance {

	@Id
	@Column(name = "clearance_level")
	private int clearanceLevel;
	
	@Column(name = "clearance_type")
	private String clearanceType;
	
	@OneToMany(mappedBy = "minClearance", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("minClearance")
	private List<Project> projects;
	
	@OneToMany(mappedBy = "clearance", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("clearance") 
	private List<Employee> employees;
	
	public Clearance()
	{
		super();
	}
	
	public Clearance(int clearanceLevel, String clearanceType, List<Project> projects, List<Employee> employees)
	{
		super();
		this.clearanceLevel = clearanceLevel;
		this.clearanceType = clearanceType;
		this.projects = projects;
		this.employees = employees;
	}

	public int getClearanceLevel() {
		return clearanceLevel;
	}

	public void setClearanceLevel(int id) {
		this.clearanceLevel = id;
	}	

	public String getClearanceType() {
		return clearanceType;
	}

	public void setClearanceType(String clearanceType) {
		this.clearanceType = clearanceType;
	}

	public List<Project> getProjects() {
		return projects;
	}

	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}
	
	@Override
	public String toString() {
		return "Clearance [id=" + clearanceLevel + ", clearanceType=" + clearanceType + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(clearanceType, clearanceLevel);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Clearance other = (Clearance) obj;
		return Objects.equals(clearanceType, other.clearanceType) && clearanceLevel == other.clearanceLevel;
	}
	
	
}
