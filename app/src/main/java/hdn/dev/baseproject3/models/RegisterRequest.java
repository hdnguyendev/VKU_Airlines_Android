package hdn.dev.baseproject3.models;


public class RegisterRequest {
    private String fullname;
    private String username;
    private String phone;
    private String password;

    public RegisterRequest() {
    }

    public RegisterRequest(String fullName, String username, String phone, String password) {
        this.fullname = fullName;
        this.username = username;
        this.phone = phone;
        this.password = password;
    }

    public String getFullName() {
        return fullname;
    }

    public void setFullName(String fullName) {
        this.fullname = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
