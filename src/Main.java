import java.io.File;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String companiesPath = "Companies/";
        String customersPath = "Customers/";
        String delimiter = ":";

        File companiesDirectory = new File(companiesPath);
        File customersDirectory = new File(customersPath);
        File[] companyFiles = companiesDirectory.listFiles();
        File[] customerFiles = customersDirectory.listFiles();

        TxtReader reader = new TxtReader();
        Converter converter = new Converter();

        String url = "jdbc:postgresql://localhost/mydb";
        String user = "marketingbot";
        String password = "marketingbot";

        pgJDBCConnector connector = new pgJDBCConnector();
        connector.setUrl(url);
        connector.setUser(user);
        connector.setPassword(password);
        connector.connect();

        connector.executeStatement("CREATE TABLE IF NOT EXISTS companies (companyID int primary key, " +
                "companyName varchar(20), companyRequirements varchar(80), companyDescription text)");
        ArrayList<Company> companies = new ArrayList<>();
        if (companyFiles != null) {
            for (File companyFile: companyFiles) {
                reader.setFilePath(companyFile.getPath());
                Company company = converter.readerToCompany(reader, delimiter);
                // Check company
                System.out.println(company.getCompanyName() + " - " + company.getCompanyRequirements());
                connector.addCompany(company);
                companies.add(company);
            }
        }

        System.out.println("==========");

        connector.executeStatement("CREATE TABLE IF NOT EXISTS customers (customerID int primary key, " +
                "customerName varchar(20), customerEmail varchar(20), customerDescription text)");
        ArrayList<Customer> customers = new ArrayList<>();
        if (customerFiles != null) {
            for (File customerFile: customerFiles) {
                reader.setFilePath(customerFile.getPath());
                Customer customer = converter.readerToCustomer(reader, delimiter);
                // Check customer
                System.out.println(customer.getCustomerName());
                connector.addCustomer(customer);
                customers.add(customer);
            }
        }

        System.out.println("==========");

        for (Company company: companies) {
            for (Customer customer: customers) {
                company.addRelevantCustomer(customer);
            }
            System.out.println(company.getCompanyName());
            for (Customer customer: company.getMatchedCustomers()) {
                System.out.println(customer.getCustomerName());
            }
            System.out.println("----------");
        }

        for (Company company: companies) {
            String tableName = company.getCompanyName() + company.getCompanyID();
            connector.executeStatement("CREATE TABLE IF NOT EXISTS " + tableName + "(customerID int PRIMARY KEY, " +
                    "FOREIGN KEY(customerID) REFERENCES customers(customerID))");
            for (Customer customer: company.getMatchedCustomers()) {
                connector.executeStatement("INSERT INTO " + tableName + " VALUES (" + customer.getCustomerID() + ")");
            }
        }
    }
}