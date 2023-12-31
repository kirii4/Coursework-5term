package ermakov.onlinebanking.model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private int idUser;
    private String login;
    private String password;
    private String status;
    private String email;
    private String name;
    private String secondName;
    private String patronymic;
    private String passportSeries;
    private int passportNumber;

    public User() {
        this.idUser = 0;
    }

    public User(int id, String login, String password, String status, String email, String name, String secondName, String patronymic, String passportSeries, int passportNumber) {
        this.idUser = id;
        this.login = login;
        this.password = password;
        this.status = status;
        this.email = email;
        this.name = name;
        this.secondName = secondName;
        this.patronymic = patronymic;
        this.passportSeries = passportSeries;
        this.passportNumber = passportNumber;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public int getIdUser() {
        return this.idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPatronymic() {
        return this.patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPassportSeries() {
        return this.passportSeries;
    }

    public void setPassportSeries(String passportSeries) {
        this.passportSeries = passportSeries;
    }

    public int getPassportNumber() {
        return this.passportNumber;
    }

    public void setPassportNumber(int passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof User)) {
            return false;
        } else {
            User user = (User)o;
            return this.getIdUser() == user.getIdUser() && this.getPassportNumber() == user.getPassportNumber() && Objects.equals(this.getLogin(), user.getLogin()) && Objects.equals(this.getPassword(), user.getPassword()) && Objects.equals(this.getStatus(), user.getStatus()) && Objects.equals(this.getEmail(), user.getEmail()) && Objects.equals(this.getName(), user.getName()) && Objects.equals(this.getSecondName(), user.getSecondName()) && Objects.equals(this.getPatronymic(), user.getPatronymic()) && Objects.equals(this.getPassportSeries(), user.getPassportSeries());
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getIdUser(), this.getLogin(), this.getPassword(), this.getStatus(), this.getEmail(), this.getName(), this.getSecondName(), this.getPatronymic(), this.getPassportSeries(), this.getPassportNumber()});
    }

    public String toString() {
        return "User{idUser=" + this.idUser + ", login='" + this.login + "', password='" + this.password + "', status='" + this.status + "', email='" + this.email + "', name='" + this.name + "', secondName='" + this.secondName + "', patronymic='" + this.patronymic + "', passportSeries='" + this.passportSeries + "', passportNumber=" + this.passportNumber + "}";
    }
}
