package piece;

import main.GamePanel;

public class Knight extends Piece {
    public Knight(int color, int col, int row) {
        super(color, col, row);

        if(color == GamePanel.WHITE){
            image = getImage("/piece/w-knight2");
        }else{
            image = getImage("/piece/b-knight");
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
