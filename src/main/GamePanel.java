package main;

import piece.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {


    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    final int FPS = 60;
    Thread gameThread;
    Board board = new Board();
    Mouse mouse = new Mouse();

   //PEÇAS
    public static ArrayList<Piece> pieces= new ArrayList<>();
    public static ArrayList<Piece> simPieces = new ArrayList<>();
    Piece activeP;

    //COR DA PEÇA
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    int currentColor = WHITE;

    //BOOLEANS
    boolean canMove;
    boolean validSquare;


    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        addMouseListener(mouse);
        addMouseMotionListener(mouse);


        setPieces();
        copyPieces(pieces, simPieces);
    }
    public void launchGame(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void setPieces(){
        //time de brancas
        pieces.add(new Pawn(WHITE,0,6));
        pieces.add(new Pawn(WHITE,1,6));
        pieces.add(new Pawn(WHITE,2,6));
        pieces.add(new Pawn(WHITE,3,6));
        pieces.add(new Pawn(WHITE,4,6));
        pieces.add(new Pawn(WHITE,5,6));
        pieces.add(new Pawn(WHITE,6,6));
        pieces.add(new Pawn(WHITE,7,6));
        pieces.add(new Rook(WHITE,0,7));
        pieces.add(new Rook(WHITE,7,7));
        pieces.add(new Knight(WHITE,1,7));
        pieces.add(new Knight(WHITE,6,7));
        pieces.add(new Bishop(WHITE,2,7));
        pieces.add(new Bishop(WHITE,5,7));
        pieces.add(new Queen(WHITE,3,7));
        pieces.add(new King(WHITE,4,7));
        //time de pretas
        pieces.add(new Pawn(BLACK,0,1));
        pieces.add(new Pawn(BLACK,1,1));
        pieces.add(new Pawn(BLACK,2,1));
        pieces.add(new Pawn(BLACK,3,1));
        pieces.add(new Pawn(BLACK,4,1));
        pieces.add(new Pawn(BLACK,5,1));
        pieces.add(new Pawn(BLACK,6,1));
        pieces.add(new Pawn(BLACK,7,1));
        pieces.add(new Rook(BLACK,0,0));
        pieces.add(new Rook(BLACK,7,0));
        pieces.add(new Knight(BLACK,1,0));
        pieces.add(new Knight(BLACK,6,0));
        pieces.add(new Bishop(BLACK,2,0));
        pieces.add(new Bishop(BLACK,5,0));
        pieces.add(new Queen(BLACK,3,0));
        pieces.add(new King(BLACK,4,0));



    }

    private void copyPieces(ArrayList<Piece> source, ArrayList<Piece> target){
        target.clear();
        for(int i = 0 ; i < source.size() ; i++){
            target.add(source.get(i));
        }
    }

    @Override
    public void run() {
        //GAME LOOP
        //puxa o metodo uptade e o paintComponent 60 vezes por segundo
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        while(gameThread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if(delta >= 1){
                update();
                repaint();
                delta--;

            }
        }

    }

    private void update(){
        //BOTAO DO MOUSE PRESSIONADO
        if(mouse.pressed){
            if(activeP == null){
                //SE A VARIAVEL activeP ESTA EM NULL CHECA SE VOCE PODE MEXER UMA PEÇA
                for(Piece piece: simPieces){
                    //SE O MOUSE ESTA NUMA PEÇA ALIADA SEGURA COMO A activeP(PEÇA ATIVA)

                    if(piece.color == currentColor
                            && piece.col == mouse.x/Board.SQUARE_SIZE
                            && piece.row == mouse.y/Board.SQUARE_SIZE){

                        activeP = piece;
                    }
                }
            }
            else{
                //SE O JOGADOR ESTA SEGURANDO UMA PEÇA, SIMULA O MOVIMENTO
                simulate();
            }
        }
        //BOTAO DO MOUSE FOI SOLTO
        if(mouse.pressed == false){
            if(activeP != null){
                if(validSquare){
                    //movimento confirmado
                    //atualiza a lista de peças em caso de captura e remoçao durante a simulaçao
                    //aplica as perdas das simpieces nas pieces
                    copyPieces(simPieces,pieces);//transfere as peças da simulaçao para as peças
                    activeP.moved = true;
                    activeP.updatePosition();
                    changePlayer();
                }
                else{
                    //movimento nao valido, reseta a posicao
                    copyPieces(pieces, simPieces);//coloca as peças reais na lista de peças simuladas
                    activeP.resetPosition();
                    activeP=null;
                }
            }
        }
    }
    private void simulate(){
        canMove = false;
        validSquare=false;
        //reseta a lista de peças
        //isso é para restaurar peças removidas durante a simulaçao
        copyPieces(pieces, simPieces);


        //SE UMA PEÇA ESTA SENDO SEGURADA ATUALIZA SUA POSIÇAO
        activeP.x = mouse.x - Board.SQUARE_SIZE/2;
        activeP.y = mouse.y - Board.SQUARE_SIZE/2;
        activeP.col=activeP.getCol(activeP.x);
        activeP.row=activeP.getRow(activeP.y);

        //CHECA SE A PEÇA ESTA EM CIMA DE UM QUADRADO ALCANÇAVEL
        if(activeP.canMove(activeP.col,activeP.row)){
            canMove = true;

            if(activeP.hittingP != null){//adicionando a mecanica de capturar,ao capturar tira a peça capturada do tabuleiro
                simPieces.remove(activeP.hittingP.getIndex());

            }
            validSquare = true;
        }

        activeP.moved = true;
    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        //tabuleiro
        board.draw(g2);
        //peças
        for(Piece p : simPieces){
            p.draw(g2);
        }
        if (activeP != null){
            if (canMove){//apenas fica colorido quandoi o movimento é possivel
                g2.setColor(Color.GRAY);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g2.fillRect(activeP.col*Board.SQUARE_SIZE,activeP.row*Board.SQUARE_SIZE , Board.SQUARE_SIZE, Board.SQUARE_SIZE);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }

            //esse codigo faz parte da "thinking phase" serve pra demonstrar onde a peça vai no tabuleiro
            activeP.draw(g2);
        }
        //mensagem de turno
    }

    public void changePlayer(){
        if(currentColor==WHITE){
            currentColor = BLACK;
        }
        else{
            currentColor = WHITE;
        }
        activeP = null;
    }
}
