
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
      |000      |http://api.github.com |  /user/repos   | 200 |git://github.com/stekkem/cucumberDemo.git       |stekkem     |owner.login       |0|

# post call example with payload from text file - site used for testing: https://reqres.in/
  Scenario Outline: <testId> post call with two key value pairs
    Given when user makes an api call using <URL>
    When a post request sent with payload <payload> from file to <request>
    Then verify github post call response
    Examples:
      |testId  | URL              |payload    | request    |
      |19      |https://reqres.in/|test19.txt |/api/users/2|
      |20      |https://reqres.in/|test20.txt |/api/users/2|
  @mytest1 @debug

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
      |21      |https://reqres.in/ |/api/users/2 | 200  | test19R.txt  |            |


