# inpowered.ai

This is a time framed challenge to produce a small program capable of process an address book text file
and answer 3 questions:
1. How many males are in the address book?
2. Who is the oldest person in the address book?
3. How many days older is Bill than Paul?

To run this project you must have Java 11 installed.

## PREMISES
* The code must be produced in a 1-hour interval
* Unit tests must be added to assure quality 
* Some interaction is welcome

## NEXT STEPS
* Add Regex matchers to validate line format
* Add consistencies to Contact properties
* Change the way the file is being acquired (the path could be in a properties file, a 
Directory Watcher could be used and so on)
* Guarantee observability
* Solve remaining TODOs

## CONSIDERATIONS
* _org.apache.commons.io.LineIterator_ lib was used considering that in the future could be a large file,
so it will be necessary to keep performance in sight
* The use of a HashSet<Contact> as a cache was only for demo purposes, in a real world scenario is not 
preferable to keep all this data on memory and a trade-off between a file load plus Regex finding against an 
external cache system should be addressed