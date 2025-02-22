package com.trafficmonitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);

    TrafficMonitor monitor = new TrafficMonitor(60);
    monitor.start();

    Bicycle bike1 = new Bicycle("ABC-011");
    Bicycle bike2 = new Bicycle("ABC-014");
    Bicycle bike3 = new Bicycle("ABC-015");
    Car car1 = new Car("ABC-012");
    Car car2 = new Car("ABC-013");
    Car car3 = new Car("ABC-014");
    Scooter scooter1 = new Scooter("ABC-013");
    Scooter scooter2 = new Scooter("ABC-014");

    monitor.onSignal(bike1);
    monitor.onSignal(bike2);
    monitor.onSignal(bike3);
    monitor.onSignal(car1);
    monitor.onSignal(car2);
    monitor.onSignal(car3);
    monitor.onSignal(scooter1);
    monitor.onSignal(scooter2);

    monitor.onSignal(bike1);
    monitor.onSignal(bike2);
    monitor.onSignal(bike3);
    monitor.onSignal(car1);
    monitor.onSignal(car2);
    monitor.onSignal(scooter1);
    monitor.onSignal(scooter2);
    monitor.onSignal(scooter2);

    System.out.println("Statistics (all):");
    for (String stat : monitor.getStatistics()) {
      System.out.println(stat);
    }

    System.out.println("\nStatistics (BICYCLE):");
    for (String stat : monitor.getStatistics(Vehicle.Type.BICYCLE)) {
      System.out.println(stat);
    }

    System.out.println("\nStatistics (CAR):");
    for (String stat : monitor.getStatistics(Vehicle.Type.CAR)) {
      System.out.println(stat);
    }

    System.out.println("\nStatistics (SCOOTER):");
    for (String stat : monitor.getStatistics(Vehicle.Type.SCOOTER)) {
      System.out.println(stat);
    }

    System.out.println("\nError count: " + monitor.getErrorCount());

    monitor.onSignal();
    System.out.println("\nState after error signal: " + monitor.getState());

    monitor.onSignal(bike1);
    System.out.println(
        "Error count after vehicle signal in ERROR state: " + monitor.getErrorCount());

    monitor.onSignalReset();
    System.out.println("\nState after reset signal: " + monitor.getState());

    System.out.println("\nStatistics after reset (all):");
    for (String stat : monitor.getStatistics()) {
      System.out.println(stat);
    }

    System.out.println("\nError count after reset: " + monitor.getErrorCount());
  }
}