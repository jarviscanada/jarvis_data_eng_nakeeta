package ca.jrvs.apps.jdbc;
import java.sql.Connection;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCExecutor {

 public static void main(String[] args) {
  final Logger logger = LoggerFactory.getLogger(JDBCExecutor.class);
  DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");
  try{
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

   Customer dbCustomer = customerDAO.create(customer);
   System.out.println(dbCustomer);
   dbCustomer = customerDAO.findById(dbCustomer.getId());
   System.out.println(dbCustomer);
   dbCustomer.setEmail("email123@gmail.com");
   dbCustomer = customerDAO.update(dbCustomer);
   System.out.println(dbCustomer);
   customerDAO.delete(dbCustomer.getId()
  }catch (SQLException e){
   logger.error(e.getMessage(),e);
  }

 }

}