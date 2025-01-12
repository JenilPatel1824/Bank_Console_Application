import dbhelper.DBH;

import java.lang.invoke.LambdaMetafactory;
import java.util.InputMismatchException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        DBH db = new DBH();
        Scanner sc=new Scanner(System.in);

        System.out.println("Application Started..");


        while (true)
        {
            System.out.println("Enter Username: ");
            String uname= sc.nextLine();
            Integer acc_no= db.userExists(uname);
            if(acc_no==null)
            {
                System.out.println("Username not found!! Try again..");
                continue;
            }

            System.out.println("Welcome "+uname);
            System.out.println("Select action: ");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");

            int action;
            try
            {
                action=sc.nextInt();
            }
            catch (InputMismatchException e)
            {
                System.out.println("Please enter valid Input: ");
                continue;
            }

            switch (action)
            {
                case 1:
                    double bal = db.view_balance(acc_no);
                    System.out.println("Balance: "+bal);
                    break;
                case 2:
                    double ammount;
                    try {
                        System.out.println("Enter amount to deposit: ");
                         ammount = sc.nextDouble();
                    }
                    catch (InputMismatchException e)
                    {
                        System.out.println("Ammount not valid try again.. ");
                        continue;
                    }
                    if(ammount>0)
                    {
                        double curr_bal= db.deposit(acc_no,ammount);
                        System.out.println("Deposit successful. New Balance: "+curr_bal);
                    }
                    else {
                        System.out.println("Please Enter ammount grater than 0");
                        continue;
                    }
                    break;
                case 3:
                    try{
                        System.out.println("Enter amount to withdraw: ");
                        ammount = sc.nextDouble();
                    }
                    catch (InputMismatchException e)
                    {
                        System.out.println("Ammount not valid try again.. ");
                        continue;
                    }
                    if(ammount>0)
                    {
                        double curr_bal;
                        try {
                             curr_bal = db.withdraw(acc_no, ammount);
                        }
                        catch (Exception e)
                        {
                            System.out.println(e.getMessage());
                            continue;
                        }
                        System.out.println("Withdraw successful. New Balance: "+curr_bal);
                    }
                    else {
                        System.out.println("Please Enter ammount grater than 0");
                        continue;
                    }


            }
        }

    }
}