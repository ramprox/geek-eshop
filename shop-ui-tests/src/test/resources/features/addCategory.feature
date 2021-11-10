Feature: Add category

  Scenario Outline: Successful add category in shop-admin-app and check it
    Given I login in shop-admin-app providing username as "<username>" and password as "<password>"
    When I navigate to category.html page
    And click add category button
    And I provide category name as "<categoryName>" and click submit button
    Then url must be equals category.html page url
    And check added category
    And remove added category
    And check removed category

    Examples:
      | username | password  | categoryName |
      | admin    | 789456123 | microwaves   |