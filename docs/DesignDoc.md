---
geometry: margin=1in
---
# PROJECT Design Documentation

## Team Information
* Team name: s2a-Momo
* Team members
  * Shubhang Mehrotra (sm9943)
  * Joel Clyne (jmc4514)
  * Sasha Persaud (srp4581)
  * Quynh Duong (quynhnnduong)
  * Dmitry Selin (des3358)

## Executive Summary

The Online WebCheckers project is a Web-based application which aims to provide its users with the functionality to play 
checkers online and in real-time, against any other online player. 
To achieve the functionality, the system has been designed on Spark Framework with a FreeMarker Template. 
The bedrock of the application is formed of Java, Javascript, HTML and CSS.

### Purpose

This projects aims to deliver a fun and engaging experience to a broad range of users who wish to play an online version
of the classic checkers game. This project was commissioned by the "WebCheckers Startup", that aims to provide users the
ability to sign in, play a game of checkers, and sign out at will.

### Glossary and Acronyms

| Term | Definition |
|------|------------|
| VO | Value Object |
| PR | Player |
| OP | Opponent |


## Requirements

This section describes the features of the application.

Users must be able to sign in to the website with a username of their choice. After signing in, users must be able to
choose an opponent for a checkers game. The program must connect the two players together and set up a WebCheckers
game in accordance to the American Rules. The application must display all the components and information of the 
checkers board, just as if it was viewable in real-life. The players must be able to resign from the game at any point. 
If no player resigns, the game continues until the end until a winner is determined. Some additional modifications that 
are also to be made are: AI Opponent and Replay Mode. Instead of selecting a human opponent, a player would have the 
ability to face an AI with a difficulty setting. Additionally, players would be able to save their previous games and
play them back through a Replay Mode from the home screen.

### Definition of MVP

* Users can sign in with a unique name of their choice.
* Two users can play a game of Checkers according to the American Rules.
* Users can sign-out/resign at any point, which would end the game.

### MVP Features

* Connection - Server/Client logic
* Display - Graphical Display of the state of the game
* Possible Moves - Checkers game logic
* AI Opponent - Artificial intelligence opponent creation
* Replay Mode - Save and replay previously played checkers games


### Roadmap of Enhancements

* Sign-In - Users are given the ability to sign in to the application with a username of their choice.
* Start a Game - Users are able to start a checkers game.
* Connection - Major Server/Client communication is defined.
* Game Logic - Rules and processes for the checkers game are defined.
* Display - Basic graphical/visual components are created to display information regarding the game.
* AI Component - All components for a fully-functioning AI checkers opponent are created.
* Replay Mode - Allows users to save and replay previously played games

## Application Domain

This section describes the application domain.

![The WebCheckers Domain Model](WebCheckers-Domain-Model.png)

The Player (user) is represented on the domain model as an entity that plays the checkers game and takes turns making 
moves that could either be a simple, single-jump, or multiple-jump move. Each move is performed by a Piece that is on 
the checkers board via a checkers square (or Space). Each checkers game contains one board with 64 checker squares and 
24 Pieces. The Checkers Game domain entity keeps track of the current state of the game (red won, white won, ongoing). 
Additionally, the Checkers Game can save the game (once finished), to be replayed back. An AI Opponent has all the same 
functions as a human player (with the exception being autonomy), therefore, the AI Opponent also interacts withe 
Checkers Game in a similar way as a human would. AI Opponents range in difficulty (Easy, Medium, Hard).

## Architecture and Design

This section describes the application architecture.

### Summary

The following Tiers/Layers model shows a high-level view of the webapp's architecture.

![The Tiers & Layers of the Architecture](architecture-tiers-and-layers.png)

As a web application, the user interacts with the system using a browser. The client-side of the UI is composed of HTML 
pages with some minimal CSS for styling the page.  There is also some JavaScript that has been provided to the team by 
the architect. The server-side tiers include the UI Tier that is composed of UI Controllers and Views. Controllers are 
built using the Spark framework and View are built using the FreeMarker framework.  The Application and Model tiers are 
built using plain-old Java objects (POJOs). Details of the components within these tiers are supplied below.

### Overview of User Interface

This section describes the web interface flow; this is how the user views and interacts
with the WebCheckers application.

![The WebCheckers Web Interface Statechart](web-interface-placeholder.png)

The user will begin their checkers experience on the Home page. They'll see "Welcome!" and a sign-in button.
They will click the button and go to the sign-in page which has a text box and a submit button to enter their name.
If the user enters an invalid name, they'll stay on the sign in page and be prompted to enter a new name. 

