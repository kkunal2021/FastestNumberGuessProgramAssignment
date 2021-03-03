package com.kunal.fastestnumberguess;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author kunal
 * @project FastestNumberGuessAssignment
 */

public class FastestNumberGuessAssignment implements Runnable {

    private static final Logger logger = LogManager.getLogger(FastestNumberGuessAssignment.class);
    private static int inputNumber;
    private static int kunal;

    private static Scanner scanner = new Scanner(System.in);

    public void run() {

        try {
            kunal = -1;
            while (kunal != inputNumber && scanner.hasNext()) {
                if (Thread.currentThread().isInterrupted())
                    return;
                kunal = scanner.nextInt();

                if (kunal == inputNumber) {
                    logger.info("The number is " + inputNumber);
                }
            }

        } catch (Exception exception) {
            logger.error("Exception Occured {} ", exception , Thread.currentThread().getName());
        }
    }

    public static void main(String args[]) throws InterruptedException {
        ThreadGroup threadGroup = new ThreadGroup("FastestNumberGuessAssignment");
        long start = System.nanoTime();
        inputNumber = (int) (Math.random() * 999999999999999l + 1);

        final Thread thread = new Thread(new FastestNumberGuessAssignment());
        thread.start();

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

        /* Wait for threads to finish the execution */
        while (threadGroup.activeCount() > 0) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException interruptedException) {
                logger.error("InterruptedException caught {} ", interruptedException , Thread.currentThread().getName());
                // clearing the active input
                scanner.next();
            }
        }
        timer.schedule(task, l);
        thread.join();
        timer.cancel();
        scanner.close();

        long end = System.nanoTime();
        System.out.println("Your number is found in " + (double) (end - start) / 1e9 + " ms");
    }
}
