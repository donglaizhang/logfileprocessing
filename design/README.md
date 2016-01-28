#System Design
---
##Outline

This document contains these contents: the process of the system, and why I design it like this.

Follow the requirements, there are more than 1,000,000 small log files in a same directory. For processing these files quickly, muti-threads is necessary. 


* Assuming the file and directory permission is ok. 

---


## Process description
According to the requirements, I design this project to several parts:

* Scaning all the log files in the target directory, recording the filenames, and sorting the filenames by timestamp.

* Get the total number of the rows of every log files separately by multi-threads and I/O operation.

* According the results which are from aboving 2 steps: sorted list of names and total number of rows, calculating the start row number for each log files. And output the start number to the disk.

* Inserting the row number to all the log files by multi-threads and the start number for each files.



![GitHub][github]

[github]: http://zdonking.com/wp-content/uploads/2016/01/process-design.png "Design graph"
---

 
## Techonology Selection

* JDK 1.8
* Maven 3
* LOG4j
* JUnit 4 (scope: test)

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

* Sort by name. Because the total number of files is about 1,000,000, and quick sort is used by JDK, the time complexsity is O(nlogn). About 2s in my Labtop
* Write the intermediate result to the file. Because the disk is SSD(ref: requirement document), and the intermediate result is about 50MB(list name is 42MB +start number is about 10MB), this process is will be quick.

* If processing of inserting the row number is quick enough, (enough CPU cores), the processing speed may arrive the maximum of disk(like 300MB/s). 1T data need 1024*1024/300=3500s.

---

## Some Details

* long for row number. Assuming the number of log file is 100,000 in average(20 bytes in one row, totally less than 2MB), and the number of log file is 1,000,000, so the total the number of row is 100,000,000,000. Less than Long.MAX_VALUE



## For Extending
* For more log files

	If the log files are more than 1 million, like 100 million, the memory is not enough to load their names. It need to be sorted by multi files, like every 1 million filenames can be sorted in memory and stored in one file. 10 million files means all filenames in 10 sorted files. Merge every two file with filenames util only one left. 




