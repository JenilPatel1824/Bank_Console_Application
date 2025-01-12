package dbhelper;

import Model.Account;
import Model.CurrentAccount;
import Model.SavingAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class DbHelper {
    private static final Logger logger = LoggerFactory.getLogger(DbHelper.class);
    private ConcurrentHashMap<Integer, Account> accounts = new ConcurrentHashMap<>();
    public DbHelper()
    {
        logger.debug("initializing data...");
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
        logger.info("DbHelper initialized with accounts.");
    }

    public ConcurrentHashMap<Integer, Account> getAccounts() {
        return accounts;
    }

    public Integer getAccountNoByUsername(String username) {
        logger.debug("Getting account number for username: {}", username);
        for (Account account : accounts.values()) {
            if (account.getAccHolderName().equals(username)) {
                return account.getAccNo();
            }
        }
        logger.warn("Username not found: {}", username);
        return null;
    }

    public double viewBalance(int accNo)
    {
        logger.debug("Checking balance for account number: {}", accNo);
        if(!isValidAccountNumber(accNo) )
        {
            logger.error("Invalid account number: {}", accNo);
            return -1;
        }
        Account a = accounts.get(accNo);
        logger.info("Balance for account number {}: {}", accNo, a.getBalance());
        return a.getBalance();
    }
    //Method Overloading
    public double viewBalance(String accHolderName) {
        logger.debug("Checking balance for account holder: {}", accHolderName);
        for (Account account : accounts.values()) {
            if (account.getAccHolderName().equals(accHolderName)) {
                logger.info("Balance for account holder {}: {}", accHolderName, account.getBalance());
                return account.getBalance();
            }
        }
        logger.error("Account holder not found: {}", accHolderName);
        throw new IllegalArgumentException("Account holder not found");
    }
    public double withdraw(int accNo,double amount) {
        logger.debug("Withdrawing {} from account number: {}", amount, accNo);
        if(!isValidAccountNumber(accNo) )
        {
            logger.error("Invalid account number: {}", accNo);
            return -1;
        }
        if(amount<=0)
        {
            logger.error("Invalid withdrawal amount: {}", amount);
            return -2;
        }
         AtomicReference<Double> amt = new AtomicReference<>((double) 0);
         accounts.compute(accNo, (key, account) -> {
            if (account != null) {
                try {
                    amt.set(account.withdraw(amount));
                    logger.info("Withdrawal successful for account number {}. New balance: {}", accNo, amt.get());
                }
                catch (Exception e)
                {
                    logger.error("Error while withdrawing from account number {}: {}", accNo, e.getMessage());
                }
            }
            return account;
        });
         return amt.get();
    }
    public double deposit(int accNo,double amount)  {
        logger.debug("Depositing {} to account number: {}", amount, accNo);
        if(!isValidAccountNumber(accNo) )
        {
            logger.error("Invalid account number: {}", accNo);
            return -1;
        }
        if(amount<=0)
        {
            logger.error("Invalid deposit amount: {}", amount);
            return -2;
        }
        AtomicReference<Double> amt = new AtomicReference<>((double) 0);
         accounts.compute(accNo, (key, account) -> {
            if (account != null) {
                //account.setBalance(account.getBalance() + amount);  // Update balance atomically
                amt.set(account.deposit(amount));
                logger.info("Deposit successful for account number {}. New balance: {}", accNo, amt.get());
            }
            return account;
        });
         return amt.get();
    }

    public boolean isValidAccountNumber(int accNo)
    {
        return accounts.containsKey(accNo);
    }
}
