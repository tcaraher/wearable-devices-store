package main;

import controllers.WearableDeviceAPI;


import models.SmartBand;
import models.SmartWatch;
import models.WearableDevice;
import utils.ScannerInput;
import utils.Utilities;

import java.util.HashMap;
import java.util.Map;

public class Driver {

    // Sets up API. All logic actually reporting and making changes runs through the API. This driver class only handles user in/out.
    private WearableDeviceAPI wearableAPI = new WearableDeviceAPI("wearableDevices.xml");

    public static void main(String[] args) throws Exception {
        new Driver().start();
    }

    public void start() {
        // Load devices so user has saved devices on run
        loadDevices();
        // run menu
        runMainMenu();
    }

    //------------------------------------------------------------------------------------------
    // ******************** MENUS ********************
    //------------------------------------------------------------------------------------------

    // Main menu and run
    private int mainMenu() {
        return ScannerInput.readNextInt("""
                Wearable Devices Store          
                   -----------------------
                   1) WearableDevice CRUD MENU
                   2) Reports MENU
                   -----------------------
                   4) Search WearableDevice Devices
                   5) Sort WearableDevice Device.
                   -----------------------
                   10) Save all
                   11) Load all
                   -----------------------
                   0) Exit
                ==>>  """);
    }

    private void runMainMenu() {
        int option = mainMenu();
        while (option != 0) {
            switch (option) {
                case 1 -> runWearableDeviceCrudMenu();
                case 2 -> runReportsMenuLevel1();
                case 4 -> searchWearableDevices();
                case 5 -> sortWearableDevices();
                case 10 -> saveDevices();
                case 11 -> loadDevices();
                default -> System.out.println("Invalid option entered: " + option);
            }

            ScannerInput.readNextLine("\nPress enter key to continue...");

            //display the main menu again
            option = mainMenu();
        }
        exitApp();
    }

    //------------------------------------------------------------------------------------------
    // Wearable Device CRUD menu (option 1)
    //------------------------------------------------------------------------------------------

    private void runWearableDeviceCrudMenu() {
        int option = ScannerInput.readNextInt("""
                WearableDevice Store Menu
                ------------------------------
                   1) Add a Wearable Device
                   2) Delete a Wearable Device
                   3) List all Wearable Devices
                   4) Update Wearable Device
                    -----------------------------
                    O) Return to main menu
                     ==>> """);

        switch (option) {
            case 1 -> addWearableDevice();
            case 2 -> deleteWearableDevice();
            case 3 -> listAllWearableDevices();
            case 4 -> updateWearableDevice();
            case 0 -> runMainMenu();
            default -> System.out.println("Invalid option entered: " + option);
        }

        exitApp();
    }


    //------------------------------------------------------------------------------------------
    //  Option 2(of main menu) - Reports menu
    //------------------------------------------------------------------------------------------


    private void runReportsMenuLevel1() {
        int option = ScannerInput.readNextInt("""
                Reports Menu
                    1) Wearable Device Overview
                    -----------------------------
                    O) Return to main menu
                     ==>> """);

        switch (option) {
            case 1 -> runReportsMenuLevel2();
            case 0 -> runMainMenu();
            default -> System.out.println("Invalid option entered: " + option);
        }
    }

    //------------------------------------------------------------------------------------------
    //  Option 1(of reports menu level 1) - Wearable Device Reports menu.
    //
    //------------------------------------------------------------------------------------------

    private void runReportsMenuLevel2() {
        int option = ScannerInput.readNextInt("""
                Wearable Device Reports Menu
                    1) List all technology
                    2) List all Smart Bands
                    3) List all Smart Watches
                    4) List all devices above a price
                    5) List all devices below a price
                    6) List the top five most expensive smart watches
                    7) List all devices for a chosen Manufacturer
                    --------------------------------------------------
                    0) Return to main menu
                         ==>> """);

        switch (option) {
            case 1 -> listAllWearableDevices();
            case 2 -> listAllWearableSmartBands();
            case 3 -> listAllWearableSmartWatches();
            case 4 -> listAllDevicesAbovePrice();
            case 5 -> listAllDevicesBelowPrice();
            case 6 -> listTopFiveMostExpensive();
            case 7 -> listAllDevicesOfManufacturer();
            case 0 -> runMainMenu();
            default -> System.out.println("Invalid option entered: " + option);
        }
    }

    //todo update methods counting methods

