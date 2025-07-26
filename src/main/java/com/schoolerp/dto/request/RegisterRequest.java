package com.schoolerp.dto.request;

import com.schoolerp.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



public class RegisterRequest {
        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username;

        @NotBlank(message = "Email is required")
        @Email(message = "Email must be valid")
        String email;

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password;

        @NotNull(message = "Role is required")
        Role role;

        @NotBlank(message = "First name is required")
        @Size(max = 50, message = "First name cannot exceed 50 characters")
        String firstName;

        @NotBlank(message = "Last name is required")
        @Size(max = 50, message = "Last name cannot exceed 50 characters")
        String lastName;
        Long createdById;

        public RegisterRequest(String username, String email, String password, Role role, String firstName, String lastName, Long createdById) {
                this.username = username;
                this.email = email;
                this.password = password;
                this.role = role;
                this.firstName = firstName;
                this.lastName = lastName;
                this.createdById = createdById;
        }
        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public Role getRole() {
                return role;
        }

        public void setRole(Role role) {
                this.role = role;
        }

        public String getFirstName() {
                return firstName;
        }

        public void setFirstName(String firstName) {
                this.firstName = firstName;
        }

        public String getLastName() {
                return lastName;
        }

        public void setLastName(String lastName) {
                this.lastName = lastName;
        }

        public Long getCreatedById() {
                return createdById;
        }

        public void setCreatedById(Long createdById) {
                this.createdById = createdById;
        }
}
// This record defines the structure for user registration requests.