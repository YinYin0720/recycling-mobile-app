package com.example.e_cynic.utilsTests;

import com.example.e_cynic.utils.ValidationUtil;

import org.junit.Assert;
import org.junit.Test;

public class ValidationUtilTest {

    @Test
    public void validateUsername1() {
        String username = "testuser";
        Assert.assertEquals(true, ValidationUtil.validateUsername(username));
    }

    @Test
    public void validateUsername2() {
        String username = "_test1234";
        Assert.assertEquals(false, ValidationUtil.validateUsername(username));
    }

    @Test
    public void validateUsername3() {
        String username = "1234test";
        Assert.assertEquals(false, ValidationUtil.validateUsername(username));
    }

    @Test
    public void validateUsername4() {
        String username = "123test#";
        Assert.assertEquals(false, ValidationUtil.validateUsername(username));
    }

    @Test
    public void validateUsername5() {
        String username = "test_123-22.";
        Assert.assertEquals(true, ValidationUtil.validateUsername(username));
    }

    @Test
    public void validateUsername6() {
        String username = "test123456_-.";
        Assert.assertEquals(true, ValidationUtil.validateUsername(username));
    }

    @Test
    public void validatePassword1() {
        String password = "test";
        Assert.assertEquals(false, ValidationUtil.validatePassword(password));
    }

    @Test
    public void validatePassword2() {
        String password = "thisispassword123";
        Assert.assertEquals(false, ValidationUtil.validatePassword(password));
    }

    @Test
    public void validatePassword3() {
        String password = "PassWord1234~";
        Assert.assertEquals(true, ValidationUtil.validatePassword(password));
    }

    @Test
    public void validatePassword4() {
        String password = "TEST_password-123.";
        Assert.assertEquals(true, ValidationUtil.validatePassword(password));
    }

    @Test
    public void validatePassword5() {
        String password = "TESTPASSWORD12345";
        Assert.assertEquals(false, ValidationUtil.validatePassword(password));
    }

    @Test
    public void validatePassword6() {
        String password = "1234_test_333_A";
        Assert.assertEquals(true, ValidationUtil.validatePassword(password));
    }


    @Test
    public void validateEmail1() {
        String email = "test@mail.com";
        Assert.assertEquals(true, ValidationUtil.validateEmail(email));
    }

    @Test
    public void validateEmail2() {
        String email = "test.user@mail.com.my";
        Assert.assertEquals(true, ValidationUtil.validateEmail(email));
    }

    @Test
    public void validateEmail3() {
        String email = "test@mail.com.edu.my";
        Assert.assertEquals(false, ValidationUtil.validateEmail(email));
    }

    @Test
    public void validateEmail4() {
        String email = "test_user.u@gmail.com";
        Assert.assertEquals(true, ValidationUtil.validateEmail(email));
    }

    @Test
    public void validateEmail5() {
        String email = "test-user@mail.com.my";
        Assert.assertEquals(true, ValidationUtil.validateEmail(email));
    }

    @Test
    public void validateEmail6() {
        String email = "test%user@mail.";
        Assert.assertEquals(false, ValidationUtil.validateEmail(email));
    }

    @Test
    public void validateEmail7() {
        String email = "test1233_323-user.u@mail.com";
        Assert.assertEquals(true, ValidationUtil.validateEmail(email));
    }

    @Test
    public void validatePhoneNumber1() {
        String phoneNumber = "+6012-123 4567";
        Assert.assertEquals(true, ValidationUtil.validatePhoneNumber(phoneNumber));
    }

    @Test
    public void validatePhoneNumber2() {
        String phoneNumber = "012-12345678";
        Assert.assertEquals(true, ValidationUtil.validatePhoneNumber(phoneNumber));
    }

    @Test
    public void validatePhoneNumber3() {
        String phoneNumber = "0123456789";
        Assert.assertEquals(true, ValidationUtil.validatePhoneNumber(phoneNumber));
    }

    @Test
    public void validatePhoneNumber4() {
        String phoneNumber = "+60123456788";
        Assert.assertEquals(true, ValidationUtil.validatePhoneNumber(phoneNumber));
    }

    @Test
    public void validatePhoneNumber5() {
        String phoneNumber = "012-3456 1234";
        Assert.assertEquals(true, ValidationUtil.validatePhoneNumber(phoneNumber));
    }

    @Test
    public void validatePhoneNumber6() {
        String phoneNumber = "123 456 7891";
        Assert.assertEquals(false, ValidationUtil.validatePhoneNumber(phoneNumber));
    }

    @Test
    public void validatePhoneNumber7() {
        String phoneNumber = "044-133 4422";
        Assert.assertEquals(false, ValidationUtil.validatePhoneNumber(phoneNumber));
    }

    @Test
    public void validatePhoneNumber8() {
        String phoneNumber = "012-223 333";
        Assert.assertEquals(false, ValidationUtil.validatePhoneNumber(phoneNumber));
    }

    @Test
    public void validateCity1() {
        String city = "cityname";
        Assert.assertEquals(true, ValidationUtil.validateCity(city));
    }

    @Test
    public void validateCity2() {
        String city = "city123";
        Assert.assertEquals(false, ValidationUtil.validateCity(city));
    }

    @Test
    public void validateCity3() {
        String city = "city test name";
        Assert.assertEquals(true, ValidationUtil.validateCity(city));
    }

    @Test
    public void validateCity4() {
        String city = "city 123 name";
        Assert.assertEquals(false, ValidationUtil.validateCity(city));
    }

    @Test
    public void validateCity5() {
        String city = "CITY NAME";
        Assert.assertEquals(true, ValidationUtil.validateCity(city));
    }

    @Test
    public void validateCity6() {
        String city = "city_name";
        Assert.assertEquals(false, ValidationUtil.validateCity(city));
    }

    @Test
    public void validateCity7() {
        String city = "548";
        Assert.assertEquals(false, ValidationUtil.validateCity(city));
    }

    @Test
    public void validateCity8() {
        String city = "33test city";
        Assert.assertEquals(false, ValidationUtil.validateCity(city));
    }

    @Test
    public void validatePostcode1() {
        String postcode = "12345";
        Assert.assertEquals(true, ValidationUtil.validatePostcode(postcode));
    }
    @Test
    public void validatePostcode2() {
        String postcode = "345";
        Assert.assertEquals(false, ValidationUtil.validatePostcode(postcode));
    }
    @Test
    public void validatePostcode3() {
        String postcode = "";
        Assert.assertEquals(false, ValidationUtil.validatePostcode(postcode));
    }
    @Test
    public void validatePostcode4() {
        String postcode = "posts";
        Assert.assertEquals(false, ValidationUtil.validatePostcode(postcode));
    }
    @Test
    public void validatePostcode5() {
        String postcode = "123aa";
        Assert.assertEquals(false, ValidationUtil.validatePostcode(postcode));
    }
    @Test
    public void validatePostcode6() {
        String postcode = "abncd";
        Assert.assertEquals(false, ValidationUtil.validatePostcode(postcode));
    }
    @Test
    public void validatePostcode7() {
        String postcode = "0";
        Assert.assertEquals(false, ValidationUtil.validatePostcode(postcode));
    }
    @Test
    public void validatePostcode8() {
        String postcode = "9000";
        Assert.assertEquals(false, ValidationUtil.validatePostcode(postcode));
    }
}