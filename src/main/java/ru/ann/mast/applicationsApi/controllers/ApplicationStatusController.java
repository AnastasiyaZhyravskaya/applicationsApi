package ru.ann.mast.applicationsApi.controllers;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.ann.mast.applicationsApi.entity.ApplicationStatus;
import ru.ann.mast.applicationsApi.servise.ApplicationStatusService;

@RestController
public class ApplicationStatusController {
	
	@Autowired
	private ApplicationStatusService applicationStatusService;	
	
	@GetMapping("/status")
	public List<ApplicationStatus> getAllStatus(){
		return applicationStatusService.getAllStatus();
	}
	
	@PostMapping("/status")
	public ApplicationStatus addNewStatus(@RequestBody @Valid ApplicationStatus status){
		applicationStatusService.saveStatus(status);
		return status;
	}
	
	@DeleteMapping("/status/{status}")
	public String deleteStatus(@PathVariable String status){
		applicationStatusService.deleteStatus(status);
		return "Status "+status+" was deleted";
	}
	
	public String deleteAllStatus(){
		applicationStatusService.deleteAllStatus();
		return "All status was deleted";
	}
	
	public String resetAutoIncrement() {
		applicationStatusService.resetAutoIncrement();
		return "AUTO_INCREMENT = 1";
	}
	
}
