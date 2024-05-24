public class Customer {
    private int customerID;
    private String customerName;
    private String customerEmail;
    private String customerDescription;

    public void setCustomerID(int id) {
        customerID = id;
    }

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerName(String name) {
        customerName = name;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerEmail(String email) {
        customerEmail = email;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerDescription(String description) {
        customerDescription = description;
    }

    public String getCustomerDescription() {
        return customerDescription;
    }
}
