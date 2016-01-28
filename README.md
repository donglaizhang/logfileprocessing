# logfileprocessing
### This project is used to process log files to add globle number to these log files.
---
## Requirements of this project

* JDK 1.8
* linux 

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
* I don't have a deploy task.


---
## How to run?
* mvn package
* cd target
* tar xvf logrow-processor-dist.tar.gz 
* cd bin/
* sh processor.sh $Path