    //------------------------------------------------------------------------------------------
    //  ******************** Add, Delete, Update Methods ********************
    //------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------
    // Option 1 (of CRUD menu) - Adds a wearable device.
    // User chooses whether to add a smart band or smart watch, it requests the relevant info from the user and runs the addWearableDevice
    // function in the api.
    //------------------------------------------------------------------------------------------
    public void addWearableDevice() {
        boolean isAdded = false;

        int option = ScannerInput.readNextInt("""
                ---------------------------
                   1) Add a Smart Band
                |   2) Add a Smart Watch
                 
                |   0) Back to CRUD menu
                ---------------------------
                ==>> """);

        switch (option) {
            case 1 -> {
                // Uses helper method that return a HashMap, getBaseWearableDeviceDetailsFromUser(). Asks the user the base info in the parent Wearable Device Field. See method's comment.
                Map<String, String> deviceDetails = new HashMap<>(getBaseWearableDeviceDetailsFromUser());
                // Gets the specific info required for the relevant class, in this case SmartBand requires a heart rate monitor field.
                char hasHeartRateMonitor = ScannerInput.readNextChar("Does this model have a heart rate monitor (y/n)?: ");
                boolean heartRateMonitor = Utilities.YNtoBoolean(hasHeartRateMonitor);
                // Parse each key value pair returned from the deviceDetails hash map.
                isAdded = wearableAPI.addWearableDevice(new SmartBand(deviceDetails.get("stringSize"), Double.parseDouble(deviceDetails.get("doublePrice")), deviceDetails.get("stringManufacturerName"), deviceDetails.get("stringMaterial"), deviceDetails.get("stringModelName"), deviceDetails.get("stringId"), heartRateMonitor));
            }
            case 2 -> {
                Map<String, String> deviceDetails = new HashMap<>(getBaseWearableDeviceDetailsFromUser());
                String displayType = ScannerInput.readNextLine("Enter the type of display:");
                isAdded = wearableAPI.addWearableDevice(new SmartWatch(deviceDetails.get("stringSize"), Double.parseDouble(deviceDetails.get("doublePrice")), deviceDetails.get("stringManufacturerName"), deviceDetails.get("stringMaterial"), deviceDetails.get("stringModelName"), deviceDetails.get("stringId"), displayType));
            }
            case 0 -> runWearableDeviceCrudMenu();

            default -> System.out.println("Invalid option entered: " + option);
        }

        if (isAdded) {
            System.out.println("Device Added Successfully");
        } else {
            System.out.println("No Device Added");
        }
        runMainMenu();
    }

    //------------------------------------------------------------------------------------------
    //  Option 2 (of CRUD menu) - Delete wearable device - Delete a wearable device. If devices exist, print all devices
    //  and ask the user to input the index of the device they wish to delete.
    //------------------------------------------------------------------------------------------

    // Deletes a device via index. Asks user to pick the object from the list presented, and deletes it with the API method, which also returns the deleted device.
    //todo add delete by id... easy extra
    public void deleteWearableDevice() {
        if (wearableAPI.numberOfWearableDevices() > 0) {
            listAllWearableDevices();
            //only ask the user to choose the device to delete if devices exist
            int indexToDelete = ScannerInput.readNextInt("Enter the index of the device to delete ==> ");
            //pass the index for deleting and check for success.
            WearableDevice deviceToDelete = wearableAPI.deleteWearableDeviceByIndex(indexToDelete);
            if (deviceToDelete != null) {
                System.out.println("Delete Successful! Deleted device: " + deviceToDelete);
            } else {
                System.out.println("Delete NOT Successful");
            }
        } else {
            System.out.println("No devices");
        }
        runMainMenu();
    }

    //------------------------------------------------------------------------------------------
    //  Option 4 (of CRUD menu) - Update a wearable device. The user is asked if it is a watch band or smart watch they would like to update, either by ID or index,
    //  then presented with a list of all devices of that class. The required details are then gathered before updating the specific object with the API.
    //  Each case uses validation to check the inputted index or id, and whether there are any devices of that class to display. Also informs the user of
    //  the validation if it fails.
    //
    //  Also it uses the same getBaseWearableDeviceDetailsFromUser() helper method as before to collect the Wearable Device fields.
    //------------------------------------------------------------------------------------------

