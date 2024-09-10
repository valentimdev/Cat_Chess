package piece;

import main.GamePanel;

public class King extends Piece {
    public King(int color, int col, int row) {
        super(color, col, row);

        if(color == GamePanel.WHITE){
            image = getImage("/piece/w-king");
        }else{
            image = getImage("/piece/b-king");
        }

    }
    public boolean canMove(int targetCol,int targetRow) {
        if(isWithinBoard(targetCol, targetRow)){
            if(Math.abs(targetCol-preCol) + Math.abs(targetRow-preRow) == 1 ||
            Math.abs(targetCol-preCol) * Math.abs(targetRow-preRow) == 1){
                if(isValidSquare(targetCol, targetRow)){
                    return true;
                }
            }
        }
        return false;
    }

}
