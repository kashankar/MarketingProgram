import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TxtReader {
    private String filePath;
    private Scanner fileScanner;

    public void setFilePath(String path) {
        filePath = path;
        try {
            File file = new File(filePath);
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred: " + e);
        }
    }
    public String getFilePath() {
        return filePath;
    }
    public String getLine() {
        if (!fileScanner.hasNextLine()) {
            fileScanner.close();
            return "";
        }
        return fileScanner.nextLine();
    }
}

class Converter {
    public Company readerToCompany(TxtReader fileReader, String delimiter) {
        Company company = new Company();
        while (true) {
            String line = fileReader.getLine();
            if (line.isEmpty()) {
                break;
            }

            String[] fields = line.split(delimiter);
            fields[0] = fields[0].strip();
            fields[1] = fields[1].strip();

            switch (fields[0]) {
                case "companyID":
                    company.setCompanyID(Integer.parseInt(fields[1]));
                    break;
                case "companyName":
                    company.setCompanyName(fields[1]);
                    break;
                case "companyRequirements":
                    company.setCompanyRequirements(fields[1]);
                    break;
                case "companyDescription":
                    company.setCompanyDescription(fields[1]);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field in file");
            }
        }

        return company;
    }

    public Customer readerToCustomer(TxtReader fileReader, String delimiter) {
        Customer customer = new Customer();
        while (true) {
            String line = fileReader.getLine();
            if (line.isEmpty()) {
                break;
            }

            String[] fields = line.split(delimiter);
            fields[0] = fields[0].strip();
            fields[1] = fields[1].strip();

            switch (fields[0]) {
                case "customerID":
                    customer.setCustomerID(Integer.parseInt(fields[1]));
                    break;
                case "customerName":
                    customer.setCustomerName(fields[1]);
                    break;
                case "customerEmail":
                    customer.setCustomerEmail(fields[1]);
                    break;
                case "customerDescription":
                    customer.setCustomerDescription(fields[1]);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown field in file");
            }
        }

        return customer;
    }
}