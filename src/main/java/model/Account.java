package model;

public abstract class Account {
    private String acc_holder_name;
    private int acc_no;
    private double balance;

    public Account(String acc_holder_name,int acc_no,double balance)
    {
        this.acc_holder_name=acc_holder_name;
        this.acc_no=acc_no;
        this.balance=balance;
    }

    public double check_balance(int acc_no){
        return this.getBalance();
    }
    public  double withdraw(double amt) throws Exception {
        if(this.getBalance()>=amt)
        {
            double current_bal=getBalance();
            this.setBalance(current_bal-amt);
            return this.getBalance();
        }
        else {
            throw new Exception("Insufficient balance");
        }
    }
    public double deposit(double ammount){
        double current_bal=getBalance();
        setBalance(current_bal+ammount);
        return getBalance();
    }
    public abstract String acc_type();

    public String getAcc_holder_name() {
        return acc_holder_name;
    }

    public void setAcc_holder_name(String acc_holder_name) {
        this.acc_holder_name = acc_holder_name;
    }

    public int getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(int acc_no) {
        this.acc_no = acc_no;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
