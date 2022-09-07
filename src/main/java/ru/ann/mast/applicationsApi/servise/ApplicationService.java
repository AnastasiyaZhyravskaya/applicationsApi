package ru.ann.mast.applicationsApi.servise;

import java.util.List;

import ru.ann.mast.applicationsApi.entity.Application;



public interface ApplicationService {
	
	public List<Application> getAllApplications();
	
	public Application getApplication(int applicationId);
	
	public Application saveApplication(Application application);
	
	public Application updateApplication(Application application);
	
	public void deleteApplication(int applicationId);
	
	public void deleteAllApplication();

	public void resetAutoIncrement();
}
