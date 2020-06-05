# System-Design
This repo has the code for some of the design problems I solve and this section has some of the helpful tips for design

Some of the initial steps which can be thought/clarified during the inital phase of the design

* Why do we need that system 
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

### Table of Contents

1. [Indexing](https://github.com/hkmandali/System-Design/blob/master/README.md#1---indexing)
1. [Data Warehousing](https://github.com/hkmandali/System-Design/blob/master/README.md#2-data-warehousing)
1. [Encoding](https://github.com/hkmandali/System-Design/blob/master/README.md#3-encoding)
1. [Distributed Data](https://github.com/hkmandali/System-Design/blob/master/README.md#4-distributed-data)

## 1.   Indexing

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
  
##  2. Data Warehousing
  
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
  
## 3. Encoding
                                                     
 >  When any data is being transmitted between two processes/applications , it is expected that data is encoded and then sent to other 
 process which can be decoded by using the same algorithm which is used to encode the content . 
 > The reason for encoding is that , data in XML/ Json take more space compared to the binary(encoded) formats, this is not the same as 
  encrypting which is used in data confidentiality purpose 
  One of the simplest ways which we use in our daily practice encoding is serialization , which can be done using serializable interface
  1. Json encoding isnt much useful because there isn't much saving in space
  
  Below are some of the ways used in RPC calls , so that data can be sent in encoded format
  
  > Thrift and protocol buffers
  
  * Binary Protocol -- Thrift
  * Compact Protocol -- Thrift
  * Avro Encoding
  
  The below two belong to Thrift
  1. Binary protocol is similar to json encoding but the difference is that there are no field names (instead we use field tags)
  1. Compact protocol is similar to binary , the diff being that it uses less bytes 
  
  > * Protocol Buffer has repeated string in addition to thrift
  
  * Avro Encoding
       This has reader and writer schema , this is preferable for dynamic schemas.
  > Thrift and compact require code generation whereas Avro doesnt need it .
   All 3 of the above use schemas
  
  ###### Once data is encoded how does the data flow happen between the processes ?
  
  > Data Flow Models
  
  1. Through Databases -- Let's say we have data which needs to be sent between two processes , the easiest way is to store the data
      from one process into a db and then retrieving the data from db through second process ( dataflow through db )
  1. Service Calls -- through Rest and RPC 
  1. Message Passing
  
  * Dataflow through DB
    What happens when a new column is added ? how does the disk seek happen ? 
    Since the data flow between old and new versions can happen i.e lets say we have data for some version and we have changed the 
    schema in the next version ? how is it going to identify ? 
    The data is encoded with the latest version so we need to make sure of backward and forward compatibility
    
  * Dataflow through Service Calls 
    * Web Service : 
      When http is used as an underlying protocol , it is called a web service . It is of two types .
      1. Rest 
         This does it through url
      1. Soap 
         Has the overhead of wsdl( web services definition lang) xml file
    * RPC 
      THis is very much different from the local calls , the diff between RPC and IPC is that in case of IPC both the processes might
      be in the same computer or on the different but RPC is a method to call server from a client .
      RPC can be built on top of rest . RPC needs something called Service discovery ( Request Routing ) and also RPC compatibility
      needs to be maintained
  * Message Passing 
    This is similar to a message queue , has the advantage of message not being lost . Distributed actor frameworks are similar to 
    message brokers .
      
## 4. Distributed Data 

   > This is the  most important chapter as in many of the real time products the data is wide spread among different machines .
    There are two ways a scale a system
    
   * Vertical Scaling - which means increasing the configurations of a machine ( At a certain point of time , this comes to threshold
      and we need to opt for horizontal scaling .
   * Horizontal Scaling - which means adding more number of machines to the system 
    
   Any system can be broadly divided into 3 types .
   1. Shared memory architecture - vertical scaling
   1. Shared Disk architecture - data is stored on independent array of disks ( also called RAID config )
   1. Shared nothing architecture - horizontal scaling
   
## 5. Replication
  
   > In any distributed system , we may have more than one db/server as the data is humongous , whenever a user enters any data 
     it needs to be initially stored in one db and then replicated to other db's in the background to attain consistency 
   > There might be a question in your mind ,what is the need for multiple dbs ? we can serve the requests for a particular user
     through the same db each time . SO each db can have a certain set of users which it provides services for . In this way we dont
     need to replicate data to other dbs . But still there's an issue . Pause for a moment and think .
     
     The answer is that what happens if that db is down ? no other db stores that particular set of data . SO we might have to return
     failure to the user . To avoid this condition we replicate the data onto other servers in order to achieve Availability (CAP )
   > There are broadly three types of replications
   * Single Leader Replication - In this data writes always happen to a single server in a cluster and then the other servers take the
     data replicated from the leader . 
   * Multi Leader Replication - In this data can be written can be written to multiple nodes and those nodes should replicate the data
     to other nodes . However the replication becomes a bit tough in this case as there can be cases where two or more update the same
     record at the same time on different machines and the collision needs to resolved , there can be many ways to resolve it , one of
     famous one is Last write wins(LWW) --> we'll be coming to this in the upcoming chapters
   * Leaderless Replication - In this method of replication , there are no leaders . So , data writes can happen to any node and then
     they must be replicating that data . Attaining consistency is most difficult in this approach as each node has its own data and all
     of them need to come to a single state . THis is called consenses i.e when majority of them vote for a value ,that would be stored.
   
   Leaders and followers can also be termed as master slave architecture.
   
   In all of the above steps replication has can happen in two ways :
   
   1. Synchronous Replication - When a user enters data to a node and gets an acknowledgement only when the data is replicated to all
      the nodes .THis is called chain replication.
   1. Asynchronous Replication - When a user enters data to a node and gets an acknowledgement immediately and the replication happens
      in the background .
   
   >  With all these thoughts in mind , how do we attain this ? how do we replicate the data maintaining consistency .
   
###### Setting up new followers 
   
   > This can be done by taking a snapshot of the data and then copying it to the followers
   > MySql does this using Binlog coordinates , Postgresql does this using log sequence number
   
   In any of the apporaches , the changes from master to slave are processed periodically . 
   
   > What happens if a node fails ?
   
   * Follower failure - Resolving this is simple because , there are other nodes as well which contain the latest data and ofcourse 
     there is master which has all the data . SO we can overcome this situation by catch up recovery 
   * Leader Failure - When the leader fails , it becomes a bit tough as we need to choose who becomes the next leader based on the 
     node which has the latest data / voting among the nodes . We'll discuss this in the upcoming sections
   
   We'll discuss how to replicate the data i.e implementation of replication logs  -
   
   1. Statement Based Replication - In this each statement i.e insert , delete , update are passed onto the slaves . THis approach is
      not preferred as there might be some functions in the given statement vix rand() , time() which generate diff values at each 
      instance so the data is not uniform over all the nodes .
   2. Write Ahead log (shipping) - IN this each transaction is written to a log , so when the master fails this can be used to rebuild .
      Also the same log can be sent to other nodes , so that they can replicate the data similar to the master . One drawback is that
      the log consists of details about which bytes were changed on which disk so if the storage format is different for different
      engines it becomes vague / tough for us to identify
   3. Logical ( row based ) log replication - This is similar to the above one but the format is different for replication and storage
      engine . If many rows are modified , it'll generate a record stating that transaction was committed .
   4. Trigger based replication - This copies only a subset of data at each intervals
   
   > Eventual consistency is something which is seen in asynchronous replication , i.e we assume that at some point of time all the 
     nodes are going to be consistent
   > Replication lag is defined as the lag / difference between leader and follower
   
      
  
