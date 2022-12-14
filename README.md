

# Project POO  - POO TV

Florea Radu - 322CD

## Skel Structure

* src/
* Main - in main I put the input from json in input class and after the program end, I put the output in the output file (arg[1])
  * Input/ - here I created the input, as required by the project
    * Movie - here I create a variable "ratingSum" which helps me to calculate an accurate rate
    * User - Here I create some variables that help me to know information about the user-movie experience (how many films did he saw, liked, rate, etc), or how much tokens that he have
  * page.Actions 
    * Constants - in this class I put all my constants
    * Info - is a class designed to output different info about my project
    * PageDetails - is a class where I store date about the current action
    * PageAction - is the class where I iterate throw the action. This class use Eager singleton, because it is called every time in main 
  * main.Pages - this package is designed to store the main pages of the "website".
    * SeeDetails - is a class which let us interact with a certain movie
    * MoviePage - is the class designed to show the movies which a user could see
    * MoviePageAction - is a interface that have the purpose to be implemented by tha action of the movie page
      * Filter - is a class that filter the movies according to certain criteria 
      * Search - is a class that search movies that start with a input from the user
  * intermediate.Pages - this package is designed to store the intermediate pages that the user need to modify database (login, buy Premium, etc).
    * IntermediatePages
    * Login - is a class designed to log in the User , it implements the interface ChangePage
    * Register - is a class designed to register a new User, it extends class login
    * Upgrades - is a class designed to help a User to buy with balance
## Methods
### PageActions
1. changePage - Is a method that is used when the action is of the changePage type.
2. onPage - Is a method that is used when the action is of the onPage type.
### MoviePage
1. moviePage - this method help the user to go to movie page and to see al the movies from his country
2. userMovies - is a method that returns all the movies that could be seen in a country
### SeeDetails
1. purchase - this method let User buy a movie , using tokens
2. watch - let him watch a movie he owns
### Info
1. showCredentials - this method display the credentials of a user
2. showMovie - this method display a movie
### Upgrades
1. buyTokens - is a method that convert balance to tokens. A problem that I had here is that balance is a string and tokens is int. My solution was to make another balance of type int and to convert every char in an int. The new balance is converting using toString
2. buyPremium - is a method that convert a user from "standard" to "premium"
## Other mentions
Classes from the package main.Pages and intermediate.Pages use lazy singleton because aren`t call every time but needs only one instance.
I was a little confused by the change of tests, but I believe that is better that in the final , page field is null when action is the type on page. The project is well done and helped me to increase my oop skill