package Program_12_Chapter_24;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.*;
import java.math.BigDecimal;


import org.apache.derby.impl.sql.catalog.SYSROUTINEPERMSRowFactory;

public class InsertUpdateDeletePlusQueries 
{
	java.sql.Statement statement = null;
	
	Connection connection = null;
	//method display menue
	public String displayMenu()
	{
		return String.format("%s%n%n %s%n%n %s%n%n %s%n%n %s%n%n %s%n%n %s%n%n %s%n%n %s%n%n %s%n%n %s%n%n %s%n%n",
				" Data Manipulation Program Menu " 
				,"---1: Add a new author"
				,"---2: Edit existing information for an author "
				,"---3: Add a new title for an author "
				,"---4: delete an author "
				,"---5: display authors table"
				,"---6: display titles table"
				,"---7: display authorisbn"
				,"---8: display author info with number of books/isbn"
				,"---9: display book titles by authors"
				,"---10: write your select statement "
				,"---11: Exit");
	}
	
	//method that returns the insertion query to author table
	public  String insertAuthor(String Fname, String Lname)
	{
		   String query1 = "INSERT INTO AUTHORS (FIRSTNAME,LASTNAME)"
		   		+ " VALUES ('"+Fname.toUpperCase()+"','"+Lname.toUpperCase()+"')";
		   return query1;
	}

	//method that returns the query that update the author table
	public  String updateAuthor(String Fname, String Lname, int AuthorID)
	{
		   String query2 = "UPDATE AUTHORS "
		   		+ "SET FIRSTNAME = '"+Fname+"',"
		   		   + " LASTNAME = '"+Lname+"' "
		   		   		+ "WHERE AUTHORID = "+AuthorID;
		   
		   return query2;
	}
	
	//search for a record
//	public <T> String searchForRecord(T searchKey,String tableName)
//	{
//		String queryAuthors = "select authorid,firstname,lastname from authors";
//		String queryTitles = "select isbn,title,editionnumber,copyright from titles";
//		
//		String queryAuthorISBN = "";
//		
//		if (tableName.toLowerCase().equals("authors"))
//			
//		if (tableName.toLowerCase().equals("titles"))
//		if (tableName.toLowerCase().equals("authorisbn"))		
//			
//		
//		
//		//String searchQuery = "select * from authors,titles where ";
//		return null;
//		
//	}

	//method to delete a column
	public  String deleteColumn(int AuthorID)
	{
			
		   String query3 = "DELETE FROM AUTHORS WHERE AUTHORID ="+AuthorID;
		   
		   return query3;
	}

	//method to insert a book title 
	public  String insertTitle(String title,int EditionNum, int CopyRight)
	{
		String query4 = "INSERT INTO TITLES (ISBN,TITLE,EDITIONNUMBER,COPYRIGHT) "
				+ "VALUES(TEST_ISBN_SEQUENCE.NEXTVAL,'"+title+"','"+EditionNum+"','"+CopyRight+"')";
		return query4;
	}

	//insert into authorisbn table 
	public  String insertIntoAuthorISBN(int AuthorID,int bookISBN)
	{
		String query5 = "INSERT INTO AUTHORISBN (AUTHORID,ISBN) VALUES ('"+AuthorID+"','"+bookISBN+"')";
		
		return query5;
	}

	//method to return join query containing authors info and number of isbn associated 
	public  String jointQueryAuthorIsbnCount()
	{
		String query5 = "SELECT  AUTHORS.AUTHORID,"
				+ "AUTHORS.FIRSTNAME,"
				+ "AUTHORS.LASTNAME, "
				+ "COUNT(AUTHORISBN.ISBN) AS ISBN_COUNT"
				+ " FROM AUTHORS JOIN AUTHORISBN ON (AUTHORS.AUTHORID = AUTHORISBN.AUTHORID) "
				+ "JOIN TITLES ON (TITLES.ISBN = AUTHORISBN.ISBN)"
				+ "GROUP BY (AUTHORS.AUTHORID,AUTHORS.FIRSTNAME,AUTHORS.LASTNAME)";
		return query5;
	}

