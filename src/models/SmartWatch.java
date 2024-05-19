package models;

import utils.DisplayTypeUtility;

import java.util.Objects;

public class SmartWatch extends WearableDevice {

    private String displayType = "LCD";

    /**
     * Constructor for objects of class WearableDevice
     *
     * @param size             Size of the wearable. It should be less than 10 chars, there is no default.
     * @param price            Price of the wearable. Default value 20. Must be >= 20, with no upper limit.
     * @param manufacturerName Brand/manufacturer name of the wearable. One of APPLE, SAMSUNG, Garmin, FitBit, Whoop. Uses {@link utils.ManufacturerNameUtility#isValidMenuName(String)}
     * @param material         Material of the wearable. It should be less than 20 chars, no default.
     * @param modelName        Model name. Max of 30 char
     * @param id               Wearable id, unique in the system.
     * @param displayType
     */
    public SmartWatch(String size, double price, String manufacturerName, String material, String modelName, String id, String displayType) {
        super(size, price, manufacturerName, material, modelName, id);
        if (DisplayTypeUtility.isValidDisplayType(displayType)) {
            this.displayType = displayType;
        };
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        if (DisplayTypeUtility.isValidDisplayType(displayType)) {
            this.displayType = displayType;
        };
    }

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
