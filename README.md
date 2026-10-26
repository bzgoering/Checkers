# Checkers

A player-vs-player checkers game built with JavaFX.

## Tech
- Java 21
- JavaFX 23 (UI built with SceneBuilder)

## How to run
You need **JDK 21+** and the **[JavaFX SDK 23](https://gluonhq.com/products/javafx/)**.

**Eclipse (how it was built):** create a JavaFX project, put the files in the `application` package, and run `Main.java`.

**Command line:**
```bash
# PATH_TO_FX = the lib folder of your JavaFX SDK
javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml -d out *.java
cp *.fxml out/application/ && touch out/application/application.css
java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml -cp out application.Main
```
The app loads an `application.css` stylesheet, which Eclipse's JavaFX template normally creates. If you don't have one, an empty file works.

## How to play
Drag a piece to move it. **Blue** (bottom) goes first.
- Only forward diagonal moves are allowed, and valid moves are final (no undo).
- **To take a piece:** drag your piece onto the enemy piece, then drag it to the tile you want to land on.
- You can only take one piece per move (no multi-jumps).
- An invalid move snaps the piece back to its last valid spot.
- Reach the far row to become a **king** (shown with a gold outline).
- You win by being the last one with pieces left, or by having the most points.

**Open Log** shows the game log (including points). **Reset** restarts the board.

## Planned
- Finish the game mechanics
- Player vs computer (takes a piece if it can, otherwise plays randomly)
- Code clean-up
