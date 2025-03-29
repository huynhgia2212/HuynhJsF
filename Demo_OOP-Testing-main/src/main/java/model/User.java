package model;

public class User {
    private Long id;
    private String username;
    private String password;
    private String role;

    // Constructor mặc định
    public User() {}

    // Constructor có tham số
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter & Setter
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
