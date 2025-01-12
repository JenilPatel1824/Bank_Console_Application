package model;

public class SavingAccount extends Account{

    public SavingAccount(String acc_holder_name,int acc_no,double balance)
    {
        super(acc_holder_name,acc_no,balance);
    }
    @Override
    public String acc_type()
    {
        return "Saving Account";
    }
}
