TODO:
determine what methods are necissary in the UI
make the GUI, graphical user interface

GUI TODO:
set up login verification in RentingComLogin.java
the necessary method for this is just an SQL query to check if the SIN exists, you could maybe use a COUNT and if said COUNT returns 0 the method returns false
the ability to view the resulting query from searching for room availability should also be 
visible in UserPage, but I haven't figured out how to directly display those results in list form

connect UserPage.java to SQL
set up employee and admin pages

set up the EmployeePage UI and the corresponding methods

a note--superficie (?) is one of the criteria for choosing a room, which is odd because it's not.. part of the Chambre entity in the first place!
 