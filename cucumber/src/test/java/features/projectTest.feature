@mytest3
Feature: Get all repositories from github
  @debug # use debug tag in runTest.java to debug any single case using the scenario below

# get call example with payload from text file - site used for testing: https://reqres.in/
  # get url from config.properties
    # compare call response with expected payload in file
    # if a key is expected to be different (datetime type) then add such keys under ignoreKeys with comma separation
  Scenario Outline: <testId> post call with two key value pairs
    # api call is collected from config.properties. test case provides the property name in example
    Given when user makes an api call getting url from config file <configProperty>
    When call made with api path <path>
    Then validate the response code is <code>
    Examples:
      |testId  |configProperty  | path        | code |
      |1       |reqresURL       |/api/users/5 | 200  |

# test case has step to verify structure of the payload, ignoring values
    #test case has step to verify a key is not null
  Scenario Outline: <testId> post call with two key value pairs
    # api url in given in example section
    Given when user makes an api call using <URL>
    When call made with api path <path>
    Then validate the response code is <code>
    # given schema file location, test performs a json structure match, ignoring values
    Then compare schema structure of response with <schemaFile>
    # test to verify a specific key is not null
    Then verify value of a key is not null <key>
    # test to verify value of a key
    Then check value of a key <key> is <value>
    Examples:
      |testId  |URL                | path        | code | schemaFile   |key       |value |
      |2       |https://reqres.in/ |/api/users/2 | 200  | jschema.json |first_name|Janet |
      |3       |https://reqres.in/ |/api/users/2 | 200  | jschema1.json|last_name |Weaver|