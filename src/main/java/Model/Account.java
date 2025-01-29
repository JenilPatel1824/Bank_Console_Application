package Model;

import java.util.concurrent.atomic.AtomicReference;
public class Account
{
    private long accountNumber;

    private AtomicReference<Double> balance;

    private long userId;

    public Account(long accountNumber,long userId)
    {
        this.userId=userId;

        this.accountNumber=accountNumber;

        this.balance=new AtomicReference<Double>(0.0);
    }

    public Account(long accountNumber, long userId, double balance)
    {
        this.userId = userId;

        this.accountNumber=accountNumber;

        if(balance>=0)
        {
            this.balance = new AtomicReference<>(balance);
        }
        else
        {
            throw new IllegalArgumentException("Please enter valid initial amount");
        }
    }

    public double deposit(double amount)
    {
        if(amount<=0)
        {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }
        return balance.updateAndGet(current_balance->current_balance+amount);
    }

    public double withdraw(double amount)
    {
        if(amount<=0)
        {
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        if(amount>balance.get())
        {
            throw new IllegalArgumentException("Insufficient Balance!!");
        }

        return balance.updateAndGet(current_balance->current_balance-amount);
    }

    public long getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(long accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public double getBalance()
    {
        return balance.get();
    }

    public long getUserId()
    {
        return userId;
    }

    public void setUserId(long userId)
    {
        this.userId=userId;
    }
}
