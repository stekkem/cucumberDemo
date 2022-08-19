@mytest
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
      |0      |http://api.github.com |  /user/repos   | 200 |git://github.com/stekkem/cucumberDemo.git       |stekkem     |owner.login       |0|

# multiple arrays in response payload
  Scenario Outline: <testId> do a get call to get repositories and check response

    Given when user makes an api call using <URL>
    When call made with api path <path>
    Then validate the response code is <code>
    Then check a repo is returned in response <repo>
    Then verify value of <key> is <value> at <location>
    Examples:
    |testId |URL                   |path                                       | code|repo                                            |value       |key               |location|
    |1      |http://api.github.com |/user/repos                                | 200 |git://github.com/stekkem/cucumberDemo.git       |stekkem     |owner.login       |0       |
    |2      |http://api.github.com |/repos/stekkem/first_steramlit_app/commits | 200 |Update streamlit_app.py                         |Suren Tekkem|commit.author.name|0       |
    |3      |http://api.github.com |/users/stekkem/repos                       | 200 |git://github.com/stekkem/first_steramlit_app.git|stekkem     |owner.login       |0       |
    |4      |http://api.github.com |/users/stekkem/repos                       | 200 |git://github.com/stekkem/first_steramlit_app.git|stekkem     |owner.login       |0       |


# no arrays in response payload
  Scenario Outline: <testId> do a get call and check response

    Given when user makes an api call using <URL>
    When call made with api path <path>
    Then validate the response code is <code>
    Then check payload value of <key> is <value>
    Examples:
      |testId | URL   | path | code | key | value |
      |5      |http://api.github.com    |/users/stekkem  | 200 |login  | stekkem             |
      |6      |http://api.github.com    |/users/stekkem  | 200 |node_id| MDQ6VXNlcjU5MjU4ODA4|


    #single array in response payload with token in example
  Scenario Outline: <testId> do a get call and check response

    Given when user makes an api call using <URL>
    When call made with api path <path>
    Then validate the response code is <code>
    Then verify value of <key> is <value> at <location>
    Examples:
      |testId | URL                    | path       | code | key | value             |location |
      |7      |http://api.github.com   |/user/orgs  | 200  |login| aipoweredmarketer |0        |

# checking response for key/value pairs using jsonpath with token in example
  Scenario Outline: <testId> do a get call and check response

    Given when user makes an api call using <URL>
    When call made with api path <path>
    Then validate the response code is <code>
    Then verify key <key> has value <value> using jsonpath
    Examples:
      |testId | URL                    | path       | code | path  | key      |value                                                 |
      |8      |http://api.github.com   |/user/orgs  | 200  |login  | hooks_url|[https://api.github.com/orgs/aipoweredmarketer/hooks] |

# post call example with payload
  Scenario Outline: <testId> post call with two key value pairs
    Given when user makes an api call using <URL>
    When a post call is made with <payload> to <request>
    Then check post call response is 201
    Examples:
    |testId  | URL              |payload                              | request  |
    |9       |https://reqres.in/|{"name":"cucumber","job":"tester"}   |/api/users|
    |10      |https://reqres.in/|{"name":"restassured","job":"tester"}|/api/users|

# put call example with payload
  Scenario Outline: <testId> put call with two key value pairs
    Given when user makes an api call using <URL>
    When a put call is made with <payload> to <request>
    Then check put call response is 200
    Examples:
      |testId  | URL              |payload                               | request    |
      |11      |https://reqres.in/|{"name":"cucumber","job":"testPut"}   |/api/users/2|
      |12      |https://reqres.in/|{"name":"restassured","job":"testPut"}|/api/users/2|

# patch call example with payload
  Scenario Outline: <testId> patch call with two key value pairs
    Given when user makes an api call using <URL>
    When a patch call is made with <payload> to <request>
    Then check patch call response is 200
    Examples:
      |testId  | URL              |payload                               | request    |
      |13      |https://reqres.in/|{"name":"cucumber","job":"testPatch"}   |/api/users/2|
      |14      |https://reqres.in/|{"name":"restassured","job":"testPatch"}|/api/users/2|

# delete call example with payload
  Scenario Outline: <testId> delete call with two key value pairs
    Given when user makes an api call using <URL>
    When a delete call is made with <request>
    Then check delete call response is 204
    Examples:
      |testId  | URL              | request    |
      |15      |https://reqres.in/|/api/users/2|
      |16      |https://reqres.in/|/api/users/2|