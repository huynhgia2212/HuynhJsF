package controller;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

import dao.UserDAO;  // Import UserDAO để dùng JDBC
import model.User;

@Named
@SessionScoped
public class AuthController implements Serializable {
    private String username;
    private String password;
    private String searchKeyword;
    private List<User> searchResults;
    private String searchMessage;


    @Inject
    private UserDAO userDAO;

    // Getter & Setter
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    // Xử lý đăng ký
    public String register() {
        boolean success = userDAO.registerUser(username, password); // Dùng JDBC để đăng ký
        if (success) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Đăng ký thành công!"));
            return "login.xhtml"; // Chuyển hướng đến trang đăng nhập
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Đăng ký thất bại!"));
            return null;
        }
    }

    // Xử lý đăng nhập
    public String login() {
        User user = userDAO.getUserByUsernameAndPassword(username, password);
        if (user != null) {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loggedInUser", user);
            return "dashboard?faces-redirect=true"; // Chuyển hướng đến trang chào mừng
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Đăng nhập thất bại!", "Tên đăng nhập hoặc mật khẩu không đúng."));
            return null;
        }
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login?faces-redirect=true"; // Chuyển về trang đăng nhập
    }
    
 // Getter & Setter
    public String getSearchKeyword() { return searchKeyword; }
    public void setSearchKeyword(String searchKeyword) { this.searchKeyword = searchKeyword; }

    public List<User> getSearchResults() { return searchResults; }
    public String getSearchMessage() { return searchMessage; }

    public void searchUsers() {
        searchResults = userDAO.searchUsers(searchKeyword);
        if (searchResults.isEmpty()) {
            searchMessage = "Không tìm thấy người dùng!";
        } else {
            searchMessage = null;
        }
    }


}
