package ru.ann.mast.applicationsApi.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.ann.mast.applicationsApi.entity.Application;
import ru.ann.mast.applicationsApi.servise.ApplicationService;


@RestController
public class ApplicationController {
	
	@Autowired
	private ApplicationService applicationService;	
	
	
	@GetMapping("/applications")
	public List<Application> getAllApplications(){
		return applicationService.getAllApplications();
	}
	
	
	@GetMapping("/applications/{id}")
	public Application getApplication(@PathVariable int applicationId){
		return applicationService.getApplication(applicationId);
	}
	
	
	@PostMapping("/applications")
	public Application addApplication(@RequestBody @Valid Application application){
		applicationService.saveApplication(application);
		return application;
	}
	
	
	@PutMapping("/applications")
	public Application updateApplication(@RequestBody @Valid Application application){
		applicationService.updateApplication(application);
		return application;
	}
	

	
	@DeleteMapping("/applications/{id}")
	public String deleteApplication(@PathVariable int applicationId){
		applicationService.deleteApplication(applicationId);
		return "Application with id="+applicationId+" was deleted";
	}
	
	public String deleteAllApplication(){
		applicationService.deleteAllApplication();
		return "All application was deleted";
	}
	
	public String resetAutoIncrement() {
		applicationService.resetAutoIncrement();
		return "AUTO_INCREMENT = 1";
	}
	
	//TODO добавить различные фильтры по компании, по дате, пло инженеру, по статусу
	
}
