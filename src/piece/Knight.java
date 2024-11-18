package piece;

import main.GamePanel;
import main.Type;

public class Knight extends Piece {
    public Knight(int color, int col, int row) {
        super(color, col, row);

        type = Type.KNIGHT;
        if(color == GamePanel.WHITE){
            image = getImage("/piece/cavalo_w");
        }else{
            image = getImage("/piece/cavalo_b");
        }
    }
    public boolean canMove(int targetCol, int targetRow) {
        if(isWithinBoard(targetRow, targetCol)){//movimentaçao do cavalo acontece na proporçao 2:1 ou 1:2 em L
            if(Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 2){//a multiplicacao aqui tem que dar 2 por conta da forma que o cavalo se movimenta
                if(isValidSquare(targetCol, targetRow)){//checa se o quadrado é valido
                    return true;
                }
            }
        }
        return false;
    }
}
