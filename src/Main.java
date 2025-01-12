import dbhelper.DBH;

import java.lang.invoke.LambdaMetafactory;
import java.util.InputMismatchException;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {

            DBH db = new DBH();
            Scanner sc = new Scanner(System.in);

            System.out.println("Application Started..");


            while (true) {
                System.out.println("Enter Username: ");
                String uname = sc.nextLine().trim();
                if (uname.isEmpty()) {
                    System.out.println("Username cannot be empty. Please try again.");
                    continue;
                }
                if (uname.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting....");
                    break;
                }
                Integer acc_no = db.userExists(uname);
                if (acc_no == null) {
                    System.out.println("Username not found!! Try again..");
                    continue;
                }


                System.out.println("Welcome " + uname);
                System.out.println("Select action: ");
                System.out.println("1. Check Balance");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Logout");


                int action = 0;
                while (true) {
                    try {
                        System.out.println("Select: ");
                        action = sc.nextInt();
                        if(action==4){
                            sc.nextLine();
                            break;
                        }

                    } catch (InputMismatchException e) {
                        sc.nextLine();
                        System.out.println("Please enter valid Input ");
                        continue;
                    }
                
                    switch (action) {
                        case 1:
                            double bal = db.view_balance(acc_no);
                            System.out.println("Balance: " + bal);
                            break;

                        case 2:
                            double ammount;
                            try {
                                System.out.println("Enter amount to deposit: ");
                                ammount = sc.nextDouble();
                                sc.nextLine();
                            } catch (InputMismatchException e) {
                                sc.nextLine();
                                System.out.println("Ammount not valid try again.. ");
                                continue;
                            }
                            if (ammount > 0) {
                                double curr_bal = db.deposit(acc_no, ammount);
                                System.out.println("Deposit successful. New Balance: " + curr_bal);
                            } else {
                                System.out.println("Please Enter ammount grater than 0");
                                continue;
                            }
                            break;
                        case 3:
                            try {
                                System.out.println("Enter amount to withdraw: ");
                                ammount = sc.nextDouble();
                                sc.nextLine();
                            } catch (InputMismatchException e) {
                                sc.nextLine();
                                System.out.println("Ammount not valid try again.. ");
                                continue;
                            }
                            if (ammount > 0) {
                                double curr_bal;
                                try {
                                    curr_bal = db.withdraw(acc_no, ammount);
                                } catch (Exception e) {
                                    System.out.println("Balance nai he!");
                                    continue;
                                }
                                System.out.println("Withdraw successful. New Balance: " + curr_bal);
                            } else {
                                System.out.println("Please Enter ammount grater than 0");

                            }
                            break;
                        default:
                            System.out.println("Enter valid option");
                            continue;
                    }
                }
            }
        }
        catch (Exception e)
        {
            System.out.print(e.getMessage());
        }

    }
}