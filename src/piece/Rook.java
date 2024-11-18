package piece;

import main.GamePanel;
import main.Type;

public class Rook extends Piece {
    public Rook(int color, int col, int row) {
        super(color, col, row);
        type = Type.ROOK;
        if(color == GamePanel.WHITE){
            image = getImage("/piece/torre_w");
        }else{
            image = getImage("/piece/torre_b");
        }
    }
    public boolean canMove(int targetCol, int targetRow) {

        if(isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)){
            if(targetCol == preCol || targetRow == preRow){
               if(isValidSquare(targetCol, targetRow) && !pieceIsOnStraightLine(targetCol, targetRow)){
                        return true;
                }
            }
        }
        return false;
    }
}
