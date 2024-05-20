package models;

import java.util.Objects;

/*
 * Smart Band class inheriting the Wearable Device Class
 */

public class SmartBand extends WearableDevice {

    private boolean heartRateMonitor;


    //------------------------------------------------------------------------------------------
    //  Constructor. Follows all required validation and uses super to call parent constructor.
    //------------------------------------------------------------------------------------------
    public SmartBand(String size, double price, String manufacturerName, String material, String modelName, String id, boolean heartRateMonitor) {
        super(size, price, manufacturerName, material, modelName, id);
        this.heartRateMonitor = heartRateMonitor;
    }

    //------------------------------------------------------------------------------------------
    //  Getters and setters.
    //------------------------------------------------------------------------------------------

    public boolean isHeartRateMonitor() {
        return heartRateMonitor;
    }

    public void setHeartRateMonitor(boolean heartRateMonitor) {
        this.heartRateMonitor = heartRateMonitor;
    }

    public boolean getHeartRateMonitor () {
        return heartRateMonitor;
    }

    //------------------------------------------------------------------------------------------
    // Abstract methods in parent class being overridden.
    //------------------------------------------------------------------------------------------

    @Override
    public double getInsurancePremium() {
        return super.getPrice() * 0.7;
    }

    @Override
    public String connectToInternet() {
        return "Connects to the internet via Companion App";
    }

    //------------------------------------------------------------------------------------------
    // Equals, hashCode and toString
    //------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        String heartRateStr = heartRateMonitor ? "Includes Heart Rate Monitor" : "No Heart Rate Monitor included";
        return "{SmartBand: " +
                heartRateStr + ", " +
                super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartBand smartBand = (SmartBand) o;
        return isHeartRateMonitor() == smartBand.isHeartRateMonitor();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isHeartRateMonitor());
    }
}

