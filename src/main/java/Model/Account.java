package Model;

public abstract class Account {
    private String accHolderName;
    private int accNo;
    private double balance;

    public Account(String accHolderName,int accNo,double balance)
    {
        this.accHolderName=accHolderName;
        this.accNo=accNo;
        this.balance=balance;
    }

    public double checkBalance(int accNo){
        return this.getBalance();
    }
    public  double withdraw(double amt) throws Exception {
        if(this.getBalance()>=amt)
        {
            double currentBal=getBalance();
            this.setBalance(currentBal-amt);
            return this.getBalance();
        }
        else {
            throw new Exception("Insufficient balance");
        }
    }
    public double deposit(double ammount){
        double currentBal=getBalance();
        setBalance(currentBal+ammount);
        return getBalance();
    }
    public abstract String accType();

    public String getAccHolderName() {
        return accHolderName;
    }

    public void setAccHolderName(String accHolderName) {
        this.accHolderName = accHolderName;
    }

    public int getAccNo() {
        return accNo;
    }

    public void setAccNo(int accNo) {
        this.accNo = accNo;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
