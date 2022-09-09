package ru.ann.mast.applicationsApi.servise;

import java.util.List;

import ru.ann.mast.applicationsApi.entity.ApplicationStatus;


public interface ApplicationStatusService {
	
	public List<ApplicationStatus> getAllStatus();
	
	public ApplicationStatus saveStatus(ApplicationStatus status);

	public void deleteStatus(String status);
	
	public void deleteAllStatus();

	public void resetAutoIncrement();
}
