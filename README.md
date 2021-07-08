# card-game

Simple luck-based card game

Basic Rules:
- Game can have four players only.
- Use a standard deck of cards (no Joker).
- Each player is dealt only three cards.
- 'A' is considered to have a number value of 1.
- 'A' is considered the top card in a face-off. So the order is A > K > Q > J > 10...2

Victory:
- A trail (three cards of the same number) is the highest possible combination.
- The next highest is a sequence (numbers in order, e.g., 4,5,6. A is considered to have a
value of 1).
- The next highest is a pair of cards (e.g.: two Kings or two 10s).
- If all else fails, the top card (by number value wins).
- If the top card has the same value, each of the tied players draws a single card from the
deck until a winner is found.
- Only the newly drawn cards are compared to decide a tie. The top card wins a tie.
- the suit (spades/hearts etc...), does not matter.


System Requirements:
- Java Version 1.8 or above

How to Download run the Game:

Option 1:
-  Download the target/cardgame-0.1.0.jar
-  In your command line run this command - java -jar [Path of downloaded Jar]/cardgame-0.1.0.jar

Option 2:
- Download entire repo and open in your IDE
- Right Click on src/main/java/cardgame/demo/DemoApplication.java
- Select Run 'DemoApplication'

How Play the Card Game:

Option 1: Local players
- Go to any Browser
- Enter localhost:8080/simple-card-game/v1
- The game starts with four players in the background and the game result is shown immediately

Option 2: Multiplayer (Better Experience if you host this in a remote server)
(Game Simplification In Progress. Wait for next update)
- Use PostMan or similar tool to register a User.
- POST localhost:8080/users/ 
  - Request Body { "name": "yourname" } 
- Repeat the same for as many users you want.
- To see user id of each user. Enter the URL localhost:8080/users in the browser.
- To simulate multiplayer. Do the following
  - For each User
    - Enter the Home page of each User by entering the URL localhost:8080/home?userId=userId
    - click on join button for each user. 
    - This will put user in the user waiting pool.
- If the waiting pool size is greater than 3, it will pick four users at a time and assign those users to a table and start the game.
- So, When you clicked on join for more than four users, click on refresh button for each waiting player to see the result of the game.

TroubleShoot:
- If you face port connectivity error
  - Follow Option 2 mentioned above
  - Open src/main/resources/application.properties file
  - modify server.port value to some other random value