	//method to return a joint query that will display the author info and book title with its isbn
	public  String jointQuery2AuhtorIsbnTitle(int authorID)
	{
		String query6 = "SELECT  DISTINCT  AUTHORS.AUTHORID ,"
				+ " CONCAT(CONCAT(AUTHORS.FIRSTNAME,' '),AUTHORS.LASTNAME) AS AUTHOR_NAME"
				+ ",AUTHORISBN.ISBN,TITLES.TITLE FROM AUTHORS "
				+ "JOIN AUTHORISBN ON (AUTHORS.AUTHORID = AUTHORISBN.AUTHORID) "
				+ "JOIN TITLES ON (TITLES.ISBN = AUTHORISBN.ISBN) "
				+ "WHERE AUTHORS.AUTHORID = '"+authorID+"' "
						+ "ORDER BY AUTHORS.AUTHORID";
		return query6;
	}
	
	public  String jointQuery2AuhtorIsbnTitle()
	{
		String query6 = "SELECT  DISTINCT  AUTHORS.AUTHORID ,"
				+ " CONCAT(CONCAT(AUTHORS.FIRSTNAME,' '),AUTHORS.LASTNAME) AS AUTHOR_NAME"
				+ ",AUTHORISBN.ISBN,TITLES.TITLE FROM AUTHORS "
				+ "JOIN AUTHORISBN ON (AUTHORS.AUTHORID = AUTHORISBN.AUTHORID) "
				+ "JOIN TITLES ON (TITLES.ISBN = AUTHORISBN.ISBN) "
				+ "ORDER BY AUTHORS.AUTHORID";
		return query6;
	}
	
	//method to display the query 
	public  void displayfirstJointQuery(ResultSet resultSet)
	{
		int AUTHORID = 0;
		String FIRSTNAME =null;
		String LASTNAME = null;
		int ISBN_COUNT = 0;
		String queryRecord = null;
		
		String sqlMessage = null;

		try
		{
			System.out.println("AUTHORID" + "\t" + "FIRSTNAME" + "\t" + "LASTNAME" + "\t" + "ISBN_COUNT");
			System.out.println("--------" + "\t" + "---------" + "\t" + "--------" + "\t" + "---------");
			
		       while (resultSet.next())
				{
					AUTHORID = resultSet.getInt("AUTHORID");
					FIRSTNAME = resultSet.getString("FIRSTNAME");
					LASTNAME = resultSet.getString("LASTNAME");
					ISBN_COUNT = resultSet.getInt("ISBN_COUNT");
					
					queryRecord = AUTHORID + "\t\t" + FIRSTNAME + "\t\t" + LASTNAME + "\t\t" + ISBN_COUNT;
					System.out.println(queryRecord);
				} // End of while loop
		       
		       System.out.printf("-----------------------------------------------------%n");
			}
		catch (SQLException e)
		{
			if (e != null)
				sqlMessage = e.getMessage();
			
			System.out.println("SQL Error Message 1: " + sqlMessage);
		}    
	} //end of method displayfirstJointQuery
	
	
	//print the titles table
		public void displayTitlesTable(ResultSet resultSet)
		{
			String QueryResult = null;
			String sqlMessage = null;
		
			try
			{
				//get the tables info store it in the metadata variable
				ResultSetMetaData metaData = resultSet.getMetaData();
				int numberOfColumns = metaData.getColumnCount(); 
				
				//header  for the titles table
				System.out.println("ISBN"+"\t\t"+"TITLE"+"\t\t\t\t\t\t\t\t"+"EDITIONNUMBER"+"\t\t"+"COPYRIGHT");
				System.out.println("----"+"\t\t"+"-----"+"\t\t\t\t\t\t\t\t"+"-------------"+"\t\t"+"---------");

			     // display query results
				while (resultSet.next())
				{	
					//cast the title column into a string object
					String title = (String) resultSet.getObject(2);
					
					//for loop to pad the title column with spaces to have same length
					for (int i =1;i<=80;i++)
					{
						if (title.length()<68)
							title = title +" ";
					}

					//print the tables data
					System.out.println(resultSet.getObject(1)+"\t"+title
							+"\t"+resultSet.getObject(3)
							+"\t\t"+resultSet.getObject(4));
				
					System.out.println();
				}
			}
			catch (SQLException e)
			{
				if (e != null)
					sqlMessage = e.getMessage();
				
				System.out.println("SQL Error Message 1: " + sqlMessage);
			}    
		}//end of method that prints the titles table
	
