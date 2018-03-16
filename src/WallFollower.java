public class WallFollower {
    private Cell[][] maze;
    private int[] position = new int[2];
    private int[] destination = new int[2];

    public WallFollower(Cell[][] maze) {
        this.maze = maze;
        destination[0] = maze.length-1;
        destination[1] = maze[0].length-1;
        position[0] = 0;
        position[1] = 0;
        this.maze[0][0].setVisited(true);
    }

    public void move() {

        if(!maze[position[0]][position[1]].isLeft() && !maze[position[0]-1][position[1]].isVisited()) {
            position[0]--;
        }
        else if(!maze[position[0]][position[1]].isBottom() && !maze[position[0]][position[1]+1].isVisited()) {
            position[1]++;
        }
        else if(!maze[position[0]][position[1]].isRight() && !maze[position[0]+1][position[1]].isVisited()) {
            position[0]++;
        }
        else if (!maze[position[0]][position[1]].isTop() && !maze[position[0]][position[1]-1].isVisited()) {
            position[1]--;
        }
        else {
            maze[position[0]][position[1]].setRevisited(true);
            if(!maze[position[0]][position[1]].isLeft() && !maze[position[0]-1][position[1]].isRevisited()) {
                position[0]--;
            } else if (!maze[position[0]][position[1]].isBottom() && !maze[position[0]][position[1]+1].isRevisited()) {
                position[1]++;
            } else if (!maze[position[0]][position[1]].isRight() && !maze[position[0]+1][position[1]].isRevisited()) {
                position[0]++;
            } else if (!maze[position[0]][position[1]].isTop() && !maze[position[0]][position[1]-1].isRevisited()) {
                position[1]--;
            }
        }
        maze[position[0]][position[1]].setVisited(true);
    }
    public void walkThroughMaze() {
        while(!destinationReached()) {
            move();
        }
    }

    public boolean destinationReached() {
        return position[0]==destination[0] && position[1]==destination[1];
    }

}

