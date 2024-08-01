package com.skillstorm.models;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "project_id")
	private int id;
	
	@Column(name = "codename")
	private String codename;
	
	@Column(name = "description")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "min_clearance", referencedColumnName = "clearance_level")
	@JsonIgnoreProperties("projects")
	private Clearance minClearance;
	
	@Column(name = "img")
	private String img;
	
	//many to many with employees
	//private Employee[] employees;
	
	public Project()
	{
		super();
	}
	
	public Project(
			  int id
			, String codename
			, String description
			, Clearance minClearance
			, String img
		//	, Employee[] employees
			)
	{
		super();
		this.id = id;
		this.codename = codename;
		this.description = description;
		this.minClearance = minClearance;
		this.img = img;
	//	this.employees = employees;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodename() {
		return codename;
	}

	public void setCodename(String codename) {
		this.codename = codename;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Clearance getMinClearance() {
		return minClearance;
	}

	public void setMinClearance(Clearance minClearance) {
		this.minClearance = minClearance;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	
	@Override
	public String toString() {
		return "Project [id=" + id + ", codename=" + codename + ", description=" + description + ", minClearance="
				+ minClearance + ", img=" + img + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(codename, description, id, img, minClearance);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Project other = (Project) obj;
		return Objects.equals(codename, other.codename) && Objects.equals(description, other.description)
				&& id == other.id && Objects.equals(img, other.img) && Objects.equals(minClearance, other.minClearance);
	}
	
	
	
	
}
