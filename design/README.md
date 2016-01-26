#System Design
---
##Outline

This document contains these contents: the process of the system, and why I design it like this.

Follow the requirements, there are more than 1,000,000 small log files in a same directory. For processing these files quickly, muti-threads is necessary. 

---


## Basic Functions
According to the requirements, I design this project to several parts:

* Calculating the counts of all the log files.

* Sorting the files by timestamp, and calculating the starting row number for every log files

* Writing the row number to every log files by muti-threads

## Optimazed Design

For solving this problem better, like the running is interrupted by exception. 

* Storing the intermediate results, like the count of the log file, and the starting row number of the log files.

* If there are no number of thread to config, automatically choosing the number of CPU cores as the deflaut number of threads.

 * Supporting if the program is interruptted, it can continue with restarting rather than from the beginning. 
 
 * Optimazing getting the number of rows and sorting by timestamp. These two processes can execute together, it will faster than running one by one.
 

---

## Techonology Selection

* JDK 1.8
* Maven 3.0
* LOG4j

