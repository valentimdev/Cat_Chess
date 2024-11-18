package piece;

import main.GamePanel;
import main.Type;

public class Pawn extends Piece {
    public Pawn(int color, int col, int row) {
        super(color, col, row);//constructor
        type = Type.PAWN;

        if (color == GamePanel.WHITE) {
            //definindo cor da peça
            image = getImage("/piece/peao_w");
        } else {
            image = getImage("/piece/peao_b");
        }
    }
    public boolean canMove(int targetCol, int targetRow) {
        if(isWithinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)) {
            //define o valor de movimento baseado na sua cor
            int moveValue;
            if (color == GamePanel.WHITE) {
                moveValue = -1;
            } else {
                moveValue = 1;
            }
            //checa se tem uma peça na frente
            hittingP = gettingHitP(targetCol, targetRow);
            //movimento de 1 quadrado do peao
            if (targetCol == preCol && targetRow == preRow + moveValue && hittingP == null) {
                return true;
            }
            // movimento de 2 quadrados do peao(movimento inicial)
            if (targetCol == preCol && targetRow == preRow + moveValue * 2 // && hittingP == null
                    && !pieceIsOnStraightLine(preCol, preRow + moveValue)  // verifica se a casa intermediária está livre
                  &&  !moved) {  // verifica se ja se moveu
                return true;
            }
            //captura na diagonal
            if(Math.abs(targetCol - preCol) == 1 && targetRow == preRow + moveValue && hittingP != null
            && hittingP.color != color) {
                return true;
            }
            //en passant
            if(Math.abs(targetCol - preCol) == 1 && targetRow == preRow + moveValue){
                for(Piece piece: GamePanel.simPieces){
                    if(piece.col == targetCol && piece.row == preRow && piece.twoStepped == true){
                        hittingP = piece;
                        return true;
                    }
                }
            }

        }return false;
    }
}


