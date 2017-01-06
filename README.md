# mir-interview
MIR Interview

## Time spent
https://wakatime.com/@f49e45c4-505e-4b31-82fa-9f3dacc6dada/projects/zwrqhznlfj?start=2016-12-30&end=2017-01-06    

## Coding Test

### The task

The task is to create an backend application that provides a set of APIs to be called from a frontend UI.

### The API

The API will have 4 endpoints and will communicate JSON with at least the following functionality for each end point:

- `/login` -  a POST request that will accept no input and return a `token` (which need to be used in subsequent calls to the API, in the `Authorization` header). Every call  to `/login` will return a new token and every invocation to this endpoint creates a new user, gives them a preset balance in a preset currency. 
- `/balance` -  a GET request that will accept an `Authorization` header (with the token value output from `/login`) and  will return the current balance along with the currency code.
- `/transactions` - a GET request that will accept an `Authorization` header (with the token value output from `/login`) and  will return a list of transactions done by the user with atleast the `date`, `description`, `amount`, `currency` for each transaction.
- `/spend` - a POST request that will accept an `Authorization` header (with the token value output from `/login`), JSON content representing one spend transaction with the transaction `date`, `description`, `amount`, `currency`.

There is a *Postman* collection at [Interviewer.postman_collection.json](https://github.com/shanmuha/interviewer/blob/master/Interviewer.postman_collection.json) that you can use to test your API implementation.

### Technology stack

- use an netty based async java framework for the server (preferably [ratpack](https://ratpack.io)) and in-memory datastore for the backend (preferably [aerospike](http://www.aerospike.com/))
- the backend will run as a standalone java process + the in-memory datastore process

### Requirements

- Implement the 4 API endpoints
- Feel free to spend as much or as little time on the exercise as you like
- Feel free to use whatever additional frameworks / libraries / packages you like
- Your code should be in a state that you would feel comfortable releasing to production
- The endpoints for the same token might be called from multiple clients concurrently.
- Writing unit/integration tests are optional but highly encouraged
