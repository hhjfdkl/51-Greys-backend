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
@Table(name = "location")
public class Location {

	@Id
	@Column(name = "location_id")
	private int id;
	
	@Column(name = "location_name")
	private String locationName;
	
	@Column(name = "province")
	private String province;
	
	@Column(name = "country")
	private String country;
	
	@Column(name = "longitude")
	private double longitude;
	
	@Column(name = "latitude")
	private double latitude;
	
	@OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
	@JsonIgnoreProperties("location")
	private List<Employee> employees;
	
	
	public Location()
	{
		super();
	}
	
	public Location(
			  int id
			, String locationName
			, String province
			, String country
			, double longitude
			, double latitude
			, List<Employee> employees
			)
	{
		super();
		this.id = id;
		this.locationName = locationName;
		this.province = province;
		this.country = country;
		this.longitude = longitude;
		this.latitude = latitude;
		this.employees = employees;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", locationName=" + locationName + ", province=" + province + ", country="
				+ country + ", longitude=" + longitude + ", latitude=" + latitude + ", employees=" + employees + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(country, employees, id, latitude, locationName, longitude, province);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		return Objects.equals(country, other.country) && Objects.equals(employees, other.employees) && id == other.id
				&& Double.doubleToLongBits(latitude) == Double.doubleToLongBits(other.latitude)
				&& Objects.equals(locationName, other.locationName)
				&& Double.doubleToLongBits(longitude) == Double.doubleToLongBits(other.longitude)
				&& Objects.equals(province, other.province);
	}
	
	
}
