This project is a solution for a payment provider where is possible to create 
Users, Accounts, Products and payments


What need to be done?
	-> Correct bugs on application
	-> Refactoring payment flux
	-> Create tests for payment flux
	-> delivery in bitbucket, github or gitlab
	


process ->
	create payment -> confirm payment
	
------------------------------------------------------------------------------------

Proposition of a Solution

What I did?
	-> Created a service class to keep the business rules and the calls on the repositories
	-> Created a Data Access Object classes for the repositories, to create a middle between the business rules and the calls on database. In that way the exceptions can be created around the calls to the database.
	-> Removed all business rules from the controller classes
	-> Created tests for all the controller and service classes
	-> Created exceptions for better user experience



