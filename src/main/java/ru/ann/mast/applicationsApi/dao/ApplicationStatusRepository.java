package ru.ann.mast.applicationsApi.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import ru.ann.mast.applicationsApi.entity.ApplicationStatus;

public interface ApplicationStatusRepository extends 
				JpaRepository<ApplicationStatus, String> {
}
