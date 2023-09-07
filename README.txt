This project was done with a tech loaning service at The University of Texas at El Paso in mind.
This is a full running program that lets you loan out items as a buisness or service.
It comes with the ability to modify and adjust your stock in an Item List.

With the university in mind an ID check is included to verify that students are using this service only
Checks are made throughout the program to ensure that you can be lent the item.
 --- Such as -----

- ID Check (Starts with 80)
- Stock Check
- Max amount of Loans Reached
- Valid Item

-----------------------------------------------------------------------------------------------
To use the commands "MakeALoan" and "ReturnAnItem" require the command, ID, FirstName, LastName, Item/ Item number.
These are seperated by spaces such as:

"MakeALoan 80000000 John Doe Laptop" or "ReturnAnItem 80000000 CSL-3"

Commmands -- These commands are case sensitive so please be sure to type it in correctly
-----------------------------------------------------------------------------------------------
MakeALoan    --- This is to create a loan profile and you are given your item number for return

ReturnAnItem --- If a customer wants to return their item

DisplayLoans --- Display current loaned out items

DisplayItems --- Displays the current stock that is left if any items were lent out

ExportLoans  --- Exports the current loans on profile to a file called loanArray.txt 
-----------------------------------------------------------------------------------------------

Any other command, string, or character will cause the program to prompt you again.

To exit the program please type "quit"


Thank you for checking out my program!