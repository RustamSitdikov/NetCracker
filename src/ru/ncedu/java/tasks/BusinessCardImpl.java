package ru.ncedu.java.tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class BusinessCardImpl implements BusinessCard {

    private static final String DATE_PATTERN = "dd-MM-yyyy";

    private String name;
    private String lastName;
    private String department;
    private int salary;
    private Calendar birthDate = Calendar.getInstance();
    private String gender;
    private String phoneNumber;

    public BusinessCardImpl(){}

    /**
     * This method obtains (via Scanner) information from an input stream
     * that contains the following information about an Employee:<br/>
     * Name - String<br/>
     * Last name - String<br/>
     * Department - String <br/>
     * Birth date - String in format: "DD-MM-YYYY", where DD - two-digits birth date,
     * MM - two-digits month of birth, YYYY - year of birth<br/>
     * Gender : F or M - Character<br/>
     * Salary : number from 100 to 100000<br/>
     * Phone number : 10-digits number<br/>
     * All entries are separated with ";" sign<br/>
     * The format of input is the following:<br/>
     * Name;Last name;Department;Birth date;Gender;Salary;Phone number
     *
     * @param scanner Data source
     * @return Business Card
     * @throws InputMismatchException Thrown if input value
     *                                does not correspond to the data format given above (for example,
     *                                if phone number is like "AAA", or date format is incorrect, or salary is too high)
     * @throws NoSuchElementException Thrown if input stream hasn't enough values
     *                                to construct a BusinessCard
     */
    @Override
    public BusinessCard getBusinessCard(Scanner scanner) throws NoSuchElementException {
        scanner.useDelimiter(";");

        setName(scanner.next());
        setLastName(scanner.next());
        setDepartment(scanner.next());
        setBirthDate(scanner.next());
        setGender(scanner.next());
        setSalary(scanner.next());
        setPhoneNumber(scanner.next());

        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return Employee Name and Last name separated by space (" "), like "Chuck Norris"
     */
    @Override
    public String getEmployee() {
        return getName() + " " + getLastName();
    }

    /**
     * @return Employee Department name, like "DSI"
     */
    @Override
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Calendar getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) throws InputMismatchException {
        try {
            Date date = new SimpleDateFormat(DATE_PATTERN).parse(birthDate);
            this.birthDate.setTime(date);
        } catch (ParseException exception) {
            throw new InputMismatchException("Birth date is incorrect!");
        }
    }

    /**
     * @return Employee Salary, like 1000
     */
    @Override
    public int getSalary() {
        return salary;
    }

    public void setSalary(String salary) throws InputMismatchException {
        try {
            this.salary = Integer.parseInt(salary);
            if ((this.salary < 100) || (this.salary > 100000)) {
                throw new InputMismatchException("Salary is incorrect!");
            }
        } catch (NumberFormatException exception) {
            throw new InputMismatchException("Salary is incorrect!");
        }
    }

    /**
     * @return Employee Age in years, like 69
     */
    @Override
    public int getAge() {
        Calendar calendar = Calendar.getInstance();

        int currentYear = calendar.get(Calendar.YEAR);
        int birthDateYear = birthDate.get(Calendar.YEAR);

        int age = currentYear - birthDateYear;

        int currentMonth = calendar.get(Calendar.MONTH);
        int birthDateMonth = birthDate.get(Calendar.MONTH);
        if (birthDateMonth > currentMonth) {
            age--;
        } else if (birthDateMonth == currentMonth) {
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
            int birthDateDay = birthDate.get(Calendar.DAY_OF_MONTH);
            if (birthDateDay > currentDay) {
                age--;
            }
        }

        return age;
    }

    /**
     * @return Employee Gender: either "Female" or "Male"
     */
    @Override
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) throws InputMismatchException{
        switch (gender) {
            case "M":
                this.gender = "Male";
                break;
            case "F":
                this.gender = "Female";
                break;
            default:
                throw new InputMismatchException("Gender is incorrect!");
        }
    }

    /**
     * @return Employee Phone Number in the following format: "+7 123-456-78-90"
     */
    @Override
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) throws InputMismatchException {
        if ((phoneNumber.length() != 10) || (!phoneNumber.matches("[0-9]+"))) {
            throw new InputMismatchException();
        }

        StringBuilder result = new StringBuilder("+7 ");;
        result.append(phoneNumber.substring(0, 3));
        result.append('-');
        result.append(phoneNumber.substring(3, 6));
        result.append('-');
        result.append(phoneNumber.substring(6, 8));
        result.append('-');
        result.append(phoneNumber.substring(8));
        this.phoneNumber = result.toString();
    }

    public static void main(String... args) {
        String input = "Chuck;Norris;DSI;10-04-1940;M;1000;1234567890";
        Scanner scanner = new Scanner(input);
        BusinessCard businessCard = new BusinessCardImpl().getBusinessCard(scanner);
        System.out.println(businessCard.toString());
        scanner.close();
    }

    public String toString() {
        return "Employee: " + this.getEmployee() + "\n" +
                "Department: " + this.getDepartment() + "\n" +
                "Salary: " + this.getSalary() + "\n" +
                "Age: " + this.getAge() + "\n" +
                "Gender: " + this.getGender() + "\n" +
                "Phone: " + this.getPhoneNumber() + "\n";
    }
}
