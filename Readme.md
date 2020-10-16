# Book My Court

## Implementation

1. Application is developed in Spring Boot 2.0.0 with Java 8 on Spring Tool Suite IDE. Database used is MySQL 5.7.17.

2. You can book slots using the application.

3. Mandatory entities to book a slot are - user, court, court with available slot for given sport.

4. Logging is done on console as well as file. Log file can be accessed at `/var/log/bookmycourt.log`.

5. Exception Handling is done using ExceptionInterceptor. 

6. Unit test cases are present for User Operations - User Registration and User Get. Cases for exceptions are also handled.


## Assumptions

For the simplicity of system, I have made following assumptions while implementing the solution -

1. No Payment flow used.


## Setup the Application

1. Create a database `bookmycourt` using the sql file `bookmycourt.sql` provided in `src/main/resources`.

2. Open `src/main/resources/application.properties` and change `spring.datasource.username` and `spring.datasource.password` properties as per your MySQL installation.

3. Type `mvn spring-boot:run` from the root directory of the project to run the application.


## Setting up the data

1. Access the application using swagger on `http://localhost:8080/book-my-court/swagger-ui.html`



## Booking a Slot

1. Use the `court-controller` heading in swagger and call the `get-available-slots` API to see the available list of slots available.

2. Select any slot from the above search result and copy its id.

3. Now go to `booking-controller` in the swagger and  execute the book ticket API using the following details - 

`{`
  `"seatType": "CLASSIC",`
  `"seatsNumbers": [`
    `"1A"`
  `],`
  `"showId": 1,`
  `"userId": 1`
`}`

This will book a slot for you and you will get booking id along with details in response.


## Verifying the results from DB

1. Login to your MySQL and go to `bookmycourt` DB

2. `SELECT * FROM bookmycourt.users;` to see all registered users.

3. `SELECT * FROM bookmycourt.court;` to see all courts.

4. `SELECT * FROM bookmycourt.sports;` to see all sports.

5. `SELECT * FROM bookmycourt.booking;` to see all booked courts.


## Other API Details

1. `UserController` -  API to add and get user

2. `CourtController` - API to get courts


## Future Scope

1. Multiple user handling 
2. Seat locking during payment
3. Payment Flow
4. Login and User Account Management