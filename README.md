# cucumber-RestAssured automation framework 
Use this cucumber/reseassured based framework to get a head start with automation needs or can be used to get some understanding on how to get started. 
its a general purpuse framework. With some changes, this framework can be used to build an API automation project. 

**General Info:**

src/test.java is where most of the files recide. 

features packages will have all *.feature files (cucumber).

stepDefinitions package will have reatassured functions/methods.

testRun package will have a file used to run tests and class files for common variables, common methods etc.

data package will have test data files.

pom.xml and config.properties will be at root level.

==========

projectTest.feature file 
    contains basic use cases, verify structure of schema, verify key value pairs, verify values are not null
    
==========

**To run tests on your local machine** with minimum changes
necessary dependencies are added in pom.xml. there shouldn't be any changes necessary to IDE

1. download git repository
2. clone the repository to your git account
3. generate a token on git and update commonValues.token in commonValues 
4. update commonValues.configFile and commonValues.dataFilePath path values to your locations
5. update repo, path, request variable values that have user/account details in test.feature/test1.feature. change "stekkem" to your account on git

==========

**other helpful tips**

6. some common methods do not have a test case, but might work as is or with very little tweaks
7. set runTest tag to @mytest run the complete suite
8. test.feature file has a test for debugging purpose. to run just one test to debug, update Example, set tag in runTest to @debug
9. framework has examples for POST, PUT, PATCH, GET, DELETE
10. add any specific headers that might apply to your application in commonValues class
11. there is a function/method to read values from config.properties, hence can add properties here
    update path to config.properties in commonValues
12. some common functions/methods have samples on how to call them. these are run with @debug tag
13. pom.xml should have all the dependencies to run the tests in this framework. you might have to add dependencies if needed for other applications
14. to run tests without passing the hostname in the request, update baseUrl in commonValues

**Note:** every time updated code is checked into git, if the tests here are being run, a new token has to be generated from git. else tests will fail
