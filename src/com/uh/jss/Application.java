package com.uh.jss;

import java.io.IOException;
import java.util.Scanner;

public class Application {

	public static void main(String[] args) throws IOException{

		Jss_controller jss_controller = new Jss_controller();
		Learners learners = new Learners();
		try (Scanner operation = new Scanner(System.in)) { 
			System.out.println("*******************************************");
			System.out.println("The Hatfield Junior Swimming School (HJSS)");
			
			// the main functionalities 
			System.out.println("*******************************************");
			System.out.println("1) Book a Swimming Lession ");
			System.out.println("2) Change / Cancel a Lession ");
			System.out.println("3) Attend a Swimming Lession  ");
			System.out.println("4) Enroll New Learner");
			System.out.println("5) Learners Report ");
			System.out.println("6) Coach report");
			System.out.println("7) Exit System");
			System.out.println("*******************************************");
			
			System.out.println(" Choose the operation to perform ");
			System.out.println("Enter Operation ID:");
			// read the operation to perform  
			int option = operation.nextInt();
			
			while (option!=7) {
				switch (option) {
				case 1: 
					System.out.println();
					System.out.println("Enter Learner ID: (sample input: L001,L002,L003,....L014,L015)");
					String learnerID= operation.next().toUpperCase();
					Learners lInfo= new Learners();
					while(!lInfo.isLearnerValid(learnerID)) {
						System.out.println("You have entered incorrect learner ID");
						System.out.println("Enter Valid learner ID Again (sample input: L001,L002,L003,....L014,L015)");
						learnerID= operation.next().toUpperCase();
						System.out.println("is valid"+lInfo.isLearnerValid(learnerID));

					}
					if(jss_controller.bookALession(learnerID)) {
						
					
					System.out.println("booked lesson is completed");
					}
					break;
				case 2: 
					
					System.out.println("1-Change Booking \n2-Cancel booking\n"
							+ "Choose option to perform");
					int op = operation.nextInt();
					if(op==1) {
						jss_controller.changeBooking();
					}
					else if(op==2) {
						jss_controller.cancelBooking();
					}
					break;
				case 3: 
					jss_controller.attendSwimLesson();
					break;
				case 4: 
					
			        System.out.println("Enter learner name:");
			        operation.nextLine();
			        String learnerName = operation.nextLine();
			        
			        System.out.println("Enter learner age:");
			        while (!operation.hasNextInt()) {
			            System.out.println("Invalid input. Please enter a valid age:");
			            operation.next();
			        }

			        System.out.println("Enter learner age (4 to 11 years):");
			        int age = operation.nextInt();
			        while (age < 4 || age > 11) {
			            System.out.println("Invalid age. Please enter an age between 4 and 11 years:");
			            age = operation.nextInt();
			        }

			        operation.nextLine();  
			        

			        System.out.println("Enter learner's mobile number:");
			        String mobileNumber = operation.nextLine();

			        String gender = "";

			        System.out.println("Choose Gender: 1 - male, 2 - female");
			        Boolean flag=true;
			        while (flag) {
			            int choice = operation.nextInt();
			            switch (choice) {
			                case 1: gender= "male"; flag = false; break;
			                case 2: gender =  "female"; flag = false; break;
			                default: System.out.println("Invalid choice. Please choose 1 for male or 2 for female.");
			            }
			        }
					String learnerData = learnerName +","+gender+","+age+","+mobileNumber +",0";
					learners.enrollLearner(learnerData);
					
					break;
				case 5: 
					jss_controller.generateLearnerReport();

					break;
				case 6: 
					jss_controller.generateCoachReport();

					break;
				case 7: System.out.println("exit system");
				break;
				}
				if (option!=7) {
					System.out.println("*******************************************");
					System.out.println("Entered to main menu please choose option");
					System.out.println("*******************************************");
					System.out.println("1) Book a Swim Lession ");
					System.out.println("2) Change / Cancel a Lession ");
					System.out.println("3) Attend a Lession  ");
					System.out.println("4) Enroll New Learner");
					System.out.println("5) Learners Report ");
					System.out.println("6) Coach report");
					System.out.println("7) Exit System");
					System.out.println("*******************************************");
					System.out.println(" Choose the operation to perform ");
					option =operation.nextInt();
				}
			}
			System.out.println("Exit Sucessful!!!");
		}
	}
	

}
