package com.leighton;

public class Main {
    public static void main(String[] args) {
//          testRCTimerControl();
//          testTimerEvents();
        runService();
    }

    public static void runService() {
        HeatingService service = new HeatingService();
        service.start();
        Controller c = service.getController();
    }
}


//    public static void testTimerEvents() {
//
//    }
//    public static void startStopTimers(TimerControl timer) {
//        timer.startAllTimers();
//        try {
//            Thread.sleep(10000);
//            timer.stopTimers();
//
//        } catch (InterruptedException e) {
//
//            e.printStackTrace();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//
//        try {
////        timer.addEvent("11:00", 90);
//            Thread.sleep(10000);
//            timer.startAllTimers();
//
//        } catch (InterruptedException e) {
//
//            e.printStackTrace();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//    }
//
//
////    System.out.println(timer.getEventList().get(0).getEventTime());
////    System.out.println(timer.getEventList().get(0).getEndTime());
//
//
//
//
//
//    private static void testRCTimerControl() {
//
//    }
//
//    private static void startService() {
//        HeatingService service = new HeatingService();
//        service.start();
//    }
//
//
//    public static void testTimerEvent() {
//
//        TimerEvent event = new TimerEvent("18:45",150);
//        int testout = event.getOffsetFromNow();
//        System.out.println(testout);
//
//        System.out.println(event.getOffsetFromNow());
//
//
//    }
//}
