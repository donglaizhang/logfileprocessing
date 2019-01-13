# logfileprocessing

** This project is used to process log files to add globle number to these log files. **

---
## Requirements of this project

* JDK 1.8
* linux for shell script

---
## Structure of project
* /src/main, for java source code and related property files
 

* /src/test, for test source code and related property files

* /design, for design documents

 
---
## How to build?

Maven 3.1

* mvn compile
* mvn test
* mvn package
* mvn install, install to the /#local_m2_path/repository/donglai/logrowprocessor/1.0-SNAPSHOT/logrowprocessor-1.0-SNAPSHOT.jar
* I don't have a deploy task. 


---
## How to run?

### Build & Run
* mvn package
* cd target
* tar xvf logrow-processor-dist.tar.gz 
* cd bin/
* sh processor.sh $Path

or 
java -jar logrowprocessor-1.0-SNAPSHOT.jar $log_dir -Xmx100m -$CLASSPATH 


### Config the Runtime Parameter
** Edit bin/config.properties: **

 * 	thread.number: The default value is 2. And the min number is 1, the max number is 100.
 *	logfile.charset: charset of the log file, default value is UTF-8



