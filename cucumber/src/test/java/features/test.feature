@mytest
Feature: Get all repositories from github

# arrays in response payload
  Scenario Outline: <testId> do a get call to get repositories and check response

    Given when user tries to get repositories from <URL>
    And passes authentication token with requests <token>
    When call made with api path <path>
    Then validate the response code is <code>
    Then check a repo is returned in response <repo>
    Then verify value of <key> is <value> at <location>
    Examples:
    |testId |URL                     | token                                    |path                                       | code|repo                                                   |value              |key        |location|
    |1      |http://api.github.com   | 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 |/user/repos                                | 200 |git://github.com/stekkem/cucumberDemo.git              |stekkem        |owner.login|0|
    |2      |http://api.github.com   | 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 |/repos/stekkem/first_steramlit_app/commits | 200 |Update streamlit_app.py                                | Suren Tekkem      |commit.author.name|0|
    |3      |http://api.github.com   | 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 |/users/stekkem/repos                       | 200 |git://github.com/stekkem/first_steramlit_app.git       |stekkem            |owner.login|0     |
    |3a      |http://api.github.com   |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 |/users/stekkem/repos                       | 200 |git://github.com/stekkem/first_steramlit_app.git       |stekkem            |owner.login|0     |

# no arrays in response payload
  Scenario Outline: <testId> do a get call and check response

    Given when user tries to get repositories from <URL>
    And passes authentication token with requests <token>
    When call made with api path <path>
    Then validate the response code is <code>
    Then check payload value of <key> is <value>
    Examples:
      |testId | URL   | token | path | code | key | value |
      |4      |http://api.github.com   | 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  |/users/stekkem  | 200 |login  | stekkem             |
      |5      |http://api.github.com   | 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  |/users/stekkem  | 200 |node_id| MDQ6VXNlcjU5MjU4ODA4|
