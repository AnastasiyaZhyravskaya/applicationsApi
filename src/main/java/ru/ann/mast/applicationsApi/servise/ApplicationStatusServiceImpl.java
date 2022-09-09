package ru.ann.mast.applicationsApi.servise;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import ru.ann.mast.applicationsApi.dao.ApplicationStatusRepository;
import ru.ann.mast.applicationsApi.entity.ApplicationStatus;
import ru.ann.mast.applicationsApi.exceptionHandling.NoSuchException;

@Service
public class ApplicationStatusServiceImpl implements ApplicationStatusService {
	
	@Autowired
	private ApplicationStatusRepository applicationStatusRepository;


	@Override
	public List<ApplicationStatus> getAllStatus() {
		return applicationStatusRepository.findAll();
	}

	@Override
	public ApplicationStatus saveStatus(ApplicationStatus status) {
		if (applicationStatusRepository.findById(status.getStatus()).isPresent()) 
			throw new NoSuchException("Status "+status.getStatus()+" already exists");
		applicationStatusRepository.save(status);
		return status;
	}


	@Override
	public void deleteStatus(String status) {
		applicationStatusRepository.findById(status).orElseThrow(()->
					new NoSuchException("Status "+status+" is not found"));
		applicationStatusRepository.deleteById(status);
	};
	
	@Override
	public void deleteAllStatus() {
		applicationStatusRepository.deleteAll();
	}
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public void resetAutoIncrement() {
		jdbcTemplate.update("ALTER TABLE mast_applications.application_status AUTO_INCREMENT = 1");

	}
	
}
