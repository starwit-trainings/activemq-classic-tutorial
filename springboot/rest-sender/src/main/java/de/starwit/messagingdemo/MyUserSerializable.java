package de.starwit.messagingdemo;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;

public class MyUserSerializable implements Serializable {
    private static final long serialVersionUID = 1L;

        @NotBlank(message = "Name is mandatory for user")
    private String name;
    
    @NotBlank(message = "Email is mandatory for user")
    private String email;

    public MyUserSerializable() {
        
    }

    public MyUserSerializable(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + 
        " [" + this.name + ", " + this.email + "]";
    }
}
