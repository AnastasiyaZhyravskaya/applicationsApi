package ru.ann.mast.applicationsApi.servise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import ru.ann.mast.applicationsApi.dao.ApplicationRepository;
import ru.ann.mast.applicationsApi.entity.Application;
import ru.ann.mast.applicationsApi.exceptionHandling.NoSuchException;

@Service
public class ApplicationServiceImpl implements ApplicationService {
	
	@Autowired
	private ApplicationRepository applicationRepository;

	@Override
	public List<Application> getAllApplications() {
		return applicationRepository.findAll();
	}

	@Override
	public Application getApplication(int applicationId) {
		return applicationRepository.findById(applicationId).orElseThrow(()->
							new NoSuchException("Application is not found, id="+applicationId));
	}

	@Override
	public Application saveApplication(Application application) {
		return applicationRepository.save(application);
	}

	@Override
	public Application updateApplication(Application application) {
		applicationRepository.findById(application.getId()).orElseThrow(()->
						new NoSuchException("Application is not found, id="+application.getId()));
		applicationRepository.save(application);
		return application;
	}

	@Override
	public void deleteApplication(int applicationId) {
		getApplication(applicationId);
		applicationRepository.deleteById(applicationId);
		
	}

	@Override
	public void deleteAllApplication() {
		applicationRepository.deleteAll();
		
	}

	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	@Override
	public void resetAutoIncrement() {
		jdbcTemplate.update("ALTER TABLE mast_applications.applications AUTO_INCREMENT = 1");
		
	}
	
	
}
