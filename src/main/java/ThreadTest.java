import dbhelper.DbHelper;

import java.util.concurrent.*;

public class ThreadTest {

    public static void main(String[] args)
    {

        DbHelper db= new DbHelper();
        //HashMap<Integer, Account> accounts= db.getAccounts();
        System.out.println("thread testing... ");
        Runnable r1 = ()->{
            System.out.println(Thread.currentThread().getName()+" Started for deposit 100 "+db.view_balance(101));
            db.deposit(101,100);
            System.out.println(Thread.currentThread().getName()+" Done deposit 100 " + db.view_balance(101));

        };
        Runnable r2 = ()->{
            System.out.println(Thread.currentThread().getName()+" Started for deposit 200 "+db.view_balance(101));
            db.deposit(101,200);
            System.out.println(Thread.currentThread().getName()+" Done deposit 200 "+db.view_balance(101));

        };
        Runnable r3 = ()->{
            System.out.println(Thread.currentThread().getName()+" Started for Withdraw 100 "+db.view_balance(101));
            try {
                db.withdraw(101,100);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
            System.out.println(Thread.currentThread().getName()+" Done withdraw 100 "+db.view_balance(101));

        };
        Runnable r4 = ()->{
            System.out.println(Thread.currentThread().getName()+" Started for Withdraw 200 "+db.view_balance(101));
            try {
                db.withdraw(101,100);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
            System.out.println(Thread.currentThread().getName()+" Done withdraw 200 "+db.view_balance(101));
        };
        Runnable r5 = ()->{
            System.out.println(Thread.currentThread().getName()+" Started for deposit 500 "+db.view_balance(101));
            db.deposit(101,500);
            System.out.println(Thread.currentThread().getName()+" Done diposit 500 "+db.view_balance(101));
        };

        Runnable t1 = ()->{
            db.deposit(101,100);
            System.out.println(Thread.currentThread().getName()+" Done "+db.view_balance(101));
        };
        Runnable t2 = ()->{
            db.deposit(101,100);
            System.out.println(Thread.currentThread().getName()+" Done "+db.view_balance(101));

        };
        Runnable t3 = ()->{
            db.deposit(101,100);
            System.out.println(Thread.currentThread().getName()+" Done "+db.view_balance(101));

        };
        Runnable t4 = ()->{
            db.deposit(101,100);
            System.out.println(Thread.currentThread().getName()+" Done "+db.view_balance(101));

        };
        Runnable t5 = ()->{
            db.deposit(101,100);
            System.out.println(Thread.currentThread().getName()+" Done "+db.view_balance(101));

        };
        Runnable t6 = ()->{
            db.deposit(101,100);
            System.out.println(Thread.currentThread().getName()+" Done "+db.view_balance(101));

        };
        Runnable t7 = ()->{
            db.deposit(101,100);
            System.out.println(Thread.currentThread().getName()+" Done "+db.view_balance(101));

        };
        Runnable t8 = ()->{
            db.deposit(101,100);
            System.out.println(Thread.currentThread().getName()+" Done "+db.view_balance(101));

        };
        Runnable t9 = ()->{
            db.deposit(101,100);
            System.out.println(Thread.currentThread().getName()+" Done "+db.view_balance(101));

        };
        Runnable t10 = ()->{
            db.deposit(101,100);
            System.out.println(Thread.currentThread().getName()+" Done "+db.view_balance(101));

        };

        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        executorService.submit(r1);
//        executorService.submit(r2);
//        executorService.submit(r3);
//        executorService.submit(r4);
//        executorService.submit(r5);




//        executorService.submit(t1);
//        executorService.submit(t2);
//        executorService.submit(t3);
//        executorService.submit(t4);
//        executorService.submit(t5);
//        executorService.submit(t6);
//        executorService.submit(t7);
//        executorService.submit(t8);
//        executorService.submit(t9);
//        executorService.submit(t10);


        for(int i=0;i<100;i++)
        {
            Runnable loop = ()-> {
                    db.deposit(101, 100);
            };
            executorService.submit(loop);
        }

        executorService.shutdown();
        try{
            executorService.awaitTermination(60, TimeUnit.SECONDS);
            System.out.println("Bal: "+db.view_balance(101));
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
}
