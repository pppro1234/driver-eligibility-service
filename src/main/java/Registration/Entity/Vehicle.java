package Registration.Entity;

import javax.swing.*;

public class Vehicle {
    private String make;
    private String model;
    private int year;
    private String color;
    private String owner;
    private String plateNumber;

    public Vehicle(String make, String model, int year, String owner, String plateNumber) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.owner = owner;
        this.plateNumber = plateNumber;
    }

    public Vehicle(Builder builder) {
        this.make = builder.make;
        this.model = builder.model;
        this.year = builder.year;
        this.color = builder.color;
        this.owner = builder.owner;
        this.plateNumber = builder.plateNumber;
    }

    public static class Builder{
        private String make;
        private String model;
        private int year;
        private String color;
        private String owner;
        private String plateNumber=null;

        public Builder make(String make) {
            this.make = make;
            return this;
        }

        public Builder model(String model) {
            this.model = model;
            return this;
        }

        public Builder year(int year) {
            this.year = year;
            return this;
        }

        public Builder color(String color) {
            this.color = color;
            return this;
        }

        public Builder owner(String owner) {
            this.owner = owner;
            return this;
        }

        public Builder plateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
            return this;
        }
        public Vehicle build(){
            return new Vehicle(this);
        }
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getColor() {
        return color;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "make='" + make + '\'' +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", color='" + color + '\'' +
                ", owner='" + owner + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                '}';
    }
}
