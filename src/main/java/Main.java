import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dbhelper.DbHelper;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            DbHelper db = new DbHelper();

            Scanner sc = new Scanner(System.in);

            logger.info("Application started..");

            while (true)
            {
                System.out.println("Enter User ID (or type 'exit' to quit): ");

                String input = sc.nextLine();

                if (input.equalsIgnoreCase("exit"))
                {
                    System.out.println("Exiting the application...");

                    break;
                }

                long userId;

                try
                {
                    userId = Long.parseLong(input);
                }

                catch (NumberFormatException e)
                {
                    logger.error("Enter valid user id");

                    continue;
                }

                System.out.println("Enter account number: ");

                long accountNumber;

                try
                {
                    accountNumber = sc.nextLong();
                }

                catch (InputMismatchException e)
                {
                    logger.error("Enter valid account number");

                    continue;
                }

                if (!db.isValidUser(userId,accountNumber))
                {
                    logger.error("No record found with provided user id and account number");

                    continue;
                }

                logger.info("login successful for {} : {}", userId,accountNumber);

                System.out.println("Welcome " + userId);

                System.out.println("Select action: ");

                System.out.println("1. Check Balance");

                System.out.println("2. Deposit");

                System.out.println("3. Withdraw");

                System.out.println("4. Logout");

                int action = 0;

                while (true)
                {
                    try
                    {
                        System.out.println("Select: ");

                        action = sc.nextInt();

                        if(action == 4)
                        {
                            sc.nextLine();

                            break;
                        }
                    }
                    catch (InputMismatchException e)
                    {
                        sc.nextLine();

                        logger.warn("Invalid input provided. Asking user to enter valid input.");

                        continue;
                    }

                    switch (action)
                    {
                        case 1:

                            db.checkBalance(userId,accountNumber);

                            break;

                        case 2:

                            double depositAmmount;

                            try
                            {
                                System.out.println("Enter amount to deposit: ");

                                depositAmmount = sc.nextDouble();

                                sc.nextLine();
                            }
                            catch (InputMismatchException e)
                            {
                                sc.nextLine();

                                logger.warn("Invalid deposit amount entered.");

                                continue;
                            }

                            db.deposit(userId, accountNumber,depositAmmount);

                            break;

                        case 3:

                            double withdrawAmount;

                            logger.info("User Requested withdraw: ");

                            try
                            {
                                System.out.println("Enter amount to withdraw: ");

                                withdrawAmount = sc.nextDouble();

                                sc.nextLine();
                            }
                            catch (InputMismatchException e)
                            {
                                sc.nextLine();

                                logger.warn("Invalid withdrawal amount entered.");

                                continue;
                            }

                            try
                            {
                                db.withdraw(userId, accountNumber,withdrawAmount);
                            }
                            catch (Exception e)
                            {
                                logger.error("Withdrawal failed. Insufficient funds.");

                                continue;
                            }
                            break;

                        default:
                            logger.warn("Invalid option selected.");
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("An error occurred: {}", e.getMessage());
        }
    }
}