Once the user enters and submits a valid name, they will go back to the Home page, which will be updated now that the user is 
logged into the system. There will be a navigation bar with the tabs "myHome" and "sign out". If the user selects the 
sign out tab, they'll be directed to a page that'll ask them to confirm their choice. If they choose to sign out, 
they'll be directed back to the sign-in page. If, instead they choose to stay signed in, they'll return to their "myHome"
page. 

On the myHome page, they will now see a list of all the players in the lobby (except themselves). Next to each
user's name, there is a button to start a game with that user. If the user chooses to initiate a checkers game with 
another currently logged in player, they will click the "Start a Game" button near their desired opponent's name. The 
selected opponent will go to the GetGameRoute when their browser detects that someone chose to play they in a game. If 
opponent is not already in a game, both users will be directed to a view of the game. This view includes a checkers board 
with pieces pre-placed on the board. There will be an Info Panel at the top left that will highlight the player whose 
turn it is, and a Control Panel that will allow the players to undo a move, submit their move, or resign the game.

From here, a standard game of checkers will be played until one player wins or resigns. After the game ends, the users will 
be returned to their respective myHome tabs. They'll be able to repeat the process and either choose a new opponent 
or sign out.

### UI Tier
> _Provide a summary of the Server-side UI tier of your architecture.
> Describe the types of components in the tier and describe their
> responsibilities.  This should be a narrative description, i.e. it has
> a flow or "story line" that the reader can follow._

The most important aspect of any application for the user is the UI Tier. The User only sees and interacts with what is 
on their screen, so it is of utmost importance for it to be top-notch! The UI Tier at the server side is nothing more 
than just text on a black screen, but that text is able to understand and respond to the user for an overall enjoyable
experience.

The tier begins with the WebServer class, which initialises the View Elements of the website by calling the appropriate 
HTTP Protocol handling class through Dependency Injection. What that means in English, is that WebServer is the stage 
upon which the game is set. The first class to be greet the user is the class that handles the Home page of the Online 
WebCheckers. The "GetHomeRoute" class is invoked and the user is able to interact with the Home Page. 

When at the Home Page, the user has the ability to see the number of players online and currently playing the game, 
and can join them by using the Sign In option.

![The Home Page](HomePage.png) 

Once they decide to Sign In, the WebServer then serves the SignIn page, handled by the "GetSignIn" class, to the user. 
Here the user has the ability to Log in using a valid name, which is checked by the application, and can then join the 
Player Lobby.

![The Player Lobby](PlayerLobby.png)  

The Player lobby lets a player choose their opponent to play against, with clicking on the "Start A Game" button. 
Once the button is pressed, the Application begins to form a game session for the players and the users can now play the 
game.

![The StartAGame Sequence Diagram](StartAGame.png)

> _At appropriate places as part of this narrative provide one or more
> static models (UML class structure or object diagrams) with some
> details such as critical attributes and methods._

> _You must also provide any dynamic models, such as statechart and
> sequence diagrams, as is relevant to a particular aspect of the design
> that you are describing.  For example, in WebCheckers you might create
> a sequence diagram of the `POST /validateMove` HTTP request processing
> or you might show a statechart diagram if the Game component uses a
> state machine to manage the game._
>

> _If a dynamic model, such as a statechart describes a feature that is
> not mostly in this tier and cuts across multiple tiers, you can
> consider placing the narrative description of that feature in a
> separate section for describing significant features. Place this after
> you describe the design of the three tiers._


### Application Tier
> _Provide a summary of the Application tier of your architecture. This
> section will follow the same instructions that are given for the UI
> Tier above._
*** DESCRIBE THE COMPONENTS ***

The Application Tier of this application consists of 3 components: Game, GameCenter, and PlayerLobby.
A Game object represents a game event in the application. It contains a "redPlayer" (a Player object that 
represents the user who interacts with the red pieces on the checker board), a "whitePlayer" (a Player object that
represents the user who interacts with the white pieces on the checker board), and an "id" (a unique number used to 
identify Game objects across multiple user sessions). The GameCenter component holds a PlayerLobby object, 
"playerLobby", which is a collection of all the users logged into the webapp across all games. PlayerLobby contains 
a structure "players" (a HashMap that maps each Player object to their name), and a GameCenter "gameCenter" that
ties the lobby to a single GameCenter.

![The Game Static Diagram](Game.png)

*** DESCRIBE THE RESPONSIBILITIES/JOBS/USES OF THESE COMPONENTS IN A NARRATIVE STYLE ***

