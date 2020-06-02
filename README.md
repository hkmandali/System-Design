# System-Design
This repo has the code for some of the design problems I solve and this section has some of the helpful tips for design

Some of the initial steps which can be thought/clarified during the inital phase of the design

* why do we need that system 
* requirements and goals of the system
* capacity estimation and constraints
* some design considerations ( to restrict ourselves into specific module/s )
* Api exposing
* high level design
* low level design
* data partitioning and replication
* caching

with these concepts in mind , we can think about an initial design on how to proceed with sql/no sql approach and rest/soap based approach
required for modules independently

                                                      1.   Indexing :
  As we have to retrieve the data from non volatile resource ( hard disk) , we dont know in which section the data is stored actually
  
  Indexing is a way by which a database record is retrieved quickly ( this is used during read purpose ) . With a table having millions of records,
  retrieving data based on some condition might take longer durations . indexing comes into picture in this scenarios . We can index a table,
  based on a column preferably primary key but not necessarily , we can also have secondary index for a table and I have mentioned primary key
  is because that *most of the queries are run on the primary key and having an index on it helps us in faster retrieval . Indexing is implemented
  majorly by using B - trees and B+ - trees , the difference between the two being B trees store the data in child as well as leaf nodes , 
  whereas B+ - trees store it only in leaf nodes .
  
  Some of the techniques for implementing INdexing are - Byte Offset , Sorted String Table(SST ) , B - Trees , B+ - trees , let us go over them one by one
  
  ByteOffset - this is the simplest of them all which is nothing but just the storing of byte location where the record is present 
  i.e lets say we have 10 records , and we store the byte offset i.e starting position of each record in a hashmap , then we just need to 
  look at those locations and check if the desired data is present or not but the over head with this is we are storing a lot of info 
  regarding where/location the data is stored.
  
  Sorted String Table - An SST is an in memory data structure i.e when writes are happening , they are sorted in memory ( on the server and 
  not on the db ) and at regular intervals this gets pushed to the server , so this write take O(logn) as it needs to sorted in memory
  which is kind of faster than the writes on disk and these writes are simultaneously written to write ahead log which can be uused in crash recovery
  and once this in memory SST is pushed onto db , there can be a back ground process which can compact all the data from different SST's into 
  a single SST . Compaction of the SST's in the backend is a much vital step for quicker retrieval as we dont need to search through 
  many SST's then  
  
   one oter hybrid approach can be thought as we push the records without sorting in memory which gives us o(1) for write and we sort them 
   in the back end similar to SST , this gives us o(logn) for reading but however if the read is done before the sorting being done in the backend
   its going to take more time than usual , so its a tradeoff
   
   LSM tree - log structured merge tree , which is a combination of memtable ( in memory ) + SST on disk  -- this is hybrid approach which I mentioned
   above , and the merge process in the backend is called compaction
   
  B - tree - B tree  is similar to BST in DS where all the nodes to the right are > root and left are < root , similarly in a B tree all the records
  > greater than root ( that particular column on which indexing is being done ) are stored to the right and similarly lesser values are stored to the
  left , this helps us in quicker retrieval however for writing the data into the db it takes o(logn) time as each record needs to pushed 
  into B+ tree which has an insertion of O(logn) , and if anytime re arranging of the tree happens it is going to take even more time .
  
  > So what's the difference between LSM and B tree as both of them seem the same as we are pushing the data into db and it is getting inserted
  at O(logn) , the diff is that in LSM ,records are accumulated in memory till a threshold and are flushed at a single go into the db,
  where the records are initially sorted and stored in SST and then compaction of those SST's happen , where as in case of B tree , the 
  records are pushed into db at each and every step making insertion and retrieval O(logn)
            Insertion             Retrieval
  LSM --    O(1)                  > O(logn)
  B tree -- O(logn)               O(logn)
  
  B+ tree is also the same as B tree but the only diff being B+ tree stores the data in only leaves whereas B tree stores it in child as well as leaves
  
  
  One thing which we need to keep in mind is that is we push all the records as is into the db without storing it in memory , we are increasing the
  I/O calls which results is more bandwidth consumption , so we need to store some data in memory and push them onto db when a certain criteria
  (threshold) is met .
  
                                                        3. Data Warehousing
  
  > Data warehousing is an important concept in distributed systems , it is predominantly used by the means of ETL ( extraction transform load) which means to extract the data --> transform it based on something --> and then load it for our business purpose .
  Query languages can be thought of as 3 types 
  1. SQL 
  1. Document -- which can be used in one to many mappings
  1. Graph QL -- which is also used in facebook to findout the mutual connections 
  
  ETL majorly uses column oriented storage 
  Online systems have two different types of processing systems called OLTP and OLAP 
  End users requests and responses are diverted to Online Transaction processing(OLTP) which basically holds the latest data
  Internal users/ analysts who work on the queries/data have access to the Online Analytic Processing(OLAP) so that the data is accessed
  from a different db and the end user doesnt see much difference in the performance as these queries run for a longer time and doesnt
  block the db 
  
  
  
