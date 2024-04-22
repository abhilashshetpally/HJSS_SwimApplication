package com.uh.jss;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class  Learners {
	public static HashMap<String, String> LearnerMapInfo ;
	
	public void learnerInfo(){
		
		 CSVController controller = new CSVController();
		 List<String> lData= controller.getAllRecords(  "learners.csv");
		 
		 LearnerMapInfo = new HashMap<String, String>();
		 String key, value;
		 int header =0;
		 for(String learner:lData) {
			 if(header != 0) {
				 key = learner.split(",")[0];
				 value = learner.replace(learner.split(",")[0]+",", "");
				 LearnerMapInfo.put(key, value);
			 }
			 else {
				 header++;
			 }
		 }
            
	}
	
	public boolean isLearnerValid(String learnerId) {
		Learners cinf= new Learners();
		cinf.learnerInfo();
		return LearnerMapInfo.containsKey(learnerId);
	}
	
	public String getLearner(String learnerId) {
		Learners cinf= new Learners();
		cinf.learnerInfo();
		return LearnerMapInfo.get(learnerId);
	}
	
	public boolean enrollLearner(String learnerData) throws IOException {
		
		CSVController controller = new CSVController();
		 String newLearnerId = controller.addNewLearner( learnerData ,"learners.csv");
		 if(newLearnerId!=null)
		 {
			 System.out.println("Enrolled New learner is successful.."+"\n"
			 		+ "use learnerId: \""+newLearnerId+"\" for further operations");
			 return true; 
		 } 
		 System.out.println("Enrollment failed.. Please try enrolling again..");
		return false;
	}
	

}
