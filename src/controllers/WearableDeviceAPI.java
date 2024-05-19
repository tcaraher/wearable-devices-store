package controllers;


import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.SmartBand;
import models.SmartWatch;
import models.WearableDevice;
import utils.ISerializer;
import utils.ManufacturerNameUtility;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The WearableDeviceAPI manages all the entered wearables into a List.
 *
 * @author Tom Caraher
 * @version 1.0
 */

public class WearableDeviceAPI implements ISerializer {

    private List<WearableDevice> wearableList;

    private File file;

    public WearableDeviceAPI() {
        wearableList = new ArrayList<>();
    }

    public boolean addWearableDevice(WearableDevice wearableDevice) {
        return wearableList.add(wearableDevice);
    }
//------------------------------------------------------------------------------------------
// CRUD Methods
// ------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------
    // Number methods
    //------------------------------------------------------------------------------------------
    public int numberOfWearableDevices() {
        return wearableList.size();
    }

    public int numberOfSmartBands() {
        int number = 0;
        for (WearableDevice wearableDevice : wearableList) {
            if (wearableDevice instanceof SmartBand) {
                number++;
            }
        }
        return number;
    }

    public int numberOfSmartWatches() {
        int number = 0;
        for (WearableDevice wearableDevice : wearableList) {
            if (wearableDevice instanceof SmartWatch) {
                number++;
            }
        }
        return number;
    }

    public int numberOfWearableDeviceByChosenManufacturer(String manufacturer) {
        if (ManufacturerNameUtility.isValidMenuName(manufacturer)) {
            int number = 0;
            for (WearableDevice wearableDevice : wearableList) {
                if (wearableDevice.getManufacturerName().equals(manufacturer)) {
                    number++;
                }
            }
            return number;
        } else return -1;
    }

//------------------------------------------------------------------------------------------
// Read/List Methods
//------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------
    // List Methods
    //------------------------------------------------------------------------------------------
    public String listAllWearableDevices() {
        String str = "";

        for (WearableDevice wearableDevice : wearableList) {
            str += wearableList.indexOf(wearableDevice) + ": " + wearableDevice.toString() + "\n";
        }

        if (str.isEmpty()) {
            return "No Posts";
        } else {
            return str;
        }
    }

    public String listAllSmartBands() {
        String str = "";

        for (WearableDevice wearableDevice : wearableList) {
            if (wearableDevice instanceof SmartBand) {
                str += wearableList.indexOf(wearableDevice) + ": " + wearableDevice.toString() + "\n";
            }
        }

        if (str.isEmpty()) {
            return "No Smart Bands";
        } else {
            return str;
        }
    }

    public String listAllSmartWatches() {
        String str = "";

        for (WearableDevice wearableDevice : wearableList) {
            if (wearableDevice instanceof SmartWatch) {
                str += wearableList.indexOf(wearableDevice) + ": " + wearableDevice.toString() + "\n";
            }
        }

        if (str.isEmpty()) {
            return "No Smart Watches";
        } else {
            return str;
        }
    }

    public String listAllWearableDevicesByChosenManufacturer(String chosenManufacturer) {
        if (ManufacturerNameUtility.isValidMenuName(chosenManufacturer)) {
            String str = "";
            for (WearableDevice wearableDevice : wearableList) {
                if (wearableDevice.getManufacturerName().contains(chosenManufacturer)) {
                    str += wearableList.indexOf(wearableDevice) + ":" + wearableDevice.toString() + "\n";
                }
            }
            return str;
        } else return null;
    }


    // TODO update this java doc

    /**
     * This method builds and returns a String containing all the products in the ArrayList
     * that are more expensive that a specific amount (passed as a parameter).
     * <p>
     * For each product added to the String, the associated index number is included.
     * If no products are stored in the ArrayList, the returned String indicates this.
     *
     * @param price The value used to filter for products costing more than it.
     * @return A String containing all the products in the ArrayList more expensive than the parameter value
     * or "No Products are more expensive than: ", if none are more expensive.  If no products are
     * in the ArrayList, the returned String contains "No products in store".
     */
    public String listAllWearableDeviceAbovePrice(double price) {
        String str = "";
        for (int i = 0; i < wearableList.size(); i++) {
            if (wearableList.get(i).getPrice() > price)
                str += i + ": " + wearableList.get(i) + "\n";
        }
        if (str.isEmpty()) {
            return "No WearableDevice more expensive than: " + price;
        } else {
            return str;
        }
    }


