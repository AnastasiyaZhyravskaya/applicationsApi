package ru.ann.mast.applicationsApi.servise;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import ru.ann.mast.applicationsApi.dao.ApplicationBodyRepository;
import ru.ann.mast.applicationsApi.entity.ApplicationBody;
import ru.ann.mast.applicationsApi.exceptionHandling.NoSuchException;

@Service
public class ApplicationBodyServiceImpl implements ApplicationBodyService {
	
	@Autowired
	private ApplicationBodyRepository applicationBodyRepository;
	
	@Override
	public ApplicationBody saveApplicationBody(ApplicationBody body) {
		return applicationBodyRepository.save(body);
	};
	
	
	@Override
	public ApplicationBody getApplicationBody(int bodyId) {
		return applicationBodyRepository.findById(bodyId).orElseThrow(()->
				new NoSuchException("Application body is not found, id="+bodyId));
	};
	
	
	@Override
	public void deleteApplicationBody(int bodyId) {
		getApplicationBody(bodyId);
		applicationBodyRepository.deleteById(bodyId);
		
	};
	
	
	@Override
	public void deleteAllApplicationBody() {
		applicationBodyRepository.deleteAll();
	};

	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	public void resetAutoIncrement() {
		jdbcTemplate.update("ALTER TABLE mast_applications.applications_bodys AUTO_INCREMENT = 1");

	}	
}
