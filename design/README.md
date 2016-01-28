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
![GitHub]: http://zdonking.com/wp-content/uploads/2016/01/process-design.png "Design graph"
![GitHub](https://avatars2.githubusercontent.com/u/3265208?v=3&s=100 "GitHub,Social Coding")

![GitHub][github]

[github]: https://avatars2.githubusercontent.com/u/3265208?v=3&s=100 "GitHub,Social Coding"
---
## Optimazed Design

For solving this problem better, like the running is interrupted by exception. 

* Storing the intermediate results, like the count of the log file, and the starting row number of the log files.

* If there are no number of thread to config, automatically choosing the number of CPU cores as the deflaut number of threads.

 * Supporting if the program is interruptted, it can continue with restarting rather than from the beginning. 
 
 * Optimazing getting the number of rows and sorting by timestamp. These two processes can execute together, it will faster than running one by one.
 

---

## Techonology Selection

* JDK 1.8
* Maven 3
* LOG4j

---
## Memory Calcuation

##### The limitation of memory is 100M, so some places should consider the memory.

List all names of log files, as the requirment assuming the total number of log files is 1,000,000. And for single file:logtest.2011-07-11.log, the length is 22, and charset is UTF-8, so size of single file name is 22*2=44 bytes. And when "start row number" had been calculated, this part of memory can be released by JVM.

*  So list of names of log files needs: 1,000,000 files: 44* 1,000,000 = 42MB

Total number of files: 1,000,000, and row number:long 8bytes, and use ArrayList to store total: 

* So list of row number of files needs total 1,000,000*8 bytes=  8MB

"insert row number to the every log file", this function was implemented by buffered reader & buffered writer. And the "Flush_size==500". Assuming the average length of the  row in the log file is 200, assuming there are 100 threads to flush the log records to the disk simultaneously. 

* So "insert row number to the every log file" needs 200\*2\*500\*100\* bytes = 20MB. 

Other functions nomarlly just used a little memory. Like:

* "start number for every logfile" stored in the file, rather than in the memory. 

---
## Performance Analysis

* Sort by name. Because the total number of files is about 1,000,000, and the algorithm of sorting in JDK is quick sort, the time complexsity is O(nlogn).
* Write the intermediate result to the file. Because the disk is SSD(ref: requirement document), and the intermediate result is about 50MB(list name is 42MB +start number is about 10MB), this process is will be quick.

* If processing of inserting the row number is quick enough, (enough CPU cores), the processing speed may arrive the maximum of disk(like 300MB/s). 1T data need 1024*1024/300=3500s.

---
## For Extending
* For more log files

	If the log files are more than 1 million, like 10 million, the memory is not enough to load them. It need to be sorted by multi files, like every 1 million filenames can be sorted in memory and stored in one file. 10 million files means 10 files for storing filenames. Merge every two file with filenames util only one left. 




