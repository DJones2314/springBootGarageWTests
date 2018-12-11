package com.qa.jones.dave.garageSpringBootGarage.model;

import java.io.Serializable;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "vehicle")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "creationDate", "lastModified" }, allowGetters = true)

public class DataModel implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	private String type; // name

	@NotBlank
	private String colour; // address
	private Integer year; // age

	@Column(nullable = false, updatable = false)

	@CreationTimestamp
	@CreatedDate
	private Date creationDate;
	
	@Column(nullable = false)
	@UpdateTimestamp
	
	@LastModifiedDate
	private Date lastModified;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getColour() {
		return colour;
	}

	public void setColour(String colour) {
		this.colour = colour;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

}
