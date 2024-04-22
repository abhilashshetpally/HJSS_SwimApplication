package com.uh.jss;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;


public class Jss_controllerTest {

	 
	@Test
	public void bookLesson() {
		System.out.println("------------------------------------------------------");
		System.out.println("Testing Book a lesson method");
		Jss_controller jss_controller = new Jss_controller();
		boolean isBooked = jss_controller.bookALession("L001");
		assertEquals(isBooked, true);
		
	}
	
	@Test
	public void cancelLesson() throws IOException {
		System.out.println("------------------------------------------------------");
		System.out.println("Testing cancel a lesson method");
		Jss_controller jss_controller = new Jss_controller();
		boolean isCancelled = jss_controller.cancelBooking();
		assertEquals(isCancelled, true);
		
	}
	
	@Test
	public void cancelLessonNegative() throws IOException {
		System.out.println("------------------------------------------------------");
		System.out.println("Testing cancel a lesson method");
		Jss_controller jss_controller = new Jss_controller();
		boolean isCancelled = jss_controller.cancelBooking();
		assertEquals(isCancelled, false);
		
	}
	
	@Test
	public void changeLesson() throws IOException {
		System.out.println("------------------------------------------------------");
		System.out.println("Testing change lesson method");
		Jss_controller jss_controller = new Jss_controller();
		boolean isBookingChanged = jss_controller.changeBooking();
		assertEquals(isBookingChanged, true);
		
	}
	
	@Test
	public void addLearner() throws IOException {
		System.out.println("------------------------------------------------------");
		System.out.println("Testing Enroll Learner method");
		Scanner operation = new Scanner(System.in);
		Learners learners = new Learners();
		System.out.println("Enter learner name:");
//        operation.nextLine();
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
		
		boolean isEnrolled =  learners.enrollLearner(learnerData);
		assertEquals(isEnrolled, true);
	}

	@Test
	public void learnerReportTest() throws IOException {
		System.out.println("------------------------------------------------------");
		System.out.println("Testing Learner Report method");
		Jss_controller jss_controller = new Jss_controller();
		boolean reportGenerated = jss_controller.generateLearnerReport();
		assertEquals(reportGenerated, true);
	}
	
	@Test
	public void coachReportTest() throws IOException {
		System.out.println("------------------------------------------------------");
		System.out.println("Testing Learner Report method");
		Jss_controller jss_controller = new Jss_controller();
		boolean reportGenerated = jss_controller.generateCoachReport();
		assertEquals(reportGenerated, true);
	}
	
	@Test
	public void attendLessonTest() throws IOException {
		System.out.println("------------------------------------------------------");
		System.out.println("Testing attend lesson method");
		Jss_controller jss_controller = new Jss_controller();
		boolean isAttended = jss_controller.attendSwimLesson();
		assertEquals(isAttended, true);
	}
}
