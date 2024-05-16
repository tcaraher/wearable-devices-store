package main;

import controllers.WearableDeviceAPI;


import models.SmartBand;
import models.SmartWatch;
import models.WearableDevice;
import utils.ScannerInput;
import utils.Utilities;

//todo check the no new line thing on the toStrings
public class Driver {


    private WearableDeviceAPI wearableAPI = new WearableDeviceAPI();

    public static void main(String[] args) throws Exception {
        new Driver().start();
    }

    public void start() {
        //TODO - construct fields HUHH

        //TODO - load all data once the serializers are set up???
        loadDevices();
        runMainMenu();
    }
//TODO - construct menus

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

            //pause the program so that the user can read what we just printed to the terminal window
            ScannerInput.readNextLine("\nPress enter key to continue...");

            //display the main menu again
            option = mainMenu();
        }
        exitApp();
    }


    //------------------------------------------------------------------------------------------
    //  Option 1 - Wearable Device CRUD menu
    //
    //------------------------------------------------------------------------------------------

    private void runWearableDeviceCrudMenu() {
        int option = ScannerInput.readNextInt("""
                WearableDevice Store Menu
                    1) Add a Wearable Device
                    2) Delete a Wearable Device
                    3) List all Wearable Devices
                    4) Update Wearable Device
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
    //  Option 1(of Wearable Device CRUD menu) - Add a wearable device
    //------------------------------------------------------------------------------------------


    public void addWearableDevice(){
        boolean isAdded = false;

        int option = ScannerInput.readNextInt("""
                    ---------------------------
                    |   1) Add a Smart Band  |
                    |   2) Add a Smart Watch |
                    ---------------------------
                    ==>> """);

        switch (option) {
            case 1 -> {
                // todo make these and the update one DRY
                String size = ScannerInput.readNextLine("Enter the size of the wearable: ");
                double price = ScannerInput.readNextDouble("Enter the price: ");
                String manufacturerName = ScannerInput.readNextLine("Enter the manufacturer name: ");
                String material = ScannerInput.readNextLine("Enter the material: ");
                String modelName = ScannerInput.readNextLine("Enter the model name: ");
                String id = ScannerInput.readNextLine("Enter the unique ID of the device: ");
                char hasHeartRateMonitor = ScannerInput.readNextChar("Does this model have a heart rate monitor (y/n)?: ");
                boolean heartRateMonitor = Utilities.YNtoBoolean(hasHeartRateMonitor);
                isAdded = wearableAPI.addWearableDevice(new SmartBand(size, price,manufacturerName,material,modelName,id,heartRateMonitor));
            }
            case 2 -> {
                String size = ScannerInput.readNextLine("Enter the size of the wearable: ");
                double price = ScannerInput.readNextDouble("Enter the price: ");
                String manufacturerName = ScannerInput.readNextLine("Enter the manufacturer name: ");
                String material = ScannerInput.readNextLine("Enter the material: ");
                String modelName = ScannerInput.readNextLine("Enter the model name: ");
                String id = ScannerInput.readNextLine("Enter the unique ID of the device: ");
                String displayType = ScannerInput.readNextLine("Enter the type of display:");
                isAdded = wearableAPI.addWearableDevice(new SmartWatch(size, price,manufacturerName,material,modelName,id,displayType));
            }
            default -> System.out.println("Invalid option entered: " + option);
        }

        if (isAdded){
            System.out.println("Device Added Successfully");
        }
        else{
            System.out.println("No Device Added");
        }
    }

    //------------------------------------------------------------------------------------------
    //  Option 2(of Wearable Device CRUD menu) - Delete a wearable device. If devices exist, print all devices and ask the user to input the index
    //              of the device they wish to delete.
    //------------------------------------------------------------------------------------------

    //todo add delete by id... easy extra
    public void deleteWearableDevice(){
        listAllWearableDevices();
        if (wearableAPI.numberOfWearableDevices() > 0){
            //only ask the user to choose the device to delete if devices exist
            int indexToDelete = ScannerInput.readNextInt("Enter the index of the device to delete ==> ");
            //pass the index for deleting and check for success.
            WearableDevice deviceToDelete = wearableAPI.deleteWearableDeviceByIndex(indexToDelete);
            if (deviceToDelete != null){
                System.out.println("Delete Successful! Deleted device: " + deviceToDelete);
            }
            else{
                System.out.println("Delete NOT Successful");
            }
        }
    }



    //------------------------------------------------------------------------------------------
    //  Option 4(of Wearable Device CRUD menu) - Update a wearable device. The user is asked if it is a watch band or smart watch,
    //  and the required details are then gathered before adding the specific object
    //------------------------------------------------------------------------------------------

    public void updateWearableDevice() {
        if (wearableAPI.numberOfWearableDevices() > 0) {
            boolean isUpdated = false;

            int option = ScannerInput.readNextInt("""
                    ---------------------------
                    |   1) Add a Smart Band  |
                    |   2) Add a Smart Watch |
                    ---------------------------
                    ==>> """);

            switch (option) {
                case 1 -> {
                    listAllWearableDevices();
                    if (wearableAPI.numberOfWearableDevices() > 0) {
                        int deviceIndexToUpdate = ScannerInput.readNextInt("Enter the index of the device to update ==> ");
                        if (wearableAPI.isValidIndex(deviceIndexToUpdate)) {
                            String size = ScannerInput.readNextLine("Enter the size of the wearable: ");
                            double price = ScannerInput.readNextDouble("Enter the price: ");
                            String manufacturerName = ScannerInput.readNextLine("Enter the manufacturer name: ");
                            String material = ScannerInput.readNextLine("Enter the material: ");
                            String modelName = ScannerInput.readNextLine("Enter the model name: ");
                            String id = ScannerInput.readNextLine("Enter the unique ID of the device: ");
                            char hasHeartRateMonitor = ScannerInput.readNextChar("Does this model have a heart rate monitor (y/n)?: ");
                            boolean heartRateMonitor = Utilities.YNtoBoolean(hasHeartRateMonitor);
                            isUpdated = wearableAPI.addWearableDevice(new SmartBand(size, price,manufacturerName,material,modelName,id,heartRateMonitor));
                        }
                    }
                }
                case 2 -> {
                    //ask the user to enter the index of the object to update, and assuming it's valid and is a PhotoPost,
                    //gather the new data from the user and update the selected object.
                    listAllWearableDevices();
                    if (wearableAPI.numberOfWearableDevices() > 0) {
                        int deviceIndexToUpdate = ScannerInput.readNextInt("Enter the index of the device to update: ");
                        if (wearableAPI.isValidIndex(deviceIndexToUpdate)) {
                            String size = ScannerInput.readNextLine("Enter the size of the wearable: ");
                            double price = ScannerInput.readNextDouble("Enter the price: ");
                            String manufacturerName = ScannerInput.readNextLine("Enter the manufacturer name: ");
                            String material = ScannerInput.readNextLine("Enter the material: ");
                            String modelName = ScannerInput.readNextLine("Enter the model name: ");
                            String id = ScannerInput.readNextLine("Enter the unique ID of the device: ");
                            String displayType = ScannerInput.readNextLine("Enter the type of display:");
                            isUpdated = wearableAPI.addWearableDevice(new SmartWatch(size, price,manufacturerName,material,modelName,id,displayType));
                        }
                    }
                }
                default -> System.out.println("Invalid option entered: " + option);
            }

            if (isUpdated) {
                System.out.println("Device Updated Successfully");
            } else {
                System.out.println("No Device Updated");
            }
        }
        else{
            System.out.println("No devices added yet");
        }
    }


    //------------------------------------------------------------------------------------------
    //  Option 2(of main menu) - Wearable Device CRUD menu
    //
    //------------------------------------------------------------------------------------------


    private void runReportsMenuLevel1() {
        int option = ScannerInput.readNextInt("""
                Reports Menu
                    1) Wearable Device Overview
                    O) Return to main menu
                     ==>> """);

        switch (option) {
            case 1 -> runReportsMenuLevel2();
            case 0 -> runMainMenu();
            default -> System.out.println("Invalid option entered: " + option);
        }
    }

    private void runReportsMenuLevel2() {
        int option = ScannerInput.readNextInt("""
                Wearable Device Reports Menu
                    1) List all technology
                    2) List all SmartBands
                    3) List all Smart watch
                    4) List all devices above a price
                    5) List all devices below a price
                    6) List the top five most expensive smart watches
                    7) List all devices for a chosen Manufacturer
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
            default -> System.out.println("Invalid option entered: " + option);
        }
    }

    //todo update methods counting methods


    //---------------------
    //  General Menu Items
    //---------------------

    //todo make this dryer - just one function and pass in option
    //------------------------------------------------------------------------------------------
    //  Option 1(of reports menu) and Option 3(of Wearable Device CRUD menu) - List all devices
    //------------------------------------------------------------------------------------------
    public void listAllWearableDevices(){
        System.out.println("List of all wearable devices: ");
        System.out.println(wearableAPI.listAllWearableDevices());
    }

    //------------------------------------------------------------------------------------------
    //  Option 2(of reports menu) - List all smart bands
    //------------------------------------------------------------------------------------------
    public void listAllWearableSmartBands(){
        System.out.println("List of all smart bands: ");
        System.out.println(wearableAPI.listAllSmartBands());
    }

    //------------------------------------------------------------------------------------------
    //  Option 3(of reports menu) - List all smart watches
    //------------------------------------------------------------------------------------------
    public void listAllWearableSmartWatches(){
        System.out.println("List of all smart watches: ");
        System.out.println(wearableAPI.listAllSmartWatches());
    }

    //------------------------------------------------------------------------------------------
    //  Option 4(of reports menu) - List all devices above a price
    //------------------------------------------------------------------------------------------
    public void listAllDevicesAbovePrice(){
        double price = ScannerInput.readNextDouble("View the devices costing more than this price:  ");
        System.out.println(wearableAPI.listAllWearableDeviceAbovePrice(price));
    }

    //------------------------------------------------------------------------------------------
    //  Option 5(of reports menu) - List all devices below a price
    //------------------------------------------------------------------------------------------
    public void listAllDevicesBelowPrice(){
        double price = ScannerInput.readNextDouble("View the devices costing less than this price:  ");
        System.out.println(wearableAPI.listAllWearableDeviceBelowPrice(price));
    }

    public void listTopFiveMostExpensive(){
        System.out.println("Top five most expensive are: ");
        System.out.println(wearableAPI.topFiveMostExpensiveWearableDevices());
    }
    public void listAllDevicesOfManufacturer(){
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

        switch (option) {
            case 1: manufacturerToSearch = "APPLE";
            case 2: manufacturerToSearch = "SAMSUNG";
            case 3: manufacturerToSearch = "Garmin";
            case 4: manufacturerToSearch = "FitBit";
            case 5: manufacturerToSearch = "Whoop";
            case 0: runReportsMenuLevel1();
        }

        System.out.println("List of all devices of chosen manufacturer: ");
        System.out.println(wearableAPI.listAllWearableDevicesByChosenManufacturer(manufacturerToSearch));
    }

