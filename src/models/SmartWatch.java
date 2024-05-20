package models;

import utils.DisplayTypeUtility;

import java.util.Objects;

/*
 * Smart Watch class inheriting the Wearable Device Class
 */

public class SmartWatch extends WearableDevice {

    private String displayType = "LCD";

    public SmartWatch(String size, double price, String manufacturerName, String material, String modelName, String id, String displayType) {
        super(size, price, manufacturerName, material, modelName, id);
        if (DisplayTypeUtility.isValidDisplayType(displayType)) {
            this.displayType = displayType;
        };
    }

    //------------------------------------------------------------------------------------------
    //  Getters and setters. Validation as outlined in spec
    //------------------------------------------------------------------------------------------

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        if (DisplayTypeUtility.isValidDisplayType(displayType)) {
            this.displayType = displayType;
        };
    }

    //------------------------------------------------------------------------------------------
    // Abstract methods in parent class being overridden.
    //------------------------------------------------------------------------------------------

    @Override
    public double getInsurancePremium() {
        return super.getPrice() * .06;
    }

    @Override
    public String connectToInternet() {
        return "Connects to the internet via bluetooth";
    }

    @Override
    public String toString() {
        return "{SmartWatch: " +
                "Display Type= " + displayType + ", " +
                super.toString();
    }

    //------------------------------------------------------------------------------------------
    // Equals, hashCode and toString
    //------------------------------------------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SmartWatch that = (SmartWatch) o;
        return Objects.equals(getDisplayType(), that.getDisplayType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDisplayType());
    }
}
