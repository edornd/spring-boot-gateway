package com.example.authserver.api.persistence.entity;

import org.springframework.data.annotation.Transient;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {

    @Id
    private String username;
    private String email;
    @Transient
    private String password;
    private String firstName;
    private String lastName;
    private boolean active;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    public User() { }

    public User(Builder builder){
        this.username = builder.username;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.password = builder.password;
        this.email = builder.email;
        this.active = builder.active;
        this.roles = builder.roles;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public static class Builder {

        private String username;
        private String email;
        private String password;
        private String firstName;
        private String lastName;
        private boolean active;
        private Set<Role> roles;

        public Builder(String username){
            this.username = username;
            this.password = "pass";
            this.active = true;
            this.roles = new HashSet<>();
        }

        public Builder name(String firstName, String lastName){
            this.firstName = firstName;
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email){
            this.email = email;
            return this;
        }

        public Builder password(String password){
            this.password = password;
            return this;
        }

        public Builder active(boolean active){
            this.active = active;
            return this;
        }

        public Builder roles(Role... roles){
            this.roles.addAll(Arrays.asList(roles));
            return this;
        }

        public User build(){
            return new User(this);
        }
    }
}