    public void updateWearableDevice() {
        if (wearableAPI.numberOfWearableDevices() > 0) {
            boolean isUpdated = false;

            int option = ScannerInput.readNextInt("""
                    ---------------------------------------
                    |   1) Update a Smart Band By Index   |
                    |   2) Update a Smart Band By ID      |
                    |   3) Update a Smart Watch By Index  |
                    |   4) Update a Smart Watch By ID     |
                    ---------------------------------------
                    ==>> """);

            switch (option) {
                case 1 -> {
                    listAllWearableSmartBands();
                    if (wearableAPI.numberOfSmartBands() > 0) {
                        int deviceIndexToUpdate = ScannerInput.readNextInt("Enter the index of the device to update ==> ");
                        if (wearableAPI.isValidIndex(deviceIndexToUpdate)) {
                            Map<String, String> deviceDetails = new HashMap<>(getBaseWearableDeviceDetailsFromUser());
                            char hasHeartRateMonitor = ScannerInput.readNextChar("Does this model have a heart rate monitor (y/n)?: ");
                            // Sets the char to a boolean via the util
                            boolean heartRateMonitor = Utilities.YNtoBoolean(hasHeartRateMonitor);
                            isUpdated = wearableAPI.updateSmartBandByIndex(deviceIndexToUpdate, new SmartBand(deviceDetails.get("stringSize"), Double.parseDouble(deviceDetails.get("doublePrice")), deviceDetails.get("stringManufacturerName"), deviceDetails.get("stringMaterial"), deviceDetails.get("stringModelName"), deviceDetails.get("stringId"), heartRateMonitor));
                        } else System.out.println("Not a valid index.");
                    } else {
                        System.out.println("No Smart Bands added yet");
                    }
                }
                case 2 -> {
                    listAllWearableSmartBands();
                    if (wearableAPI.numberOfSmartBands() > 0) {
                        String deviceIDToUpdate = ScannerInput.readNextLine("Enter the id of the device to update ==> ");
                        if (wearableAPI.isValidId(deviceIDToUpdate)) {
                            Map<String, String> deviceDetails = new HashMap<>(getBaseWearableDeviceDetailsFromUser());
                            char hasHeartRateMonitor = ScannerInput.readNextChar("Does this model have a heart rate monitor (y/n)?: ");
                            // Sets the char to a boolean via the util
                            boolean heartRateMonitor = Utilities.YNtoBoolean(hasHeartRateMonitor);
                            isUpdated = wearableAPI.updateSmartBandByID(deviceIDToUpdate, new SmartBand(deviceDetails.get("stringSize"), Double.parseDouble(deviceDetails.get("doublePrice")), deviceDetails.get("stringManufacturerName"), deviceDetails.get("stringMaterial"), deviceDetails.get("stringModelName"), deviceDetails.get("stringId"), heartRateMonitor));
                        } else System.out.println("Not a valid ID.");
                    } else {
                        System.out.println("No Smart Bands added yet");
                    }
                }
                case 3 -> {
                    listAllWearableSmartWatches();
                    if (wearableAPI.numberOfSmartWatches() > 0) {
                        int deviceIndexToUpdate = ScannerInput.readNextInt("Enter the index of the device to update: ");
                        if (wearableAPI.isValidIndex(deviceIndexToUpdate)) {
                            Map<String, String> deviceDetails = new HashMap<>(getBaseWearableDeviceDetailsFromUser());
                            String displayType = ScannerInput.readNextLine("Enter the type of display:");
                            isUpdated = wearableAPI.updateSmartWatchByIndex(deviceIndexToUpdate, new SmartWatch(deviceDetails.get("stringSize"), Double.parseDouble(deviceDetails.get("doublePrice")), deviceDetails.get("stringManufacturerName"), deviceDetails.get("stringMaterial"), deviceDetails.get("stringModelName"), deviceDetails.get("stringId"), displayType));
                        } else System.out.println("Not a valid index.");
                    } else {
                        System.out.println("No Smart Watches added yet");
                    }
                }
                case 4 -> {
                    listAllWearableSmartWatches();
                    if (wearableAPI.numberOfSmartWatches() > 0) {
                        String deviceIDToUpdate = ScannerInput.readNextLine("Enter the id of the device to update: ");
                        if (wearableAPI.isValidId(deviceIDToUpdate)) {
                            Map<String, String> deviceDetails = new HashMap<>(getBaseWearableDeviceDetailsFromUser());
                            String displayType = ScannerInput.readNextLine("Enter the type of display:");
                            isUpdated = wearableAPI.updateSmartWatchByID(deviceIDToUpdate, new SmartWatch(deviceDetails.get("stringSize"), Double.parseDouble(deviceDetails.get("doublePrice")), deviceDetails.get("stringManufacturerName"), deviceDetails.get("stringMaterial"), deviceDetails.get("stringModelName"), deviceDetails.get("stringId"), displayType));
                        } else System.out.println("Not a valid ID.");
                    } else {
                        System.out.println("No Smart Watches added yet");
                    }
                }
                default -> System.out.println("Invalid option entered: " + option);
            }

            if (isUpdated) {
                System.out.println("Device Updated Successfully");
            } else {
                System.out.println("No Device Updated");
            }
        } else {
            System.out.println("No devices added yet");
        }
        runMainMenu();
    }

