# ec135v2
## BTC Looking Glass


### sudo update-alternatives --config java
There are 2 choices for the alternative java (providing /usr/bin/java).

  Selection    Path                                         Priority   Status
------------------------------------------------------------
* 0            /usr/lib/jvm/java-18-openjdk-amd64/bin/java   1811      auto mode
  1            /usr/lib/jvm/java-11-openjdk-amd64/bin/java   1111      manual mode
  2            /usr/lib/jvm/java-18-openjdk-amd64/bin/java   1811      manual mode


### gradle install
#### linux
https://linuxhint.com/installing_gradle_ubuntu/


$ source /etc/profile.d/gradle.sh
$ gradle --version

------------------------------------------------------------
Gradle 7.4.2
------------------------------------------------------------

Build time:   2022-03-31 15:25:29 UTC
Revision:     540473b8118064efcc264694cbcaa4b677f61041

Kotlin:       1.5.31
Groovy:       3.0.9
Ant:          Apache Ant(TM) version 1.10.11 compiled on July 10 2021
JVM:          18.0.2-ea (Private Build 18.0.2-ea+9-Ubuntu-222.04)
OS:           Linux 5.15.0-43-generic amd64


#### MacBook Pro
https://kodejava.org/how-do-i-install-gradle-in-os-x/

Open Terminal.app.
Create a new directory sudo mkdir /usr/local/gradle.
Extract the downloaded Gradle distribution archive by executing sudo unzip gradle-4.0.2-all.zip -d /usr/local/gradle.
Edit .bash_profile in your home directory to add GRADLE_HOME variable with the following line export GRADLE_HOME=/usr/local/gradle/gradle-4.0.2
Also update the PATH variable to include $GRADLE_HOME/bin. If you don’t already have the PATH variable add the following line export PATH=$GRADLE_HOME/bin:$PATH
Run source ~/.bash_profile to executes the update version of .bash_profile. Or you can open a new Terminal.app to make this changes active.

## start looking glass project

ivwall@hpu01:~/git-ec135v2/ec135v2$ gradle init

Select type of project to generate:
  1: basic
  2: application
  3: library
  4: Gradle plugin
Enter selection (default: basic) [1..4] 2

Select implementation language:
  1: C++
  2: Groovy
  3: Java
  4: Kotlin
  5: Scala
  6: Swift
Enter selection (default: Java) [1..6] 3

Split functionality across multiple subprojects?:
  1: no - only one application project
  2: yes - application and library projects
Enter selection (default: no - only one application project) [1..2] 2

Select build script DSL:
  1: Groovy
  2: Kotlin
Enter selection (default: Groovy) [1..2] 

Generate build using new APIs and behavior (some features may change in the next minor release)? (default: no) [yes, no] 
Project name (default: ec135v2): io.crtp.ec135
Source package (default: io.crtp.ec135): 

> Task :init
Get more help with your project: https://docs.gradle.org/7.4.2/samples/sample_building_java_applications_multi_project.html

BUILD SUCCESSFUL in 2m 18s
2 actionable tasks: 2 executed


 gradle build
 gradle run

 ## running with more memory
 nohup java -Xmx16384m -jar app-all.jar > duplicate-addr-writes.txt &



 ## java thread-cpu affinity
 http://www.javased.com/?source_dir=Java-Thread-Affinity/src/main/java/vanilla/java/affinity/AffinityLock.java

https://www.javai.net/post/202204/java-thread-affinity/

https://www.javai.net/post/202204/java-thread-pool/

http://www.javased.com/?post=12471496


## performace goal - 50 Million Address writes per day
50,000,000    / 24 hours
 2,083,333.33 /  1 hour
    34,722.22 /  1 min
       578.70 /  1 sec




## logging
http://makble.com/gradle-slf4j-integration-example-with-eclipse

https://github.com/smithlamar/java-logging-slf4j-demo

https://www.slf4j.org/manual.html

https://sematext.com/blog/slf4j-tutorial/

https://www.quickprogrammingtips.com/spring-boot/using-log4j2-with-spring-boot.html

https://www.slf4j.org/manual.html

https://docs.gradle.org/current/userguide/build_lifecycle.html

https://stackoverflow.com/questions/59178076/how-to-set-slf4j-in-intellij-with-gradle



### new lines in github readme.md
space bar, space bar, enter

### spaces

### mariadb connetions
[mariadb]> select @@global.max_connections

151 -- is the current setting

[mariadb]> show status like 'Conn%';
[mariadb]> show status like '%onn%';


## how to get to 600 / sec?

### block 51728 has 279 trxs

Data flow characteristic number 1.
The first few thousand blocks have under 600 trxs per block.
<pre>
+-------------+  
|  RPC Calls  |  
|  get block  |  
+-------------+  

+-------------+
| parse out   |
| trxs        |
+-------------+

+-------------+
| db instert  |
+-------------+   
</pre>

Sometime later, date unknown at this time, blocks hold 1000s of trxs.

date/time     trx            duration
10101630                     dd hh mn sec  --- basically started at 4:30
             50,000          00:00:07:30
            100,000 addr     00:00:15:55

400,000 / hour * 24 hours = 960_000 / day ... 400 days

10101907
            ~96,799 add     00:00:15:00

10101925     97,561         00:00:15:00
    but something isn't correct 

10101950     94,318         00:00:15:00

19192995     57,862         00:00:15:00


## load table from a file
https://mariadb.com/kb/en/how-to-quickly-insert-data-into-mariadb/

## consider this db singlestore.com 
singlestore.com formally MemSQL


chown mysql /tmp/ < addresses file >
chgrp mysql /tmp/ < addresses file >


### "insert file"
mysql --user='root' --password='the5thAdventure' silkpurse < '/tmp/1011-insert2.out'

file 1011-insert2.out
insert into wallet (addr) values (" address ");


### will this work

### db load from a file
file via vi
<pre>
1," [address] "
2," [address] "
</pre>

mariadb command
<pre>
MariaDB [silkpurse]> load data infile '/tmp/load-one.txt' into table wallet fields terminated by '.';
</pre>

# new architecture
### step 1
<pre>
read a bitcoin nodes *.dat files
generate files to load into a maria db
</pre>
### step 2
<pre>
copy files over to mariadb
load the files
</pre>
### step 3 
## setup steps
### gradle init - see above
### configure build.gradle to create fatJars
<pre>
add artifacts -- bottom of file
add task fatJar -- next from bottom
gradle build
java -jar ./app/build/libs/app-all.jar
</pre>
### test, run
<pre>
test using .dat files in ../../1014-bdfx/blocks/
run on bnd01 and generate db load files
    scp ./app/build/libs/app-all.jar ivwall@10.10.89.92:/home/ivwall
    /dev/sda2       916G  479G  390G  56% /
copy files to php64460gln321
</pre>