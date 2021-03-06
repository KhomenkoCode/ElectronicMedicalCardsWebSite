package com.khomenkocode.graduationproject.entities;
// Generated 07.05.2018 15:13:57 by Hibernate Tools 5.1.4.Final

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ThospitalDoctors generated by hbm2java
 */
@Entity
@Table(name = "thospital_doctors")
public class THospitalDoctors implements java.io.Serializable {

	private int hospitalDoctorsId;
	private TDoctors tdoctors;
	private THospitals thospitals;
	private Date startDate;

	public THospitalDoctors() {
	}

	public THospitalDoctors( TDoctors tdoctors, THospitals thospitals) {
		this.tdoctors = tdoctors;
		this.thospitals = thospitals;
	}

	public THospitalDoctors( TDoctors tdoctors, THospitals thospitals, Date startDate) {
		this.tdoctors = tdoctors;
		this.thospitals = thospitals;
		this.startDate = startDate;
	}

	@Id
	@SequenceGenerator(name="pk_sequence",sequenceName="thospitals_department_hospital_department_id_seq", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@Column(name = "hospital_doctors_id", unique = true, nullable = false)
	public int getHospitalDoctorsId() {
		return this.hospitalDoctorsId;
	}

	public void setHospitalDoctorsId(int hospitalDoctorsId) {
		this.hospitalDoctorsId = hospitalDoctorsId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "doctor_id", nullable = false)
	public TDoctors getTdoctors() {
		return this.tdoctors;
	}

	public void setTdoctors(TDoctors tdoctors) {
		this.tdoctors = tdoctors;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hospital_id", nullable = false)
	public THospitals getThospitals() {
		return this.thospitals;
	}

	public void setThospitals(THospitals thospitals) {
		this.thospitals = thospitals;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "start_date", length = 13)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

}
