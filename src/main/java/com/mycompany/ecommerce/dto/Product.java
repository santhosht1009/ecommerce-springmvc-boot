package com.mycompany.ecommerce.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Product {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@NotEmpty(message = "*This is Required Field")
	private String name;
	@NotNull(message = "*This is Required Field")
	@DecimalMin(value = "1", message = "*Enter Value Greater than 1")
	private int stock;
	@NotNull(message = "*This is Required Field")
	@DecimalMin(value = "1", message = "*Enter Value Greater than 1")
	private double price;
	@NotEmpty(message = "*This is Required Field")
	private String category;
	boolean approved;
	@ManyToOne
	Merchant merchant;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	byte[] picture;
}
