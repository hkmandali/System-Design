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
1. [Replication](https://github.com/hkmandali/System-Design/blob/master/README.md#5-replication)
1. [Partitioning](https://github.com/hkmandali/System-Design/blob/master/README.md#6-partitioning)
1. [Transactions](https://github.com/hkmandali/System-Design/blob/master/README.md#7-transactions)

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
     nodes are going to be consistent.
   Replication lag is defined as the lag / difference between leader and follower
   
   * Reading your own writes - This happens in many applications viz insta / fb , where a user posts something and he can view
     immediately , however for other uses it takes some time
   * Monotonic Reads - lets take a condition where user has posted something and it has been written on a machine , when he reloads
     the app immediately ,if the request goes to a different machine , the previous write wont be seen and he might upload again
     To avoid this reads of a user go to the same machine -- this is called montonic Read
   * Consistent Prefix Reads - Lets say we have some comments to a post and some users have posted some comments to your post .
     What happens is you read the replys to a comment first and then the comment later ,that doesnt make sense . To avoid this 
     reads are read in the same order of writes 
   * One other solution for replication log is read after write and monotonic read .
   
 ###### Multi Leader Replication 
   
   Though it has many advantages , it should be avoided whereever possible because of the reasons mentioned above .SOme of the use cases
   
   * Clients with offline operation like calendar i.e every person updates it to their own and when they commit it has conflicts
   * Couchdb is also an example of offline operation
   * Collaborative editing like sharepoint where in many users may update simultaneously 
   
   > To resolve all of the above problems we have something like
 ###### Automatic Conflict Resolution
   1. Conflict Free Data Types ( CRDTS) --> two eay merge
   1. Mergeable persistent data structures --> three way merge function
   1. Operational Transformation --> which is used in google docs 
   
   We'll discuss Multi Leader Replication Topologies 
   
   1. Mysql -- circular topology i.e replication happens in a circular way , version vectors are used to resolve concurrent write conflicts
   1. Leaderless Replication - Dynamo Db
   1. Read Repair - When a client reads some data that column is updated with the newer value , this is applied during read
   1. Anti Entropy - Back ground thread running 
   1. Quorum for read and write - Num of machines which accept a specific read or write 
   1. Monitoring Staleness - Whether data is replicated properly or not 
   
###### Sloppy Quorums and Hinted Handoff
   Lets say based on the hashid(discussed later) of the user , the request goes to a particular node and if that is not available at
   that instance of time , the transaction is temporarily written to other nodes and once the required nodes are reachable, the writes 
   are handed back to them ( handoff)
   Cassandra is also an example of leaderless replication
###### Detecting Concurrent writes
   * Last write wins - As discussed above based on the timestamp , we can decide which one is latest and proceed, however we need to 
     keep track of unreliable clocks
   * Happens before relationship is also another way to identify this -- it is passed with version number , so that we can get to know
     which transaction has occurred before the other
   * Merging COnsistently written values - Automatic conflict resolution
   * Version Vectors - This is widely used in case of multiple replicas to identify which version has happened before which.
  
## 6. Partitioning:
   
   Partitioning of data is an essential in large scale systems as it would be used for quicker retrieval if we know in which segment
   the data is partitioned into . Let's say we have 1-100 values , we know that 56 is going to lie in between 50-60 ,we can part, ition
   the data into equal segments of length 10 .
   > Partitioning can be done in different ways :
   * By Key Range - This does partition based on the keys similar to above 1-100 , this is used in BigTable (HBase) as in similar
     to encyclopedia
   * By Hash of value - We can store the data into the segment based on the hash of the key . MD5 is an example.
   * Consistent Hashing - Storing the same data in multiple partitions by consistently hashing i.e simultaneouslt storing the data
     in segments through multiple hash functions . This has the advantage from the traditional method that in case of tradtional method
     in case a new partition ( server db) is added , many of the previous values need to be to new partition based on hash function or
     if one of the db goes down all its requests go to the next server there by increasing its load by drastic amount,
     whereas in case of consistent hashing , as the distribution is uniformly random there by partitioning is also uniformly random .
   * Skewed workload and relieving hotspots - If any of the server is overloaded we need to distribute it to other servers there by 
     relieving that hotspot 
###### Partitioning and secondary Indexes :
   1. Document based partitioning :
      This is based on local index, this also uses scatter gather i,e send a request to all of the documents and get results 
   1. Term Based partitioning :
      In this , indices are present only once throughout all the partitions i.e they are also partitioned
###### Rebalancing :
   >  Similar to consistent hashing , creates more partitions than nodes ( viz youtube )
   
   * Dynamic Partitioning -
     Depending on the data , it is split (or) combined to partitions . HBase uses HDFS for transfer of partition files 

   > Partitioning proportional to nodes .
   
   * Request Routing 
     * Zookeeper : Each node registers itself under zookeeper and it maintains the mapping of partitions to nodes . Linkedin Expresso 
       uses Helix ( Zookeeper)
  
 ## 7. Transactions:
    
    > There can be different types of transactions , viz
    
      1. Read Committed
      1. Snapshot Isolation
      1. Serializability
    
    We'll discussing these in the upcoming sections
    Whenever any write happens in the system , it can be of two types
      * Single Object writes 
      * Multi Objecct Writes
    
    > In any of the above two scenarios there can be a variety of problems like in case of single " when we upload a document of size 20
      kb and it fails after 10kb is uploaded, do we need to upload the whole doc again ? or how do we identify till which point it has 
      been uploaded ? In case of multi object writes there can be clauses like foreign key of multiple objects need to be valid " , how
      do we handle the errors ?
      
    > Handling errors & aborts :
      
      Sometimes retry is not so efficient as it might have sideeffects .
    > Some of the ways to achieve fail proof transactions is by using below methods -

###### Weak Isolation Levels : 
       By maintaining lock on the same object 
###### Read Committed : 
       No dirty reads ( this means we should read only the data which has been committed )
###### Dirty Writes :
       A second write should not over write first write if the second write hasn't been committed yet .

    > Databases implement dirty writes by having row level locks and dirty reads by maintaining two values ( old & new )

###### Snapshot Isolation & repeatable reads :
       Multi version concurrency control (MVCC)

    * Preventing lost updates 
###### Atomic write operations : 
       This removes the need for read - modify - write , we can allow all writes on a single thread (or)
       lock an object when it is read and keep it locked till it is written . This is also called Explicit Locking
      * Automatically detecting lost update :
        If we dont value to follow on a single thread , we can use below methods - 
      1. Compare and Set :
         Using this approach we can mitigate lost operations by updating the value only if it hasnt been changed since the last time it
         has been read , else read modify write must be retried
      1. Write Skews & phantoms : 
         This is a scenario when different transactions modify different objects but still there is a problem to lock rows we use for 
         update . For eg : Two doctors on call duty , both of them updating diff doctor objects but still there is an issue
###### Phantoms causing write skews :
       Writes in one transaction changes the result of a search query in another transaction is called phantom .
       
###### Serializability :
       This can be in three ways 
      1. Literally Serially - i.e transactions happen one after the other , there is no scenario if concurrent transactions ,hence it is
         called literally serially
      1. 2 Phase Locking - Writers block other readers and writers ( reads can happen simultaneously )
      1. Snapshot Isolation - 
         * Predicate Lock - SImilar to shared lock , whoever has the lock can access the resource
         * Detecting Stale MVCC reads - In a single table flags are modified 
         * Writes that affect prior reads - Modified in different tables .

