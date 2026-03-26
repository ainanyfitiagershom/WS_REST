package mg.restaurant.restapi.dto;

import jakarta.validation.constraints.NotBlank;

public class RegisterDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private String role = "USER";

    public RegisterDTO() {}

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
