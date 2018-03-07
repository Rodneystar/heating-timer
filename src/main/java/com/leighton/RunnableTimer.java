package com.leighton;

final public class RunnableTimer implements Runnable {

    private int timerOnPeriod;
    private Controller rc;
//    private int testingRatio = 5;
    public RunnableTimer(int delay,Controller rc){
        this.rc = rc;
        this.timerOnPeriod = delay;

    }

    public void run() {
        System.out.print("thread id  : " + Thread.currentThread().getId());

        rc.timerSwitch(true);

        try {
            Thread.sleep(this.timerOnPeriod * 60000  );
        } catch (InterruptedException e) {
            e.printStackTrace();
            rc.timerSwitch(false);
            System.out.print("interrupted");
        }

        System.out.print("thread id  : " + Thread.currentThread().getId());
        rc.timerSwitch(false);
    }
}
