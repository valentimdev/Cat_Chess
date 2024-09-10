package piece;

import main.GamePanel;

public class Pawn extends Piece {
    public Pawn(int color, int col, int row) {
        super(color, col, row);//constructor

        if(color == GamePanel.WHITE){
            //definindo cor da peça
            image=getImage("/piece/w-pawn");
        }else {
            image=getImage("/piece/b-pawn");
        }   
    }


}
