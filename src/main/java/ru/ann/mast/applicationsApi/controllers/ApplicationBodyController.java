package ru.ann.mast.applicationsApi.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.ann.mast.applicationsApi.entity.ApplicationBody;
import ru.ann.mast.applicationsApi.servise.ApplicationBodyService;


@RestController
public class ApplicationBodyController {
	
	@Autowired
	private ApplicationBodyService applicationBodyService;	
	
	
	@PostMapping("/bodys")
	public ApplicationBody addApplicationBody(@RequestBody @Valid ApplicationBody body){
		applicationBodyService.saveApplicationBody(body);
		return body;
	}
	
	@GetMapping("/bodys/{id}")
	public ApplicationBody getApplicationBody(@PathVariable int id){
		return applicationBodyService.getApplicationBody(id);
	}
	
	@DeleteMapping("/bodys/{id}")
	public String deleteApplicationBody(@PathVariable int id){
		applicationBodyService.deleteApplicationBody(id);
		return "Application body with id="+id+" was deleted";
	}
	
	public String deleteAllBody(){
		applicationBodyService.deleteAllApplicationBody();
		return "All applicationBody was deleted";
	}
	
	public String resetAutoIncrement() {
		applicationBodyService.resetAutoIncrement();
		return "AUTO_INCREMENT = 1";
	}
	
}
