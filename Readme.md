# A Cab Booking System using Java.

The main functions of the program for the User and Admin sides:

### User Mode (for Students):
Register on the portal
Specify trip details (destination, origin, date and time of departure)
Submit request for cab sharing
When a possible sharing option comes up, the student should be able to accept or reject it

### Admin Mode (for SU):
Manage details of students
Implement an algorithm to group students into a shared cab based on time and destination (offer an adjustment of +-30 mins to each student to consider a shared cab)
Charge each student based on sharing number, trip distance, etc.

## Use of Design Patterns in the Project
The Command and State Design Patterns have been used in the project. The implementation of the same is rather unconventional in this case. 
The Page interface and the subclasses implementing it use the execute() method to update their states and respond
accordingly. The State Design Pattern has also been used in the Authentication class where the
isLoggedin boolean helps to check the state of the user across the project and the isAdmin()
method helps to check if the current user is an admin.

Further, the Singleton design pattern has been used in the single initialization of global
maps and lists that have been used as data storage facilities while the application is running.
Although data is being stored in csv files, upon running the application, when data is retrieved,
the AllUsers, AllBookings, All cabs and AllRoutes maps along with the AllNotifications list are
instantiated once to store the data. Although this is different from how the pattern is generally
used by forming constructors, the basic idea of having only one instance has been used. If the
project could be redone, this pattern could be implemented in a better fashion.


Design Patterns that can find an application in this project:
The Observer pattern could have been used to notify users of the share requests made by
other users and allow them to accept or reject the same. Thus keeping multiple users updated.
But since the application is being accessed by a single user at once, it wasn’t implemented for
this version of the project.

It became tedious to construct classes with a lot of parameters to be passed in their
constructors. Hence, to solve the same problem, the Builder pattern could have been used while
constructing instances of data classes, such as User, Booking, Route, Notification and Cab.
Furthermore, the Command pattern could have been used to start and join threads in case of
loading the data. It could also have been used as a control to switch between implementations
when receiving user input on every page such as Login, register, BookingPage and Transaction.
This is being done using switch cases for now however, I know that this isn’t the best practice
and could be optimised.

## Analysis of Code with respect to SOLID Design Principles Used
The Single Responsibility Principle which states that a class should change for only one reason is
implemented at various places throughout the code. Classes such as Login class perform the sole
function of logging in the user. Every class in the project fulfills only its own responsibility.
However, the App class has been kept as a global wrapper to allow various packages to access
common data and methods to modify that data.

Also, various classes are open for extension so as to add new features. For example we have a
method calculateCost which calculates the fare for the cab service but the payment gateway has
not been implemented yet and there is room for extension to add this feature without
significantly modifying other classes. Also, we have a Driver class which can be extended
further to make separate profiles for the Drivers in future. However, the existing fields of this
class can no longer be completely modified without disturbing the coupling in the project. This
outlines the use of open-close principle in the project.

Liskov’s Substitution Principle is followed wherever a class inherits another class, such as the
User and Admin classes and the classes in the Pages package as well as all the Data Loading
classes extending the Thread class.

Due to a high amount of abstraction and low coupling that we have strived to achieve throughout
the project, the Dependency Inversion Principle comes into play and allows higher level modules
to remain independent of lower level modules.

Note: Generics have not been used in this project.
