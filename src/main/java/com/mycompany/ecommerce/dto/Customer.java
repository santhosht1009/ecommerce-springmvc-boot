package com.mycompany.ecommerce.dto;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Component
public class Customer {

	
	@Id
	@GeneratedValue(generator = "customer_id")
	@SequenceGenerator(name = "customer_id", initialValue = 111001, allocationSize = 1, sequenceName = "customer_id")
	private int id;
	@Size(min = 5, message = "*Atleast Enter 5 Charecter")
	private String name;
	@Email(message = "*Email Format is Not Correct")
	@NotEmpty(message = "must not be empty")
	private String email;
	@NotNull
	@DecimalMin(value = "6000000000",message = "enter proper number")
	@DecimalMax(value = "10000000000",message = "enter proper number")
	private long mobile;
	@Size(min=8,message = "*password should be min 8 characters")
	private String password;
	@NotEmpty(message = "select one gender")
	private String gender;
	@Past(message = "Date Must Not be Todays or Futures Date")
	@NotNull(message = "not be empty")
	private LocalDate dob;
	private boolean status;
	private int otp;
}
