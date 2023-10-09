# MyBookApp_java

This is L3T06 Task 2

Feedback:

1. 🔸Your program, unfortunately, does not require a book id, presumably because you have set a default value or some other setting for it in your database. Please send me screenshots or an SQL file that shows how you created your database so that I can duplicate it for full testing of your application.
   Please refer to database setup screenshot.
   +--------+-------------+------+-----+---------+----------------+
   | Field | Type | Null | Key | Default | Extra |
   +--------+-------------+------+-----+---------+----------------+
   | id | int | NO | PRI | NULL | auto_increment |
   | Title | varchar(50) | YES | | NULL | |
   | Author | varchar(50) | YES | | NULL | |
   | Qty | int | YES | | NULL | |
   +--------+-------------+------+-----+---------+----------------+

2. 🔸Consider having a submenus when updating a book so that the user does not have to go through all the fields to change only one of them.
   My code already does this, just leave the field blank if there are no changes, reference code below
   System.out.println("Enter new title for " + existingBook.getTitle() + " or press enter to keep the same: ");

3. 🔸Also consider giving the user the option of searching by id and title. That would be a much more realistic functionality for an application such as this.
   Code updated
