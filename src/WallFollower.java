import java.awt.*;

public class WallFollower {
    private Cell[][] maze;
    private int posX = 0, posY = 0;
    private int desX, desY;
    private final int CELLSIZE = 8;

    public WallFollower(Cell[][] maze) {
        this.maze = maze;
        desX = maze.length-1;
        desY = maze[0].length-1;
        this.maze[0][0].setVisited(true);
    }

    public void moveRight(Graphics2D g) {
        g.setColor(Color.GREEN);
        if(!maze[posX][posY].isRight() && !maze[posX+1][posY].isVisited()) {
            g.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(3*CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
            posX++;
        }
        else if(!maze[posX][posY].isBottom() && !maze[posX][posY+1].isVisited()) {
            g.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(3*CELLSIZE/2));
            posY++;
        }
        else if(!maze[posX][posY].isLeft() && !maze[posX-1][posY].isVisited()) {
            g.drawLine((posX*CELLSIZE)-(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
            posX--;
        }
        else if (!maze[posX][posY].isTop() && !maze[posX][posY-1].isVisited()) {
            g.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)-(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
            posY--;
        }
        else {
            maze[posX][posY].setRevisited(true);
            g.setColor(Color.BLUE);
            if (!maze[posX][posY].isRight() && !maze[posX+1][posY].isRevisited()) {
                g.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(3*CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
                posX++;
            }
            else if (!maze[posX][posY].isBottom() && !maze[posX][posY+1].isRevisited()) {
                g.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(3*CELLSIZE/2));
                posY++;
            }
            else if(!maze[posX][posY].isLeft() && !maze[posX-1][posY].isRevisited()) {
                g.drawLine((posX*CELLSIZE)-(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
                posX--;
            }
            else if (!maze[posX][posY].isTop() && !maze[posX][posY-1].isRevisited()) {
                g.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)-(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
                posY--;
            }
        }
        maze[posX][posY].setVisited(true);
    }

    public void moveLeft(Graphics2D g) {
        g.setColor(Color.GREEN);
        if(!maze[posX][posY].isLeft() && !maze[posX-1][posY].isVisited()) {
            g.drawLine((posX*CELLSIZE)-(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
            posX--;
        }
        else if(!maze[posX][posY].isBottom() && !maze[posX][posY+1].isVisited()) {
            g.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(3*CELLSIZE/2));
            posY++;
        }
        else if(!maze[posX][posY].isRight() && !maze[posX+1][posY].isVisited()) {
            g.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(3*CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
            posX++;
        }
        else if (!maze[posX][posY].isTop() && !maze[posX][posY-1].isVisited()) {
            g.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)-(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
            posY--;
        }
        else {
            maze[posX][posY].setRevisited(true);
            g.setColor(Color.BLUE);
            if(!maze[posX][posY].isLeft() && !maze[posX-1][posY].isRevisited()) {
                g.drawLine((posX*CELLSIZE)-(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
                posX--;
            }
            else if (!maze[posX][posY].isBottom() && !maze[posX][posY+1].isRevisited()) {
                g.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(3*CELLSIZE/2));
                posY++;
            }
            else if (!maze[posX][posY].isRight() && !maze[posX+1][posY].isRevisited()) {
                g.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2),(posX*CELLSIZE)+(3*CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
                posX++;
            }
            else if (!maze[posX][posY].isTop() && !maze[posX][posY-1].isRevisited()) {
                g.drawLine((posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)-(CELLSIZE/2),(posX*CELLSIZE)+(CELLSIZE/2),(posY*CELLSIZE)+(CELLSIZE/2));
                posY--;
            }
        }
        maze[posX][posY].setVisited(true);
    }

    public boolean destinationReached() {
        return posX==desX && posY==desY;
    }

}

