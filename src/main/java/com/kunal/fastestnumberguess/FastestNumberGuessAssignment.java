package com.kunal.fastestnumberguess;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

/**
 * @author kunal
 * @project FastestNumberGuessAssignment
 */

public class FastestNumberGuessAssignment implements Runnable {

    private static final Logger logger = LogManager.getLogger(FastestNumberGuessAssignment.class);
    private static int inputNumber;
    private static int kunal;

    private static int counterCount = 1;
    private static int loopCounterCount = 0;

    /*
        Made the "scanner" static so that the main method can close it once everything is done.
    */
    private static Scanner scanner = new Scanner(System.in);

    public void run() {

        try {
            kunal = -1;
            //boolean isFloatOnly = Pattern.matches("[0-9]+", scanner);
            /*
                hasNext --- returns true if this scanner has another token in its input.
             */
            while (kunal != inputNumber && counterCount <= 999999999999999l && scanner.hasNextInt()) {
                loopCounterCount = loopCounterCount + 1;
                counterCount++;
                logger.info("Total iterations or comparisons done by all the threads to reach or find that number {} " + counterCount++);

                /*
                    Added a check for interrupt, otherwise this thread will never end unless the user enters the input.
                */

                if (Thread.currentThread().isInterrupted())
                    return;
                kunal = scanner.nextInt();

                /*
                    Checking the input number provided as per the initial requirement.
                 */
                if (kunal == inputNumber) {
                    logger.info("The number is " + inputNumber);
                }
            }

        } catch (Exception exception) {
            /*
                Thread.currentThread().getName() ---> currentThread() will return an instance of Thread
                which we can then call getName() on that instance.
             */
            logger.error("Exception Occured {} ", exception , Thread.currentThread().getName());
        }
    }

    public static void main(String args[]) throws InterruptedException {
        ThreadGroup threadGroup = new ThreadGroup("FastestNumberGuessAssignment");
        long start = System.nanoTime();
        inputNumber = (int) (Math.random() * 999999999999999l + 1);

        /* Here , declaring the guessing thread as final so that it can be used
         inside of the TimerTask which is created later.
        */
        final Thread thread = new Thread(new FastestNumberGuessAssignment());
        thread.start();

        /*
            Here, we are using a Timer to enforce our time limit where the TimerTask will execute an interrupt of our above guessing thread
            final Thread thread = ....
            if the thread is still alive.
        */
        long l = 1000 * 5;
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {

            @Override
            public void run() {
                if (thread.isAlive()) {
                    thread.interrupt();
                    logger.debug("Input a number between 0 and 999999999999999 :  ");
                }
            }
        };

        /* Wait for existing threads to finish the execution */
        while (threadGroup.activeCount() > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException interruptedException) {
                logger.error("InterruptedException caught {} ", interruptedException , Thread.currentThread().getName());
                // clearing the active input or clearing the buffer
                scanner.next();
            }
        }
        timer.schedule(task, l);
        /*
            Holding or Waiting up for the speculating string to wrap up some time recently canceling the timer
        */
        thread.join();

        /*
            We need to clean up by cancelling the timer as the user might have got the answer from the console / keyboard input.
        */
        timer.cancel();

        /*
            Its important to close the scanner to release the resources.
         */
        scanner.close();

        /*
            Here, calculating the output of the code taken to complete its execution for the input provided,
            which will print in the console as per the initial requirement ---> Your number is found in 11.449078745 ms
            1e9 is scientific notation used to denote the number followed by nine zeros
         */
        long end = System.nanoTime();
        System.out.println("Your number is found in " + (double) (end - start) / 1e9 + " ms");
    }
}
