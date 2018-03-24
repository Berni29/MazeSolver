import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public class WallFollowerRight extends Solver {
    private Cell[][] maze;
    private MazeFromFile mff;
    private JLabel mazeArea;
    private JSlider speed;
    private Graphics2D walker;
    private BufferedImage img;
    private int posX = 0, posY = 0;
    private int desX, desY;
    private boolean kill = false;

    public WallFollowerRight(MazeFromFile mff, JLabel mazeArea, JSlider speed) {
        this.mff = mff;
        this.mazeArea = mazeArea;
        this.speed = speed;
        this.maze = this.mff.getMaze();
        img = this.mff.getImageMaze();
        walker = (Graphics2D) img.getGraphics();
        desX = maze.length-1;
        desY = maze[0].length-1;
        this.maze[0][0].setVisited(true);
    }

    public void move(Graphics2D g) {
        int CELLSIZE = Cell.getCellSize();
        g.setStroke(new BasicStroke(CELLSIZE/2));
        g.setColor(Color.GREEN);
        if(!maze[posX][posY].isRight() && !maze[posX+1][posY].isVisited()) {
            g.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(3*CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
            posX++;
        }
        else if (!maze[posX][posY].isTop() && !maze[posX][posY-1].isVisited()) {
            g.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)-(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
            posY--;
        }
        else if(!maze[posX][posY].isLeft() && !maze[posX-1][posY].isVisited()) {
            g.drawLine((posX*CELLSIZE)-(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
            posX--;
        }
        else if(!maze[posX][posY].isBottom() && !maze[posX][posY+1].isVisited()) {
            g.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(3*CELLSIZE/2));
            posY++;
        }
        else {
            maze[posX][posY].setRevisited(true);
            g.setColor(Color.BLUE);
            if (!maze[posX][posY].isRight() && !maze[posX+1][posY].isRevisited()) {
                g.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(3*CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
                posX++;
            }
            else if (!maze[posX][posY].isTop() && !maze[posX][posY-1].isRevisited()) {
                g.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)-(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
                posY--;
            }
            else if(!maze[posX][posY].isLeft() && !maze[posX-1][posY].isRevisited()) {
                g.drawLine((posX*CELLSIZE)-(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
                posX--;
            }
            else if (!maze[posX][posY].isBottom() && !maze[posX][posY+1].isRevisited()) {
                g.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(3*CELLSIZE/2));
                posY++;
            }
        }
        maze[posX][posY].setVisited(true);
    }

    @Override
    public void walkThrough(){
        Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(!kill &&!destinationReached()){
                        try {
                            move(walker);
                            mazeArea.setIcon(new ImageIcon(img));
                            TimeUnit.MILLISECONDS.sleep(Solver.getBaseSpeed()*(100-speed.getValue()));
                            mazeArea.repaint();
                        }
                        catch (InterruptedException e) {
                            System.err.println(e.getMessage());
                        }
                    }
                }
            });
            thread.start();
    }
    @Override
    public void reset(){
        try {
            kill = true;
            TimeUnit.MILLISECONDS.sleep(101);
            kill = false;
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        mff.clearMazeTrace();
        img = mff.getImageMaze();
        mazeArea.setIcon(new ImageIcon(img));
        mazeArea.repaint();
    }

    public boolean destinationReached() {
        return posX==desX && posY==desY;
    }

}

