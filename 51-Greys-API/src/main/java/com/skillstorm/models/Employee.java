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
@Table(name = "employee")
public class Employee 
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private int id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "occupation")
	private String occupation;
	
	@ManyToOne
	@JoinColumn(name = "clearance_level", referencedColumnName = "clearance_level")
	@JsonIgnoreProperties("employees")
	private Clearance clearance;
	
	@ManyToOne
	@JoinColumn(name = "location_id", referencedColumnName = "location_id")
	@JsonIgnoreProperties({"employees.projects","employees.location", "employees.clearance"})
	private Location location;
	
	@Column(name = "img")
	private String img;
	
	@Column(name = "sp_id")
	private String spId;
	
	@Column(name = "sp_acct_id")
	private String spAcctId;

	@ManyToOne
	@JoinColumn(name="project_id", referencedColumnName = "project_id")
	@JsonIgnoreProperties({"employees", "minClearance"})
	private Project project;
	
	
	//Default constructor
	public Employee()
	{
		super();
	}
	
	//Constructor overload for all info that will display on the front
	public Employee(
			  int id
			, String firstName
			, String lastName
			, String email
			, String phoneNumber
			, String occupation
			, Clearance clearance
			, Location location
			, String img
			, Project project
			)
	{
		super();
		this.id = id; 
		this.firstName = firstName; 
		this.lastName = lastName; 
		this.email = email; 
		this.phoneNumber = phoneNumber;
		this.occupation = occupation;
		this.clearance = clearance;
		this.location = location;
		this.img = img;
		this.project = project;
	}
	
	//Constructor overload allowing SailPoint User id
	public Employee(
			  int id
			, String firstName
			, String lastName
			, String email
			, String phoneNumber
			, String occupation
			, Clearance clearance
			, Location location
			, String img
			, String spId
			, Project project
			)
	{
		super();
		this.id = id; 
		this.firstName = firstName; 
		this.lastName = lastName; 
		this.email = email; 
		this.phoneNumber = phoneNumber;
		this.occupation = occupation;
		this.clearance = clearance;
		this.location = location;
		this.img = img;
		this.spId = spId;
		this.project = project;
	}

	//Constructor overload allowing SailPoint Account id
	//Account MUST have a user, so we won't make a constructor
	//for SP account without a SP user
	public Employee(
			  int id
			, String firstName
			, String lastName
			, String email
			, String phoneNumber
			, String occupation
			, Clearance clearance
			, Location location
			, String img
			, String spId
			, String spAcctId
			, Project project
			)
	{
		super();
		this.id = id; 
		this.firstName = firstName; 
		this.lastName = lastName; 
		this.email = email; 
		this.phoneNumber = phoneNumber;
		this.occupation = occupation;
		this.clearance = clearance;
		this.location = location;
		this.img = img;
		this.spId = spId;
		this.spAcctId = spAcctId;
		this.project = project;
	}

	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public Clearance getClearance() {
		return clearance;
	}

	public void setClearance(Clearance clearance) {
		this.clearance = clearance;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getSpAcctId() {
		return spAcctId;
	}

	public void setSpAcctId(String spAcctId) {
		this.spAcctId = spAcctId;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}


	
	



	@Override
	public String toString() {
		return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", occupation=" + occupation + ", clearance=" + clearance + ", img="
				+ img + "]";
	}


	@Override
	public int hashCode() {
		return Objects.hash(clearance, email, firstName, id, img, lastName, occupation, phoneNumber);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(clearance, other.clearance) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && id == other.id && Objects.equals(img, other.img)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(occupation, other.occupation)
				&& Objects.equals(phoneNumber, other.phoneNumber);
	}
	
	
	
	
}
