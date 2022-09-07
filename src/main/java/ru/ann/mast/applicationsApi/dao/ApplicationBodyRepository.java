package ru.ann.mast.applicationsApi.dao;


import org.springframework.data.jpa.repository.JpaRepository;

import ru.ann.mast.applicationsApi.entity.ApplicationBody;

public interface ApplicationBodyRepository extends 
				JpaRepository<ApplicationBody, Integer> {
}
