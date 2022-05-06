package com.qa.ims.persistence.domain;

import java.util.Objects;

public class Order {

	private Long id;
	private Long customerID;
	

	public Order(Long customerID) {
		super();
		this.customerID = customerID;
	}

	
	public Order(Long id, Long customerID) {
		super();
		this.id = id;
		this.customerID = customerID;
	}

	


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCustomerID() {
		return customerID;
	}

	public void setCustomerID(Long customerID) {
		this.customerID = customerID;
	}



	@Override
	public String toString() {
		return "Order: " + id + ", customer ID: " + customerID;
	}


	@Override
	public int hashCode() {
		return Objects.hash(customerID, id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		return Objects.equals(customerID, other.customerID) && Objects.equals(id, other.id);
	}
	
	

}
