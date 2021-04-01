Feature: Requirement_1: Login
  Scenario Outline:As an App user, I want be able to log into the app successfully when I provided correct credentials.
    Given  The user is on login screen
    When  The user enters correct login details: "<password>"
    When The user clicks the login button
    Then  Verify if the user has logged in successful
    Examples:
      |password|
      |asdfgh|
      |asdfgh|

  Scenario Outline: As an app Administrator, I want the app to throw an error when a user has captured incorrect password.
    Given  The user is on login screen
    When  The user enters incorrect login password: "<password>"
    When The user clicks the login button
    Then Verify if the app is throwing the error: "<expected_error>"
    Examples:
      |password|expected_error|
      |as123|LOGIN ERROR     |

