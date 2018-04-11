import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class AStar implements Solver {

    private Cell[][] maze;
    private Point start;
    private Point goal;
    private MazeFromFile mff;
    private JLabel mazeArea;
    private JSlider speed;
    private Graphics2D walker;
    private BufferedImage img;
    private HashSet<Point> closedSet;
    private HashSet<Point> openSet;
    private HashMap<Point,Point> cameFrom;
    private HashMap<Point,Double> gScore;
    private HashMap<Point,Double> fScore;
    private Point[][] coordinates;
    private boolean kill = false;
    private int baseSpeed = 1;

    public AStar(MazeFromFile mff, JLabel mazeArea, JSlider speed) {
        this.mff = mff;
        this.mazeArea = mazeArea;
        this.speed = speed;
        this.maze = this.mff.getMaze();
        img = this.mff.getImageMaze();
        walker = (Graphics2D) img.getGraphics();
        int desX = maze.length-1;
        int desY = maze[0].length-1;
        coordinates = new Point[maze.length][maze[0].length];
        for(int x = 0; x<maze.length; x++) {
            for(int y = 0; y<maze[0].length; y++) {
                coordinates[x][y] = new Point(x,y);
            }
        }
        start = coordinates[0][0];
        goal = coordinates[desX][desY];
    }


    @Override
    public void walkThrough() {
        closedSet = new HashSet<>();
        openSet = new HashSet<>();
        cameFrom = new HashMap<>();
        gScore = new HashMap<>();
        fScore = new HashMap<>();
        fScore.put(start,Point.distance(start.x,start.y,goal.x,goal.y));
        gScore.put(start,0.0);
        openSet.add(start);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!kill&&!openSet.isEmpty()) {
                        int size = Cell.getCellSize();
                        Point current = null;
                        for (Point point : openSet) {
                            if (current == null) {
                                current = point;
                            } else if (fScore.get(point) <= fScore.get(current)) {
                                current = point;
                            }
                        }
                        if (current == goal) {
                            reconstruct_path(cameFrom, current);
                            break;
                        }
                        openSet.remove(current);
                        closedSet.add(current);
                        int x = current.x;
                        int y = current.y;
                        walker.setColor(Color.GRAY);
                        walker.fillRect(x*size + size*20/100, y*size + size*20/100, size - size*40/100, size - size*40/100);
                        mazeArea.setIcon(new ImageIcon(img));
                        mazeArea.repaint();
                        TimeUnit.MILLISECONDS.sleep(10);
                        for (Point neighbor : possibleWays(current)) {
                            if (closedSet.contains(neighbor)) {
                                continue;
                            }
                            if (!openSet.contains(neighbor)) {
                                openSet.add(neighbor);
                                x = neighbor.x;
                                y = neighbor.y;
                                walker.setColor(Color.GREEN);
                                walker.fillRect(x*size + size*20/100, y*size + size*20/100, size - size*40/100, size - size*40/100);
                                mazeArea.setIcon(new ImageIcon(img));
                                mazeArea.repaint();
                                TimeUnit.MILLISECONDS.sleep(10);
                            }
                            Double tennative_gScore = gScore.get(current) +
                                    Point.distance(current.x, current.y, neighbor.x, neighbor.y);
                            if (gScore.get(neighbor) != null && tennative_gScore >= gScore.get(neighbor)) {
                                continue;
                            }
                            cameFrom.put(neighbor, current);
                            gScore.put(neighbor, tennative_gScore);
                            fScore.put(neighbor, gScore.get(neighbor) +
                                    Point.distance(neighbor.x, neighbor.y, goal.x, goal.y));

                        }
                        TimeUnit.MILLISECONDS.sleep(baseSpeed*(100-speed.getValue()));
                    }
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void reconstruct_path(HashMap<Point,Point> cameFrom,Point current){
        int size = Cell.getCellSize();
        walker.setStroke(new BasicStroke(size/2));
        walker.setColor(Color.GREEN);
        Point temp = current;
        while(cameFrom.get(temp)!=null) {
            Point next = cameFrom.get(temp);
            walker.drawLine(temp.x*size+size/2,temp.y*size+size/2,next.x*size+size/2,next.y*size+size/2);
            cameFrom.remove(temp);
            temp = next;
            mazeArea.setIcon(new ImageIcon(img));
            mazeArea.repaint();
        }

    }

    public HashSet<Point> possibleWays(Point current){
        HashSet<Point> result = new HashSet<>();
        int x = current.x;
        int y = current.y;
        if(!maze[x][y].isTop()) {
            result.add(coordinates[x][y-1]);
        }
        if(!maze[x][y].isRight()) {
            result.add(coordinates[x+1][y]);
        }
        if(!maze[x][y].isBottom()) {
            result.add(coordinates[x][y+1]);
        }
        if(!maze[x][y].isLeft()) {
            result.add(coordinates[x-1][y]);
        }
        return result;
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


}
