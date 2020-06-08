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
    private String Get_One = "select * from customer where customer_id=?";
    private String DELETE = "delete from customer where customer_id=?";
    private String UPDATE = "update customer set first_name = ?, last_name = ?, email = ?, phone = ?," + "address = ?, city = ?, state = ?, zipcode = ? where customer_id = ?";


    public CustomerDAO(Connection connection) {
        super(connection);
    }

    @Override
    public Customer create(Customer dto) {

        try (PreparedStatement statement = this.connection.prepareStatement(INSERT)) {
            statement.setString(1, dto.getFirst_name());
            statement.setString(2, dto.getLast_name());
            statement.setString(3, dto.getEmail());
            statement.setString(4, dto.getPhone());
            statement.setString(5, dto.getAddress());
            statement.setString(6, dto.getCity());
            statement.setString(7, dto.getState());
            statement.setString(8, dto.getZipcode());

            statement.execute();
            int id = this.getLastVal(CUSTOMER_SEQUENCE);
            return this.findById(id);

        } catch (SQLException e) {
            this.logger.error(e.getMessage(), e);
            throw new RuntimeException( e);
        }
    }

    @Override
    public Customer findById(long id) {
        Customer customer = new Customer();
        try (PreparedStatement statement = this.connection.prepareStatement(Get_One)) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
            List<OrderLines> orderLinesList = new ArrayList<OrderLines>();
            while (rs.next()) {
                customer.setCustomer_id(rs.getLong("customer_id"));
                customer.setFirst_name(rs.getString("first_name"));
                customer.setLast_name(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("address"));
                customer.setCity(rs.getString("city"));
                customer.setState(rs.getString("state"));
                customer.setZipcode(rs.getString("zipcode"));
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
            throw new RuntimeException( e);
        }

        try (PreparedStatement statement = this.connection.prepareStatement(UPDATE);) {
            statement.setString(1, dto.getFirst_name());
            statement.setString(2, dto.getLast_name());
            statement.setString(3, dto.getEmail());
            statement.setString(4, dto.getPhone());
            statement.setString(5, dto.getAddress());
            statement.setString(6, dto.getCity());
            statement.setString(7, dto.getState());
            statement.setString(8, dto.getZipcode());
            statement.setLong(9, dto.getId());

            statement.execute();
            customer = this.findById(dto.getId());

        } catch (SQLException e) {
            try {
                this.connection.rollback();
            } catch (SQLException re) {
                this.logger.error(e.getMessage(), re);
                throw new RuntimeException( re);
            }
            this.logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public void delete(long id) {
        try (PreparedStatement statement = this.connection.prepareStatement(DELETE);) {
            statement.setLong(1, id);
            statement.execute();
        } catch (SQLException e) {
            this.logger.error(e.getMessage(), e);
            throw new RuntimeException( e);
        }
    }
}