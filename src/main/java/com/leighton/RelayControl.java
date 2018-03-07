package com.leighton;


public class RelayControl {

//    final GpioController gpio = GpioFactory.getInstance();
//    final GpioPinDigitalOutput pin =
//            gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "HeatRelay", PinState.LOW);

    public void off() {
        System.out.println("relay off");
//        pin.low();
    }

    public void on() {
        System.out.println("relay on");
//        pin.high();

    }

}
