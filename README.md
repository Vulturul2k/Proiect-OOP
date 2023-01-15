

# Project POO  - POO TV - UPDATE

Florea Radu - 322CD

## Skel Structure

* src/
* Main - in main I put the input from json in input class and after the program end, I put the output in the output file (arg[1])
  * Input/ - here I created the input, as required by the project
    * Movie - here I create a variable "ratingSum" which helps me to calculate an accurate rate
    * User - Here I create some variables that help me to know information about the user-movie experience (how many films did he saw, liked, rate, etc), or how much tokens that he have
  * pageactions 
    * Constants - in this class I put all my constants
    * Info - is a class designed to output different info about my project
    * PageDetails - is a class where I store data about the current action
    * PageAction - is the class where I iterate throw the action. This class use Eager singleton, because it is called every time in main 
    * Recommendation - this class recommend a movie to a premium user based on their preferences. Count all the likes of the user, after that order all the movies based on his preferred gen show the first one. (I ordered with sort from arraylist)
  * mainpages - this package is designed to store the main pages of the "website".
    * MainPages - is an interface created for the main pages from this project 
    * SeeDetails - is a class which let us interact with a certain movie
    * Purchase - is a class design to purchase a movie
    * Watch - is class design to watch a movie and verify what movies are already watched
    * Like - is class design to like a movie
    * Rate - is class design to rate a movie
    * MoviePage - is the class designed to show the movies which a user could see
    * MoviePageAction - is a interface that have the purpose to be implemented by tha action of the movie page
      * Filter - is a class that filter the movies according to certain criteria 
      * Search - is a class that search movies that start with a input from the user
  * intermediatepages - this package is designed to store the intermediate pages that the user need to modify database (login, buy Premium, etc).
    * IntermediatePages - is an interface designed for the intermediate pages
    * Login - is a class designed to log in the User , it implements the interface ChangePage
    * Register - is a class designed to register a new User, it extends class login
    * Upgrades - is a class designed to help a User to buy with balance
  * admin - in this package I put the classes that make important changes for the Users
    * Add - this class add a new movie in the main list after verify if the movie already exist
    * Delete - this class delete a movie from the main list
    * GensLikes - this class it`s used by Recommendation class
## Methods
### PageActions
1. changePage - Is a method that is used when the action is of the changePage type.
2. onPage - Is a method that is used when the action is of the onPage type.
### MoviePage
1. moviePage - this method help the user to go to movie page and to see al the movies from his country
2. userMovies - is a method that returns all the movies that could be seen in a country
### Info
1. showCredentials - this method display the credentials of a user
2. showMovie - this method display a movie
### Upgrades
1. buyTokens - is a method that convert balance to tokens. A problem that I had here is that balance is a string and tokens is int. My solution was to make another balance of type int and to convert every char in an int. The new balance is converting using toString
2. buyPremium - is a method that convert a user from "standard" to "premium"
### Back
I implement this feature with the Arraylist pages, where I saved all the pages where the user was. When the user want to previous page, the method change page is called with the previous page after verify if we could do that.
## Other mentions
Classes from the package main.Pages and MoviePage, SeeDetails, Filter and Search use lazy singleton because aren`t call every time but needs only one instance. And PageActions use eager singleton , because we need always just a instance.
Back type was implemented with the design pattern Command 
I was a little confused by the change of tests, but I believe that is better that in the final , page field is null when action is the type on page. The project is well done and helped me to increase my oop skill.


