# Portfolio tracker


#### What will the application do? 
Portfolio trackers keeps track of stocks and equities of the user. It will record buy, sell, and current prices and calculate profit.

#### Who will use it? 
This application can be used by any individual who is interested in investing on stock markets and wants to keep track of his or her performance.

#### Why is this project of interest to you?
Personally, I like investing in different markets such as Bitcoin or stock market. Currently, I am using exel to keep track of my purchases. However, now that I am learning Java, I wanted to make my own program that suits me best.

## User Stories:

- As a user, I want to be able to create a new **stock market** to my **stock markets list**
- As a user, I want to be able to create a new **stock** and add it to one of my **stock markets**
- As a user, I want to be able to add **dividend yield** to each of my stocks
- As a user, I want to be able to add **number of stocks bought** to each of my stocks
- As a user, I want to be able to add **total cost** to each of my stocks
- As a user, I want to be able to add the **sell price** to each of my stocks
- As a user, I want to be able to update **market value** of each of my stocks
- As a user, I want to be able to save an exchange containing my stocks to file
- As a user, I want to be able to be able to load an exchange from file

## Phase 4: Task 2:

**First option: "Test and design a class in your model package that is robust.  You must have at least one method that
throws a checked exception. You must have one test for the case where the exception is expected and another where the
exception is not expected."**

#### Changed methods:
- Class: Exchange, Method: searchForName(String name)
- Class Stock, Method: updateMarketPrice(double marketPrice)
- Class Stock, Method: sell(int quantity, double sellPrice)
- Class Stock, Method: divProfit(int period)

## Phase 4: Task 3:
- Because of the limitation on the maximum number of lines in one method, I had to break my methods.
  Therefore, it reduced its readability. However, after I added more functionality to my code,
  given more time, I would put these breaks at more reasonable locations to group similar functionalities together.
- I would have liked to move some methods relating to creating a stock, or an exchange by the user their own class. 
  However, because I needed to gave feedback to the user, I had to keep those in my GUI class. I tried using String
  for returns, but messages were different which lost its point.
- Otherwise, I believe I was able to keep my code cohesive.

