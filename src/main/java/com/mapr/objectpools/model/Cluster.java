package com.mapr.objectpools.model;

public class Cluster {

    private final String name;

    public Cluster(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
