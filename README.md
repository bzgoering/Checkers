Recreation of player vs player checkers.

Rules are mostly the same:
  - The bottom (Blue) goes first.
  - only forward diagonal moves are allowed.
  - valid moves are final (no undo).
  - multi taking of pieces is not permitted, one piece at a time.
  - The winner goes to the last piece standing or the most points. (Can only be seen through log).
  
How to take a piece: 
  - move a piece to the desired piece to jump. 
  - Then, move the piece to the desired tile. 
  - Any invalid moves will reset to the last valid spot. 

Future updates:
  - finish game mechanics.
  - player vs computer with a basic algorithm(takes piece if available, random plays otherwise).
  - Code clean-up.

About code:
  - Main runs program, opening the GUI and starting the controller
  - Sample.fxml is the GUI presentation
  - SampleController initializes any GUI variables and initializes the game at start-up.
  - draggable will have the user's limitations (Checkers Rules).
