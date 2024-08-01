package com.skillstorm.models;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clearance")
public class Clearance {

	@Id
	@Column(name = "clearance_level")
	private int id;
	
	@Column(name = "clearance_type")
	private String clearanceType;
	
	public Clearance()
	{
		super();
	}
	
	public Clearance(int id, String clearanceType)
	{
		super();
		this.id = id;
		this.clearanceType = clearanceType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClearanceType() {
		return clearanceType;
	}

	public void setClearanceType(String clearanceType) {
		this.clearanceType = clearanceType;
	}

	@Override
	public String toString() {
		return "Clearance [id=" + id + ", clearanceType=" + clearanceType + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(clearanceType, id);
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
		return Objects.equals(clearanceType, other.clearanceType) && id == other.id;
	}
	
	
}