//TODO - write all the methods that are called from your menu
    //---------------------
    //  Search/Sort
    //---------------------

// TODO search by different criteria i.e. look at the list methods and give options based on that.

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
            case 1: paramToSearch = "id";
            break;
            case 2: paramToSearch = "material";
            break;
            case 3: paramToSearch = "manufacturer name";
            break;
            case 4: paramToSearch = "model name";
            break;
            case 5: paramToSearch = "display type";
            break;
            case 0: runMainMenu();
        }

        String searchQuery = ScannerInput.readNextLine("Enter the " + paramToSearch + " you would like to search for: ");
        System.out.println("Here are all of the " + paramToSearch + " devices:");;
        System.out.println(wearableAPI.searchByDeviceParam(paramToSearch,searchQuery));;
    }

// TODO sort  (and give a list of options - not a recurring menu thou)

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
            case 0: runMainMenu();
        }
        System.out.println("Here is your sorted list by " + ": ");
        listAllWearableDevices();

    }







    //---------------------
    //  Helper Methods
    //---------------------

//TODO- write any helper methods that are required

    private void exitApp() {
        System.out.println("Exiting....");
        System.exit(0);
    }

    //load all the products into the store from a file on the hard disk
    private void loadDevices() {
        try {
            wearableAPI.load();
        } catch (Exception e) {
            System.err.println("Error reading from file: " + e);
        }
    }

    private void saveDevices() {
        try {
            wearableAPI.save();
        } catch (Exception e) {
            System.err.println("Error reading from file: " + e);
        }
    }

}

