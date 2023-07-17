# ec135v2 - The Bitcoin Looking Glass

<pre>
Lewis Carroll introduced "Down the rabbit hole" into the english lexicon  
when he title chapter one of his 1865 novel Alice's Adventures in Wonderland,  
"Down the rabbit hole."  
  
https://en.wikipedia.org/wiki/Down_the_rabbit_hole
</pre>


<pre>
Looking-Glass Land is the location for Lewis Carroll's 1871 novel  
"Through the Looking-Glass, and What Alice Found There." The entire   
land is divided into squares by a series ... .  
  
https://aliceinwonderland.fandom.com/wiki/Looking-Glass_Land  
</pre>

<pre>
As opposed a land divied into squares, the Bitcoin Looking-Glass land is    
a series of, shall we say, blocks, crypto graphically linked blocks.  
  
This project, The Bitcoin Looking-Glass, works to pull out of the "rabbit hole"  
all the addresses of Alice's Bitcoin blockchain.    
  
https://news.bitcoin.com/meet-alice-bob-the-foundation-of-bitcoins-cryptography/
</pre>

### Bitcoin Looking-Glass

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


## Nov 3, 2022 - towards a new arch
<pre>
last checkin posted the github starts the new architecutre
Run 1, two *.dat files
reading the btc *.dat pulls data out much faster ()
     800000 unique addresses found in 2 mins
Run 2, three *.dat files
    1347837 unique addreses found in 8 mins, addr.out size 135472660
    file order
        File blk00001.dat
        File blk00002.dat
        File blk00000.dat
Run 3, four *.dat files
    1870256 uads found in 10 mins, addr.out size 174672228
        add blk00003.dat
Run 4, five *.dat files
    2286502 uads found in 12 mins, addr.out size 206421808

</pre>

## p2sh, start working
<pre>
possible solutions

https://www.mycryptopedia.com/p2sh-pay-to-script-hash-explained/

https://stackoverflow.com/questions/68046300/generate-p2sh-bitcoin-address-from-wif-in-bitcoinj-java

https://bitcoin.stackexchange.com/questions/107117/generate-p2sh-bitcoin-address-from-wif-in-bitcoinj-java

https://medium.com/@parthshah.ce/generate-bitcoin-addresses-using-java-in-six-steps-b1c418796a9e

https://academy.bit2me.com/en/que-es-p2sh/


</pre>

## read .dat files, write flat file to db
<pre>
Nov 24, 2022
about 15 minutes to read 10 blocks, pulled about 3.7M addresses.

Nov 25, 2022
7.5 hours, pulled out 23M addresses

assume 500,000,000 addr / 23,000,000 addr = 21M x 8rs = 173 hours = 7 days

</pre>






https://en.wikipedia.org/wiki/Down_the_rabbit_hole

<pre>
"Down the rabbit hole" is an English-language idiom or trope which refers to getting deep into something, or ending up somewhere strange. Lewis Carroll introduced the phrase as the title for chapter one of his 1865 novel Alice's Adventures in Wonderland, after which the term slowly entered the English vernacular.
</pre>


https://aliceinwonderland.fandom.com/wiki/Looking-Glass_Land

<pre>
Looking-Glass Land is the location for Lewis Carroll's 1871 novel Through the Looking-Glass, and What Alice Found There. The entire land is divided into squares by a series of little brooks with hedges growing perpendicular to them. It consists of two factions: the Reds and the Whites, and each side has it's own King, Queen, knights, armies, castles and also bishops. In the Looking-Glass Land, the language that is used is the Looking-Glass language, which is a mirror-image of English.
</pre>
