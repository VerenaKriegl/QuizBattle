/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author krieg
 */
public class StopWatch {

    private int count = 15;

    public StopWatch() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                System.out.println(count);
                if (count > 0) {
                    count--;
                }

                if (count == 0) {
                    System.exit(0);
                }
            }
        };
        timer.schedule(task, 0, 1000);
    }

    public static void main(String[] args) {
        new StopWatch();
    }
}
