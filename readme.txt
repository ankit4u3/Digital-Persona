This sample project demonstrates the below:

1.) Verifying fingerprints from file.
2.) Identifying fingerprints from database.

To use the database feature you will need to update the project source code to use the appropriate connection string
 (server,username, password, etc) as specified in both "Enrollment.java" and "Verification.java".
You will also want to refer to the "CreateTable.sql" script contained in this directory to see what schema to use for the table 
you create.

Note:  Even though the sample enrolls a single finger per user, DigitalPersona recommends enrolling at least 2 fingers per user.  

