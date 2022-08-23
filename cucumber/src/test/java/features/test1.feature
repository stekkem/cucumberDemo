
Feature: Get all repositories from github
@debug # use debug tag in runTest.java to debug any single case using the scenario below
  # multiple arrays in response payload
  Scenario Outline: <testId> debug a test case
    Given when user makes an api call using <URL>
    When call made with api path <path>
    Then validate the response code is <code>
    Then check a repo is returned in response <repo>
    Then verify value of <key> is <value> at <location>
    Examples:
      |testId |URL                   |  path          | code|repo                                            |value       |key               |location|
      |000    |http://api.github.com |  /user/repos   | 200 |git://github.com/stekkem/cucumberDemo.git     |stekkem     |owner.login       |0|

# post call example with payload from text file - site used for testing: https://reqres.in/
# make changes to values in the payload
  Scenario Outline: <testId> post call with two key value pairs
    Given when user makes an api call using <URL>
    When a post request sent with payload <payload> from file to <request>
    Then verify github post call response
    Examples:
      |testId  | URL              |payload    | request    |
      |19      |https://reqres.in/|test.json |/api/users/2|
      |20      |https://reqres.in/|test1.json |/api/users/2|

# post call example with dynamic payload build from json template file - site used for testing: https://reqres.in/

  Scenario Outline: <testId> post call with two key value pairs
    Given when user makes an api call using <URL>
    When a post request sent with dynamic payload <payload> from file to <request>
    Then verify github post call response
    Examples:
      |testId   | URL              |payload    | request    |
      |19d      |https://reqres.in/|test.json |/api/users/2|
      |20d      |https://reqres.in/|test1.json |/api/users/2|

# get call example with payload from text file - site used for testing: https://reqres.in/
    # compare call response with expected payload in file
    # if a key is expected to be different (datetime type) then add such keys under ignoreKeys with comma separation
  Scenario Outline: <testId> post call with two key value pairs
    Given when user makes an api call using <URL>
    When call made with api path <path>
    Then validate the response code is <code>
    Then verify response payload from file <responseFile> and ignore keys <ignoreKeys>
    Examples:
      |testId  |URL                | path        | code | responseFile | ignoreKeys |
      |21      |https://reqres.in/ |/api/users/2 | 200  | test21R.txt  |            |


# get call example with payload from text file - site used for testing: https://reqres.in/
  # get url from config.properties
    # compare call response with expected payload in file
    # if a key is expected to be different (datetime type) then add such keys under ignoreKeys with comma separation
  Scenario Outline: <testId> post call with two key value pairs
    Given when user makes an api call getting url from config file <configProperty>
    When call made with api path <path>
    Then validate the response code is <code>
    Then verify response payload from file <responseFile> and ignore keys <ignoreKeys>
    Examples:
      |testId  |configProperty  | path        | code | responseFile | ignoreKeys |
      |22      |reqresURL       |/api/users/5 | 200  | test22R.txt  |            |

  @mytest1
# test case has step to verify structure of the payload, ignoring values
    #test case has step to verify a key is not null
  Scenario Outline: <testId> post call with two key value pairs
    Given when user makes an api call using <URL>
    When call made with api path <path>
    Then validate the response code is <code>
    Then verify response payload from file <responseFile> and ignore keys <ignoreKeys>
    Then compare schema structure of response with <schemaFile>
    Then verify value of a key is not null <key>
    Then check value of a key <key> is <value>
    Examples:
      |testId  |URL                | path        | code | responseFile | ignoreKeys |schemaFile   |key       |value |
      |23      |https://reqres.in/ |/api/users/2 | 200  | test21R.txt  |            |jschema.json |first_name|Janet |
      |24      |https://reqres.in/ |/api/users/2 | 200  | test21R.txt  |            |jschema1.json|last_name |Weaver|