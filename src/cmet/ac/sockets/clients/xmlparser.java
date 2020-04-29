package cmet.ac.sockets.clients;

import org.w3c.dom.*;

import javax.xml.parsers.*;
import java.io.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;



public class xmlparser {
	
	//ref to keep score
	public static int correctCount = 0;
	
	
	public void xmlparser1() {
		//30 second timer for quiz
		Timer t = new Timer();
		t.schedule(new TimerTask() {

		            @Override
		            public void run() {
		            	System.out.println("");
		                System.out.println("Times up! - you have " + correctCount + " points");
		                xmlparser.endofquiz();

		            }
		        }, 1000*30);
	
		
		//XML parser begins
	      try {
	         File inputFile = new File("questions.xml");
	         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
	         Document doc = dBuilder.parse(inputFile);
	         doc.getDocumentElement().normalize();
	         System.out.println("Now you have connected, please answer the questions below - " + doc.getDocumentElement().getNodeName());
	         System.out.println("");
	         NodeList q1 = doc.getElementsByTagName("Question-1");
	        
	         //Name and surname from WriteToFile added to become fullname
	         String s = WriteToFile.fullname;
	        
	        	
	         System.out.println("----------------------------");
	         
	         //block reads and displays question 1
	         for (int temp = 0; temp < q1.getLength(); temp++) {
	            Node nNode = q1.item(temp);
	            System.out.println("\n" + nNode.getNodeName());
	            System.out.println("Please choose A, B or C");
	            
	            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	               Element eElement = (Element) nNode;
	               System.out.println("" 
	                  + eElement.getAttribute("number"));
	               System.out.println(" " 
	                  + eElement
	                  .getElementsByTagName("quizquestion")
	                  .item(0)
	                  .getTextContent());
	               System.out.println("A) " 
	                  + eElement
	                  .getElementsByTagName("answera")
	                  .item(0)
	                  .getTextContent());
	               System.out.println("B) " 
	                  + eElement
	                  .getElementsByTagName("answerb")
	                  .item(0)
	                  .getTextContent());
	               System.out.println("C) " 
	                  + eElement
	                  .getElementsByTagName("answerc")
	                  .item(0)
	                  .getTextContent());
	            
	               @SuppressWarnings("resource")
	               //scanner to read user answer
		 			Scanner keyboardInput = new Scanner(System.in);
		 	        
		 	    	String answer1 = keyboardInput.nextLine(); 
		 	    	//below code takes user input and decides if correct or incorrect
		 	        do {
			 	    	
			 	    	  System.out.println("");
			 	    	   break;
			 	    	
			 	    	} while (!answer1.equals("A") && !answer1.equals("B") && !answer1.equals("C"));

			 	    	if (answer1.equals("B")) {
			 	    		System.out.println("----Thats the correct answer!---- ");
			 	    		System.out.println("-----------------------");	
				 	    	correctCount++;
				 	    	}
			 	    	else {
			 	    		System.out.println("Wrong answer, sorry");
			 	    		System.out.println("-----------------------");	
			 	    	
			 	    	}
			 	    	
			 	    //block reads and displays question 2
		 	    	try{
			         doc.getDocumentElement().normalize();
			         System.out.println("Next Question");
			         System.out.println("-----------------------");	
		 	    	NodeList q2 = doc.getElementsByTagName("Question-2");
		 	    	
		 	       //block reads and displays question 2
		 	    	  for (int temp2 = 0; temp2 < q2.getLength(); temp2++) {
		 		            Node nNode2 = q2.item(temp2);
		 		            System.out.println("\n" + nNode2.getNodeName());
		 		           System.out.println("Please choose A, B or C");
		 		            
		 		           if (nNode2.getNodeType() == Node.ELEMENT_NODE) {
		 		               Element eElement2 = (Element) nNode2;
		 		               System.out.println("" 
		 		                  + eElement2.getAttribute("number"));
		 		               System.out.println(" " 
		 		                  + eElement2
		 		                  .getElementsByTagName("quizquestion")
		 		                  .item(0)
		 		                  .getTextContent());
		 		               System.out.println("A) " 
		 		                  + eElement2
		 		                  .getElementsByTagName("answera")
		 		                  .item(0)
		 		                  .getTextContent());
		 		               System.out.println("B) " 
		 		                  + eElement2
		 		                  .getElementsByTagName("answerb")
		 		                  .item(0)
		 		                  .getTextContent());
		 		               System.out.println("C) " 
		 		                  + eElement2
		 		                  .getElementsByTagName("answerc")
		 		                  .item(0)
		 		                  .getTextContent());
			         {     
			
			        	 
			               String answer2 = keyboardInput.nextLine(); 
				 	    	do {
					 	    	   System.out.println("");
					 	    	   break;
					 	    	
					 	    	} while (!answer2.equals("A") && !answer2.equals("B") && !answer2.equals("C"));

					 	    	if (answer2.equals("B")) {
					 	    		System.out.println("----Thats the correct answer!---- ");
					 	    		System.out.println("-----------------------");	
						 	    	correctCount++;
						 	    
						 	    	}
					 	    	else {
					 	    		System.out.println("Wrong answer, sorry");
					 	    		System.out.println("-----------------------");	
					 	    	
					 	     
					 	    	}
					 	    	
					 	    	//begins question 3
				 	    	try{
						         doc.getDocumentElement().normalize();
						         System.out.println("Next Question");
						         System.out.println("-----------------------");	
						         System.out.println("Please choose A, B or C");
					 	    	NodeList q3 = doc.getElementsByTagName("Question-3");
					 	     
					 	    	
					 	    	
					 	    	  for (int temp3 = 0; temp3 < q3.getLength(); temp3++) {
					 		            Node nNode3 = q3.item(temp3);
					 		            System.out.println("\n" + nNode3.getNodeName());
					 		            
					 		           if (nNode3.getNodeType() == Node.ELEMENT_NODE) {
					 		               Element eElement3 = (Element) nNode3;
					 		               System.out.println("" 
					 		                  + eElement3.getAttribute("number"));
					 		               System.out.println(" " 
					 		                  + eElement3
					 		                  .getElementsByTagName("quizquestion")
					 		                  .item(0)
					 		                  .getTextContent());
					 		               System.out.println("A) " 
					 		                  + eElement3
					 		                  .getElementsByTagName("answera")
					 		                  .item(0)
					 		                  .getTextContent());
					 		               System.out.println("B) " 
					 		                  + eElement3
					 		                  .getElementsByTagName("answerb")
					 		                  .item(0)
					 		                  .getTextContent());
					 		               System.out.println("C) " 
					 		                  + eElement3
					 		                  .getElementsByTagName("answerc")
					 		                  .item(0)
					 		                  .getTextContent());
						         {     
						        	 
						        	 
						        	    //sees if answer is right or wrong
						        	 
							 	    	String answer3 = keyboardInput.nextLine(); 
							 	    	do {
							 	    	   System.out.println("");
							 	    	   break;
							 	    	
							 	    	} while (!answer3.equals("A") && !answer3.equals("B") && !answer3.equals("C"));

							 	    	if (answer3.equals("B")) {
							 	    		System.out.println("----Thats the correct answer!---- ");
							 	    		System.out.println("-----------------------");	
								 	    	correctCount++;
								 	    	System.out.println("Thank you for playing my quiz! " + s + " Your point total is - " + correctCount);
								 	    	}
							 	    	else {
							 	    		System.out.println("Wrong answer, sorry");
							 	    		System.out.println("-----------------------");
							 	    		System.out.println("");
							 	            System.out.println("Thank you for playing my quiz! " + s + " Your point total is - " + correctCount);
							 	       	System.out.println("");
							 	           System.out.println("Type 'done' to finish quiz!");
							 	    	}
							 	    	
							 	    	//reads input waiting for word 'done'. to finish quiz
							 	    	try {
											BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
											String message = null;
			
											while (true) {
												message = fromConsole.readLine();
												if(message.equals("done"))
													 System.out.println("Data is now being sent to the server...");
											}
											
									}catch (Exception e) {
									   e.printStackTrace();}
							 	    	
		                                    }  	
                                        }
					 		           
                                    }
					 		               }catch (Exception e) {
											   e.printStackTrace();
								}
			         }
		 		           }
		 	    	  }
			         }catch (Exception e) {
								   e.printStackTrace();}
	            }
	         }
	         }catch (Exception e) {
						   e.printStackTrace();}
			}
					
	    
	      
	     
		//beguns end of quiz scripts
	 public static void endofquiz() {
		 try {
				BufferedReader fromConsole = new BufferedReader(new InputStreamReader(System.in));
				String message = null;
				
				
				while (true) {
					message = fromConsole.readLine();
					if(message.equals("done"))
						 System.out.println("Please enter 'done' when finished");
						System.out.println("");
						 System.out.println("Data is now being sent to the server...");
              }
				
				
	}catch (Exception e) {
        e.printStackTrace();
    }
   }
  }

	       
	      

	         
	      
	      
	    	
	    	
	      

	    
	
	 
	  
 
		 
		 
     

	   
	


	
	

