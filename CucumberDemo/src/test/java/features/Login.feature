Feature: Login to GitHub

@Login_to_GitHub
  Scenario: Login to GitHub application
   Given I am on "login" page
   And I type "Git_username"
   And I type "Git_password"
   When I click "Git_login_button"
   #Then I should see "GitHome_Page" field 
   
  @Login_to_GitHub1
  Scenario: Login to GitHub application
   Given I am on "login" page
   And I type "Git_username"
   And I type "Git_password"
   When I click "Git_login_button"
   #Then I should see "GitHome_Page" field 
   
  