/* readme.txt

******************

Name : Tom Caraher

Student Number: 20108883

Part A - (models) Inheritance Hierarchy (24 / 24)
-----------------------------

/src/models/WearableDevice
WearableDevices (10/10)
Line 14 - Abstract class set up
Lines 17-22 All required fields for parent Class
Line 28 - constructor with all validation from spec
Lines 50 - 111 - All setters and getters with validation -
    ex: line 97 uses Utitlities.validStringLength to check for max string length. It will not update if it doesn't pass that validation.
    ex: line 76 uses isValidMenuName to check that it is a valid menu name
Lines 126 - 140 - Equals, hashCode and toString with all fields including the return values of the abstract methods, so as to not need them in each child class.
Lines 117 and 119 - Abstract methods connectToInternet and getInsurancePremium

/src/models/SmartWatch
SmartWatch( 7 / 7)
Line 15 - Constructor calling the super (parent class constructor) to set all fields, including the child class' displayType field if it is a valid display type
Lines 26 and 30 - getter and setter for display type
Lines 41 and 46 - Abstract methods implemented to spec
Line 51 - toString adding in a SmartWatch identifier and the displayType field, along with the super(parent class' toString).toString

/src/models/SmartBand
SmartBand ( 7 / 7)
Line 17 - Constructor calling super with added hearRateMon field
Lines 26-34 - setters and getters for heartRateMonitor
Lines 43 - 38 - Abstract methods implemented to spec
Line 57 - toString with parent class toString implemented



Part B - (controllers) TechnologyDeviceAPI  (34 /34)
-----------------------------
All code for this section in /src/controllers/WearableDeviceAPI

basic CRUD (13 /13 ) - all methods built to spec
Line 51 - addWearableDevice adds a new device object to the wearableList
Lines 93 and 120 - updates SmartWatch/Band by id, also have by index feature as mentioned in extra credit section
Lines 180 and 192 - Deletes wearable device by index or id
Lines 212,224 - gets wearable device by index or id

reporting/numberOf methods (8 / 8)
Line 248 - number of all devices
Line 256 - num of smart bands
Line 270 - num of smart watches
Line 285 - num of wearable devices by chosen manufacturer
Line 305 - List all devices
Line 323 - List all smart bands
Line 343 - List all smart watches
Line 364 - List all wearables by chosen manufacturer
Line 383 - List devices above a price
Line 401 - list devices below a price

validation methods  & persistence  (5 / 5)
Line 430 and 443 - is valid id and valid index implemented here.
Line 650 and 664 - save and load methods set up

sorting / top 5 (8/ 8)
Lines 455 and 470 - sorts by price ascending and descending, using selection sorting
Line 523 - swap wearable device helper method for selection sort
Line 542 and 563 - top five most expensive wearable devices and smart watches




Part C - UX (user experience) and Driver:Mark (23 /23)
-----------------------------
All methods include good validation and user feedback, for example checking that what is entered is a valid index,
or checking that there are smart bands to return etc..

Good Menu Structure  (4 / 4)
I implemented the same menu structure as in the spec. With the same sub menus etc..
Line 36  and 53 - main menu and run main menu
Line 78 - CRUD menu
Lines 108 and 128 - Report and Reports menu

ArrayListCRUD  - all  types handled  (7 / 7) -
Line 164 - adds a wearable device by asking user if they want to add a smart watch or band.
Line 211 - deletes a wearable device
Line 238 - Updates a device - Asks user for what and how they would like to update

Reports Menu - for all wearables (7 / 7)
Lines 329,339 and 349 - Lists and devices, smart bands, or smartwatches
Line 359 and 369 - lists all devices above/below price
Line 391 - list all devices of chosen manufacturer by asking the user which to pick

Search, Sort, top5   (4 / 4)
Line 379 - top five most expensive
Line 439 - search Wearable devices by asking which (of any string field type) to search.
            Logic for it is in WearableDeviceAPI line 607
Line 487 - Sorts wearable devices by asking the user for how - price ascending/descending or by model name (extra)

Save, Load, Exit   (1 / 1)
Lines 550 - 572: exit, save and load




Part D - DX (Overall style) (11%)	 (11 /11)
-----------------------------
Code correctly commented (3 / 3)
Yes. Including nice headings for each section. I feel the code is thoroughly and consistently commented when needed
Standard naming, indentation, DRY Code etc. (5 / 5)
Everything has been formatted via IntelliJ's autoformatting. I feel naming conventions are consistent.
I made an effort to make the code as DRY as possible, for example - my getBaseWearableDeviceDetailsFromUser() highlighted in extra credit
Javadoc written for WearableDeviceAPI  (3 / 3)
Yes

Part E - For Extra Credit (10%)  (7 / 10)
-----------------------------
Driver -
1. Line 486 - Gives option to sort by model name alphabetically, logic for it is on line 502 of the API
2. Line 532 - getBaseWearableDeviceDetailsFromUser(). Returns a hash map of each field for the base constructor.
            So the method calling it doesn't have to write all of the code multiple times - as I use it in the add and update methods
3. Line 586 - isSortedAscending - checks if the list passed in is already sorted

API -
4. Line 607 - Search by device parameter. This is ran in line 477 of driver. Takes any field type to search and searches for it!
5. Line 65 and 148 - Updates smart watch or band by index, in addition to id
6. Line 364 - listAllWearableDevicesByChosenManufacturer. Not included in spec but added it
7. Full git repo and commit history at https://github.com/tcaraher/wearable-devices-store


Part F - Reflection (8 / 8)
-----------------------------
Filled out parts A - E above (3 / 3)

Main learnings from my engagement with assignment:
Much of my personal learning and practice around software development prior to undertaking this course has been haphazard and unstructured, often leading to code that is not thorough and well thought out -
    just get it working would have been my primary focus! But the (somewhat opinionated!) structures that OOP enforces really helped to consolidated my understanding in general, and to really make clear what
    needs to go in to a fully reliable and scalable application.

Mandatory : PLease list any references used in your development/ implementation of your submission.
Just the lectures, notes and labs.

Please consider the following statements and choose one (delete the inappropriate one)

- This is my work apart from the specific references noted above (and any code from class notes). I understand the code and can decribe any parts of the solution if needs be;
