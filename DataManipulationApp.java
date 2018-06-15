package Program_12_Chapter_24;

//Fig. 24.23: DisplayAuthors.java
//Displaying the contents of the authors table.

//Fig. 24.23: DisplayAuthors.java
//Displaying the contents of the authors table.
import java.sql.Connection;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DataManipulationApp 
{
	
public Scanner input ;
public  Scanner inputLine ;

public static void main(String args[]) throws SQLException
{
	  String sqlMessage = null;
	
	  //initialise a boolean continue loop
	  boolean continueLoop = true;
	  
	  //initialize the boolean variable for the inner while loop
	  boolean InnerContinue = false;
	  
	  //initialize the boolean variables
	  boolean OuterContinue = true;
	  
	  //initialize the choice variable
	  int choice = 1 ;
	  
	  //declare two scanners
	  Scanner input = new Scanner(System.in);
	  Scanner inputLine = new Scanner(System.in);
	  
	  //create a final datadase URL 
	  final String DATABASE_URL = "jdbc:oracle:thin:@localhost:1521:orcl";

	  //create an object of the InsertUpdateDeletePlusQueries class
	  InsertUpdateDeletePlusQueries objectDB = new InsertUpdateDeletePlusQueries();
	  
	  //create a Connection variable that will take 3 argument to establish the connection to the db
	   Connection connection = DriverManager.getConnection(DATABASE_URL, "SYS AS SYSDBA", "admin"); 
	   
	   //create a Statement variable that will execute the connection
	   Statement statement = connection.createStatement();  
	
	   //prompt a choice menu
	   System.out.println(objectDB.displayMenu()+"\n");
	   

	   
  while (OuterContinue)
	{    
		   try
			  {
				System.out.print("Enter your choice : ");
				//prompt the user to enter the choice
		 	 	choice = input.nextInt();  
		 	 	if (choice == 0)
		 	 	{
		 	 		System.out.println("Zero is an invalid choice \n");
		 	 	}
		 	 	if (choice > 11)
		 	 		System.out.println("\""+choice+ "\" is not a valid choice \n");
			  }
			  catch (InputMismatchException e)
		      {
				  if (e != null)
						sqlMessage = e.getMessage();
					
				  System.out.println("Invalid input "+e);
		         //choice = input.nextInt(); // discard input so user can try again
		         System.out.printf("You must enter integers. Please try again.%n%n");
		      }
   
	 switch(choice)
	      {
	      case 1: //add a new author 
		    	  try {
		    		  
		    		  //prompt for the author first name
		    	  	  System.out.println("Enter First name: ");
		      		  String Fname = inputLine.nextLine();
		      		  
		      		  //prompt for the last name
		      		  System.out.println("Enter Last name: ");
		    		  String Lname = inputLine.nextLine();
		    	  
		    		  //run the insertion query
		    		  ResultSet resultSet = statement.executeQuery(objectDB.insertAuthor(Fname,Lname));
		    		  //run the commit statement
		    		  ResultSet resultSetCommit = statement.executeQuery("commit");
	
	    	    	  //prompt the user to display the author table or not
	    	          System.out.println("Would you like to display the author table: Y/N?");
	    	          String answer = input.next();
	    	          
	    	          //store the entire authors table in ResultSet variable resultSet1
		    	      ResultSet resultSet1 = statement.executeQuery("select * from authors");
		    	         
		    	         //if the user enter y or yes 
		    	         if(answer.toUpperCase().equals("Y")|| answer.toUpperCase().equals("YES"))
		    	         {
		    	        	 //display the query
		    	        	 objectDB.displayAuthorTable(resultSet1);
		    	        	 System.out.println();
		    	         }
		    	         else 
		    	         {
		    	        	 System.out.println("your entry was : "+Fname+" "+Lname);
		    	        	 System.out.printf("Insertion Terminated.");
		    	         }
		    	      } // AutoCloseable objects' close methods are called now 
					  catch(InputMismatchException e)
					  {
						  if (e != null)
								sqlMessage = e.getMessage();
							
							System.out.println("Invalid input "+e);
						  //		  e.printStackTrace();
					  }
				      catch (SQLException sqlException)                                
				      {                                                                  
				    	  if (sqlException != null)
								sqlMessage = sqlException.getMessage();
							
							System.out.println("SQL Error Message 1: " + sqlMessage);
				      }   
			    	  catch(IllegalArgumentException e)
			    	  {
			    		  e.printStackTrace();
			    	  }
		    	      break;
	    	      
	      case 2: // edit existing author information
	    	  
		    	  	  System.out.println("Enter the author first name: ");
		      		  String Firstname = inputLine.nextLine();
		      		  
		      		  System.out.println("Enter the author last name: ");
		    		  String Lastname = inputLine.nextLine();
		    		  
		    		  System.out.println("Enter the author ID: ");
		      		  int AuthorID = input.nextInt();
		      		  
		      		  //call the update query method
		      		  ResultSet resultSet2 = statement.executeQuery(objectDB.updateAuthor(Firstname,Lastname,AuthorID));
		      		  ResultSet resultSetCommit = statement.executeQuery("commit");
		      		  
		      		//use try-with-resources to connect to and query the database
		    	      try 
		    	      	{
		    	    	  //prompt the user to display the author table or not
		    	         System.out.println("Would you like to display the author table: Y/N?");
		    	         String answer = input.next();
		    	         
		    	         //execute the author table
		    	         ResultSet resultSet1 = statement.executeQuery("select * from authors");
		    	         
		    	         //if the user enter y or yes 
		    	         if(answer.toUpperCase().equals("Y")|| answer.toUpperCase().equals("YES"))
		    	         {
		    	        	 //display the query
		    	        	 objectDB.displayAuthorTable(resultSet1);
		    	        	 System.out.println();
		    	         }
		    	         else 
		    	         {
		    	        	 System.out.println("your entry was : "+answer);
		    	        	 System.out.printf("Editing Author Info Terminated. %n");
		    	         }
		    	      } // AutoCloseable objects' close methods are called now 
		    	      catch (SQLException sqlException)                                
		    	      {                                                                  
		    	    	  if (sqlException != null)
		  					sqlMessage = sqlException.getMessage();
		  				
		  				System.out.println("SQL Error Message 1: " + sqlMessage);
		    	      }      
		    	  break;
	    	  
	      case 3: // Add a new title for an author (add title then assign it to an author )
			  		 try 
		    	      	{
			  			  System.out.print("Enter the book title: \t");
				  		  String title = inputLine.nextLine();
				  		  
				  		  System.out.print("Enter the Edition number: \t");
						  int editionNumber = input.nextInt();
						  
						  System.out.println("Enter the copy right: ");
				  		  int copyRight = input.nextInt();
				  		  
			  			 //call the update query method
				  		  ResultSet resultSet4 = statement.executeQuery(objectDB.insertTitle(title,editionNumber,copyRight));
				  		  ResultSet resultSetCommit4 = statement.executeQuery("commit");
				  		  
				  		  
				  		  System.out.println("Which author would you like to assign this title \""+title+ "\" to ? \n");
				  		  System.out.println("******** Authors table ********"+"\n");
				  		  
				  		  //display authors table
				  		  ResultSet 	resultSet1 = statement.executeQuery("select * from authors"); 
				  		  objectDB.displayAuthorTable(resultSet1);
				  		  
				  		  //this query will return the recently added title
				  		  String query5 = "SELECT DISTINCT ISBN FROM TITLES WHERE TITLE = '"+title+"' ";
				  		  
				  		  ResultSet resultSet6 = statement.executeQuery(query5);
				  		  //read the each isbn 
				  		  resultSet6.next();
				  		  //store the isbn retrieved from the getInt() 
				  		  int bookISBN = resultSet6.getInt("ISBN");
				  		  
		    	    	  //prompt the user to display the author table or not
				  		  System.out.println("Enter the Author ID: ");
				  		  int authorID = input.nextInt();
		    	         
		    	         if(authorID >=1)
		    	         {
		    	        	//assign the added title to the selected author
		    	        	 ResultSet resultSet5 = statement.executeQuery(objectDB.insertIntoAuthorISBN(authorID,bookISBN));
					  		 ResultSet resultSetCommit5 = statement.executeQuery("commit");
					  		 System.out.printf(" Insertion into AUTHORISBN Terminated. "+"\n");
					  		 
					  						  		
					  		 //prompt the user to display the recent title insertion and assignment to the author
		    	        	 System.out.println("would you like to display the author info with the assigned ISBN? Y/N "+"\n");
		    	        	 String userAnswer = input.next();
		    	        	 
		    	        	 //store the joint query that displays the information about the selected author 
		    	        	 //and information about his books
		    	        	 
		    	        	 ResultSet resultSet7 = statement.executeQuery(objectDB.jointQueryAuthorIsbnCount());
	
		    	        	 //validate the user input
		    	        	 String validatedAnswer = objectDB.validateAnswer(userAnswer);
		    	        	 
			    	         //if the user enter y or yes 
				     	     if(validatedAnswer.equals("YES"))
				     	         {
				     	        	 //display the query
				     	        	 objectDB.displayfirstJointQuery(resultSet7);
				     	        	 System.out.println();
				     	         }
				     	         else 
				     	         {
				     	        		 System.out.println("you entered : "+userAnswer);
				     	        	 	 System.out.printf(" Terminated. %n");
				     	        }
		    	        	 }
		    	         //if the user enters a zero or negative author id
		    	         else 
		    	         {
		    	        	 throw new IllegalArgumentException("Invalid Author ID entered !!!");
		    	         }
		    	         
		    	      } 
			  		 // AutoCloseable objects' close methods are called now 
			  		 catch (SQLException sqlException)                                
		    	      {                                                                  
		    	    	  if (sqlException != null)
		  					sqlMessage = sqlException.getMessage();
		  				
		  				System.out.println("SQL Error Message 1: " + sqlMessage);
		    	      }  
		    	  break;
	     case 4:// delete an author
 	    	  
	    	  System.out.println("Delete author record : "+"\n");
	    	  
	    	  
	    	 //display authors table
    	      try 
    	      	{
    	         ResultSet resultSet1 = statement.executeQuery("select * from authors");
    	         
	        	 //display the query
    	         objectDB.displayAuthorTable(resultSet1);
	        	 System.out.println();
	        	 
	        	//prompt the user to display the author table or not
		  		  System.out.println("Enter the Author ID to remove: ");
		  		  int authorID = input.nextInt();
		  		  
		  		  ResultSet resultSet = statement.executeQuery(objectDB.deleteColumn(authorID));
		  		  ResultSet resultSet3 = statement.executeQuery("commit");
		  		  
		  		  //display deleteion completed
		  		  System.out.println("Author record deleted. \n");
		  		  
    	         
    	      } // AutoCloseable objects' close methods are called now 
    	      catch (SQLException sqlException)                                
    	      {                                                                  
    	    	  if (sqlException != null)
  					sqlMessage = sqlException.getMessage();
  				
  				System.out.println("SQL Error Message 1: " + sqlMessage);
    	      }        
	    	  break;
    	  
	      case 5:// display author table
 	    	  
		    	  System.out.println(" AUTHORS TABLE: "+"\n");
		    	 //display authors table
	    	      try 
	    	      	{
	    	         ResultSet resultSet1 = statement.executeQuery("select * from authors");
	    	         
		        	 //display the query
	    	         objectDB.displayAuthorTable(resultSet1);
		        	 System.out.println();
	    	         
	    	      } // AutoCloseable objects' close methods are called now 
	    	      catch (SQLException sqlException)                                
	    	      {                                                                  
	    	    	  if (sqlException != null)
	  					sqlMessage = sqlException.getMessage();
	  				
	  				System.out.println("SQL Error Message 1: " + sqlMessage);
	    	      }        
		    	  break;
	    	  
	      case 6: //display the titles table
	    	  
		    	  System.out.println(" TITLES TABLE: "+"\n");
		    	  //display titles table
	    	      try 
	    	      	{
	    	         ResultSet resultSet1 = statement.executeQuery("select * from titles");
	    	         
		        	 //display the query
	    	         objectDB.displayTitlesTable(resultSet1);
		        	 System.out.println();
	    	         
	    	      } // AutoCloseable objects' close methods are called now 
	    	      catch (SQLException sqlException)                                
	    	      {                                                                  
	    	    	  if (sqlException != null)
	  					sqlMessage = sqlException.getMessage();
	  				
	  				System.out.println("SQL Error Message 1: " + sqlMessage);
	    	      }  
		    	  break;
	    	  
	      case 7://display the authorISBN table
	    	  
		    	  System.out.println("AUTHORISBN TABLE: "+"\n");
		    	  
		    	  //display the authorisbn table
	    	      try 
	    	      	{
	    	         ResultSet resultSet1 = statement.executeQuery("select * from authorisbn");
	    	         
		        	 //display the query
	    	         objectDB.displayAuthorISBN(resultSet1);
	    	         
		        	 System.out.println();
		        	 
	    	        } // AutoCloseable objects' close methods are called now 
	    	      catch (SQLException sqlException)                                
	    	      {                                                                  
	    	    	  if (sqlException != null)
	  					sqlMessage = sqlException.getMessage();
	  				
	  				System.out.println("SQL Error Message 1: " + sqlMessage);
	    	      }        
		    	  break;
	    	  
	      case 8://display author info with number of books/isbn
	    	
	    	      try 
	    	      	{
	    	         ResultSet resultSet5 = statement.executeQuery(objectDB.jointQueryAuthorIsbnCount());
	    	         
		        	 //display the query
	    	         objectDB.displayfirstJointQuery(resultSet5);
		        	 System.out.println();
		        	 
	    	        } // AutoCloseable objects' close methods are called now 
	    	      catch (SQLException sqlException)                                
	    	      {                                                                  
	    	    	  if (sqlException != null)
	  					sqlMessage = sqlException.getMessage();
	  				
	  				System.out.println("SQL Error Message 1: " + sqlMessage);
	    	      } 
		    	  break;
	     
	      case 9: 	   //display book titles by authors  	
	    	      try 
		    	      	{
		    	         ResultSet resultSet6 = statement.executeQuery(objectDB.jointQuery2AuhtorIsbnTitle());
		    	         
			        	 //display the query
		    	         objectDB.displayGenericTable(resultSet6);
			        	 System.out.println();
			        	 
		    	        } // AutoCloseable objects' close methods are called now 
	    	      
	    	      catch (SQLException e)
	    			{
	    				if (e != null)
	    					sqlMessage = e.getMessage();
	    				System.out.println("SQL Error Message 1: " + sqlMessage);
	    			}  
		    	  break;
	    	  
	      case 10: //write your own query
	    	  
		    	  	System.out.println("Write you query without \";\" ");
		    	  	String openQuery = inputLine.nextLine();
	    	  //display the query
    	      try 
	    	      	{
	    	         ResultSet resultSet7 = statement.executeQuery(openQuery);
	    	         
		        	 //display the query
	    	         objectDB.displayGenericTable(resultSet7);
		        	 System.out.println();
		        	 
	    	        } // AutoCloseable objects' close methods are called now 
    	      
    	      catch (SQLException sqlException)                                
		    	      {                                                                  
		    	    	  if (sqlException != null)
		  					sqlMessage = sqlException.getMessage();
		  				
		  				System.out.println("SQL Error Message 1: " + sqlMessage);
		    	      } 
	    	  break;
	    	
	      
	      case 11:  //exit the program
	    	  
			    	  System.out.println("Thank you for using my program.");
			      	  System.exit(1);
	      
	      } //end of the switch statement
		
		  System.out.println("Would you like to run the program again? Y/N");
		  String userAnswer1 = input.next();
		  
		  //verify the user input 
		  String validatedAnswer = objectDB.validateAnswer(userAnswer1);
		  
		  if (validatedAnswer.equals("YES"))
		  {
	        	System.out.println(objectDB.displayMenu());
	        	OuterContinue = true;
		  }
	  	  else if  (validatedAnswer.equals("NO"))
	  		{
	  		  OuterContinue = false;
	  			System.out.println("Program ended..");
	  		}
	  	  else 
	  	  {
	  		    System.out.println("\""+userAnswer1+"\" is not a valid answer. \n");
	  		  	System.out.println("Please enter YES/Y or NO/N");
	  		  	userAnswer1 = input.next();
	  		  	
	  		  	String validAns = objectDB.validateAnswer(userAnswer1);
	  		  	
	  		  	do 
	  		  	{
	  		  		// test the re-entered value if its null keep asking the user
		  		  	if (validAns.equals("null"))  
					{	
				  		System.out.println("\""+userAnswer1+"\" is not a valid answer. \n");
			  		  	System.out.println("Please enter YES/Y or NO/N");
			  		  	userAnswer1 = input.next();
			  		  	validAns= objectDB.validateAnswer(userAnswer1);
			  		  	OuterContinue = true;
					}
		  		    // if the user re-entered a valid value
		  		  	if(validAns.equals("YES"))
				  	{
		  		  	OuterContinue = true; //
			  		    System.out.println(objectDB.displayMenu());
				  		
			        }
				  	if (validAns.equals("NO"))
				  	{
				  		OuterContinue = false;
				  		System.out.println("Program ended..");
				  	}
	  		  	}
				while(validAns.equals("null"));
	  		  
	  	  }
	
		 }//end of while loop		  
  	  inputLine.close();
	  input.close();
	   
	} //end of method main  

} // end class DisplayAuthors