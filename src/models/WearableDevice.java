package models;

import utils.ManufacturerNameUtility;
import utils.Utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * A Wearable Device abstract Class
 *
 * @author Tom Cararaher
 * @version 1.0
 */

public abstract class WearableDevice {
    private String size;
    private double price = 20;
    private String manufacturerName;
    private String material;
    private String modelName = "unknown";
    private String id;


    /**
     * Constructor for objects of class WearableDevice
     *
     * @param size             Size of the wearable. It should be less than 10 chars, there is no default.
     * @param price            Price of the wearable. Default value 20. Must be >= 20, with no upper limit.
     * @param manufacturerName Brand/manufacturer name of the wearable. One of APPLE, SAMSUNG, Garmin, FitBit, Whoop. Uses {@link ManufacturerNameUtility#isValidMenuName(String)}
     * @param material         Material of the wearable. It should be less than 20 chars, no default.
     * @param modelName        Model name. Max of 30 char
     * @param id               Wearable id, unique in the system.
     */

    public WearableDevice(String size, double price, String manufacturerName, String material, String modelName, String id) {
        this.size = Utilities.truncateString(size, 10);
//        TODO Do exception handling for these with console message
        if (Utilities.minValue(price, 20)) {
            this.price = price;
        }
        ;
        if (ManufacturerNameUtility.isValidMenuName(manufacturerName)) {
            this.manufacturerName = manufacturerName;
        }
        ;
        this.material = Utilities.truncateString(material, 20);
        this.modelName = Utilities.truncateString(modelName, 30);
        ;
        // todo this also supossed to be within valid range?
        this.id = Utilities.truncateString(id, 10);
    }

    // set up array to iterate?? extra credit
//    private List<String> getFields = new ArrayList<>();

    public abstract double getInsurancePremium();

    public abstract String connectToInternet();

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        if (Utilities.validStringlength(size, 10)) {
            this.size = size;
        }
        ;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (Utilities.minValue(price, 20)) {
            this.price = price;
        }
        ;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        if (ManufacturerNameUtility.isValidMenuName(manufacturerName)) {
            this.manufacturerName = manufacturerName;
        }
        ;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        if (Utilities.validStringlength(material, 20)) {
            this.material = material;
        }
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        if (Utilities.validStringlength(modelName, 30)) {
            this.modelName = modelName;
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (Utilities.validStringlength(id, 10)) {
            this.id = id;
        }
    }

    // TODO to string code formatting
    @Override
    public String toString() {
        return "size= " + size +
                ", price= " + price +
                ", manufacturerName= " + manufacturerName +
                ", material= " + material +
                ", " + ", modelName= " + modelName + ", " + ", id= " + id
                + ", " + connectToInternet() + ", " +
                "Insurance Premium = " + getInsurancePremium() + ", ";

    }
}