    //------------------------------------------------------------------------------------------
    //  ******************** List Methods/Reports menu options ********************
    //------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------
    //  Option 1(of reports menu) and Option 3(of Wearable Device CRUD menu) - List all devices
    //------------------------------------------------------------------------------------------
    public void listAllWearableDevices() {
        System.out.println("List of all wearable devices: ");
        System.out.println(wearableAPI.listAllWearableDevices());
    }

    //------------------------------------------------------------------------------------------
    //  Option 2(of reports menu) - List all smart bands
    //------------------------------------------------------------------------------------------
    public void listAllWearableSmartBands() {
        System.out.println("List of all smart bands: ");
        System.out.println(wearableAPI.listAllSmartBands());
    }

    //------------------------------------------------------------------------------------------
    //  Option 3(of reports menu) - List all smart watches
    //------------------------------------------------------------------------------------------
    public void listAllWearableSmartWatches() {
        System.out.println("List of all smart watches: ");
        System.out.println(wearableAPI.listAllSmartWatches());
    }

    //------------------------------------------------------------------------------------------
    //  Option 4(of reports menu) - List all devices above a price
    //------------------------------------------------------------------------------------------
    public void listAllDevicesAbovePrice() {
        double price = ScannerInput.readNextDouble("View the devices costing more than this price:  ");
        System.out.println(wearableAPI.listAllWearableDeviceAbovePrice(price));
    }

    //------------------------------------------------------------------------------------------
    //  Option 5(of reports menu) - List all devices below a price
    //------------------------------------------------------------------------------------------
    public void listAllDevicesBelowPrice() {
        double price = ScannerInput.readNextDouble("View the devices costing less than this price:  ");
        System.out.println(wearableAPI.listAllWearableDeviceBelowPrice(price));
    }

    //------------------------------------------------------------------------------------------
    //  Option 6(of reports menu) - List top five most expensive devices
    //------------------------------------------------------------------------------------------
    public void listTopFiveMostExpensive() {
        System.out.println("Top five most expensive are: ");
        System.out.println(wearableAPI.topFiveMostExpensiveWearableDevices());
    }

    //------------------------------------------------------------------------------------------
    //  Option 7(of reports menu) - List all devices of a chosen manufacturer.
    //  Asks user for the manufacturer they would like to list and returns same. Switch statement
    //  sets the local variable manufacturerToSearch to the users choice.
    //------------------------------------------------------------------------------------------
    public void listAllDevicesOfManufacturer() {
        String manufacturerToSearch = "";

        int option = ScannerInput.readNextInt("""
                --------------------------------
                | Please Select a manufacturer: |
                |   1) APPLE                    |
                |   2) SAMSUNG                  |
                |   3) Garmin                   |
                |   4) FitBit                   |
                |   5) Whoop                    |
                |   0) Go Back                  |
                ---------------------------
                ==>> """);

        // Sets variable to the user choice.
        switch (option) {
            case 1:
                manufacturerToSearch = "APPLE";
            case 2:
                manufacturerToSearch = "SAMSUNG";
            case 3:
                manufacturerToSearch = "Garmin";
            case 4:
                manufacturerToSearch = "FitBit";
            case 5:
                manufacturerToSearch = "Whoop";
            case 0:
                runReportsMenuLevel1();
        }

        if (wearableAPI.numberOfWearableDeviceByChosenManufacturer(manufacturerToSearch) > 0) {
            System.out.println("List of all devices of chosen manufacturer: ");
            // Passes in the chosen manufacturer to the API method.
            System.out.println(wearableAPI.listAllWearableDevicesByChosenManufacturer(manufacturerToSearch));
        } else {
            System.out.println("There are no devices of manufacturer, " + manufacturerToSearch + ".");
        }
    }

