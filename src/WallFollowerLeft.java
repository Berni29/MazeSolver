import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public class WallFollowerLeft implements Solver {
    private Cell[][] maze;
    private MazeFromFile mff;
    private JLabel mazeArea;
    private JSlider speed;
    private Graphics2D walker;
    private BufferedImage img;
    private int posX = 0, posY = 0;
    private int desX, desY;
    private boolean kill = false;
    private String facing = "top";
    private int baseSpeed = 1;

    public WallFollowerLeft(MazeFromFile mff, JLabel mazeArea, JSlider speed) {
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

    public String direction() {
        switch(facing) {
            case "top":
                if(maze[posX][posY].isLeft()) {
                    if (!maze[posX][posY].isTop()) {
                        return "top";
                    }
                }
                else {
                    return "left";
                }
                facing = "right";
                break;
            case "right":
                if(maze[posX][posY].isTop()) {
                    if (!maze[posX][posY].isRight()) {
                        return "top";
                    }
                }
                else {
                    return "left";
                }
                facing = "bottom";
                break;
            case "bottom":
                if(maze[posX][posY].isRight()) {
                    if (!maze[posX][posY].isBottom()) {
                        return "top";
                    }
                }
                else {
                    return "left";
                }
                facing = "left";
                break;
            case "left":
                if(maze[posX][posY].isBottom()) {
                    if (!maze[posX][posY].isLeft()) {
                        return "top";
                    }
                }
                else {
                    return "left";
                }
                facing = "top";
                break;
        }
        return "none";
    }

    public void go(String direction){
        switch(facing) {
            case "top":
                switch(direction) {
                    case "top":
                        drawTop(walker);
                        posY--;
                        facing = "top";
                        break;
                    case "left":
                        drawLeft(walker);
                        posX--;
                        facing = "left";
                        break;
                }
                break;
            case "right":
                switch(direction) {
                    case "top":
                        drawRight(walker);
                        posX++;
                        facing = "right";
                        break;
                    case "left":
                        drawTop(walker);
                        posY--;
                        facing = "top";
                        break;
                }
                break;
            case "bottom":
                switch(direction) {
                    case "top":
                        drawBottom(walker);
                        posY++;
                        facing = "bottom";
                        break;
                    case "left":
                        drawRight(walker);
                        posX++;
                        facing = "right";
                        break;
                }
                break;
            case "left":
                switch(direction) {
                    case "top":
                        drawLeft(walker);
                        posX--;
                        facing = "left";
                        break;
                    case "left":
                        drawBottom(walker);
                        posY++;
                        facing = "bottom";
                        break;
                }
                break;

        }
    }

    private void drawTop(Graphics2D walker){
        int CELLSIZE = Cell.getCellSize();
        walker.setStroke(new BasicStroke(CELLSIZE/2));
        if(maze[posX][posY-1].isVisited()) {
            walker.setColor(Color.RED);
        }
        else {
            walker.setColor(Color.GREEN);
        }
        walker.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)-(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
    }

    private void drawRight(Graphics2D walker){
        int CELLSIZE = Cell.getCellSize();
        walker.setStroke(new BasicStroke(CELLSIZE/2));
        if(maze[posX+1][posY].isVisited()) {
            walker.setColor(Color.RED);
        }
        else {
            walker.setColor(Color.GREEN);
        }
        walker.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(3*CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
    }

    private void drawBottom(Graphics2D walker){
        int CELLSIZE = Cell.getCellSize();
        walker.setStroke(new BasicStroke(CELLSIZE/2));
        if(maze[posX][posY+1].isVisited()) {
            walker.setColor(Color.RED);
        }
        else {
            walker.setColor(Color.GREEN);
        }
        walker.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(3*CELLSIZE/2));
    }

    private void drawLeft(Graphics2D walker){
        int CELLSIZE = Cell.getCellSize();
        walker.setStroke(new BasicStroke(CELLSIZE/2));
        if(maze[posX-1][posY].isVisited()) {
            walker.setColor(Color.RED);
        }
        else {
            walker.setColor(Color.GREEN);
        }
        walker.drawLine((posX*CELLSIZE)-(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
    }

    public boolean destinationReached() {
        return posX==desX && posY==desY;
    }

    public void move() {
        String direction;
        maze[posX][posY].setVisited(true);
        do{
            direction = direction();
        }
        while(direction.equals("none"));
        go(direction);
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

    @Override
    public void walkThrough(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(!kill &&!destinationReached()){
                    try {
                        move();
                        mazeArea.setIcon(new ImageIcon(img));
                        TimeUnit.MILLISECONDS.sleep(baseSpeed*(100-speed.getValue()));
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

}
