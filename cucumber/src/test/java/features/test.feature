Feature: Get all repositories from github
# adminToken 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
  @mytest
  Scenario Outline: <testId> do a get call to get repositories and check response

    Given when user tries to get repositories from <URL>
    And passes authentication token with requests <token>
    When git returns the repositories with <path>
    Then validate the response code is <code>
    Then check a repo is returned in response <repo>

    Examples:
    |testId |URL                     | token                                    |path                                       | code|repo                                                   |
    |1      |http://api.github.com   | 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 |/user/repos                                | 200 |git://github.com/stekkem-dev/cucumber-framework.git    |
    |2      |http://api.github.com   | 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 |/repos/stekkem/first_steramlit_app/commits | 200 |Update streamlit_app.py    |
    |3      |http://api.github.com   | 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 |/users/stekkem/repos                       | 200 |git://github.com/stekkem/first_steramlit_app.git    |