    //------------------------------------------------------------------------------------------
    //  ******************** Search/Sort Methods ********************
    //------------------------------------------------------------------------------------------

    //------------------------------------------------------------------------------------------
    // Search Wearable Devices (option 4 of main menu)
    // Asks the user for the parameter to search by, then asks the user for a string with the query.
    //------------------------------------------------------------------------------------------
    public void searchWearableDevices() {
        String paramToSearch = "";

        int option = ScannerInput.readNextInt("""
                ---------------------------
                | Search by:             |
                |   1) ID                |
                |   2) Material          |
                |   3) Manufacturer Name |
                |   4) Model Name        |
                |   5) Display Type      |
                |   0) Main Menu         |
                ---------------------------
                ==>> """);

        switch (option) {
            case 1:
                paramToSearch = "id";
                break;
            case 2:
                paramToSearch = "material";
                break;
            case 3:
                paramToSearch = "manufacturer name";
                break;
            case 4:
                paramToSearch = "model name";
                break;
            case 5:
                paramToSearch = "display type";
                break;
            case 0:
                runMainMenu();
        }

        String searchQuery = ScannerInput.readNextLine("Enter the " + paramToSearch + " you would like to search for: ");
        System.out.println("Here are all of the " + paramToSearch + " devices:");
        ;
        // Passes the requested paramater to search by and the query in to the API method, searchByDeviceParam.
        System.out.println(wearableAPI.searchByDeviceParam(paramToSearch, searchQuery));
        ;
    }

    //------------------------------------------------------------------------------------------
    // Sort Devices (opt 5 of main menu). Sorts wearable devices based on user request. As will all methods in Driver, each method from the api
    // handles the logic for sorting.
    //------------------------------------------------------------------------------------------

    public void sortWearableDevices() {
        String searchSelection = "";

        int option = ScannerInput.readNextInt("""
                ----------------------------------
                | Sort all wearable devices by:  |
                |   1) Price Ascending           |
                |   2) Price Descending          |
                |   3) Model Name Alphabetically |
                |   0) Main Menu                 |
                ---------------------------------
                ==>> """);

        switch (option) {
            case 1:
                searchSelection = "price ascending";
                wearableAPI.sortByPriceAscending();
                break;
            case 2:
                searchSelection = "price descending";
                wearableAPI.sortByPriceDescending();
                break;
            case 3:
                searchSelection = "model name alphabetically";
                wearableAPI.sortProductsByModelNameAscending();
                break;
            case 0:
                runMainMenu();
        }
        // Construct string based on user option
        System.out.println("Here is your sorted list by " + searchSelection + ": ");
        // Lists all the devices in the new order.
        listAllWearableDevices();

    }


    //------------------------------------------------------------------------------
    //  ******************* Helper Methods ******************************
    //------------------------------------------------------------------------------


    // Gets parent class WearableDevice base information from the user. Helps make all of the update and add methods
    // dry by not having to rewrite these statements multiple times.
    // Returns HashMap to easily query the correct values in the relevant constructors.
    private Map<String, String> getBaseWearableDeviceDetailsFromUser() {
        Map<String, String> deviceDetails = new HashMap<>();
        String size = ScannerInput.readNextLine("Enter the size of the wearable: ");
        deviceDetails.put("stringSize", size);
        double price = ScannerInput.readNextDouble("Enter the price: ");
        deviceDetails.put("doublePrice", Double.toString(price));
        String manufacturerName = ScannerInput.readNextLine("Enter the manufacturer name: ");
        deviceDetails.put("stringManufacturerName", manufacturerName);
        String material = ScannerInput.readNextLine("Enter the material: ");
        deviceDetails.put("stringMaterial", material);
        String modelName = ScannerInput.readNextLine("Enter the model name: ");
        deviceDetails.put("stringModelName", modelName);
        String id = ScannerInput.readNextLine("Enter the unique ID of the device: ");
        deviceDetails.put("stringId", id);
        return deviceDetails;
    }

    // Exits app
    private void exitApp() {
        System.out.println("Exiting....");
        System.exit(0);
    }

    //Load all the products into the wearable device store from a file on the hard disk
    private void loadDevices() {
        try {
            wearableAPI.load();
        } catch (Exception e) {
            System.err.println("Error reading from file: " + e);
        }
    }

    //Save all the products into the wearable device store from a file on the hard disk

    private void saveDevices() {
        try {
            wearableAPI.save();
        } catch (Exception e) {
            System.err.println("Error reading from file: " + e);
        }
    }

}