    public String listAllWearableDeviceBelowPrice(double price) {
        String str = "";
        for (int i = 0; i < wearableList.size(); i++) {
            if (wearableList.get(i).getPrice() < price)
                str += i + ": " + wearableList.get(i) + "\n";
        }
        if (str.isEmpty()) {
            return "No WearableDevice cheaper than: " + price;
        } else {
            return str;
        }
    }

//------------------------------------------------------------------------------------------
// Get Technology methods
//------------------------------------------------------------------------------------------

    /**
     * @param indexOfObjToReturn Index of the object to get
     * @return returns the WearableDevice obj
     */
    public WearableDevice getWearableDeviceByIndex(int indexOfObjToReturn) {
        if (isValidIndex(indexOfObjToReturn)) {
            return wearableList.get(indexOfObjToReturn);
        }
        return null;
    }

    // todo double check this but should be fine
    public WearableDevice getWearableDeviceByID(String id) {
        if (isValidId(id)) {
            for (WearableDevice objToGetByID : wearableList) {
                if (objToGetByID.getId().contains(id)) {
                    return objToGetByID;
                }
            }
        }
        return null;
    }

    // Update methods

    public boolean updateSmartWatchByIndex(int indexToUpdate, SmartWatch detailsToUpdate) {
        //find the object by the index number
        WearableDevice getDevice = getWearableDeviceByIndex(indexToUpdate);
        //if the object exists, and it has found it, use the object passed in the parameters to
        //update the found object in the ArrayList.
        // todo check that i actually need this validation
        if (getDevice == null) {
            return false;
        } else if (getDevice instanceof SmartWatch) {
            getDevice.setSize(detailsToUpdate.getSize());
            getDevice.setPrice(detailsToUpdate.getPrice());
            getDevice.setManufacturerName(detailsToUpdate.getManufacturerName());
            getDevice.setMaterial(detailsToUpdate.getMaterial());
            getDevice.setModelName(detailsToUpdate.getModelName());
            ((SmartWatch) getDevice).setDisplayType(detailsToUpdate.getDisplayType());
            return true;
        }
        //if the object was not found, return false, indicating that the update was not successful
        return false;
    }

    public boolean updateSmartWatchByID(String id, SmartWatch detailsToUpdate) {
        //find the object by the index number
        WearableDevice getDevice = getWearableDeviceByID(id);
        //if the object exists, and it has found it, use the object passed in the parameters to
        //update the found object in the ArrayList.
        // todo check that i actually need this validation
        if (getDevice == null) {
            return false;
        } else if (getDevice instanceof SmartWatch) {
            getDevice.setSize(detailsToUpdate.getSize());
            getDevice.setPrice(detailsToUpdate.getPrice());
            getDevice.setManufacturerName(detailsToUpdate.getManufacturerName());
            getDevice.setMaterial(detailsToUpdate.getMaterial());
            getDevice.setModelName(detailsToUpdate.getModelName());
            ((SmartWatch) getDevice).setDisplayType(detailsToUpdate.getDisplayType());
            return true;
        }
        //if the object was not found, return false, indicating that the update was not successful
        return false;
    }

    public boolean updateSmartBandByID(String id, SmartBand detailsToUpdate) {
        //find the object by the index number
        WearableDevice getDevice = getWearableDeviceByID(id);
        //if the object exists, and it has found it, use the object passed in the parameters to
        //update the found object in the ArrayList.
        // todo check that i actually need this validation
        if (getDevice == null) {
            return false;
        } else if (getDevice instanceof SmartBand) {
            getDevice.setSize(detailsToUpdate.getSize());
            getDevice.setPrice(detailsToUpdate.getPrice());
            getDevice.setManufacturerName(detailsToUpdate.getManufacturerName());
            getDevice.setMaterial(detailsToUpdate.getMaterial());
            getDevice.setModelName(detailsToUpdate.getModelName());
            ((SmartBand) getDevice).setHeartRateMonitor(detailsToUpdate.getHeartRateMonitor());
            return true;
        }
        //if the object was not found, return false, indicating that the update was not successful
        return false;
    }

