package Model;

public class CurrentAccount extends Account{

    public CurrentAccount(String acc_holder_name,int acc_no,double balance)
    {
        super(acc_holder_name,acc_no,balance);
    }
    @Override
    public String acc_type()
    {
        return "Current Account";
    }
}
