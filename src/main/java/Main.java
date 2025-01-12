import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dbhelper.DbHelper;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    // Creating logger instance
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            DbHelper db = new DbHelper();
            Scanner sc = new Scanner(System.in);
            logger.info("Application started..");

            while (true) {
                logger.info("Prompting for Username...");
                System.out.println("Enter Username: ");
                String uname = sc.nextLine().trim();

                if (uname.isEmpty()) {
                    logger.warn("Username is empty. Prompting user to try again.");
                    System.out.println("Username cannot be empty. Please try again.");
                    continue;
                }

                if (uname.equalsIgnoreCase("exit")) {
                    logger.info("User requested to exit.");
                    System.out.println("Exiting....");
                    break;
                }

                Integer acc_no = db.getAccountNoByUsername(uname);
                if (acc_no == null) {
                    logger.error("Username {} not found!", uname);
                    System.out.println("Username not found!! Try again..");
                    continue;
                }

                logger.info("Welcome {}", uname);
                System.out.println("Welcome " + uname);
                System.out.println("Select action: ");
                System.out.println("1. Check Balance");
                System.out.println("2. Deposit");
                System.out.println("3. Withdraw");
                System.out.println("4. Logout");

                int action = 0;
                while (true) {
                    try {
                        logger.info("Prompting user for action selection.");
                        System.out.println("Select: ");
                        action = sc.nextInt();
                        if(action == 4){
                            sc.nextLine();
                            break;
                        }

                    } catch (InputMismatchException e) {
                        sc.nextLine();
                        logger.warn("Invalid input provided. Asking user to enter valid input.");
                        System.out.println("Please enter valid Input ");
                        continue;
                    }

                    switch (action) {
                        case 1:
                            double bal = db.viewBalance(acc_no);
                            logger.info("User requested balance check. Balance: {}", bal);
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
                                logger.warn("Invalid deposit amount entered.");
                                System.out.println("Ammount not valid try again.. ");
                                continue;
                            }
                            if (ammount > 0) {
                                double curr_bal = db.deposit(acc_no, ammount);
                                logger.info("Deposit successful. New Balance: {}", curr_bal);
                                System.out.println("Deposit successful. New Balance: " + curr_bal);
                            } else {
                                logger.warn("Invalid deposit amount. Should be greater than 0.");
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
                                logger.warn("Invalid withdrawal amount entered.");
                                System.out.println("Ammount not valid try again.. ");
                                continue;
                            }
                            if (ammount > 0) {
                                double curr_bal;
                                try {
                                    curr_bal = db.withdraw(acc_no, ammount);
                                    logger.info("Withdraw successful. New Balance: {}", curr_bal);
                                } catch (Exception e) {
                                    logger.error("Withdrawal failed. Insufficient funds.");
                                    System.out.println("Balance nai he!");
                                    continue;
                                }
                                System.out.println("Withdraw successful. New Balance: " + curr_bal);
                            } else {
                                logger.warn("Invalid withdrawal amount. Should be greater than 0.");
                                System.out.println("Please Enter ammount grater than 0");
                            }
                            break;
                        default:
                            logger.warn("Invalid option selected.");
                            System.out.println("Enter valid option");
                            continue;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage());
            System.out.print(e.getMessage());
        }
    }
}