    public boolean updateSmartBandByIndex(int indexToUpdate, SmartBand detailsToUpdate) {
        //find the object by the index number
        WearableDevice getDevice = getWearableDeviceByIndex(indexToUpdate);
        //if the object exists, and it has found it, use the object passed in the parameters to
        //update the found object in the ArrayList.
        // todo check that i actually need this validation
        if (getDevice == null) {
            return false;
        } else if (getDevice instanceof SmartBand) {
            getDevice.setSize(detailsToUpdate.getSize());
            getDevice.setPrice(detailsToUpdate.getPrice());
            getDevice.setManufacturerName(detailsToUpdate.getManufacturerName());
            getDevice.setMaterial(detailsToUpdate.getMaterial());
            getDevice.setModelName(detailsToUpdate.getModelName());
            ((SmartBand) getDevice).setHeartRateMonitor(detailsToUpdate.getHeartRateMonitor());
            return true;
        }
        //if the object was not found, return false, indicating that the update was not successful
        return false;
    }


    //TODO - delete methods

    /**
     * Deletes WearableDevice object by index
     *
     * @param indexToDelete The index to delete
     * @return if the index is valid, returns the object that was deleted
     */
//    TODO check if this returns the object
    public WearableDevice deleteWearableDeviceByIndex(int indexToDelete) {
        if (isValidIndex(indexToDelete)) {
            return wearableList.remove(indexToDelete);
        }
        return null;
    }

    /**
     * Deletes WearableDevice object by ID.
     *
     * @param id The id of the object to delete
     * @return if the id is valid, returns the object that was deleted. Or returns null
     */
    public WearableDevice deleteWearableDeviceById(String id) {
        if (isValidId(id)) {
            for (WearableDevice objToDeleteByID : wearableList) {
                if (objToDeleteByID.getId().contains(id)) {
                    wearableList.remove(objToDeleteByID);
                    return objToDeleteByID;
                }
            }
        }
        return null;
    }


    // Validation Methods

    /*
    NOTE - I have changed the return value of this from the provided code. I feel that asking the question, isValidId?,
    should return true if it is valid, not false. It is not asking the question "isInvalidID?". It can still be used to check
    if the user entered an invalid id, or tries to make a new wearable device object with an invalid id by checking !isValidId.
     */

    /**
     * Checks if id does/does not exist in collection.
     * @param id id of WearableDevice object in wearableList
     * @return False if it already exists in the array (can't have duplicates of IDs), true if it does not exist yet.
     */
    public boolean isValidId(String id) {
        for (WearableDevice techDev : wearableList) {
            if (techDev.getId().equals(id)) return true;
        }
        return false;
    }

    /**
     * Checks if the index is valid against our wearableList
     *
     * @param index index of wearableList
     * @return true/false base on if the index of the list is valid.
     */
    public boolean isValidIndex(int index) {
        return (index >= 0) && (index < wearableList.size());
    }


    //TODO - sort methods
    // todo check this
    public void sortByPriceDescending() {
        for (int i = wearableList.size() - 1; i >= 0; i--) {
            int lowestIndex = 0;
            for (int j = 0; j <= i; j++) {
                if (wearableList.get(j).getPrice() < wearableList.get(lowestIndex).getPrice()) {
                    lowestIndex = j;
                }
            }
            swapWearableDevice(wearableList, i, lowestIndex);
        }
    }

    public void sortByPriceAscending() {
        for (int i = wearableList.size() - 1; i >= 0; i--) {
            int highestIndex = 0;
            for (int j = 0; j <= i; j++) {
                if (wearableList.get(j).getPrice() > wearableList.get(highestIndex).getPrice()) {
                    highestIndex = j;
                }
            }
            swapWearableDevice(wearableList, i, highestIndex);
        }
    }

    public void sortByPriceAscending(List<WearableDevice> listToSort) {
        for (int i = listToSort.size() - 1; i >= 0; i--) {
            int highestIndex = 0;
            for (int j = 0; j <= i; j++) {
                if (listToSort.get(j).getPrice() > listToSort.get(highestIndex).getPrice()) {
                    highestIndex = j;
                }
            }
            swapWearableDevice(listToSort, i, highestIndex);
        }
    }

