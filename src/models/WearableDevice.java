package models;

import utils.ManufacturerNameUtility;
import utils.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * Wearable Device abstract Class
 */

public abstract class WearableDevice {

    // Instance fields as per spec
    private String size;
    private double price = 20;
    private String manufacturerName = "unknown";
    private String material;
    private String modelName = "unknown";
    private String id = "unknown";


    //------------------------------------------------------------------------------------------
    //  Constructor. Follows all required validation
    //------------------------------------------------------------------------------------------
    public WearableDevice(String size, double price, String manufacturerName, String material, String modelName, String id) {
        this.size = Utilities.truncateString(size, 10);
//      Implements all validation
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
        this.id = Utilities.truncateString(id, 10);
    }


    //------------------------------------------------------------------------------------------
    //  Getters and setters
    //------------------------------------------------------------------------------------------

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

    //------------------------------------------------------------------------------------------
    // Abstract Methods
    //------------------------------------------------------------------------------------------

    public abstract double getInsurancePremium();

    public abstract String connectToInternet();

    //------------------------------------------------------------------------------------------
    // Equals, hashCode and toString
    //------------------------------------------------------------------------------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WearableDevice that = (WearableDevice) o;
        return Double.compare(getPrice(), that.getPrice()) == 0 && Objects.equals(getSize(), that.getSize()) && Objects.equals(getManufacturerName(), that.getManufacturerName()) && Objects.equals(getMaterial(), that.getMaterial()) && Objects.equals(getModelName(), that.getModelName()) && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSize(), getPrice(), getManufacturerName(), getMaterial(), getModelName(), getId());
    }


    @Override
    public String toString() {
        return "size: " + size + ", price= " + price + ", manufacturerName= " + manufacturerName + ", material= " + material + ", " + ", modelName= " + modelName + ", " + ", id= " + id + ", " + connectToInternet() + ", " + "Insurance Premium = " + getInsurancePremium() + "}";

    }
}

