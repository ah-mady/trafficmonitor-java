package com.trafficmonitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TrafficMonitor {

  public enum State {INIT, ACTIVE, ERROR, STOPPED}

  private State state;
  private final int period;
  private int errorCounter;
  private final Map<String, Integer> vehicleCounts;
  private final List<Vehicle> vehicleList;

  public TrafficMonitor(int period) {
    this.state = State.INIT;
    this.period = period;
    this.errorCounter = 0;
    this.vehicleCounts = new ConcurrentHashMap<>();
    this.vehicleList = new ArrayList<>();
  }

  public void start() {
    if (state == State.INIT) {
      state = State.ACTIVE;
    }
  }

  public void stop() {
    if (state == State.ACTIVE) {
      state = State.STOPPED;
    }
  }

  public void reset() {
    state = State.ACTIVE;
    resetStatistics();
  }

  public void onSignal(Vehicle vehicle) {
    if (state == State.ACTIVE) {
      String id = vehicle.getId();
      vehicleCounts.put(id, vehicleCounts.getOrDefault(id, 0) + 1);
      if (!vehicleList.contains(vehicle)) {
        vehicleList.add(vehicle);
      }
    } else if (state == State.ERROR) {
      errorCounter++;
      System.out.println(
          "Error: Vehicle signal received in Error state for vehicle ID: " + vehicle.getId());
    }
  }

  public void onSignal() {
    if (state == State.ACTIVE) {
      state = State.ERROR;
    }
  }

  public void onSignalReset() {
    reset();
  }

  public int getErrorCount() {
    return errorCounter;
  }

  public List<String> getStatistics(Vehicle.Type type) {
    List<String> stats = new ArrayList<>();
    for (Vehicle vehicle : vehicleList) {
      if (vehicle.getType() == type) {
        stats.add(
            vehicle.getId() + " - " + vehicle.getType() + " (" + vehicleCounts.get(vehicle.getId())
                + ")");
      }
    }
    Collections.sort(stats);
    return stats;
  }

  public List<String> getStatistics() {
    List<String> stats = new ArrayList<>();
    for (Vehicle vehicle : vehicleList) {
      stats.add(
          vehicle.getId() + " - " + vehicle.getType() + " (" + vehicleCounts.get(vehicle.getId())
              + ")");
    }
    Collections.sort(stats);
    return stats;
  }

  private void resetStatistics() {
    vehicleCounts.clear();
    vehicleList.clear();
    errorCounter = 0;
  }

  public State getState() {
    return state;
  }
}