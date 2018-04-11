import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class MazeFromFile {
    private Cell [][] maze;
    private int x = 0 , y = 0;

    public void checkMazeSize(File path){
        String stringX = "0", stringY = "0";
        String buffer;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            do {
                buffer = reader.readLine();
                if(buffer!=null) {
                    stringX = buffer.split(" ")[0];
                    stringY = buffer.split(" ")[1];
                }
            } while (buffer!=null);
            reader.close();
            x = Integer.parseInt(stringX);
            y = Integer.parseInt(stringY);
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
    }

    public void createMaze(File path) {
        String buffer;
        checkMazeSize(path);
        String[] info = {"","",""};
        maze = new Cell[x+1][y+1];
        for(int i = 0; i <= x; i++) {
            for(int j = 0; j <= y; j++) {
                maze[i][j] = new Cell();
            }
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            for(int i = 0 ; i < (x+1)*(y+1) ; i++) {
                buffer = reader.readLine();
                info[0] = buffer.split(" ")[0];
                info[1] = buffer.split(" ")[1];
                try {
                    info[2] = buffer.split(" ")[2];
                }
                catch(ArrayIndexOutOfBoundsException e) {
                    info[2] = "";
                }
                int pX = Integer.parseInt(info[0]);
                int pY = Integer.parseInt(info[1]);
                switch(info[2]) {
                    case "PD":
                        maze[pX][pY].setTop(true);
                        maze[pX][pY].setLeft(true);
                        if(pX==x) {
                            maze[pX][pY].setRight(true);
                        }
                        if(pY==y) {
                            maze[pX][pY].setBottom(true);
                        }
                        if(pY>0) {
                            maze[pX][pY-1].setBottom(true);
                        }
                        if(pX>0) {
                            maze[pX-1][pY].setRight(true);
                        }
                        break;
                    case "P":
                        maze[pX][pY].setTop(true);
                        if(pX==x) {
                            maze[pX][pY].setRight(true);
                        }
                        if(pY==y) {
                            maze[pX][pY].setBottom(true);
                        }
                        if(pY>0) {
                            maze[pX][pY-1].setBottom(true);
                        }
                        break;
                    case "D":
                        maze[pX][pY].setLeft(true);
                        if(pX==x) {
                            maze[pX][pY].setRight(true);
                        }
                        if(pY==y) {
                            maze[pX][pY].setBottom(true);
                        }
                        if(pX>0) {
                            maze[pX-1][pY].setRight(true);
                        }
                        break;
                    default:
                        if(pX==x) {
                            maze[pX][pY].setRight(true);
                        }
                        if(pY==y) {
                            maze[pX][pY].setBottom(true);
                        }
                        break;
                }
            }
            reader.close();
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }
    public void clearMazeTrace() {
        int x = maze.length;
        int y = maze[0].length;
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                maze[i][j].setVisited(false);
            }
        }
    }
    public Cell[][] getMaze() {
        return maze;
    }
    public BufferedImage getImageMaze() {
        int CELLSIZE = Cell.getCellSize();
        BufferedImage mazeImg = new BufferedImage((x+1)*CELLSIZE+1,(y+1)*CELLSIZE+1,BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D)mazeImg.getGraphics();
        g.setColor(Color.BLACK);
        for(int j = 0; j<=y; j++){
            for(int i = 0; i<=x; i++){
                if(maze[i][j].isTop())
                    g.drawLine(i*CELLSIZE,j*CELLSIZE,i*CELLSIZE+CELLSIZE,j*CELLSIZE);
                if(maze[i][j].isLeft())
                    g.drawLine(i*CELLSIZE,j*CELLSIZE,i*CELLSIZE,j*CELLSIZE+CELLSIZE);
                if(maze[i][j].isBottom())
                    g.drawLine(i*CELLSIZE,j*CELLSIZE+CELLSIZE,i*CELLSIZE+CELLSIZE,j*CELLSIZE+CELLSIZE);
                if(maze[i][j].isRight())
                    g.drawLine(i*CELLSIZE+CELLSIZE,j*CELLSIZE,i*CELLSIZE+CELLSIZE,j*CELLSIZE+CELLSIZE);
            }
        }
        return mazeImg;
    }


}
