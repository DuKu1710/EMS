package cls;

public class User {
    private static String userName;
    private static int roleID;
    private String id;
    private String name;
    private String gender;
    private String phone;
    private String email;
    private String position;
    private double salary;

    // Static methods for managing current user data
    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        User.userName = userName;
    }

    public static int getRoleID() {
        return roleID;
    }

    public static void setRoleID(int roleID) {
        User.roleID = roleID;
    }

    // Constructor
    public User(String id, String name, String gender, String phone, String email, String position, double salary) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.position = position;
        this.salary = salary;
    }

    // Getters and Setters for other fields
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
