package com.trafficmonitor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TrafficMonitorTest {

  private TrafficMonitor monitor;

  @BeforeEach
  public void setUp() {
    monitor = new TrafficMonitor(60);
  }

  @Test
  public void testStart() {
    monitor.start();
    assertEquals(TrafficMonitor.State.ACTIVE, monitor.getState());
  }

  @Test
  public void testStop() {
    monitor.start();
    monitor.stop();
    assertEquals(TrafficMonitor.State.STOPPED, monitor.getState());
  }

  @Test
  public void testReset() {
    monitor.start();
    monitor.onSignal(new Bicycle("ABC-011"));
    monitor.reset();
    assertEquals(0, monitor.getStatistics().size());
    assertEquals(0, monitor.getErrorCount());
    assertEquals(TrafficMonitor.State.ACTIVE, monitor.getState());
  }

@Test
public void testOnSignalVehicle() {
  monitor.start();
  Bicycle bike1 = new Bicycle("ABC-011");
  monitor.onSignal(bike1);
  assertEquals(1, monitor.getStatistics().size());
  assertTrue(monitor.getStatistics().contains("ABC-011 - BICYCLE (1)"));
}

  @Test
  public void testOnSignalError() {
    monitor.start();
    monitor.onSignal();
    assertEquals(TrafficMonitor.State.ERROR, monitor.getState());
  }

  @Test
  public void testOnSignalReset() {
    monitor.start();
    monitor.onSignal();
    monitor.onSignalReset();
    assertEquals(TrafficMonitor.State.ACTIVE, monitor.getState());
  }

  @Test
  public void testGetErrorCount() {
    monitor.start();
    monitor.onSignal();
    monitor.onSignal(new Bicycle("ABC-011"));
    assertEquals(1, monitor.getErrorCount());
  }

  @Test
  public void testGetStatisticsByType() {
    monitor.start();
    Bicycle bike1 = new Bicycle("ABC-011");
    Car car1 = new Car("ABC-012");
    monitor.onSignal(bike1);
    monitor.onSignal(car1);
    assertEquals(1, monitor.getStatistics(Vehicle.Type.BICYCLE).size());
    assertEquals(1, monitor.getStatistics(Vehicle.Type.CAR).size());
  }

  @Test
  public void testGetStatistics() {
    monitor.start();
    Bicycle bike1 = new Bicycle("ABC-011");
    Car car1 = new Car("ABC-012");
    monitor.onSignal(bike1);
    monitor.onSignal(car1);
    assertEquals(2, monitor.getStatistics().size());
  }
}