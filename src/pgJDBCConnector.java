import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class pgJDBCConnector {
    private String url;
    private String user;
    private String password;

    private Connection connection;

    public void setUrl(String newUrl) {
        url = newUrl;
    }
    public String getUrl() {
        return url;
    }
    public void setUser(String newUser) {
        user = newUser;
    }
    public String getUser() {
        return user;
    }
    public void setPassword(String newPassword) {
        password = newPassword;
    }

    public void connect() {
        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected!");
        } catch (SQLException e) {
            System.out.println("Failed to connect - " + e);
        }
    }

    public void executeStatement(String statement) {
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(statement);
        } catch (SQLException e) {
            System.out.println("Failed to execute statement - " + e);
        }
    }

    public void addCompany(Company company) {
        String statement = "INSERT INTO companies VALUES (" +
                company.getCompanyID() + ", '" +
                company.getCompanyName() + "', '" +
                company.getCompanyRequirements() + "', '" +
                company.getCompanyDescription() + "')";
        executeStatement(statement);
    }

    public void addCustomer(Customer customer) {
        String statement = "INSERT INTO customers VALUES (" +
                customer.getCustomerID() + ", '" +
                customer.getCustomerName() + "', '" +
                customer.getCustomerEmail() + "', '" +
                customer.getCustomerDescription() + "')";
        executeStatement(statement);
    }
}
