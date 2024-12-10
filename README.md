Standard recreation of player vs player checkers.

Rules are mostly the same:
  The bottom (Blue) goes first.
  only forward diagonal moves are allowed.
  valid moves are final (no undo).
  multi taking of pieces is permitted.
  
How to take a piece: 
  move a piece to the desired piece to jump. 
  Then, move the piece to the desired tile. 
  Any invalid moves will reset your turn. 

Future updates:
  player vs computer with a basic algorithm(takes piece if available, random plays otherwise).
  Code clean-up with debug.

About code:
  Main runs program, opening the GUI and starting the controller
  Sample.fxml is the GUI presentation
  SampleController initializes any GUI variables and initializes the game at start-up.
  Draggable will have the user's limitations (Checkers Rules).
