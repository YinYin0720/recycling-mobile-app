package com.example.e_cynic.entity;

import androidx.annotation.Nullable;

public class Address {
    public Integer addressId;
    public Integer userId;
    public String firstLine;
    public String secondLine;
    public String thirdLine;
    public String city;
    public String state;
    public Integer postcode;

    public Address (){}

    public Address(@Nullable Integer addressId, @Nullable Integer userId, String firstLine,
                   @Nullable String secondLine,
                   @Nullable String thirdLine, String city, String state, Integer postcode) {
        this.addressId = addressId;
        this.userId = userId;
        this.firstLine = firstLine;
        this.secondLine = secondLine;
        this.thirdLine = thirdLine;
        this.city = city;
        this.state = state;
        this.postcode = postcode;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + addressId +
                ", firstLine='" + firstLine + '\'' +
                ", secondLine='" + secondLine + '\'' +
                ", thirdLine='" + thirdLine + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", postcode=" + postcode +
                '}';
    }

    public String getAddressString() {
        return (firstLine + "\n")
                .concat(secondLine.equals("") ? "" : secondLine + "\n")
                .concat(thirdLine.equals("") ? "" : thirdLine.concat("\n"))
                .concat(postcode + " " + city + "\n").concat(state);
    }
}
