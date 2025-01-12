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
                System.out.println("Enter Username: ");
                String uname = sc.nextLine().trim();

                if (uname.isEmpty()) {
                    logger.warn("Username is empty. Prompting user to try again.");
                    continue;
                }

                if (uname.equalsIgnoreCase("exit")) {
                    logger.info("User requested to exit.");
                    break;
                }

                Integer acc_no = db.getAccountNoByUsername(uname);
                if (acc_no == null) {
                    logger.error("Username {} not found!", uname);
                    continue;
                }

                logger.info("login successful for {}", uname);
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
                        if(action == 4){
                            sc.nextLine();
                            break;
                        }
                    } catch (InputMismatchException e) {
                        sc.nextLine();
                        logger.warn("Invalid input provided. Asking user to enter valid input.");
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
                                continue;
                            }
                            if (ammount > 0) {
                                double curr_bal = db.deposit(acc_no, ammount);
                                logger.info("Deposit successful. New Balance: {}", curr_bal);
                            } else {
                                logger.warn("Invalid deposit amount. Should be greater than 0.");
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
                                continue;
                            }
                            if (ammount > 0) {
                                double curr_bal;
                                try {
                                    curr_bal = db.withdraw(acc_no, ammount);
                                    logger.info("Withdraw successful. New Balance: {}", curr_bal);
                                } catch (Exception e) {
                                    logger.error("Withdrawal failed. Insufficient funds.");
                                    continue;
                                }
                            } else {
                                logger.warn("Invalid withdrawal amount. Should be greater than 0.");
                            }
                            break;
                        default:
                            logger.warn("Invalid option selected.");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("An error occurred: {}", e.getMessage());
        }
    }
}
