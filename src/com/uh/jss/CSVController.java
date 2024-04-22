package com.uh.jss;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CSVController {
	
	
	//this method returns the timetable filtered by grade
	public List<String> getRecordsByGrade( String splitBy, String fileLocation, String grade) {
		
		List<String> recordsList= new ArrayList<String>();
		System.out.println("avaiable bookings");
		try (// show available bookings
				BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
				String line;
				while ((line = br.readLine()) != null) {   //returns a Boolean value  
					
					String checkFitness= line.split(",")[1];

					if(checkFitness.equalsIgnoreCase(grade)) {
						// append all records to list
						recordsList.add(line);
						
					}
				}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return recordsList;
	}

	//this method returns the timetable filtered by day
	public List<String> getRecordsByDay( String splitBy, String fileLocation, String day) {
		
		List<String> recordsList= new ArrayList<String>();
		System.out.println("avaiable bookings");
		try (// show available bookings
				BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
				String line;
				while ((line = br.readLine()) != null) {   //returns a Boolean value  
					/*
					 * // append all records to list recordsList.add(line);
					 */
					String checkFitness= line.split(",")[6];
	//				System.out.println("compare months:"+checkMonth+"-"+Month);
					if(checkFitness.equalsIgnoreCase(day)) {
						// append all records to list
						recordsList.add(line);
						
					}
				}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return recordsList;
	}

	//this method returns the timetable filtered by coach name
	public List<String> getRecordsByCoach( String splitBy, String fileLocation, String coach) {
		
		List<String> recordsList= new ArrayList<String>();
		System.out.println("avaiable bookings");
		try (// show available bookings
				BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
				String line;
				while ((line = br.readLine()) != null) {   //returns a Boolean value  
					
					String checkFitness= line.split(",")[5];
	//				System.out.println("compare months:"+checkMonth+"-"+Month);
					if(checkFitness.equalsIgnoreCase(coach)) {
						// append all records to list
						recordsList.add(line);
						
					}
				}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return recordsList;
	}
	
	
	public boolean isEnrolled(String swimID, String learnerID,String className) {
		String fileLocation  = "bookings.csv";

		try (
				BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
				String line,swimId[];
//				1,L001,Grade 1,coach2,3/18/2023,,attended,4,goodsession,1
				while ((line = br.readLine()) != null) {   //returns a Boolean value  
					swimId=line.split(",");
					if ((swimId[9].equals(swimID) && swimId[1].equals(learnerID))
							&& (swimId[6].equalsIgnoreCase("booked") ||
									swimId[6].equalsIgnoreCase("attended"))
//							||
//							(swimId[7].equals(className) && swimId[1].equals(learnerID))
							)
					
					{
					return true;// returns true if user is already enrolled in class
					}	
				}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	//to get the count of already enrolled customers
	public int getEnrollCount(String swimID) {
		String fileLocation  = "timetable.csv";;
		try (// show available bookings
				BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
				String line,swID[];
				
				
				
				while ((line = br.readLine()) != null) {   //returns a Boolean value  
					// append all records to list
					
					swID=line.split(",");
					
					if (swID[0].equals(swimID)) {
					return Integer.valueOf(swID[4]);
					}	
				}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean updateTimeTable(String lessonID, int count) {
		String filepath="TTtemp2.csv";
		//timetable path to update booking count
		String timetable = "timetable.csv";
		File oldfile = new File(timetable);
		File tempfile= new File(filepath);
		try {
			FileWriter fw= new FileWriter(tempfile,true);
			BufferedWriter bw= new BufferedWriter(fw);
			PrintWriter pw= new PrintWriter(bw);
			
			FileReader fr = new FileReader(timetable);
			BufferedReader br= new BufferedReader(fr);
			String Currentline;
			String[] swimID=null;
			while ((Currentline = br.readLine()) != null) {
				//read line and 
				swimID=Currentline.split(",");
				
				if (swimID[0].equals(lessonID)) {
					String apnd= String.valueOf(Integer.valueOf(swimID[4])+count);
					// append line by incrementing by one
					
					Currentline= swimID[0]+","+swimID[1]+","+ swimID[2]+ ","+  swimID[3]+ ","+
							apnd+","+swimID[5]+","+swimID[6];
					//System.out.println("new vlaue--->>\n"+Currentline);
					pw.println(Currentline);
					
				}else {
					//System.out.println("else vlaue--->>"+Currentline);
					
					pw.println(Currentline);						
				}
				
			}
			
			pw.flush();
			pw.close();
			fr.close();
			br.close();
			fw.close();
			bw.close();
			
			oldfile.delete(); 
			File dump= new File(timetable); 
			tempfile.renameTo(dump);
		}
		catch(Exception e ){
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	
	
	public boolean enrollBooking(String learnerID, String ClassID, int count, String enrollData) throws IOException {
		String path="bookings.csv";
		BufferedWriter bw=new BufferedWriter(new FileWriter(path, true));
        // construct string with all values
		
	    String []enrollDataArr= enrollData.split(",");
		String bookingId = String.valueOf( getRecordsLength(",", "bookings.csv")+1);

		String newData = bookingId+","+learnerID+","+enrollDataArr[0]+","+enrollDataArr[3]
						+","+enrollDataArr[1]+","+enrollDataArr[4]+",booked,,,"+ClassID+"\n";

        bw.write(newData);
		bw.close();
		return true;
		
	}

	public int getRecordsLength( String splitBy, String fileLocation) {
		
		int count=0;
		try (
				BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
				String line;
				while ((line = br.readLine()) != null) {   //returns a Boolean value  
					count++;
					
				}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	public List<String> getRecords(String learnerId, String splitBy, String fileLocation) {
		
		List<String> recordsList= new ArrayList<String>();
		
		try (// show available bookings
				BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
				String line;
				while ((line = br.readLine()) != null) {   //returns a Boolean value  
					String[] Prices = line.split(splitBy);    // use comma as separator  
					if(Prices.length>0) { // to skip the empty record
						if(learnerId.equals(Prices[1])) {
							recordsList.add(line);
							 
						}
					}
				}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return recordsList;
	}

	public boolean updateCancelStatus(String BookingID) {
		String filepath="BookingTemp.csv";
		//timetable path to update booking count
		String custTable = "bookings.csv";
		File oldfile = new File(custTable);
		File tempfile= new File(filepath);
		try {
			FileWriter fw= new FileWriter(tempfile,true);
			BufferedWriter bw= new BufferedWriter(fw);
			PrintWriter pw= new PrintWriter(bw);
			
			FileReader fr = new FileReader(custTable);
			BufferedReader br= new BufferedReader(fr);
			String Currentline;
			String[] BookingData=null;
			while ((Currentline = br.readLine()) != null) {
				//read line and 
				BookingData=Currentline.split(",");
				
				if (BookingData[0].equals(BookingID)) {
//					5,L004,grade 1,coach1,11/3/2024,5-6pm,booked, , ,13
	//				0 1		2		3		4		5		6	 7 8  9
					// reconstruct line with attended status
					Currentline= BookingData[0]+","+BookingData[1]+","+ BookingData[2]+ ","+
								BookingData[3]+","+BookingData[4]+","+BookingData[5]+","
								+"Cancelled"+","+BookingData[7]+","+BookingData[8]
										+","+BookingData[9];

					pw.println(Currentline);
					
				}else {
					pw.println(Currentline);						
				}
				
			}
			
			pw.flush();
			pw.close();
			fr.close();
			br.close();
			fw.close();
			bw.close();
			
			oldfile.delete(); 
			File dump= new File(custTable); 
			tempfile.renameTo(dump);
				  
			
		}
		catch(Exception e ){
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}

	public List<String> getAllRecords( String fileLocation) {
		
		List<String> recordsList= new ArrayList<String>();
		
		try (// show available bookings
				BufferedReader br = new BufferedReader(new FileReader(fileLocation))) {
				String line;
				while ((line = br.readLine()) != null) {   //returns a Boolean value  
					String[] Prices = line.split(",");    // use comma as separator  
					if(Prices.length>0) { // to skip the empty record
							recordsList.add(line);
					}
				}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return recordsList;
	}


	public String addNewLearner(String learnerData,String path) throws IOException {

		BufferedWriter bw=new BufferedWriter(new FileWriter(path, true));
        // construct string with all values
		
	    String learnerId ="L0"+ String.valueOf( getRecordsLength(",", path)+1);

		String newData = learnerId+","+learnerData +"\n";
        bw.write(newData);
        System.out.println("enrolled");
		bw.close();
		return learnerId;
		
	}
	
	
	public boolean Attend(String BookingID, String review, String rating) {
//		need to change
		String filepath="BookingTemp.csv";
		//timetable path to update booking count
		String custTable = "bookings.csv";
		File oldfile = new File(custTable);
		File tempfile= new File(filepath);
		try {
			FileWriter fw= new FileWriter(tempfile,true);
			BufferedWriter bw= new BufferedWriter(fw);
			PrintWriter pw= new PrintWriter(bw);
			
			FileReader fr = new FileReader(custTable);
			BufferedReader br= new BufferedReader(fr);
			String Currentline;
			String[] BookingData=null;
			while ((Currentline = br.readLine()) != null) {
				//read line and 
				BookingData=Currentline.split(",");
				
				if (BookingData[0].equals(BookingID)) {
//					5,L004,grade 1,coach1,11/3/2024,5-6pm,booked, , ,13
	//				0 1		2		3		4		5		6	 7 8  9
					// reconstruct line with attended status
					Currentline= BookingData[0]+","+BookingData[1]+","+ BookingData[2]+ ","+
								BookingData[3]+","+BookingData[4]+","+BookingData[5]+","
								+"attended"+","+rating+","+review
										+","+BookingData[9];

					pw.println(Currentline);
					
				}else {
					pw.println(Currentline);						
				}
				
			}
			
			pw.flush();
			pw.close();
			fr.close();
			br.close();
			fw.close();
			bw.close();
			
			oldfile.delete(); 
			File dump= new File(custTable); 
			tempfile.renameTo(dump);
				  
			
		}
		catch(Exception e ){
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}

	public String upgradeLearnerGrade(String learnerId, int lessonGrade) {
//		need to change
		String filepath="LearnerTemp.csv";
		//timetable path to update booking count
		String custTable = "learners.csv";
		File oldfile = new File(custTable);
		File tempfile= new File(filepath);
		int grade=-1;
		try {
			FileWriter fw= new FileWriter(tempfile,true);
			BufferedWriter bw= new BufferedWriter(fw);
			PrintWriter pw= new PrintWriter(bw);
			
			FileReader fr = new FileReader(custTable);
			BufferedReader br= new BufferedReader(fr);
			String Currentline;
			String[] learnerData=null;
			while ((Currentline = br.readLine()) != null) {
				//read line and 
				learnerData=Currentline.split(",");
//				grade = Integer.valueOf(learnerData[5]);
				if (learnerData[0].equals(learnerId) && grade<5
						&& grade<lessonGrade) {

					grade = Integer.valueOf(learnerData[5]);
					
//					L005,raJ,male,11,124412441,0
					
					grade=grade+1;
					// reconstruct line with attended status
					Currentline= learnerData[0]+","+learnerData[1]+","+ learnerData[2]+ ","+
								learnerData[3]+","+learnerData[4]+","+String.valueOf(grade);

					pw.println(Currentline);
					
				}else {
					pw.println(Currentline);						
				}
				
			}
			
			pw.flush();
			pw.close();
			fr.close();
			br.close();
			fw.close();
			bw.close();
			
			oldfile.delete(); 
			File dump= new File(custTable); 
			tempfile.renameTo(dump);
				  
			
		}
		catch(Exception e ){
			e.printStackTrace();
			return null;
		}
		
		
		return "Grade "+String.valueOf(grade);
	}

}
