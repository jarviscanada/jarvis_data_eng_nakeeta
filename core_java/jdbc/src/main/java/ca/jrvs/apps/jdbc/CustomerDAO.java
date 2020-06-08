import ca.jrvs.apps.jdbc.util.DataAccessObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerDAO extends DataAccessObject<Customer> {

    final Logger logger = LoggerFactory.getLogger(CustomerDAO.class);
    private String INSERT = "insert into customer (first_name, last_name, email, phone, address, city, state, zipcode) " + "values (?,?,?,?,?,?,?,?)";
    private String findById = "select * from customer where customer_id=?";
    private String DELETE = "delete from customer where customer_id=?";
    private String UPDATE = "update customer set first_name = ?, last_name = ?, email = ?, phone = ?," + "address = ?, city = ?, state = ?, zipcode = ? where customer_id = ?";


    public CustomerDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Customer create(Customer dto) {

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT)) {
            preparedStatement.setString(1, dto.getFirst_name());
            preparedStatement.setString(2, dto.getLast_name());
            preparedStatement.setString(3, dto.getEmail());
            preparedStatement.setString(4, dto.getPhone());
            preparedStatement.setString(5, dto.getAddress());
            preparedStatement.setString(6, dto.getCity());
            preparedStatement.setString(7, dto.getState());
            preparedStatement.setString(8, dto.getZipcode());

            preparedStatement.execute();
            int lastId = this.getLastVal(CUSTOMER_SEQ);
            return this.findById(lastId);

        } catch (SQLException e) {
            this.logger.error(e.getMessage(), e);
            throw new RuntimeException("Create Failed", e);
        }
    }

    @Override
    public Customer findById(long id) {
        Customer customer = new Customer();
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(findById)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<OrderLines> orderLinesList = new ArrayList<OrderLines>();
            while (resultSet.next()) {
                customer.setCustomer_id(resultSet.getLong("customer_id"));
                customer.setFirst_name(resultSet.getString("first_name"));
                customer.setLast_name(resultSet.getString("last_name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setPhone(resultSet.getString("phone"));
                customer.setAddress(resultSet.getString("address"));
                customer.setCity(resultSet.getString("city"));
                customer.setState(resultSet.getString("state"));
                customer.setZipcode(resultSet.getString("zipcode"));
            }
        } catch (SQLException e) {
            this.logger.error(e.getMessage(), e);
            throw new RuntimeException("findById Failed", e);
        }
        return customer;
    }

    @Override
    public Customer update(Customer dto) {

        Customer customer = null;

        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException e) {
            this.logger.error(e.getMessage(), e);
            throw new RuntimeException("SetAutoCommit Failed", e);
        }

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(UPDATE)) {
            preparedStatement.setString(1, dto.getFirst_name());
            preparedStatement.setString(2, dto.getLast_name());
            preparedStatement.setString(3, dto.getEmail());
            preparedStatement.setString(4, dto.getPhone());
            preparedStatement.setString(5, dto.getAddress());
            preparedStatement.setString(6, dto.getCity());
            preparedStatement.setString(7, dto.getState());
            preparedStatement.setString(8, dto.getZipcode());
            preparedStatement.setLong(9, dto.getId());

            preparedStatement.execute();
            this.connection.commit();
            customer = this.findById(dto.getId());

        } catch (SQLException e) {
            try {
                this.connection.rollback();
            } catch (SQLException eroll) {
                this.logger.error(e.getMessage(), eroll);
                throw new RuntimeException("Rollback Failed", eroll);
            }
            this.logger.error(e.getMessage(), e);
            throw new RuntimeException("Update Failed", e);
        }
        return customer;
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(DELETE)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            this.logger.error(e.getMessage(), e);
            throw new RuntimeException("Delete Failed", e);
        }
    }
}