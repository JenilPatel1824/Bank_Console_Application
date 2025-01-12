package dbhelper;

import model.Account;
import model.CurrentAccount;
import model.SavingAccount;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class DbHelper {

    private ConcurrentHashMap<Integer, Account> accounts = new ConcurrentHashMap<>();

    public DbHelper()
    {
        Account a1=new SavingAccount("Jenil1",101,100);
        accounts.put(101,a1);
        Account a2=new SavingAccount("Jenil2",102,100);
        accounts.put(102,a2);
        Account a3=new SavingAccount("Jenil3",103,100);
        accounts.put(103,a3);
        Account a4=new SavingAccount("Jenil4",104,100);
        accounts.put(104,a4);
        Account a5=new CurrentAccount("Jenil5",105,100);
        accounts.put(105,a5);
    }

    public ConcurrentHashMap<Integer, Account> getAccounts() {
        return accounts;
    }

    public Integer getAccountNoByUsername(String username) {
        for (Account account : accounts.values()) {
            if (account.getAcc_holder_name().equals(username)) {
                return account.getAcc_no();
            }
        }
        return null;
    }

    public double viewBalance(int accNo)
    {
        if(!isValidAccount(accNo) )
        {
            System.out.println("Acc no invalid");
            return -1;
        }
        Account a = accounts.get(accNo);
        return a.getBalance();
    }
    //Method Overloading
    public double view_balance(String acc_holder_name) {
        for (Account account : accounts.values()) {
            if (account.getAcc_holder_name().equals(acc_holder_name)) {
                return account.getBalance();
            }
        }
        throw new IllegalArgumentException("Account holder not found");
    }
    public double withdraw(int acc_no,double amount) {
        if(!isValidAccount(acc_no) )
        {
            System.out.println("Acc no invalid");
            return -1;
        }
        if(amount<=0)
        {
            System.out.println("amount invalid");
            return -2;
        }
         AtomicReference<Double> amt = new AtomicReference<>((double) 0);
         accounts.compute(acc_no, (key, account) -> {
            if (account != null) {
                try {
                    amt.set(account.withdraw(amount));
                }
                catch (Exception e)
                {
                    try {
                        throw new Exception("Aukat me re bhai, garib hai tu!");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
            return account;
        });
         return amt.get();
    }
    public double deposit(int accNo,double amount)  {
        if(!isValidAccount(accNo) )
        {
            // Use logger here
            System.out.println("Acc no invalid");
            return -1;
        }
        if(amount<=0)
        {
            // Use logger
            System.out.println("amount invalid");
            return -2;
        }
        AtomicReference<Double> amt = new AtomicReference<>((double) 0);
         accounts.compute(accNo, (key, account) -> {
            if (account != null) {
                //account.setBalance(account.getBalance() + amount);  // Update balance atomically
                amt.set(account.deposit(amount));
            }
            return account;
        });
         return amt.get();
    }

    public boolean isValidAccount(int accountNo)
    {
        return accounts.containsKey(accountNo);
    }
}
