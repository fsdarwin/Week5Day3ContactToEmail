package com.example.week5day3contacttoemail.pojos;

import java.util.List;

public class Contact {

    String name;
    List<String> numberList;
    String emailList;

    public Contact(String name, List<String> numberList, String emailList) {
        this.name = name;
        this.numberList = numberList;
        this.emailList = emailList;
    }

    public String getEmailList() {
        return emailList;
    }

    public void setEmailList(String emailList) {
        this.emailList = emailList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getNumberList() {
        return numberList;
    }

    public void setNumberList(List<String> numberList) {
        this.numberList = numberList;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name='" + name + '\'' +
                ", numberList=" + numberList +
                " emailList=" + emailList + "}";
    }
}
