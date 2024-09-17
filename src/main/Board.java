package main;

import java.awt.*;

public class Board {
    final int MAX_COL = 8;
    final int MAX_ROW = 8;
    public static final int SQUARE_SIZE = 75;//o normal é 100 fiz alterações por conta do meu monitor de note
    public static final int HALF_SQUARE_SIZE = SQUARE_SIZE / 2;

    public void draw(Graphics2D g2){
        //Fazendo o tabuleiro 8x8 dentro desses dois loops

        int c=0;

        for(int row=0; row<MAX_ROW; row++){
            for(int col=0; col<MAX_COL; col++){

                if((row + col) % 2 == 0){
                    g2.setColor(new Color(102,0,0)); // cor dos quadrados claros // rgb 141,145,141 cinza //144,180,147 verde // 114,131,112 verde escuro
                } else {
                    g2.setColor(new Color(0,68,116)); // cor dos quadrados escuros // 112,102,119 roxo
                }
                g2.fillRect(col * SQUARE_SIZE, row * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }

            if(c == 0){
                c = 1;
            }else{
                c = 0;
            }
        }
    }
}
