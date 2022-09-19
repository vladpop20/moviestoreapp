package com.stackroute.MovieService.model;

import java.util.Objects;

public class Actor {


    private String firstName;
    private String lastName;
    private String nationality;

    public Actor(){

    }

    public Actor(String firstName, String lastName, String nationality) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
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

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return firstName.equalsIgnoreCase(actor.firstName) && lastName.equalsIgnoreCase(actor.lastName) &&
                nationality.equalsIgnoreCase(actor.nationality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, nationality);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}
