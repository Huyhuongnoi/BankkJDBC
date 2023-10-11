package model;

public class Card extends User {
    private String password;
    private float balance;

    public Card(String userName, String password, float balance) {
        super(userName);
        this.password = password;
        this.balance = balance;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getBalance() {
        return balance;
    }
}
