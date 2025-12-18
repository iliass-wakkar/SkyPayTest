package entity;

public class User {
    private int userId;
    private int balance;

    // Constructor
    public User(int userId, int balance) {
        this.userId = userId;
        this.balance = balance;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", balance=" + balance +
                '}';
    }
}
