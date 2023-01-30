package org.example.models;

public class UserModel {
    // Работа с JSON как с объектом  через отедльный класс
    private String email;
    private String password;
    private String name;

 public  UserModel (String email,String password,String name) {
     this.email = email;
     this.password = password;
     this.name = name;
 }
 // пустой класс для ресташура
    public UserModel() {

    }


    // создаю геттеры и сетеры для удобства обращения

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
