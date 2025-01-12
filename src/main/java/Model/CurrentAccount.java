package Model;

public class CurrentAccount extends Account{

    public CurrentAccount(String accHolderName,int accNo,double balance)
    {
        super(accHolderName,accNo,balance);
    }
    @Override
    public String accType()
    {
        return "Current Account";
    }
}
