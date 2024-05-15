package models;

public class SmartBand extends WearableDevice {

    private boolean heartRateMonitor;

    /**
     * Constructor for objects of class WearableDevice
     *
     * @param size             Size of the wearable. It should be less than 10 chars, there is no default.
     * @param price            Price of the wearable. Default value 20. Must be >= 20, with no upper limit.
     * @param manufacturerName Brand/manufacturer name of the wearable. One of APPLE, SAMSUNG, Garmin, FitBit, Whoop. Uses {@link utils.ManufacturerNameUtility#isValidMenuName(String)}
     * @param material         Material of the wearable. It should be less than 20 chars, no default.
     * @param modelName        Model name. Max of 30 char
     * @param id               Wearable id, unique in the system.
     */
    public SmartBand(String size, double price, String manufacturerName, String material, String modelName, String id, boolean heartRateMonitor) {
        super(size, price, manufacturerName, material, modelName, id);
        this.heartRateMonitor = heartRateMonitor;
    }

    public boolean isHeartRateMonitor() {
        return heartRateMonitor;
    }

    public void setHeartRateMonitor(boolean heartRateMonitor) {
        this.heartRateMonitor = heartRateMonitor;
    }

    public boolean getHeartRateMonitor () {
        return heartRateMonitor;
    }


    @Override
    public double getInsurancePremium() {
        return super.getPrice() * 0.7;
    }

    @Override
    public String connectToInternet() {
        return "Connects to the internet via Companion App";
    }

    @Override
    public String toString() {
        String heartRateStr = heartRateMonitor ? "Includes Heart Rate Monitor" : "No Heart Rate Monitor included";
        return "SmartBand: " +
                heartRateStr + ", " +
                super.toString();
    }
}

