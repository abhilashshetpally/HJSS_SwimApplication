package com.uh.jss;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class Jss_controller {

	String learnerID;

	public boolean bookALession(String LearnerId) {
		Scanner read = new Scanner(System.in);
		String splitBy = ",";
		

		learnerID= LearnerId.toUpperCase();//read.next().toUpperCase();

		Scanner input = new Scanner(System.in);

		
		CSVController csvoper =new CSVController();
		
		String classesCSVFile="timetable.csv";
		
		
		System.out.println("please choose option to get your time table");
		System.out.println("1-> By Day\n2-> By Grade\n3-> By Coach");
		
		boolean checkFlag=true;
		List<String> classesListByDay,ClassesList = null,classesListByGrade;
		int cont=0;
		while (checkFlag) {
			int choice= read.nextInt();
			if(choice==1) {
				checkFlag=false;
				String dayValue="";// = input.nextLine();

				int day=0;
				
				try
				{
					boolean flag= true;
					while (flag) {
						System.out.println("Choose Day to display time table"
								+ "\n (1-monday\t2-wedneday\t3-friday\t4-saturday)");
						day = input.nextInt();
					
				        if(day>0 && day<5) {
				        	flag = false;
				        	if(day==1) {
				        		dayValue = "monday";
				        	}else if(day==2) {
				        		dayValue = "wednesday";
				        	}else if(day==3) {
				        		dayValue = "friday";
				        	}else if(day==4) {
				        		dayValue = "saturday";
				        	}
				        }else {

				        	System.out.println("Because we have lessions on monday, wednesday, friday, and saturdayonly!!! \n please enter valid option..");
				        }
			        }
					//List<String> 
//					3,grade 1,4/3/2024,6-7pm,0,coach1,Monday

					classesListByDay=csvoper.getRecordsByDay(splitBy,classesCSVFile , dayValue);
					System.out.println("------------------------------------------------------------------------------------------------");
					System.out.println("SwimID\t grade\t\t Date\t\t time \t\tcoach \t\tvacancies\tDay");
					System.out.println("------------------------------------------------------------------------------------------------");
					cont = 0;
					for(String clsDay:classesListByDay) {
						System.out.println(clsDay.split(",")[0]+"\t"
								+ clsDay.split(",")[1]+"\t\t"
								+ clsDay.split(",")[2]+"\t"
								+ clsDay.split(",")[3]+"\t\t"
								+ clsDay.split(",")[5]+"\t\t"
								+ Integer.valueOf(4- Integer.valueOf( clsDay.split(",")[4]))+"\t\t"
								+ clsDay.split(",")[6]
								);
						cont++;
					}
					System.out.println("------------------------------------------------------------------------------------------------");
					ClassesList=classesListByDay;
				} 
				catch (Exception e) 
				{
				   System.out.println("Exception occured..");
				}
				
			}
			else if(choice==2) {
				checkFlag=false;
				System.out.println("Enter Grade to display time table"
						+ "\n(sample input: Grade 1, Grade 2, Grade 3, Grade 4, Grade 5 )");
				 read.nextLine();
				String swimType = read.nextLine();
//				List<String>
				classesListByGrade=csvoper.getRecordsByGrade(splitBy,classesCSVFile , swimType);
				System.out.println("------------------------------------------------------------------------------------------------");
				System.out.println("SwimID\t grade\t\t Date\t\t time \t\tcoach \t\tvacancies\tDay");
				System.out.println("------------------------------------------------------------------------------------------------");
				cont=0;
				for(String clsGrade:classesListByGrade) {
					System.out.println(clsGrade.split(",")[0]+"\t"
							+ clsGrade.split(",")[1]+"\t\t"
							+ clsGrade.split(",")[2]+"\t"
							+ clsGrade.split(",")[3]+"\t\t"
							+ clsGrade.split(",")[5]+"\t\t"
							+ Integer.valueOf(4- Integer.valueOf( clsGrade.split(",")[4]))+"\t\t"
							+ clsGrade.split(",")[6]
							);
					cont++;
				}
				System.out.println("------------------------------------------------------------------------------------------------");
				
				ClassesList=classesListByGrade;

			}

			else if(choice==3) {
				checkFlag=false;
				System.out.println("Enter coach name to display time table"
						+ "\n(sample input: Danny, Lara, Jack, Maya, William)");
				 read.nextLine();
				String coach = read.nextLine();

				classesListByGrade=csvoper.getRecordsByCoach(splitBy,classesCSVFile , coach);
				System.out.println("------------------------------------------------------------------------------------------------");
				System.out.println("SwimID\t grade\t\t Date\t\t time \t\tcoach \t\tvacancies\tDay");
				System.out.println("------------------------------------------------------------------------------------------------");
				cont=0;
				for(String clsGrade:classesListByGrade) {
					System.out.println(clsGrade.split(",")[0]+"\t"
							+ clsGrade.split(",")[1]+"\t\t"
							+ clsGrade.split(",")[2]+"\t"
							+ clsGrade.split(",")[3]+"\t\t"
							+ clsGrade.split(",")[5]+"\t\t"
							+ Integer.valueOf(4- Integer.valueOf( clsGrade.split(",")[4]))+"\t\t"
							+ clsGrade.split(",")[6]
							);
					cont++;
				}
				System.out.println("------------------------------------------------------------------------------------------------");
				
				ClassesList=classesListByGrade;

			}
			else {
				System.out.println("Enter Valid option");
			}
		}
		
		
		if(cont==0) {
			System.out.println("We don't have any classes.. Try with other Day or other Type...\n\n");
			return false;
		}
			
		
		System.out.println("enter Lesson ID to join ");
		String classID = read.next();

		boolean flag = ClassesList.stream()
                .anyMatch(c -> c.split(",")[0].equals(classID));

		
		if(!flag) {
			System.out.println("you have entered wrong Lesson Id.. Please enter valid lesson Id to book a lesson ");
			return false;
		}
		
		//get data from classes
		String[] compare;
		String []swimData= null;
		for (String clist : ClassesList)
		{
			compare = clist.split(",");
			//System.out.println(clist);
			if(compare[0].equals(classID)) {
				
				swimData=clist.split(",");
				break;
			}
		}


		String className=swimData[1];
		String enrollData = swimData[1]+","+swimData[2]+","+swimData[4]+","+swimData[5]+","+swimData[3];

		boolean isEnrolled = csvoper.isEnrolled(classID, learnerID,className);

		Learners learners= new Learners();
		String learnerGrade=learners.getLearner(LearnerId).split(",")[4];
		if(!((Integer.valueOf(learnerGrade) == Integer.valueOf(swimData[1].split(" ")[1]))
			||(Integer.valueOf(learnerGrade)+1 == Integer.valueOf(swimData[1].split(" ")[1]))
				))	{

			System.out.println("You are not allowed to book this grade. \nYou can just book only one level higher to you current grade.");
			return false;
		}
		if(!isEnrolled) {
			int enrollcount=csvoper.getEnrollCount(classID);
			if ((enrollcount+1)<=4) {
				
				try {
					csvoper.enrollBooking( learnerID, classID, 1, enrollData);
					csvoper.updateTimeTable(classID, 1);
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}else {
				System.out.println("Don't have enough seats to register");
				return false;
			}
			
		}else {
			System.out.println("Already you have enrolled for this class");
			return false;
		}
		
		System.out.println("The Session is Booked Successfully!!!");
		
		return true;
	}

	
	public boolean cancelBooking() throws IOException {
		String csvFile = "bookings.csv";
		
		Scanner read= new Scanner(System.in);
		System.out.println("Enter Customer ID: (sample input: L001,L002,L003,....L014,L015)");
		String learnerID= read.next().toUpperCase();
		Learners lInfo= new Learners();

		while(!lInfo.isLearnerValid(learnerID)) {
			System.out.println("Enter Valid Customer ID: (sample input: L001,L002,L003,....L014,L015)");
			learnerID= read.next().toUpperCase();
			System.out.println("is valid"+lInfo.isLearnerValid(learnerID));

		}
			
				try {
				
					System.out.println("**********************");
					System.out.println("**********************");
					CSVController csvoper = new CSVController();
					// call method and get records with particular customer ID
					List<String> testList = csvoper.getRecords(learnerID, ",", csvFile);
					
					System.out.println("available Lessons on Leaner booking");
					System.out.println("===================================================");
					
					System.out.println("BookingID\tGrade\t LessonId\tstatus");
					System.out.println("===================================================");
					int count = 0;
					for (String add : testList)
					{
//						 System.out.println("add=="+add);
						if(add.split(",")[6].equals("booked")) {
							System.out.println(add.split(",")[0]+"\t\t"+
												add.split(",")[2]+"\t   "+
												add.split(",")[9]+"\t\t"+
												add.split(",")[6]);
						count++;
						}
					}
					
					System.out.println("===================================================");
					
					// take  user input --> class id
					if(count==0) {
						System.out.println("You don't have any available bookings");
						return false;
					}
					System.out.println("Enter Booking ID you want to cancel ");
					String cancel_id=read.next();
//					count=0;
					String class_id="";
					// to get the existing count 
					Boolean flag=false;
					for (String add : testList)
					{
						//System.out.println(add);
						// 
						if(add.split(",")[0].equals(cancel_id)) {
//							count = -Integer.valueOf( add.split(",")[4]);
							class_id = add.split(",")[9];
							flag = true;
							break;
						}
					}
					if(!flag) {
						System.out.println("You have entered wrong booking id");
						return false;
					}
					
					boolean cancel=csvoper.updateCancelStatus(cancel_id);
					if(cancel) {

//						csvoper.updaterecord(class_id, count);
						csvoper.updateTimeTable(class_id, -1);
						System.out.println("Cancellation Successful");
					}
					else {
						System.out.println("Failed Cancellation");
						return false;
					}
				}
				catch(Exception e) {
					e.printStackTrace();
					return false;
				}

			
		
		System.out.println("Cancelled Lesson Successfully!!!");
		return true;
	}
	
	public boolean changeBooking() throws IOException {
		
		System.out.println("**********************");
		System.out.println("**********************");

		Scanner read = new Scanner(System.in);
		System.out.println("Enter Customer ID: (sample input: L001,L002,L003,....L014,L015)");
		String learnerId= read.next().toUpperCase();
		Learners lInfo= new Learners();


		//System.out.println("is valid"+cinfo.isCustValid(custID));
		while(!lInfo.isLearnerValid(learnerId)) {
			System.out.println("Enter Valid Customer ID: (sample input: L001,L002,L003,....L014,L015)");
			learnerId= read.next().toUpperCase();
			System.out.println("is valid"+lInfo.isLearnerValid(learnerId));

		}
		String learnerID= learnerId;
		
		CSVController csvoper = new CSVController();
		String csvFile="bookings.csv";
		// call method and get records with particular customer ID
		List<String> testList = csvoper.getRecords(learnerID, ",", csvFile);
		
		
		System.out.println("available Lessons on Leaner booking");
		System.out.println("===================================================");
		
		System.out.println("BookingID\tGrade\t LessonId\tstatus");
		System.out.println("===================================================");
		int count = 0;
		for (String add : testList)
		{
//			 System.out.println("add=="+add);
			if(add.split(",")[6].equals("booked")) {
				System.out.println(add.split(",")[0]+"\t\t"+
									add.split(",")[2]+"\t   "+
									add.split(",")[9]+"\t\t"+
									add.split(",")[6]);
			count++;
			}
		}
		
		System.out.println("===================================================");
		
		// take  user input --> class id
		if(count==0) {
			System.out.println("You don't have any available bookings");
			return false;
		}
		
		System.out.println("------------------------------------------");
		// take  user input --> class id
		System.out.println("enter lesson ID ");
		String change_id=read.next();
		int checkCount =0;
		String classId="";
		for (String add : testList)
		{
			
			if(add.split(",")[0].equals(change_id)) {
				
				checkCount=1;
				classId = add.split(",")[9];
				break;
			}
		}
		if(checkCount==0) {
			System.out.println("You have entered wrong booking id");
			return false;
		}
		
		Jss_controller jss_controller = new Jss_controller();
		if(jss_controller.bookALession(learnerId)) {
			if(csvoper.updateCancelStatus(change_id)) {
			
			

				csvoper.updateTimeTable(classId, -1);
				System.out.println("Cancellation Successful");
			}
		}else {
			System.out.println("change booking is failed. please try again.");
			return false;
		}
			
		System.out.println("Modifed Booking Successfully!!!");
		return true;
	}
	
	public boolean attendSwimLesson() {

		System.out.println("**********************");
		System.out.println("**********************");

		Scanner read = new Scanner(System.in);
		System.out.println("Enter Customer ID: (sample input: L001,L002,L003,....L014,L015)");
		String learnerId= read.next().toUpperCase();
		Learners lInfo= new Learners();


		//System.out.println("is valid"+cinfo.isCustValid(custID));
		while(!lInfo.isLearnerValid(learnerId)) {
			System.out.println("Enter Valid Customer ID: (sample input: L001,L002,L003,....L014,L015)");
			learnerId= read.next().toUpperCase();
			System.out.println("is valid"+lInfo.isLearnerValid(learnerId));

		}
		String learnerID= learnerId;
		
		CSVController csvoper = new CSVController();
		String csvFile="bookings.csv";
		// call method and get records with particular customer ID
		List<String> testList = csvoper.getRecords(learnerID, ",", csvFile);
		
		
		System.out.println("available Lessons on Leaner booking");
		System.out.println("===================================================");
		
		System.out.println("BookingID\tGrade\t LessonId\tstatus");
		System.out.println("===================================================");
		int count = 0;
		for (String add : testList)
		{

			if(add.split(",")[6].equals("booked")) {
				System.out.println(add.split(",")[0]+"\t\t"+
									add.split(",")[2]+"\t   "+
									add.split(",")[9]+"\t\t"+
									add.split(",")[6]);
			count++;
			}
		}
		
		System.out.println("===================================================");
		
		if(count==0) {
			System.out.println("You don't have any available bookings");
			return false;
		}
		
		
		System.out.println("------------------------------------------");
		// take  user input --> class id
		System.out.println("enter lesson ID ");
		String change_id=read.next();

		int checkCount =0;
		int lessonGrade=0;
		for (String add : testList)
		{
			
			if(add.split(",")[0].equals(change_id)) {
				
				checkCount=1;
				lessonGrade = Integer.valueOf( add.split(",")[2].split(" ")[1]);
				break;
			}
		}
		if(checkCount==0) {
			System.out.println("You have entered wrong booking id");
			return false;
		}
		
		System.out.println("Provide Session Rating rangin 1 - 5"
				+ "\n(1: Very dissatisfied, 2: Dissatisfied, 3: Ok, 4: Satisfied, 5: Very Satisfied)");
		int rating = read.nextInt();
		 while (rating < 1 || rating > 5) {
			  System.out.println("Provide valid Rating (Range between: 1 to 5)"); 
			  rating = read.nextInt(); 
		  }
		System.out.println("Enter Review:");
		String review = read.nextLine();
		review=read.nextLine();
		
		
		if(csvoper.Attend(change_id,review,String.valueOf(rating))) {
			String grade = csvoper.upgradeLearnerGrade(learnerID,lessonGrade);
			if(grade != null) {
				System.out.println("learner Upgraded to "+grade);
				System.out.println("Attending a Lesson Successful");
				return true;
			}
				
			
		}else {
			System.out.println("Attending a lesson is booking is failed. please try again.");
			return false;
		}
		return true;
	}


	
	public Boolean generateCoachReport() {
		    // Map to hold the rating data for each coach
		CSVController controller = new CSVController();
		List<String> bookingDetailsList = controller.getAllRecords("bookings.csv");
		    HashMap<String, Integer[]> ratingMap = new HashMap<>();
		    Scanner operation = new Scanner(System.in);
			 System.out.println("enter month  (range between: 1 to 12)"); 
			  int month = operation.nextInt(); 
			  while (month < 1 || month > 12) {
				  System.out.println("enter valid month (range between: 1 to 12)"); 
				  month = operation.nextInt(); 
			  }
		    // List of coaches
		    String[] coachArray = {"Danny","Lara","Jack","Maya","William"};
		    // Initialize map with empty data
		    for (String coach : coachArray) {
		        ratingMap.put(coach, new Integer[]{0, 0}); // {totalRating, count}
		    }

		    // Process each booking detail
		    for (String bookingDetail : bookingDetailsList) {
		        String[] details = bookingDetail.split(",");
		        String coach = details[3];
		        String status = details[6];
		        int rating ;//= Integer.parseInt(details[7]);

		        // Check if the booking was attended by the coach
		        if ("attended".equalsIgnoreCase(status) && ratingMap.containsKey(coach)
		        		&&
		        		details[4].split("/")[1].equalsIgnoreCase(String.valueOf(month))
		        		) {
		        	 rating = Integer.parseInt(details[7]);
//		        	 System.out.println("rating");
		        	Integer[] ratings = ratingMap.get(coach);
		            ratings[0] += rating; // Update total rating
		            ratings[1] += 1;       // Update count
		            ratingMap.put(coach, ratings);
		        }
		    }

		    // Print the overall coach report
		    System.out.println("*******Overall Coach Report*******\n");
		    for (Entry<String, Integer[]> entry : ratingMap.entrySet()) {
		        Integer[] data = entry.getValue();
		        if (data[1] > 0) { // Ensure there's at least one rating
		            double averageRating = (double) data[0] / data[1];
		            System.out.println("Coach Name: --> " + entry.getKey() +
		                               "\nAverage Rating: " + averageRating +
		                               "\nTotal number of learners attended your lessons: " + data[1] + "\n");
		        }
		    }
		    
		    return true;
		
	}

	public Boolean generateLearnerReport() {
	    // Map to hold the rating data for each coach
	CSVController controller = new CSVController();
	List<String> bookingDetailsList = controller.getAllRecords("bookings.csv");
	    Scanner operation = new Scanner(System.in);
	 System.out.println("enter month  (range between: 1 to 12)"); 
	  int month = operation.nextInt(); 
	  while (month < 1 || month > 12) {
		  System.out.println("enter valid month (range between: 1 to 12)"); 
		  month = operation.nextInt(); 
	  }
	 
	   // Map to hold learner info with counts of attended, cancelled and booked sessions
    HashMap<String, Map<String, Integer>> learnerSessionCounts = new HashMap<>();

    // Populate the counts from booking details
    for (String booking : bookingDetailsList) {
        String[] details = booking.split(",");
        String learnerId = details[1];
        String status = details[6].toLowerCase(); // Normalize status to lowercase

        if(details[4].split("/")[1].equalsIgnoreCase(String.valueOf(month))) {
	        // Initialize the map if this is the first record for the learner
	        learnerSessionCounts.putIfAbsent(learnerId, new HashMap<String, Integer>() {{
	            put("attended", 0);
	            put("cancelled", 0);
	            put("booked", 0);
	        }});
	
	        // Get the map for this learner
	        Map<String, Integer> counts = learnerSessionCounts.get(learnerId);
	
	        // Increment the appropriate counter based on status
	        if (status.contains("attended")) {
	            counts.put("attended", counts.get("attended") + 1);
	        } else if (status.contains("cancelled")) {
	            counts.put("cancelled", counts.get("cancelled") + 1);
	        }
	        else if (status.contains("booked")) {
	            counts.put("booked", counts.get("booked") + 1);
	        }
        }
        
    }

    // Print the results
    System.out.println("Learner Session Counts:");
    learnerSessionCounts.forEach((learnerId, counts) -> {
        System.out.println("Learner ID: " + learnerId);
        System.out.println("Attended: " + counts.get("attended"));
        System.out.println("Cancelled: " + counts.get("cancelled"));
        System.out.println("Booked: " + counts.get("booked"));
        System.out.println();
    });

	return true;
}

	

}
