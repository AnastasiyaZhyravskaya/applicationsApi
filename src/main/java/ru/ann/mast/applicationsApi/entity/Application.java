package ru.ann.mast.applicationsApi.entity;


import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonSerialize
@JsonAutoDetect
public class Application {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	
	@Column(name = "application_name")
	private String applicationName;
	
	
	@Column(name = "region_id")
	private int regionId;
	
	
	@Column(name = "company_id")
	private int companyId;
	
	
	@Column(name = "responsible_id")
	private int responsibleId;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
//	@OneToOne(mappedBy = "id",
//			cascade = CascadeType.ALL)
	@Column(name = "application_body_id")
	private int applicationBodyId;
	
	
//	@JsonInclude(JsonInclude.Include.NON_NULL)
	@OneToMany(mappedBy = "status",
			cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
	@Column(name = "application_status")
	private List<ApplicationStatus> applicationStatus;
	
	
	@Column(name = "date_create")
	private Date dateCreate;
	
	
	@Column(name = "date_in_work")
	private Date dateInWork;
	
	
	@Column(name = "date_completed")
	private Date dateCompleted;
	
}
