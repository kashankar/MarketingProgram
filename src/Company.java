import java.util.ArrayList;

public class Company {
    private int companyID;
    private String companyName;
    private String companyRequirements;
    private ExpressionParser expressionParser = new ExpressionParser();
    private String companyDescription;
    private final ArrayList<Customer> matchedCustomers = new ArrayList<>();

    public void setCompanyID(int id) {
        companyID = id;
    }

    public int getCompanyID() {
        return companyID;
    }

    public void setCompanyName(String name) {
        companyName = name;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyRequirements(String requirements) {
        companyRequirements = requirements;
        expressionParser.setRawExpression(requirements);
    }

    public String getCompanyRequirements() {
        return companyRequirements;
    }

    public void setExpressionParser(ExpressionParser exprP) {
        expressionParser = exprP;
    }

    public ExpressionParser getExpressionParser() {
        return expressionParser;
    }

    public void setCompanyDescription(String description) {
        companyDescription = description;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public ArrayList<Customer> getMatchedCustomers() {
        return matchedCustomers;
    }

    public boolean parseRequirements(Customer customer) {
        return expressionParser.evaluateExpression(customer);
    }

    public void addRelevantCustomer(Customer customer) {
        boolean passed = parseRequirements(customer);
        if (passed) {
            matchedCustomers.add(customer);
        }
    }
}