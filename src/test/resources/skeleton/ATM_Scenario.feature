Feature: Test ATM Machine
  Scenario: check Balance for new user
    Given Sign up
    When Get Token
    Then View Balance for new user

  Scenario: Sign up and get token
    Given Sign up
    When Get Token

  Scenario: check deposit Balance for new user
    Given Sign up
    When Get Token
    Then deposit and withdraw parellel

  Scenario: withdraw balance for new user
    Then Get Token
    Then withdraw balance from the new user
