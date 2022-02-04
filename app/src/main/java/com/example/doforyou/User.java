package com.example.doforyou;

public class User {

    private int id;
    private String firstName;
    private String lastName;


     public User(int id, String firstName, String lastName){

         this.id = id;
         this.firstName=firstName;
         this.lastName = lastName;
     }

     public User(){

     }

    @Override
    public String toString() {
        return "customer{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}