    /**
     * Selection sort algorithm for  sorting the arraylist of devices by model name ascending.
     */
    public void sortProductsByModelNameAscending()
    {
        for (int i = wearableList.size() -1; i >= 0; i--)
        {
            int highestIndex = 0;
            for (int j = 0; j <= i; j++)
            {
                if (wearableList.get(j).getModelName().compareTo(wearableList.get(highestIndex).getModelName()) > 0) {
                    highestIndex = j;
                }
            }
            swapWearableDevice(wearableList, i, highestIndex);
        }
    }

    // todo check this warning
    private void swapWearableDevice(List<WearableDevice> devices, int i, int j) {
        WearableDevice intOriginal = devices.get(i);
        WearableDevice intToSwap = devices.get(j);

        devices.set(i, intToSwap);
        devices.set(j, intOriginal);
    }


    //TODO Top 5 methods

    public List<WearableDevice> topFiveMostExpensiveWearableDevices() {
        List<WearableDevice> listOfTopFive = new ArrayList<>();
        List<WearableDevice> newWearableListToSort = new ArrayList<>();
        if (!isSortedAscending(wearableList)) {
            newWearableListToSort = wearableList;
            sortByPriceAscending(newWearableListToSort);
        }
        for (int i = newWearableListToSort.size()-1; i >= newWearableListToSort.size() - 5; i--) {
            listOfTopFive.add(newWearableListToSort.get(i));
        }
        return listOfTopFive;
    }

    public boolean isSortedAscending(List<WearableDevice> deviceList){
        for(int i=0; i< deviceList.size()-1; i++) {
            //in a sorted array current number should be
            //always less than the next number in the array
            if(deviceList.get(i).getPrice() > deviceList.get(i+1).getPrice()) {
                return false;
            }
        }
        return true;
    }
    // Search Methods

    /**
     * This method returns a string containing all devices whose device paramater passed in contained (regardless of case)
     * the search string.  The index location of the device in the arraylist is also returned. This saves having a method for each
     * string paramater of Wearable Device to run a search on.
     *
     * @param paramToSearch The string to search by
     * @return products whose product name contains the search string
     */

//   Curious attempt to make below code DRY
//    private String simplifyDeviceAndSearchString(WearableDevice deviceString ,String searchQuery) {
//        return deviceString.toUpperCase().contains(searchQuery.toUpperCase());
//    }


    // Might need to make these separate functions.
    public String searchByDeviceParam(String paramToSearch, String searchQuery) {
        boolean queryToCheck = false;
        String matchingDevices = "";
//        System.out.println(paramToSearch);
        for (WearableDevice device : wearableList) {
            switch (paramToSearch) {
                case "size":
                    queryToCheck = device.getSize().toUpperCase().contains(searchQuery.toUpperCase());
                    break;
                case "manufacturer name":
                    queryToCheck = device.getManufacturerName().toUpperCase().contains(searchQuery.toUpperCase());
                    break;
                case "material":
                    queryToCheck = device.getMaterial().toUpperCase().contains(searchQuery.toUpperCase());
                    break;
                case "id":
                    queryToCheck = device.getId().toUpperCase().contains(searchQuery.toUpperCase());
                    break;
                case "model name":
                    queryToCheck = device.getModelName().toUpperCase().contains(searchQuery.toUpperCase());
            }
            if (queryToCheck) {
                matchingDevices += wearableList.indexOf(device) + ": " + device + "\n";
            }
        }

        if (matchingDevices.isEmpty()) {
            return "No devices match your search";
        } else {
            return matchingDevices;
        }
    }


    // TODO Persistence methods

    /**
     * The save method uses the XStream component to write all the objects in the posts ArrayList
     * to the posts.xml file stored on the hard disk.
     *
     * @throws Exception An exception is thrown if an error occurred during the save e.g. drive is full.
     */
    @Override
    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(fileName()));
        out.writeObject(wearableList);
        out.close();
    }

    /**
     * The load method uses the XStream component to read all the models.MessagePost objects from the posts.xml
     * file stored on the hard disk.  The read objects are loaded into the posts ArrayList
     *
     * @throws Exception An exception is thrown if an error occurred during the load e.g. a missing file.
     */
    @Override
    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{SmartBand.class, SmartWatch.class, WearableDevice.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(fileName()));
        wearableList = (List<WearableDevice>) in.readObject();
        in.close();

    }

    /**
     * @return a str with the file name
     */
    public String fileName() {
        return "wearableDevices.xml";
    }


}