	//method to display the authors query 
	public  void displayAuthorTable(ResultSet resultSet)
	{   
	
			int AUTHORID = 0;
			String FIRSTNAME =null;
			String LASTNAME = null;
			//int ISBN = 0;
			String queryRecord = null;
			
			String sqlMessage = null;

			try
			{
				System.out.println("AUTHORID"+"\t"+"FIRSTNAME"+"\t"+"LASTNAME");
				System.out.println("--------"+"\t"+"---------"+"\t"+"--------");
			       while (resultSet.next())
					{
						AUTHORID = resultSet.getInt("AUTHORID");
						FIRSTNAME = resultSet.getString("FIRSTNAME");
						LASTNAME = resultSet.getString("LASTNAME");
						queryRecord = AUTHORID + "\t\t" + FIRSTNAME + "\t\t" + LASTNAME;
						System.out.println(queryRecord);
					} // End of while loop
			       
			       System.out.printf("------------------------------------------%n");
				}
			catch (SQLException e)
			{
				if (e != null)
					sqlMessage = e.getMessage();
				
				System.out.println("SQL Error Message 1: " + sqlMessage);
			}    
	}//end of method print query
	
	//print the authorisbn table
	public void displayAuthorISBN(ResultSet resultSet)
	{		
		String sqlMessage = null;
	
		try
		{
			//get the tables info store it in the metadata variable
			ResultSetMetaData metaData = resultSet.getMetaData();
			int numberOfColumns = metaData.getColumnCount(); 
			
			System.out.println("AUTHORID"+"\t"+"ISBN");
			System.out.println("--------"+"\t"+"---------");
		    		       
		       // display query results
		       while (resultSet.next()) 
		       {
		          for (int i = 1; i <= numberOfColumns; i++)
		             System.out.printf("%-8s\t", resultSet.getObject(i));
		          System.out.println();
		       }
		       System.out.printf("-------------------------------------%n");
			}
		catch (SQLException e)
		{
			if (e != null)
				sqlMessage = e.getMessage();
			
			System.out.println("SQL Error Message 1: " + sqlMessage);
		}    
	}//end of method print query
	
	//print any  table
		public void displayGenericTable(ResultSet resultSet)
		{		
			String sqlMessage = null;
			try
			{
				ResultSetMetaData metaData = resultSet.getMetaData();
				
			       int numberOfColumns = metaData.getColumnCount(); 
				   // display the names of the columns in the ResultSet
			       for (int i = 1; i <= numberOfColumns; i++)
			          System.out.printf("%-8s\t", metaData.getColumnName(i));
			       System.out.println();
			    		       
			       // display query results
			       while (resultSet.next()) 
			       {
			          for (int i = 1; i <= numberOfColumns; i++)
			             System.out.printf("%-8s\t", resultSet.getObject(i));
			          System.out.println();
			       }
			       System.out.printf("-------------------------------------%n");
				}
			catch (SQLException e)
			{
				if (e != null)
					sqlMessage = e.getMessage();
				
				System.out.println("SQL Error Message 1: " + sqlMessage);
			}    
		}//end of method print query
	
	//method to validate the user input
	public String validateAnswer(String answer)
	{   
		String validateYES ="YES";
		String validateNo ="NO";
		String invalid = "null";
		
        if (answer.toUpperCase().equals("Y")||answer.toUpperCase().equals("YES"))
        {
        	
        	return validateYES;
        }
			
		else if (answer.toUpperCase().equals("N")||	answer.toUpperCase().equals("NO"))
			{
        	return validateNo;
			}
		else 
        	return invalid;
	}
}
