package piece;

import main.Board;
import main.GamePanel;
import main.Type;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Piece {
    public Type type;
    public BufferedImage image;
    public int x, y;
    public int col, row, preCol, preRow;
    public int color;
    public Piece hittingP;
    public boolean moved = false;
    public boolean twoStepped;

    public Piece(int color, int col,int row){
        this.color = color;
        this.col = col;
        this.row = row;
        x=getX(color);
        y=getY(color);
        preCol = col;
        preRow = row;
        this.image=getImage("/piece/rei_w");
        updatePosition();
    }
    public BufferedImage getImage(String imagePatch){
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream(imagePatch + ".png"));

        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }


    public int getX(int col){
        return col * Board.SQUARE_SIZE;
    }
    public int getY(int row){
        return row * Board.SQUARE_SIZE;
    }
    public int getCol(int x){
        return(x + Board.SQUARE_SIZE/2)/Board.SQUARE_SIZE;
    }
    public int getRow(int y){
        return(y + Board.SQUARE_SIZE/2)/Board.SQUARE_SIZE;
    }
    public int getIndex(){
        for(int index=0; index < GamePanel.simPieces.size(); index++){
            if(GamePanel.simPieces.get(index)==this){
                return index;
            }
        }
        return 0;
    }


    public void updatePosition(){

        //checagem do en pasant
        if(type == Type.PAWN){
            if(Math.abs(row - preRow) == 2){
                twoStepped = true;
            }
        }
        x = getX(col);
        y = getY(row);
        preCol = getCol(x);
        preRow = getRow(y);
//        moved = true
    }
    public void resetPosition(){
        col=preCol;
        row=preRow;
        x = getX(col);
        y = getY(row);
    }
    public boolean canMove(int targetCol,int targetRow){
        return false;
    }
    public boolean isWithinBoard(int targetCol,int targetRow){
        if(targetCol >= 0 && targetCol <=7 && targetRow >= 0 && targetRow <=7){
            return true;//checa se a peça esta no tabuleiro
        }
        return false;
    }
    public Piece gettingHitP(int targetCol,int targetRow){
        for(Piece piece : GamePanel.simPieces){
            if(piece.col == targetCol && piece.row == targetRow && piece != this){
                return piece;
            }
        }
        return null;
    }
    public boolean isSameSquare(int targetCol,int targetRow){
        if(targetCol == preCol && targetRow == preRow){
            return true;
        }
        return false;
    }
    public boolean isValidSquare(int targetCol,int targetRow){
        hittingP=gettingHitP(targetCol,targetRow);
        if(hittingP==null){//o quadrado esta vazio
            return true;
        }else{//o quadrado esta ocupado
            if(hittingP.color != this.color){//checando a cor pra ver se pode capturar
                return true;
            }else{//se tem a mesma cor,a peça nao pode mover para aquele quadrado
                hittingP = null;
            }

        }
        return false;
    }
    public boolean pieceIsOnStraightLine(int targetCol,int targetRow){//checar se tem alguma peça na reta que a peça esta tentando se mover
        //peça se movendo pra esquerda
        for (int c = preCol-1; c > targetCol; c--){
            for(Piece piece : GamePanel.simPieces){
                if(piece.col == c && piece.row == targetRow){
                    hittingP = piece;
                    return true;
                }
            }
        }
        //peça se movendo pra direita
        for (int c = preCol+1; c < targetCol; c++){
            for(Piece piece : GamePanel.simPieces){
                if(piece.col == c && piece.row == targetRow){
                    hittingP = piece;
                    return true;
                }
            }
        }
        // peça se movento pra cima
        for (int r = preRow-1; r > targetRow; r--){
            for(Piece piece : GamePanel.simPieces){
                if(piece.col == targetCol && piece.row == r){
                    hittingP = piece;
                    return true;
                }
            }
        }
        //peça se movendo pra baixo
        for (int r = preRow+1; r < targetRow; r++){
            for(Piece piece : GamePanel.simPieces){
                if(piece.col == targetCol && piece.row == r){
                    hittingP = piece;
                    return true;
                }
            }
        }
        return false;
    }
    public boolean pieceIsOnDiagonalLine(int targetCol,int targetRow){//checa se tem alguma peça no caminho na diagonal
        if(targetRow<preRow){
            //superior esquerdo
            for(int c = preCol - 1; c > targetCol; c--){
                int diff = Math.abs(c-preCol);
                for(Piece piece : GamePanel.simPieces){
                    if(piece.col == c && piece.row == preRow - diff){
                        hittingP = piece;
                        return true;
                    }
                }
            }
            //superior direito
            for(int c = preCol + 1; c < targetCol; c++){
                int diff = Math.abs(c-preCol);
                for(Piece piece : GamePanel.simPieces){
                    if(piece.col == c && piece.row == preRow - diff){
                        hittingP = piece;
                        return true;
                    }
                }
            }
        }
        if(targetRow>preRow){
            //inferior direito
            for(int c = preCol+1; c < targetCol; c++){
                int diff = Math.abs(c-preCol);
                for(Piece piece : GamePanel.simPieces){
                    if(piece.col == c && piece.row == preRow - diff){
                        hittingP = piece;
                        return true;
                    }
                }
            }
            //inferior esquerdo
            for(int c = preCol-1; c > targetCol; c--){
                int diff = Math.abs(c-preCol);
                for(Piece piece : GamePanel.simPieces){
                    if(piece.col == c && piece.row == preRow + diff){
                        hittingP = piece;
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void draw(Graphics2D g2) {
        if (image != null) {
            g2.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
        }
    }}
