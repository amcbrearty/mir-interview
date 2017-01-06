# Answers To Questions
### Question 1.
 Questions
 * How long did you spend on the coding test?
 * What would you add to your solution if you spent more time on it?
 
 Answers
 * Approx 13 hours over 4 days - see the waka time tracker graph linked from the github repo README
 * Error Handling; IoC - probably Guice; fuller integration tests; unit tests; externalised configuration properties; concurrency tests; security failure for an unknown token
 
### Question 2.
 Question
 * What was the most useful feature that was added to Java 8? Please include a snippet of code that shows how you've used it.
 
 Answer
 * Lambdas:  `ctx.byMethod(method -> method.get(() -> ctx.render(json(dbService.findBalance(accountUuid)))));`

### Question 3.
 Question
 * What is your favourite framework / library / package that you love but couldn't use in the task? What do you like about it so much?
 
 Answer
 * JOOQ: recently discovered for a client where JPA was poorly integrated into an ESB product
 * Excellent where a team has SQL knowledge and poor JPA knowledge
 * Easy, intuitive fluid API to build SQL queries
 * DB Agnostic as the code level
 * Simple to configure and has a straight forward domain generation tool
 * Documentation is clear and concise but the API is straight forward so rarely needed
 * Needs little additional configuration to use
 * Simple to build typesafe queries

### Question 4.
 Question
 * What great new thing you learnt about in the past year and what are you looking forward to learn more about over the next year?
 
 Answer
 * JOOQ again. I hope to learn more about functional programming. Improve my knowledge and experience with lambda's and potentially learn a lot more about Ratpack and Aerospike.
 
### Question 5.
 Questions
 * How would you track down a performance issue in production?
 * Have you ever had to do this?
 * Can you add anything to your implementation to help with this?
 
 Answers
 * Start with the logs and monitoring tools (if they're in place) to identify which aspect of the application is performing poorly. Dependent upon that would drive the next steps e.g. DB: identify queries that are performing poorly and check indexes exist / are correct.
 * Yes - DB performance is predominantly the area of an application I've had to resolve performance issues with
 * Good log output showing timings and detailed DB connection / resource logs 
 
### Question 6.
 Question
 * How would you improve the APIs that you just used?
 
 Answer
 * correct security implementation / enforcement
 * business errors e.g. spend beyond limit; spend in unsupported currency; transactions ordered by date
 * REST best practice responses for invalid requests
 
### Question 7.
 Question
 * Please describe yourself in JSON format
  
 Answer
 * { "really": "i've got better things to do with my time" }
 
### Question 8.
 Question
 * What is the meaning of life?
 
 Answers
 * Always look on the bright side...
 * 42
 * “You will never be happy if you continue to search for what happiness consists of. You will never live if you are looking for the meaning of life.” 
   ― Albert Camus
 