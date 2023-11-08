package Ermakov.Server.model;

import java.io.Serializable;
import java.util.Objects;

public class Bank implements Serializable {
    private int idBank;
    private String name;
    private String location;

    public Bank() {
        this.idBank = 0;
    }

    public int getIDBank() {
        return this.idBank;
    }

    public void setIDBank(int idBank) {
        this.idBank = idBank;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Bank)) {
            return false;
        } else {
            Bank bank = (Bank)o;
            return this.getIDBank() == bank.getIDBank() && Objects.equals(this.getName(), bank.getName()) && Objects.equals(this.getLocation(), bank.getLocation());
        }
    }

    public int hashCode() {
        return Objects.hash(new Object[]{this.getIDBank(), this.getName(), this.getLocation()});
    }

    public String toString() {
        return "Bank{idBank=" + this.idBank + ", name='" + this.name + "', location='" + this.location + "'}";
    }
}
