@mytest
Feature: Get all repositories from github

# multiple arrays in response payload
  Scenario Outline: <testId> do a get call to get repositories and check response

    Given when user makes an api call using <URL>
    And passes authentication token with requests <token>
    When call made with api path <path>
    Then validate the response code is <code>
    Then check a repo is returned in response <repo>
    Then verify value of <key> is <value> at <location>
    Examples:
    |testId |URL                   | token                                    |path                                       | code|repo                                            |value       |key               |location|
    |1      |http://api.github.com | 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 |/user/repos                                | 200 |git://github.com/stekkem/cucumberDemo.git       |stekkem     |owner.login       |0|
    |2      |http://api.github.com | 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 |/repos/stekkem/first_steramlit_app/commits | 200 |Update streamlit_app.py                         |Suren Tekkem|commit.author.name|0|
    |3      |http://api.github.com | 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 |/users/stekkem/repos                       | 200 |git://github.com/stekkem/first_steramlit_app.git|stekkem     |owner.login       |0     |
    |3a     |http://api.github.com | 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 |/users/stekkem/repos                       | 200 |git://github.com/stekkem/first_steramlit_app.git|stekkem     |owner.login       |0     |


# no arrays in response payload
  Scenario Outline: <testId> do a get call and check response

    Given when user makes an api call using <URL>
    And passes authentication token with requests <token>
    When call made with api path <path>
    Then validate the response code is <code>
    Then check payload value of <key> is <value>
    Examples:
      |testId | URL   | token | path | code | key | value |
      |4      |http://api.github.com   | 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  |/users/stekkem  | 200 |login  | stekkem             |
      |5      |http://api.github.com   | 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  |/users/stekkem  | 200 |node_id| MDQ6VXNlcjU5MjU4ODA4|

  Scenario Outline: <testId> do a get call and check response

    Given when user makes an api call using <URL>
    And passes authentication token with requests <token>
    When call made with api path <path>
    Then validate the response code is <code>
    Examples:
     |testId | URL | token | path | code |
     |6      |http://api.github.com   | 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  |/organizations  | 200 |

    #single array in response payload
  Scenario Outline: <testId> do a get call and check response

    Given when user makes an api call using <URL>
    And passes authentication token with requests <token>
    When call made with api path <path>
    Then validate the response code is <code>
    Then verify value of <key> is <value> at <location>
    Examples:
      |testId | URL   | token | path | code | key | value |location |
      |7      |http://api.github.com   | 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  |/user/orgs  | 200 |login  | aipoweredmarketer             |0|

# checking response for key/value pairs using jsonpath
  Scenario Outline: <testId> do a get call and check response

    Given when user makes an api call using <URL>
    And passes authentication token with requests <token>
    When call made with api path <path>
    Then validate the response code is <code>
    Then verify key <key> has value <value> using jsonpath
    Examples:
      |testId | URL   | token | path | code | path | key |value |
      |7      |http://api.github.com   | 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  |/user/orgs  | 200 |login  | hooks_url|[https://api.github.com/orgs/aipoweredmarketer/hooks] |

# post call example with payload
  Scenario Outline: <testId> post call with two key value pairs
    Given when user makes an api call using <URL>
    When a post call is made with <payload> to <request>
    Then check post call response is 201
    Examples:
    |testId  | URL              |payload                           | request  |
    |8       |https://reqres.in/|{"name":"cucumber","job":"tester"}|/api/users|
    |9       |https://reqres.in/|{"name":"restassured","job":"tester"}|/api/users|