When the application is started up, a GameCenter and a PlayerLobby are both also constructed. These both store 
application-wide data. When players sign-in to the checkers app, they are added to the PlayerLobby HashMap. The names
listed in the Player Lobby are the names saved in the PlayerLobby object. This is the main responsibility of the
PlayerLobby: to hold a record of all the Player objects, and for ease of display, their names as Strings. Once a user 
can see the other signed-in users in the lobby, they can choose one to play a game against. Once the "Start a Game" 
button is pressed and the request is validated, a new Game object is created. This Game object is responsible for 
representing a checker game and it's players. Therefore, the user who initiates the game is assigned at the red player
and the user that was chosen by the first user is then added to the game as the white player. Both users' Player object
representations are held in the Game object, as well as the unique id that allows the application to differentiate 
different games as well as to tie users in different sessions to the same game.

Currently, the GameCenter is not used in full as designed. When multiple games are being held, all the Game instances 
and players will be held in the application-wide gameCenter. The gameCenter will then keep track of the wins and 
losses across the application, the number of users and games, and other features like saved games or the all-time user
with the highest number of wins. Currently, the design implemented does not include these planned responsibilities
of the GameCenter class.

### Model Tier
> _Provide a summary of the Application tier of your architecture. This
> section will follow the same instructions that are given for the UI
> Tier above._
>

The Model Tier provides the game logic for the system. It takes the input from the UI tier and applies it to the inner
workings of the games. It brings the different attributes of the players and checkers game into one tier where they dictate
how both the game and player select work. 

For the most important part of any game, the people who play it, there is the player object. It holds various booleans determining 
what play states they are in, how they relate to their opponents, and the unique attributes each player has. 

The players need a checkerboard to play on. That's where BoardView comes in. BoardView acts as a representation of a checkerboard
comprised of 8 rows of spaces. The spaces on each row have a color, and an indicator of whether there is a piece on it. 
These keep track of the board in the current game state.

Speaking of the pieces, the Piece class represents a checker piece including what color it is and if it is a king. Through
the UI tier, the players move these pieces across the BoardView until someone wins. 

In order to maintain the good practice of High Cohesion and Low Coupling, the Player class does not directly interact with the BoardView, but 
rather serves a representation of the people who are playing the checkers game. The inputs from the UI are converted to 
the Move objects change the position  of pieces on the BoardView. The Move object has 2 Position objects, a denotation of 
the row and column of a change, one for the start and end of a piece movement. 

![The Model Diagram](ModelDiagram.png)

### Design Improvements
> _Discuss design improvements that you would make if the project were
> to continue. These improvement should be based on your direct
> analysis of where there are problems in the code base which could be
> addressed with design changes, and describe those suggested design
> improvements. After completion of the Code metrics exercise, you
> will also discuss the resutling metric measurements.  Indicate the
> hot spots the metrics identified in your code base, and your
> suggested design improvements to address those hot spots._

## Testing

### Acceptance Testing
> _Report on the number of user stories that have passed all their
> acceptance criteria tests, the number that have some acceptance
> criteria tests failing, and the number of user stories that
> have not had any testing yet. Highlight the issues found during
> acceptance testing and if there are any concerns._

Of the 5 user stories that we have worked on, 3 of them pass all of their acceptance criteria, and the other 2 pass some
of their acceptance criteria.

Turns - Players successfully switch between turns when one finishes. Although not a bug with turns directly,
both players see their own pieces as red.

Quit Game - Players can easily quit a game by clicking the resign button or closing the window. The player still playing 
the game doesn't know that the other person left. Also, the session of the player who was still playing can no longer see
other players in the player lobby after they sign out once they quit a game.

Sign Out - Players are taken to a sign out screen upon signing out and are no longer shown in the home page to other 
players.

Illegal Moves - The player cannot move pieces on top of other pieces, or on a white space. However, other forms of 
Illegal moves such as moving across 2 diagonal spaces aren't prohibited.

Simple Move - The player can drag and drop the selected piece to their desired space, but the change doesn't persist when
their turn ends.

### Unit Testing and Code Coverage
> _Discuss your unit testing strategy. Report on the code coverage
> achieved from unit testing of the code base. Discuss the team's
> coverage targets, why you selected those values, and how well your
> code coverage met your targets. If there are any anomalies, discuss
> those._

Our Unit Testing strategy was to start with the Model classes then work our way up to the UI classes. This way,
we could incrementally test our code and use less mocks classes in the UI tests, allowing us to simulate the
exact behavior of the UI classes as they are used when the web application is run. 

Our code coverage is currently 100% for the application tier, 90% for the model tier and 35% for the UI tier. Our 
code coverage targets were 95-100% for the Model and Application tiers and 75-80% for the UI tier. This is because 
the tests for the Model and Application Tier were central to the business logic of the web application. We held testing
to a higher standard for the classes in these tiers because they are essential for many of the features of the 
application.

We reached our targets for both Model and Application tier, but in our results for the UI tier were an outlier.
As a team, we struggled with testing the Routes, and some tests are absent. In the next release of the Checkers Web 
Application, these will be implemented.


