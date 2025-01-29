package dbhelper;

import Model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

public class DbHelper {
    private static final Logger logger = LoggerFactory.getLogger(DbHelper.class);

    private static final ConcurrentHashMap<Long, Account> accounts= new ConcurrentHashMap<>();

    public DbHelper()
    {
        logger.info("DbHelper Constructor called");

        Account firstAccount= new Account(101,1,100);

        accounts.put(1L,firstAccount);

        Account secoundAccount= new Account(102,2,100);

        accounts.put(2L,secoundAccount);

        Account thirdAccount= new Account(103,3,100);

        accounts.put(3L,thirdAccount);

        Account fourthAccount= new Account(104,4,100);

        accounts.put(4L,fourthAccount);

        Account fifthAccount= new Account(105,5,100);

        accounts.put(5L,fifthAccount);

        logger.info("DbHelper Constructor Completed");

    }

    public double checkBalance(long userId,long accountNumber)
    {
        logger.debug("Checking balance for account number: {}", accountNumber);

        return accounts.computeIfPresent(userId, (key, account) -> {

            if (account.getAccountNumber()==accountNumber)
            {
                double balance = account.getBalance();

                logger.info("checkBalance Successfull for {} {} : {}",userId,accountNumber,balance);

                return account;
            }
            else
            {
                logger.error("Account number mismatch or invalid account");

                return account;
            }
        }) != null ? accounts.get(userId).getBalance() : 0.0;
    }

    public double withdraw(long userId,long accountNumber,double withdrawAmount)
    {
        logger.debug("Withdrawing {} from account number: {}", withdrawAmount, accountNumber);

        return accounts.computeIfPresent(userId, (key, account) -> {

            if (account.getAccountNumber()==accountNumber)
            {
                double balance = account.withdraw(withdrawAmount);

                logger.info("withdraw Successfull for {} {} : new balance: {}",userId,accountNumber,balance);

                return account;
            }
            else
            {
                logger.error("Account number mismatch or invalid account");

                return account;
            }
        }) != null ? accounts.get(userId).getBalance() : 0.0;
    }

    public double deposit(long userId,long accountNumber,double depositAmount)
    {
        logger.debug("Depositing {} to account number: {}", depositAmount, accountNumber);

        return accounts.computeIfPresent(userId, (key, account) -> {

            if (account.getAccountNumber()==accountNumber)
            {
                double balance = account.deposit(depositAmount);

                logger.info("deposit Successfull for {} {} : {}",userId,accountNumber,balance);

                return account;
            }
            else
            {
                logger.error("Account number mismatch or invalid account");

                return account;
            }
        }) != null ? accounts.get(userId).getBalance() : 0.0;
    }

    public boolean validateUser(long userId, long accountNumber)
    {
        logger.debug("Validate user called for {} {}",userId , accountNumber);

        Account account = accounts.get(userId);

        if(account!=null)
        {
            if(account.getAccountNumber()==accountNumber)
            {
                return true;
            }
        }
        return false;
    }
}
