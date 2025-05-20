package main;

import piece.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements Runnable {


    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    final int FPS = 60;
    Thread gameThread;
    Board board = new Board();
    Mouse mouse = new Mouse();
    Rectangle replayButton = null;
   //PEÇAS
    public static ArrayList<Piece> pieces= new ArrayList<>();
    public static ArrayList<Piece> simPieces = new ArrayList<>();
    Piece activeP, checkingP;
    ArrayList<Piece> promoPieces = new ArrayList<>();
    public static Piece castlingP;

    //COR DA PEÇA
    public static final int WHITE = 0;
    public static final int BLACK = 1;
    int currentColor = WHITE;

    //BOOLEANS
    boolean canMove;
    //checa se o rei pode ir para aquele quadrado
    boolean validSquare = true;
    boolean promotion;
    boolean gameOver;
    boolean stalemate;


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
//        pieces.add(new Pawn(WHITE,0,6));
//        pieces.add(new Pawn(WHITE,1,6));
//        pieces.add(new Pawn(WHITE,2,6));
//        pieces.add(new Pawn(WHITE,3,6));
//        pieces.add(new Pawn(WHITE,4,6));
//        pieces.add(new Pawn(WHITE,5,6));
//        pieces.add(new Pawn(WHITE,6,6));
//        pieces.add(new Pawn(WHITE,7,6));
//        pieces.add(new Rook(WHITE,0,7));
//        pieces.add(new Rook(WHITE,7,7));
//        pieces.add(new Knight(WHITE,1,7));
//        pieces.add(new Knight(WHITE,6,7));
//        pieces.add(new Bishop(WHITE,2,7));
//        pieces.add(new Bishop(WHITE,5,7));
//        pieces.add(new Queen(WHITE,3,7));
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
        if ((gameOver || stalemate) && mouse.pressed && replayButton != null && replayButton.contains(mouse.x, mouse.y)) {
            resetGame();
            repaint();
            return;
        }
        if (promotion) {
            promoting();
        } else if(!gameOver && !stalemate) {

            //BOTAO DO MOUSE PRESSIONADO
            if (mouse.pressed) {
                if (activeP == null) {
                    //SE A VARIAVEL activeP ESTA EM NULL CHECA SE VOCE PODE MEXER UMA PEÇA
                    for (Piece piece : simPieces) {
                        //SE O MOUSE ESTA NUMA PEÇA ALIADA SEGURA COMO A activeP(PEÇA ATIVA)

                        if (piece.color == currentColor
                                && piece.col == mouse.x / Board.SQUARE_SIZE
                                && piece.row == mouse.y / Board.SQUARE_SIZE) {

                            activeP = piece;
                        }
                    }

                } else {
                    //SE O JOGADOR ESTA SEGURANDO UMA PEÇA, SIMULA O MOVIMENTO
                    simulate();
                }
            }
            //BOTAO DO MOUSE FOI SOLTO
            if (mouse.pressed == false) {
                if (activeP != null) {
                    if (validSquare) {
                        //movimento confirmado
                        //atualiza a lista de peças em caso de captura e remoçao durante a simulaçao
                        //aplica as perdas das simpieces nas pieces
                        copyPieces(simPieces, pieces);//transfere as peças da simulaçao para as peças
                        activeP.updatePosition();
                        activeP.moved = true;
                        if (castlingP != null) {
                            castlingP.updatePosition();
                        }
                        if(isKingInCheck() && isCheckmate()) {
                            //checar possibilidade de fim de jogo
                            gameOver = true;
                        }else if(isStalemate()){
                            stalemate = true;
                        }
                        else{
                            if(canPromote()){
                                promotion = true;
                            }else{
                                if(canPromote()){
                                    promotion = true;
                                }else{
                                    changePlayer();
                            }
                        }}

                    } else {
                        //movimento nao valido, reseta a posicao
                        copyPieces(pieces, simPieces);//coloca as peças reais na lista de peças simuladas
                        activeP.resetPosition();
                        activeP = null;
                    }
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

        //reseta o status da peça do roque
        if(castlingP!=null){
            castlingP.col = castlingP.preCol;
            castlingP.x = castlingP.getX(castlingP.col);
            castlingP = null;
        }


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
            checkCastling();
            if(!isIllegal(activeP) && !opponentCanCaptureKing()){
                validSquare = true;
            }
        }
    }

    private boolean canPromote(){
        if(activeP.type == Type.PAWN){
            if(currentColor == WHITE && activeP.row == 0 || currentColor == BLACK && activeP.row ==7){
                promoPieces.clear();
                promoPieces.add(new Rook(currentColor,9,2));
                promoPieces.add(new Knight(currentColor,9,3));
                promoPieces.add(new Bishop(currentColor,9,4));
                promoPieces.add(new Queen(currentColor,9,5));
                return true;
            }
        }return false;
    }
    private void promoting(){
        if(mouse.pressed){
            int x = (WIDTH - Board.SQUARE_SIZE) / 2;
            int startY = (HEIGHT - 4 * Board.SQUARE_SIZE) / 2;

            for(int i = 0; i < promoPieces.size(); i++){
                Piece piece = promoPieces.get(i);
                int y = startY + i * Board.SQUARE_SIZE;

                // Verifica se o clique do mouse está dentro da imagem da peça
                if(mouse.x >= x && mouse.x <= x + Board.SQUARE_SIZE &&
                        mouse.y >= y && mouse.y <= y + Board.SQUARE_SIZE){

                    switch(piece.type){
                        case ROOK: simPieces.add(new Rook(currentColor, activeP.col, activeP.row)); break;
                        case KNIGHT: simPieces.add(new Knight(currentColor, activeP.col, activeP.row)); break;
                        case BISHOP: simPieces.add(new Bishop(currentColor, activeP.col, activeP.row)); break;
                        case QUEEN: simPieces.add(new Queen(currentColor, activeP.col, activeP.row)); break;
                        default: break;
                    }

                    simPieces.remove(activeP.getIndex());
                    copyPieces(simPieces, pieces);
                    activeP = null;
                    promotion = false;
                    changePlayer();
                    break; // já encontrou, não precisa testar o resto
                }
            }
        }

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
            if (canMove){//fica cinza quando o movimento é possivel e vermelho quando nao é
                if(isIllegal(activeP) || opponentCanCaptureKing()){
                g2.setColor(Color.RED);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g2.fillRect(activeP.col*Board.SQUARE_SIZE,activeP.row*Board.SQUARE_SIZE , Board.SQUARE_SIZE, Board.SQUARE_SIZE);
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
            }else{
                    g2.setColor(Color.GRAY);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                    g2.fillRect(activeP.col*Board.SQUARE_SIZE,activeP.row*Board.SQUARE_SIZE , Board.SQUARE_SIZE, Board.SQUARE_SIZE);
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                }
            }

            //esse codigo faz parte da "thinking phase" serve pra demonstrar onde a peça vai no tabuleiro
            activeP.draw(g2);
        }
        if(promotion){
            // 1. Fundo escurecido
            g2.setColor(new Color(0, 0, 0, 150)); // preto com 150 de transparência
            g2.fillRect(0, 0, WIDTH, HEIGHT);

            // 2. Coordenadas para desenhar no centro
            int x = (WIDTH - Board.SQUARE_SIZE) / 2;
            int startY = (HEIGHT - 4 * Board.SQUARE_SIZE) / 2;

            // 3. Título acima
            g2.setFont(new Font("Consolas", Font.BOLD, 32));
            g2.setColor(Color.WHITE);
            g2.drawString("Choose a cat upgrade", WIDTH / 2 - 200, startY - 30);

            // 4. Desenho das peças com moldura
            for(int i = 0; i < promoPieces.size(); i++){
                Piece piece = promoPieces.get(i);
                int y = startY + i * Board.SQUARE_SIZE;

                // Moldura branca
                g2.setColor(Color.WHITE);
                g2.setStroke(new BasicStroke(3));
                g2.drawRect(x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE);

                // Imagem da peça
                g2.drawImage(piece.image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);

                // Atualizar col e row para permitir clique
                piece.col = x / Board.SQUARE_SIZE;
                piece.row = y / Board.SQUARE_SIZE;
            }
        }


        if(gameOver){
            if(gameOver){
                g2.setColor(new Color(0, 0, 0, 200));
                g2.fillRect(0, 0, WIDTH, HEIGHT);
                String s = (currentColor == WHITE) ? "White Cats WIN!" : "Black Cats WIN!";
                g2.setFont(new Font("Consolas", Font.BOLD, 50));
                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(s);
                g2.drawString(s, (WIDTH - textWidth)/2, HEIGHT/2 - 50);

                int buttonX = WIDTH/2 - 120;
                int buttonY = HEIGHT/2;
                int buttonW = 240;
                int buttonH = 60;

                g2.setColor(Color.LIGHT_GRAY);
                g2.fillRect(buttonX, buttonY, buttonW, buttonH);
                g2.setColor(Color.BLACK);
                g2.setFont(new Font("Consolas", Font.BOLD, 30));
                g2.drawString("Play Again", buttonX + 20, buttonY + 40);

                replayButton = new Rectangle(buttonX, buttonY, buttonW, buttonH);
            }

        }

        if(stalemate){
            g2.setColor(new Color(0, 0, 0, 200));
            g2.fillRect(0, 0, WIDTH, HEIGHT);

            String s = "Empate!";
            g2.setFont(new Font("Consolas", Font.BOLD, 60));
            g2.setColor(Color.WHITE);
            FontMetrics fm = g2.getFontMetrics();
            int textWidth = fm.stringWidth(s);
            g2.drawString(s, (WIDTH - textWidth)/2, HEIGHT/2 - 100);

            int buttonW = 260;
            int buttonH = 60;
            int buttonX = (WIDTH - buttonW) / 2;
            int buttonY = HEIGHT/2;

            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(buttonX, buttonY, buttonW, buttonH);

            g2.setColor(Color.DARK_GRAY);
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(buttonX, buttonY, buttonW, buttonH);

            g2.setFont(new Font("Consolas", Font.BOLD, 28));
            g2.setColor(Color.BLACK);
            String btnText = "Jogar Novamente";
            FontMetrics btnFm = g2.getFontMetrics();
            int btnTextWidth = btnFm.stringWidth(btnText);
            int btnTextX = buttonX + (buttonW - btnTextWidth)/2;
            int btnTextY = buttonY + (buttonH + btnFm.getAscent())/2 - 4;
            g2.drawString(btnText, btnTextX, btnTextY);

            replayButton = new Rectangle(buttonX, buttonY, buttonW, buttonH);
        }
    }
    private boolean isIllegal(Piece king){
        if(king.type == Type.KING){
            for(Piece piece: simPieces){
                if(piece != king && piece.color != king.color && piece.canMove(king.col,king.row)){
                    return true;
                }
            }
        }return false;
    }
    private boolean isCheckmate(){

        Piece king = getKing(true);
        if(kingCanMove(king)){
            return false;
        }
        else {
            //checa se é possivel bloquear o xeque com alguma peça


            int colDiff = Math.abs(checkingP.col - king.col);
            int rowDiff = Math.abs(checkingP.row - king.row);

            if (colDiff == 0) {
                //a peça que esta aplicando o xeque ataca na vertical
                if(checkingP.row < king.row){
                    //a peça atacante esta acima do rei
                    for(int row = checkingP.row; row < king.row; row ++){
                        for(Piece piece: simPieces){
                            if(piece != king && piece.color != currentColor && piece.canMove(checkingP.col, row )){
                                return false;
                            }
                        }
                    }

                }
                if(checkingP.row > king.row){
                    //a peça atacante esta abaixo do rei
                    for(int row = checkingP.row; row > king.row; row --){
                        for(Piece piece: simPieces){
                            if(piece != king && piece.color != currentColor && piece.canMove(checkingP.col, row )){
                                return false;
                            }
                        }
                    }

                }
            }else if(rowDiff == 0){
                //a peça que esta atacando ataca na horizontal
                if(checkingP.col < king.col){
                    //a peça ataca da esquerda
                    for(int col = checkingP.col; col < king.col; col ++){
                        for(Piece piece: simPieces){
                            if(piece != king && piece.color != currentColor && piece.canMove(col, checkingP.row )){
                                return false;
                            }
                        }
                    }
                }
                if(checkingP.col > king.col){
                    //a peça ataca da direita
                    for(int col = checkingP.col; col > king.col; col --){
                        for(Piece piece: simPieces){
                            if(piece != king && piece.color != currentColor && piece.canMove(col, checkingP.row )){
                                return false;
                            }
                        }
                    }
                }
            }else if(colDiff==rowDiff){
                //atacando na diagonal
                if(checkingP.row < king.row){
                    //acima do rei
                    if(checkingP.col < king.col){
                        //superior esquerdo
                        for(int col = checkingP.col, row = checkingP.row; col < king.col;col++ ,row++){
                            for(Piece piece : simPieces){
                                if(piece != king && piece.color != currentColor && piece.canMove(col, row)){
                                    return false;
                                }
                            }
                        }
                    }
                    if(checkingP.row > king.row){
                        //superior direito
                        for(int col = checkingP.col, row = checkingP.row; col < king.col;col++ ,row--){
                            for(Piece piece : simPieces){
                                if(piece != king && piece.color != currentColor && piece.canMove(col, row)){
                                    return false;
                                }
                            }
                        }
                    }
                }
                if(checkingP.row > king.row){
                    //abaixo do rei
                    if(checkingP.col < king.col){
                        //inferior esquerdo
                        for(int col = checkingP.col, row = checkingP.row; col < king.col;col++ ,row--){
                            for(Piece piece : simPieces){
                                if(piece != king && piece.color != currentColor && piece.canMove(col, row)){
                                    return false;
                                }
                            }
                        }
                    }
                    if(checkingP.row > king.row){
                        //inferior direito
                        for(int col = checkingP.col, row = checkingP.row; col < king.col;col-- ,row--){
                            for(Piece piece : simPieces){
                                if(piece != king && piece.color != currentColor && piece.canMove(col, row)){
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
            else{
                //a peça que esta atacando o rei é um cavalo
            }
        }
        return true;

    }
    private boolean kingCanMove(Piece king){
        //simula o movimento do rei
        if (isValidMove(king, -1, -1)) {return true;}
        if (isValidMove(king, 0, -1)) {return true;}
        if (isValidMove(king, 1, -1)) {return true;}
        if (isValidMove(king, -1, 0)) {return true;}
        if (isValidMove(king, 1, 0)) {return true;}
        if (isValidMove(king, -1, 1)) {return true;}
        if (isValidMove(king, 0, 1)) {return true;}
        if (isValidMove(king, 1, 1)) {return true;}

        return false;
    }
    private boolean isValidMove(Piece king, int colPlus, int rowPlus){
        boolean isValidMove = false;
        //atualiza a posição do rei por um segundo
        king.col += colPlus;
        king.row += rowPlus;

        if(king.canMove(king.col, king.row)){
            if(king.hittingP != null){
                simPieces.remove(king.hittingP.getIndex());
            }
            if(!isIllegal(king)){
                isValidMove = true;
            }
        }
        //reseta a posição do rei e volta as peças removidas
        king.resetPosition();
        copyPieces(pieces, simPieces);

        return isValidMove;
    }
    private boolean isStalemate(){
        int count = 0;
        //conta o numero de peças
        for(Piece piece : simPieces){
            if(piece.color != currentColor){
                count++;
            }
        }//se sobrar apenas o rei
        if(count == 1){
            if(!kingCanMove(getKing(true))){
                return true;
            }
        }
        return false;
    }

    public void checkCastling(){
        if(castlingP != null){
            if(castlingP.col == 0){
                castlingP.col += 3;
            }
            else if(castlingP.col == 7){
                castlingP.col -= 2;
            }
            castlingP.x = castlingP.getX(castlingP.col);
        }
    }

        private boolean opponentCanCaptureKing(){

        Piece king = getKing(false);

        for(Piece piece : simPieces){
            if(piece.color != king.color && piece.canMove(king.col,king.row)){
                return true;
            }
        }
        return false;
    }
    private boolean isKingInCheck(){
        Piece king = getKing(true);
        if(activeP.canMove(king.col,king.row)){
            checkingP = activeP;
            return true;
        }else{
            checkingP = null;
        }
        return false;
    }
    private Piece getKing(boolean opponent){
        Piece king = null;

        for(Piece piece : simPieces){
            if(opponent){
                if(piece.type == Type.KING && piece.color != currentColor){
                    king = piece;
                }
            }else{
                if(piece.type == Type.KING && piece.color == currentColor){
                    king = piece;
                }
            }
        }
        return king;
    }
    public void changePlayer(){
        if(currentColor==WHITE){
            currentColor = BLACK;
            //reseta o status para o en pasant
            for(Piece piece:pieces){
                if(piece.color == BLACK){
                    piece.twoStepped = false;
                }
            }
        }
        else{
            currentColor = WHITE;
            //reseta o status para o en pasant
            for(Piece piece:pieces){
                if(piece.color == WHITE){
                    piece.twoStepped = false;
                }
            }
        }
        activeP = null;
    }
    private void resetGame() {
        GamePanel.pieces.clear();
        GamePanel.simPieces.clear();
        promoPieces.clear();

        GamePanel.castlingP = null;
        checkingP = null;
        activeP = null;

        currentColor = WHITE;
        promotion = false;
        gameOver = false;
        stalemate = false;


        setPieces();

        copyPieces(pieces, simPieces);

        replayButton = null;
    }


}
