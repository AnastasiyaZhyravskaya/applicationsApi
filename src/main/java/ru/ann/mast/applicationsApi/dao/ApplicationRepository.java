package ru.ann.mast.applicationsApi.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.ann.mast.applicationsApi.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Integer>{

}
