package ca.jrvs.apps.jdbc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;

public class JDBCExecutor {

 public static void main(String[] args) {
  final Logger logger = LoggerFactory.getLogger(JDBCExecutor.class);
  DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");
  try{
   /*
   Connection connection = dcm.getConnection();
   CustomerDAO customerDAO = new CustomerDAO(connection);
   Customer customer = new Customer();
   customer.setFirstName("Will");
   customer.setLastName("Smith");
   customer.setEmail("willSmith@gmail.com");
   customer.setPhone("(123) 456-789");
   customer.setAddress("12 River St");
   customer.setCity("Paris");
   customer.setState("ON");
   customer.setZipCode("22222");
   customer
   */

   Connection connection =dcm.getConnection();
   CustomerDAO customerDAO =new CustomerDAO(connection);
   Customer customer = customerDAO.findByID (1000);
   System.out.println(customer.getFirstName()+" " +customer.getLastname() " "+customer.getEmail());
   customer.setEmail("will.Smith@gmail.com");
   customer= customerDAO.update(customer);
   System.out.println(customer.getFirstName()+" " +customer.getLastname() " "+customer.getEmail());

  }catch (SQLException e){
   logger.error(e.getMessage(),e);
  }

 }

}