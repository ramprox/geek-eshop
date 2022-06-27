Feature: Add product in cart

  Scenario Outline: Add product in cart and check it
    Given I open browser
    When navigate to products.html in shop-backend-app
    And click to button "Add to cart" for first product on page
    And after i navigate to cart.html
    Then added products count in cart must be 1
    When after click to "Delete line item" button
    Then added product must be removed from page

    Examples:
      | username |
      | admin    |