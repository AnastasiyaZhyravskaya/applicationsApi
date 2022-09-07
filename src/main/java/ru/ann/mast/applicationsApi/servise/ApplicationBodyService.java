package ru.ann.mast.applicationsApi.servise;

import ru.ann.mast.applicationsApi.entity.ApplicationBody;


public interface ApplicationBodyService {
	
	public ApplicationBody saveApplicationBody(ApplicationBody body);
	
	public ApplicationBody getApplicationBody(int bodyId);
	
	public void deleteApplicationBody(int bodyId);
	
	public void deleteAllApplicationBody();

	public void resetAutoIncrement();
}
