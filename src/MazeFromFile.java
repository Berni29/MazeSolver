import java.io.*;

public class MazeFromFile {
    private Cell [][] maze;

    public int[] checkMazeSize(String path){
        String[] sizeString = new String[2];
        int[] size = {0,0};
        String buffer;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            do {
                buffer = reader.readLine();
                if(buffer!=null) {
                    sizeString[0] = buffer.split(" ")[0];
                    sizeString[1] = buffer.split(" ")[1];
                }
            } while (buffer!=null);
            reader.close();
            size[0] = Integer.parseInt(sizeString[0]);
            size[1] = Integer.parseInt(sizeString[1]);
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }
        return size;
    }

    public void createMaze(int x, int y, String path) {
        String buffer;
        String[] info = {"","",""};
        maze = new Cell[x+1][y+1];
        for(int i = 0; i <= x; i++) {
            for(int j = 0; j <= y; j++) {
                maze[j][i] = new Cell();
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
    public String getStringMaze() {
        StringBuilder sb = new StringBuilder();
        for(int j = 0; j<maze[0].length; j++) {
            for(int i = 0; i<maze.length; i++){
                if(maze[i][j].isTop())
                    sb.append("+---");
                else
                    sb.append("+   ");
            }
            sb.append("+\n");
            for(int i = 0; i<maze.length; i++){
                if(maze[i][j].isLeft())
                    if(maze[i][j].isVisited())
                        sb.append("| O ");
                    else
                        sb.append("|   ");
                else
                    if(maze[i][j].isVisited())
                        sb.append("  O ");
                    else
                        sb.append("    ");
            }
            sb.append("|\n");
            if(j==maze[0].length-1) {
                for (int i = 0; i < maze.length; i++) {
                    if (maze[i][j].isBottom())
                        sb.append("+---");
                    else
                        sb.append("+   ");
                }
                sb.append("+\n");
            }
        }
        return sb.toString();
    }
    public void clearMazeTrace() {
        int x = maze.length;
        int y = maze[0].length;
        for(int i = 0; i < x; i++) {
            for(int j = 0; j < y; j++) {
                maze[j][i].setVisited(false);
            }
        }
    }
    public Cell[][] getMaze() {
        return maze;
    }

}
