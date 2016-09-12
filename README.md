# MoneyTransferService

Here is test project with REST API for money transfers between accounts.

There are two entities — `Account` and `Transaction`. API is resource-oriented, so in order to transfer money between accounts Client should create a `Transaction` via REST API.

### Project Structure

The project consists of four modules:
- `model` contains data model entities.
- `core` contains main logic of service.
- `api` contains API based on [Restlet Framework](http://www.restlet.com). All data are to be sent in JSON format.
- `client` contains the example of API using. Client also uses Restlet Framework. Due to this client does not need to work with low-level issues of REST API.

### API Description

Server is listening to requests on `http://localhost/v1/`. There are no API versioning, but address structure allows to add it if necessarily.

API is resource-oriented. Available methods are:

##### Get Account

`GET http://localhost/v1/accounts`

##### Get All Accounts

`GET http://localhost/v1/accounts/{accountId}`

##### Create A Transaction

`PUT http://localhost/v1/transactions/{transactionId}`

Identifiers of transactions are supposed to be generated on Client. Due to this the method is idempotent. So, if client does not receive response for creating transaction request due to connection issues, it can simply repeat the request until it receives an answer.

In order to provide identifier uniqueness Client must use `UUID` as an identifier.

When Server receives request for new `Transaction` to be created for the first time, it transfers money from one `Account` to another.

##### Get Transaction

`GET http://localhost/v1/transactions/{transactionId}`

##### Get All Transactions

`GET http://localhost/v1/transactions`

### How to Run Server

Server entry point is `Application` class. Server runs on `http://localhost`.

One can also start the Server using scripts in directory `bin` of application zip archive located in `api\build\distributions`.

### Locking

In order to provide concurrency correctness, `TransactionService` uses locks for `Account`s it operates. In order to prevent deadlock `TransactionService` claims and releases locks in the specific order.

### Tests

There are two test classes. `ClientTest` is about testing Client-Server integration. More complex cases are covered on the server-side (see `TransactionServiceTest`).
