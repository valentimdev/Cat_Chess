# Cat Chess - A Charming Twist on the Classic Game

**Cat Chess** is a creative and delightful project that reimagines the classic game of chess with a feline twist. Every chess piece is transformed into an adorable cat character, bringing personality and fun to each move on the board. From playful pawns to majestic kings, this game combines strategic depth with a whimsical and heartwarming theme. Whether you're a chess master, a casual player, or simply a cat lover, **Cat Chess** offers an engaging and visually appealing experience for all.

![Cat Chess](https://github.com/user-attachments/assets/aa705616-4d50-4c1b-b549-6baf0d773f83)

---

## Meet the Pieces

Each piece is lovingly designed as a cute cat, adding charm and character to the game. Below are the **Cat Chess** pieces:

### **King**
![King White](https://github.com/user-attachments/assets/3a7ad276-bb30-41d6-bb18-9d451529c91d) ![King Black](https://github.com/user-attachments/assets/af1c3009-9494-4f85-b214-8fc7b703cffc)

### **Queen**
![Queen White](https://github.com/user-attachments/assets/59c17af2-f6e6-4da1-b79b-fda751524e09) ![Queen Black](https://github.com/user-attachments/assets/0d0f6587-133a-4b61-ba9f-5e1a92dfb722)

### **Pawn**
![Pawn White](https://github.com/user-attachments/assets/d7e92a95-0047-4ae4-9c3b-d5473520468c) ![Pawn Black](https://github.com/user-attachments/assets/56ae6ffa-d384-4388-b6d0-2eab8c2c475e)

### **Rook**
![Rook White](https://github.com/user-attachments/assets/7b9513ad-b114-450f-befd-d50e441e6dac) ![Rook Black](https://github.com/user-attachments/assets/55d81aa8-1e48-4da6-af2e-163541dddd67)

### **Knight**
![Knight White](https://github.com/user-attachments/assets/7e8c1ee2-03ea-4664-8353-6db216b77d2a) ![Knight Black](https://github.com/user-attachments/assets/165b17ec-97bd-45e9-9d6a-a0dbcedcbc44)

### **Bishop**
![Bishop White](https://github.com/user-attachments/assets/2aad4c52-3c31-4d53-919d-0e0c288e1bd1) ![Bishop Black](https://github.com/user-attachments/assets/e829f62d-53fe-41b3-bd44-89904150b55d)

---

## How to Play

Currently, **Cat Chess** is under development, and a ready-to-use executable is not yet available. However, you can run the project directly from the source code if you have **Java** installed.

### Steps to Play:
1. Clone or download the repository.
2. Make sure you have Java installed.
3. Compile and run the project from the source code.



## **Explaining the Classes**
# GamePanel Class 

## **Initialization and Setup Methods**

### `public GamePanel()`
- Constructor of the class that initializes the game panel:
  - Sets the preferred size of the panel.
  - Configures the background color as black.
  - Adds mouse event listeners.
  - Initializes the chess pieces on the board (`setPieces`) and creates an initial copy of them in `simPieces`.

### `public void launchGame()`
- Starts the game thread, which runs the main game loop.

### `public void setPieces()`
- Initializes the pieces on the board in their starting positions, for both white and black pieces.

### `private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target)`
- Copies the list of pieces from a source (`source`) to a target (`target`). Used to synchronize the actual pieces (`pieces`) with the simulated ones (`simPieces`).

---

## **Main Game Loop Methods**

### `@Override public void run()`
- Contains the **main game loop**:
  - Ensures the `update` method (game logic) and `repaint` method (rendering) are executed 60 times per second.

### `private void update()`
- Updates the game state:
  - Manages promotion behavior, piece movements, and victory or draw conditions.
  - Checks for mouse interactions and applies logic for moving or resetting pieces.

### `public void paintComponent(Graphics g)`
- Renders the game:
  - Draws the board, pieces, visual effects for player actions (valid/invalid moves), promotions, and victory or draw messages.

---

## **Game Mechanics Methods**

### `private void simulate()`
- Simulates the movement of the active piece (`activeP`):
  - Updates the temporary position of the piece being moved with the mouse.
  - Checks if the move is valid and simulates captures or special moves, like castling.

### `private boolean canPromote()`
- Checks if a pawn has reached the last rank and is eligible for promotion:
  - Populates the list of promotion pieces (`promoPieces`) for selection.

### `private void promoting()`
- Handles the pawn promotion process:
  - Allows the player to select a piece for promotion by clicking on it.

### `private boolean isIllegal(Piece king)`
- Checks if a move places the king in check:
  - Returns `true` if any enemy piece can capture the king in the simulated position.

### `private boolean isCheckmate()`
- Determines if the game is in checkmate:
  - Verifies if the king is in check and if there are any valid moves to block or capture the checking piece.

### `private boolean kingCanMove(Piece king)`
- Checks if the king can move to any valid position around it.

### `private boolean isValidMove(Piece king, int colPlus, int rowPlus)`
- Simulates king movements in specific directions and checks their validity.

### `private boolean isStalemate()`
- Determines if the game is in stalemate:
  - This happens if the king cannot move and no other pieces on the board can make valid moves.

### `private boolean opponentCanCaptureKing()`
- Checks if any opponent piece can capture the current player's king.

### `private boolean isKingInCheck()`
- Determines if the king is in check and identifies the attacking piece (`checkingP`).

---

## **Auxiliary and Logical Methods**

### `public void checkCastling()`
- Handles the logic for castling, adjusting the position of the corresponding rook.

### `private Piece getKing(boolean opponent)`
- Retrieves the king of the current player or the opponent, depending on the `opponent` parameter.

### `public void changePlayer()`
- Switches the turn between players and resets the state of some pieces, such as the "en passant" status.

# `Board` Class Explanation

The `Board` class is responsible for rendering and defining the chessboard for the Cat Chess game. It includes functionality to display an 8x8 grid of squares in alternating colors, which forms the playing area.

## Fields

### `MAX_COL` and `MAX_ROW`
- **Type**: `int`
- **Purpose**: Define the dimensions of the chessboard (8x8 grid).
- **Value**: Both are set to 8, representing the 8 rows and 8 columns of a standard chessboard.

### `SQUARE_SIZE`
- **Type**: `int`
- **Purpose**: Specifies the size of each square on the chessboard in pixels.
- **Value**: 75. This is slightly reduced from the typical size (100) for better compatibility with smaller screens.

### `HALF_SQUARE_SIZE`
- **Type**: `int`
- **Purpose**: Half the size of each square, calculated for convenience in positioning elements on the board.
- **Value**: `SQUARE_SIZE / 2`.

## Methods

### `draw(Graphics2D g2)`
- **Parameters**: 
  - `g2` (`Graphics2D`): Used to perform 2D drawing on the screen.
- **Purpose**: 
  - Draws an 8x8 chessboard with alternating light and dark squares.
  - The colors of the squares are customizable using RGB values.
- **Implementation**:
  - Uses two nested `for` loops to iterate through rows and columns of the board.
  - For each square, determines whether it should be light or dark by calculating `(row + col) % 2`. 
    - If the result is `0`, the square is colored light (`#90B493` - green).
    - Otherwise, it is colored dark (`#706677` - purple).
  - Each square is filled using `g2.fillRect`, positioned at `(col * SQUARE_SIZE, row * SQUARE_SIZE)`, and sized according to `SQUARE_SIZE`.

### Notes
- The color palette used in the board design creates a visually appealing chessboard with a green and purple theme.
- The use of modular arithmetic ensures that the checkerboard pattern is properly maintained.


# `Mouse` Class Explanation

The `Mouse` class extends `MouseAdapter` to handle mouse input events for the Cat Chess game. It tracks mouse movement, dragging, and button states, which can be used to interact with the game.

## Fields

### `x`
- **Type**: `int`
- **Purpose**: Stores the current x-coordinate of the mouse pointer.

### `y`
- **Type**: `int`
- **Purpose**: Stores the current y-coordinate of the mouse pointer.

### `pressed`
- **Type**: `boolean`
- **Purpose**: Tracks the state of the mouse button.
  - `true`: Mouse button is pressed.
  - `false`: Mouse button is released.

## Methods

### `mousePressed(MouseEvent e)`
- **Purpose**: Sets the `pressed` field to `true` when the mouse button is pressed.
- **Parameters**: 
  - `e` (`MouseEvent`): Contains information about the mouse event.
- **Overrides**: Method from `MouseAdapter`.

### `mouseReleased(MouseEvent e)`
- **Purpose**: Sets the `pressed` field to `false` when the mouse button is released.
- **Parameters**:
  - `e` (`MouseEvent`): Contains information about the mouse event.
- **Overrides**: Method from `MouseAdapter`.

### `mouseDragged(MouseEvent e)`
- **Purpose**: Updates the `x` and `y` fields with the current position of the mouse while the button is pressed and the mouse is moved.
- **Parameters**:
  - `e` (`MouseEvent`): Contains the x and y coordinates of the mouse.
- **Overrides**: Method from `MouseAdapter`.

### `mouseMoved(MouseEvent e)`
- **Purpose**: Updates the `x` and `y` fields with the current position of the mouse when it is moved without any button pressed.
- **Parameters**:
  - `e` (`MouseEvent`): Contains the x and y coordinates of the mouse.
- **Overrides**: Method from `MouseAdapter`.

## Notes
- The `Mouse` class provides essential input handling, enabling smooth interaction with the game interface.
- Tracking both mouse movement (`mouseMoved`, `mouseDragged`) and button state (`mousePressed`, `mouseReleased`) allows for versatile gameplay interactions such as selecting and moving pieces.
- The use of the `MouseAdapter` superclass simplifies the implementation by providing default, no-op behavior for unused mouse event methods.

# `Type` Enum Explanation

The `Type` enum defines the different types of chess pieces available in the Cat Chess game. Enumerations like this are used to represent a fixed set of constants, making the code more readable and type-safe.

## Enum Constants

### `PAWN`
- **Description**: Represents a pawn, the most numerous and weakest piece in chess. Pawns move forward and attack diagonally.

### `ROOK`
- **Description**: Represents a rook, a powerful piece that moves horizontally or vertically across the board.

### `KNIGHT`
- **Description**: Represents a knight, a unique piece that moves in an L-shape and can jump over other pieces.

### `BISHOP`
- **Description**: Represents a bishop, which moves diagonally across the board.

### `QUEEN`
- **Description**: Represents the queen, the most powerful piece, capable of moving horizontally, vertically, or diagonally.

### `KING`
- **Description**: Represents the king, the most crucial piece in the game. The objective of the game is to checkmate the opposing king. The king can move one square in any direction.

## Purpose
- The `Type` enum allows easy identification and categorization of chess pieces.
- Using this enum ensures that only valid piece types are used in the game's logic.

## Notes
- The `Type` enum improves code readability and reduces the risk of using invalid piece types.
- It can be used in combination with other classes (e.g., `Piece` or `Board`) to define the behavior and characteristics of each chess piece.


 
 
 # Piece Class Documentation

The `Piece` class represents a chess piece in the game. It includes attributes for the piece's type, color, position, and logic for movement and interactions with other pieces.

## Class Attributes

- **`Type type`**: The type of the piece (e.g., PAWN, ROOK, etc.).
- **`BufferedImage image`**: The visual representation of the piece.
- **`int x, y`**: The pixel coordinates of the piece on the board.
- **`int col, row`**: The current column and row of the piece on the board grid.
- **`int preCol, preRow`**: The previous column and row of the piece.
- **`int color`**: The color of the piece (e.g., 0 for white, 1 for black).
- **`Piece hittingP`**: Stores the reference to a piece being captured or blocked.
- **`boolean moved`**: Indicates whether the piece has moved from its initial position.
- **`boolean twoStepped`**: Specific to pawns, indicates if the piece moved two squares forward in its last move.

---

## Constructor

### `Piece(int color, int col, int row)`
Initializes a new piece with the given color, column, and row.
- **Parameters**:
  - `int color`: The color of the piece.
  - `int col`: The initial column position.
  - `int row`: The initial row position.
- **Actions**:
  - Sets the initial position and loads the piece's image.
  - Updates the pixel position using `updatePosition`.

---

## Methods

### `BufferedImage getImage(String imagePath)`
Loads the image of the piece from the given path.
- **Parameters**:
  - `String imagePath`: The file path for the piece's image.
- **Returns**:
  - The loaded image as a `BufferedImage`.

---

### `int getX(int col)`
Calculates the X-coordinate on the board for a given column.
- **Parameters**:
  - `int col`: The column index.
- **Returns**:
  - The X-coordinate in pixels.

### `int getY(int row)`
Calculates the Y-coordinate on the board for a given row.
- **Parameters**:
  - `int row`: The row index.
- **Returns**:
  - The Y-coordinate in pixels.

### `int getCol(int x)`
Converts a pixel-based X-coordinate into a column index.
- **Parameters**:
  - `int x`: The X-coordinate in pixels.
- **Returns**:
  - The column index.

### `int getRow(int y)`
Converts a pixel-based Y-coordinate into a row index.
- **Parameters**:
  - `int y`: The Y-coordinate in pixels.
- **Returns**:
  - The row index.

### `int getIndex()`
Retrieves the index of the piece in the `GamePanel.simPieces` list.
- **Returns**:
  - The index of the piece.

---

### `void updatePosition()`
Updates the piece's position based on its column and row.
- **Actions**:
  - Adjusts pixel coordinates (`x`, `y`) based on grid position.
  - Tracks changes for pawn-specific mechanics like `twoStepped`.

### `void resetPosition()`
Resets the piece to its previous position.

---

### `boolean canMove(int targetCol, int targetRow)`
Checks if the piece can move to a specific column and row.
- **Returns**:
  - Always `false` in the base class (meant to be overridden).

---

### `boolean isWithinBoard(int targetCol, int targetRow)`
Validates if the target position is within the board's boundaries.
- **Returns**:
  - `true` if the position is valid, otherwise `false`.

### `Piece gettingHitP(int targetCol, int targetRow)`
Finds the piece occupying the target position.
- **Returns**:
  - The `Piece` at the target position or `null` if empty.

### `boolean isSameSquare(int targetCol, int targetRow)`
Checks if the target position matches the current position.
- **Returns**:
  - `true` if positions match, otherwise `false`.

### `boolean isValidSquare(int targetCol, int targetRow)`
Validates if the target square is accessible.
- **Actions**:
  - Checks if the square is empty or occupied by an opponent piece.
- **Returns**:
  - `true` if the square is valid, otherwise `false`.

---

### `boolean pieceIsOnStraightLine(int targetCol, int targetRow)`
Checks for obstacles when moving in straight lines.
- **Returns**:
  - `true` if a piece is blocking the path, otherwise `false`.

### `boolean pieceIsOnDiagonalLine(int targetCol, int targetRow)`
Checks for obstacles when moving diagonally.
- **Returns**:
  - `true` if a piece is blocking the path, otherwise `false`.

---

### `void draw(Graphics2D g2)`
Draws the piece on the board.
- **Parameters**:
  - `Graphics2D g2`: The graphics context for rendering.
- **Actions**:
  - Draws the piece's image at its current pixel position.

---

## Usage Notes
- The `Piece` class is a base class and is expected to be extended for specific piece types (e.g., Pawn, Rook).
- Logic for movement and capture is implemented generically, with certain methods meant to be overridden in subclasses.


# Pawn Class Documentation

The `Pawn` class extends the `Piece` class and represents a pawn in the chess game. It overrides the movement logic specific to pawns and handles pawn-specific actions like en passant and two-square initial movement.

## Constructor

### `Pawn(int color, int col, int row)`
The constructor initializes a new pawn with a specific color, column, and row.
- **Parameters**:
  - `int color`: The color of the pawn (`GamePanel.WHITE` for white, `GamePanel.BLACK` for black).
  - `int col`: The initial column of the pawn.
  - `int row`: The initial row of the pawn.
- **Actions**:
  - Sets the type of the piece to `Type.PAWN`.
  - Loads the appropriate image for the pawn based on its color (either white or black).

---

## Methods

### `boolean canMove(int targetCol, int targetRow)`
Determines if the pawn can move to the target column and row.
- **Parameters**:
  - `int targetCol`: The column the pawn is trying to move to.
  - `int targetRow`: The row the pawn is trying to move to.
- **Returns**:
  - `true` if the pawn can legally move to the target square, otherwise `false`.
- **Logic**:
  - Checks if the target square is within the board and not the same square as the current one.
  - Determines if the pawn can move:
    1. **One square forward** (if the square is empty).
    2. **Two squares forward** (if it hasn’t moved yet and the path is clear).
    3. **Capture diagonally** (if an opponent’s piece is present).
    4. **En passant** (if the conditions for this special move are met).

---

## Usage Notes

- The `Pawn` class extends the base `Piece` class, so it inherits general piece properties such as position, color, and image handling.
- The primary difference for the pawn is in its movement, which includes special rules like moving two squares on the first move and en passant captures.
- The `canMove` method handles all of the specific rules for pawn movement, checking both normal moves and special cases like captures and en passant.

# Queen Class Documentation

The `Queen` class extends the `Piece` class and represents the queen in the chess game. The queen can move vertically, horizontally, and diagonally, combining the abilities of both the rook and bishop. This class overrides the `canMove` method to implement the queen's movement logic.

## Constructor

### `Queen(int color, int col, int row)`
The constructor initializes a new queen with a specific color, column, and row.
- **Parameters**:
  - `int color`: The color of the queen (`GamePanel.WHITE` for white, `GamePanel.BLACK` for black).
  - `int col`: The initial column of the queen.
  - `int row`: The initial row of the queen.
- **Actions**:
  - Sets the type of the piece to `Type.QUEEN`.
  - Loads the appropriate image for the queen based on its color (either white or black).

---

## Methods

### `boolean canMove(int targetCol, int targetRow)`
Determines if the queen can move to the target column and row.
- **Parameters**:
  - `int targetCol`: The column the queen is trying to move to.
  - `int targetRow`: The row the queen is trying to move to.
- **Returns**:
  - `true` if the queen can legally move to the target square, otherwise `false`.
- **Logic**:
  - The queen can move in two primary ways:
    1. **Vertically or Horizontally** (like a rook):
       - The target square must be either in the same row or column as the current position.
       - The path must be clear (checked by `pieceIsOnStraightLine`).
    2. **Diagonally** (like a bishop):
       - The difference between the target column and row must be the same (`Math.abs(targetCol - preCol) == Math.abs(targetRow - preRow)`).
       - The path must be clear (checked by `pieceIsOnDiagonalLine`).
  - The `isValidSquare` method is used to check if the target square is empty or occupied by an opponent’s piece.

---

## Usage Notes

- The `Queen` class extends the base `Piece` class, so it inherits general piece properties such as position, color, and image handling.
- The queen's movement combines the movements of the rook and bishop, allowing it to move in straight lines (vertical or horizontal) or along diagonals.
- The `canMove` method ensures that the queen can only move to valid squares and that the path is clear of other pieces.



# Rook Class Documentation

The `Rook` class extends the `Piece` class and represents the rook in the chess game. The rook can move any number of squares along a row or column, but it cannot move diagonally. This class overrides the `canMove` method to implement the rook's movement logic.

## Constructor

### `Rook(int color, int col, int row)`
The constructor initializes a new rook with a specific color, column, and row.
- **Parameters**:
  - `int color`: The color of the rook (`GamePanel.WHITE` for white, `GamePanel.BLACK` for black).
  - `int col`: The initial column of the rook.
  - `int row`: The initial row of the rook.
- **Actions**:
  - Sets the type of the piece to `Type.ROOK`.
  - Loads the appropriate image for the rook based on its color (either white or black).

---

## Methods

### `boolean canMove(int targetCol, int targetRow)`
Determines if the rook can move to the target column and row.
- **Parameters**:
  - `int targetCol`: The column the rook is trying to move to.
  - `int targetRow`: The row the rook is trying to move to.
- **Returns**:
  - `true` if the rook can legally move to the target square, otherwise `false`.
- **Logic**:
  - The rook can move in straight lines either horizontally (along a row) or vertically (along a column).
  - The target column or row must match the rook's current position (`targetCol == preCol` or `targetRow == preRow`).
  - The path must be clear of other pieces, which is checked by the `pieceIsOnStraightLine` method.
  - The target square must either be empty or contain an opponent's piece, verified by the `isValidSquare` method.

---

## Usage Notes

- The `Rook` class inherits general piece properties from the `Piece` class, such as position, color, and image handling.
- The rook's movement is limited to straight lines, either vertically or horizontally, and it cannot move diagonally.
- The `canMove` method ensures that the rook can only move to valid squares and that the path is clear of any other pieces.


# Knight Class Documentation

The `Knight` class extends the `Piece` class and represents the knight piece in chess. The knight moves in an "L" shape: two squares in one direction (either horizontal or vertical) and one square perpendicular to that, or one square in one direction and then two squares perpendicular to that. The knight is unique because it can jump over other pieces.

## Constructor

### `Knight(int color, int col, int row)`
The constructor initializes a new knight with a specific color, column, and row.
- **Parameters**:
  - `int color`: The color of the knight (`GamePanel.WHITE` for white, `GamePanel.BLACK` for black).
  - `int col`: The initial column of the knight.
  - `int row`: The initial row of the knight.
- **Actions**:
  - Sets the type of the piece to `Type.KNIGHT`.
  - Loads the appropriate image for the knight based on its color (either white or black).

---

## Methods

### `boolean canMove(int targetCol, int targetRow)`
Determines if the knight can move to the target column and row.
- **Parameters**:
  - `int targetCol`: The column the knight is trying to move to.
  - `int targetRow`: The row the knight is trying to move to.
- **Returns**:
  - `true` if the knight can legally move to the target square, otherwise `false`.
- **Logic**:
  - The knight moves in an "L" shape: either 2 squares in one direction (horizontal or vertical) and 1 square perpendicular to that, or 1 square in one direction and 2 squares perpendicular to that.
  - This is verified using the condition `Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 2`, ensuring the movement follows the correct "L" shape.
  - The target square must be within the board and valid for the knight to land on, verified by the `isValidSquare` method.

---

## Usage Notes

- The `Knight` class inherits general piece properties from the `Piece` class, such as position, color, and image handling.
- The knight's movement is unique because it can "jump" over other pieces. It doesn't need the path to be clear, as long as it moves in an "L" shape.
- The `canMove` method ensures that the knight follows the movement rules for its "L" shape and checks if the target square is valid.

# Bishop Class Documentation

The `Bishop` class extends the `Piece` class and represents the bishop piece in chess. The bishop moves diagonally on the board, covering squares of a single color (either light or dark) throughout the game. It can move any number of squares along a diagonal, as long as the path is unobstructed.

## Constructor

### `Bishop(int color, int col, int row)`
The constructor initializes a new bishop with a specific color, column, and row.
- **Parameters**:
  - `int color`: The color of the bishop (`GamePanel.WHITE` for white, `GamePanel.BLACK` for black).
  - `int col`: The initial column of the bishop.
  - `int row`: The initial row of the bishop.
- **Actions**:
  - Sets the type of the piece to `Type.BISHOP`.
  - Loads the appropriate image for the bishop based on its color (either white or black).

---

## Methods

### `boolean canMove(int targetCol, int targetRow)`
Determines if the bishop can move to the target column and row.
- **Parameters**:
  - `int targetCol`: The column the bishop is trying to move to.
  - `int targetRow`: The row the bishop is trying to move to.
- **Returns**:
  - `true` if the bishop can legally move to the target square, otherwise `false`.
- **Logic**:
  - The bishop moves diagonally, so the difference between the target column and the current column must be equal to the difference between the target row and the current row (i.e., `Math.abs(targetCol - preCol) == Math.abs(targetRow - preRow)`).
  - The target square must be within the board and valid for the bishop to land on, verified by the `isValidSquare` method.
  - Additionally, the bishop checks for other pieces blocking its diagonal path with the `pieceIsOnDiagonalLine` method.

---

## Usage Notes

- The `Bishop` class inherits general piece properties from the `Piece` class, such as position, color, and image handling.
- The bishop moves along diagonal lines and can cover long distances, but it cannot jump over other pieces.
- The `canMove` method ensures that the bishop follows the movement rules for its diagonal path and checks if the target square is valid.



# King Class Documentation

The `King` class extends the `Piece` class and represents the king piece in chess. The king can move one square in any direction (vertically, horizontally, or diagonally). Additionally, the king has the special ability to perform castling under specific conditions.

## Constructor

### `King(int color, int col, int row)`
The constructor initializes a new king with a specific color, column, and row.
- **Parameters**:
  - `int color`: The color of the king (`GamePanel.WHITE` for white, `GamePanel.BLACK` for black).
  - `int col`: The initial column of the king.
  - `int row`: The initial row of the king.
- **Actions**:
  - Sets the type of the piece to `Type.KING`.
  - Loads the appropriate image for the king based on its color (either white or black).

---

## Methods

### `boolean canMove(int targetCol, int targetRow)`
Determines if the king can move to the target column and row.
- **Parameters**:
  - `int targetCol`: The column the king is trying to move to.
  - `int targetRow`: The row the king is trying to move to.
- **Returns**:
  - `true` if the king can legally move to the target square, otherwise `false`.
- **Logic**:
  - The king can move one square in any direction, so the sum of the absolute differences between the target column and the current column, and the target row and the current row, must be equal to 1 (i.e., `Math.abs(targetCol - preCol) + Math.abs(targetRow - preRow) == 1`), or it must be a valid diagonal move (i.e., `Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 1`).
  - The target square must be valid and not occupied by a piece of the same color, verified by the `isValidSquare` method.
  - The king also has the special move known as castling, which allows it to move two squares toward a rook if neither piece has moved, and if the spaces between them are unoccupied.

#### Castling:
- **Right Castling**: If the king has not moved and the target position is two squares to the right (castling toward the right rook), the king checks if the rook has also not moved and if there are no pieces in between.
- **Left Castling**: Similar to right castling, but the king moves two squares to the left, and it checks if there is a clear path and the rook has not moved.

---

## Usage Notes

- The `King` class inherits general piece properties from the `Piece` class, such as position, color, and image handling.
- The king can move one square at a time in any direction, and it is restricted by the edges of the board and the presence of other pieces.
- Castling is a special move that is handled inside the `canMove` method, where the king checks for unblocked paths and un-moved rooks.



