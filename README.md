# cucumber-RestAssured 
cucumber/reseassured based framework that helps to get a head start with automation needs or just to get some understanding on how to get started
src/test.java is where most of the files
feature packages will have all *.feature files (cucumber)
stepDefinitions will have reatassured functions/methods
testRun will have a file used to run tests and class files for common variables, common methods etc

=======================================================================================================
To run on your local machine tests with minimum changes
necessary dependencies are added in pom.xml. there shouldn't be any changes necessary to IDE

1. download git repository
2. clone the repository to your git account
3. generate a token on git and update token in commonValues 
4. update configFile and dataFilePath path values to your locations
5. update repo, path, request variable values that have user/account details in test.feature/test1.feature. change "stekkem" to your account on git
====================================================================================================

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

every time updated code is checked into git, if the tests here are being run, a new token has to be generated from git. else tests will fail
