package cmet.ac.sockets.clients;

import java.io.*;
import java.util.Scanner;

public class WriteToFile
{
	public static final int correctCount = 0;
	static BufferedWriter out;
    String Name;
    String Surname;
    public static String fullname;
	
    public void userinput()
    {
        try
            {
                out = new BufferedWriter(new FileWriter("TESTING.txt",true ));
              
                
        
        
               //scanner declared for user input
                @SuppressWarnings("resource")
				Scanner input = new Scanner(System.in);

               //ask for first name
                System.out.println("Please enter your first name: ");
                Name = input.nextLine();

                //ask for surname
                System.out.println("Please enter your last name: ");
                Surname = input.nextLine();
              
                out.write(Name + " - " + Surname);
                //name and surname added together to become var fullname
               fullname = Name + Surname;
                		
                System.out.println("Welcome " + fullname);
                out.newLine(); 

           
                
               System.out.println("-----------------");
  
               //run xml parser after writetofile is finished
                xmlparser xmlparser = new xmlparser();
        		xmlparser.xmlparser1();
        		 out.close();
             
        	
        		 
            }  catch(IOException e) {
            }
            
     
    }
  
    	
    }


        	
        	
        	
   
        				 
        			    
        			
            
            
        		
    
        	        		
	
	
   

