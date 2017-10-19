package nsy209.cnam.seldesave.shared.dto;

import com.squareup.moshi.Json;

import java.io.Serializable;

public class PersonDto implements Serializable{

	/**
	 * For checking version
	 */
	private static final long serialVersionUID = 1L;

	@Json(name ="id")
	private Long id;

	@Json(name ="name")
	private String name;

	@Json(name ="address")
	private String address;

	@Json(name ="postalCode")
	private String postalCode;

	@Json(name ="town")
	private String town;

	@Json(name ="email")
	private String email;

	@Json(name ="cellNumber")
	private String cellNumber;

	@Json(name ="phoneNumber")
	private String phoneNumber;
	
	
	/* getter and setter */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getTown() {
		return town;
	}

	public void setTown(String town) {
		this.town = town;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
