package com.trafficmonitor;

public abstract class Vehicle {
    public enum Type { BICYCLE, CAR, SCOOTER }

    private final String id;
    private final Type type;

    public Vehicle(String id, Type type) {
        this.id = id;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }
}