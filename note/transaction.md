#Transaction
*What is database transaction?*
- A database transaction symbolizes a unit of work performed within a database management system (or similar system) against a database, and treated in a coherent and reliable way independent of other transactions. 
- A transaction generally represents any change in a database. 

Transactions in a database environment have two main purposes:
1. To provide reliable units of work that allow correct recovery from failures and keep a database consistent even in cases of system failure, when execution stops (completely or partially) and many operations upon a database remain uncompleted, with unclear status.
2. To provide isolation between programs accessing a database concurrently. If this isolation is not provided, the programs' outcomes are possibly erroneous.

## Transaction in JDBC procedure:
1. Access the connection of database:
```java
Connection con = DriverManager.getConnection();
```
2. Open the transaction by con.setAutoCommit(true/false)
3. Implement the query (eg. CRUD)
4. Commit or rollback con.commit()/con.rollback();
5. Close the connection

## Transaction in Spring
- In Spring, we do not need to write the code for the step 2 and step 4 in the previous procedures. All these procedures are
finished by the Spring automatically.
- By add the @transaction annotation, we can open the transaction in Spring.
- Spring will add and parse the corresponding bean, and set a proxy for these methods and class and configure the transactions
according to the parameter injected in the @transaction annotation.