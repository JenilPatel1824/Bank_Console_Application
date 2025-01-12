package Model;

public class SavingAccount extends Account{

    public SavingAccount(String accHolderName,int accNo,double balance)
    {
        super(accHolderName,accNo,balance);
    }
    @Override
    public String accType()
    {
        return "Saving Account";
    }
}
