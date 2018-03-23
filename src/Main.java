import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.TimeUnit;

public class Main {

    private JPanel form;
    private JButton wallFollowerRightButton;
    private JButton loadMazeButton;
    private JLabel mazeArea;
    private JButton wallFollowerLeftButton;
    private JButton resetButton;
    private JSlider speed;
    private MazeFromFile mff = new MazeFromFile();
    private WallFollower wf = null;
    private BufferedImage img;
    private Graphics2D walker;
    private File file;
    private boolean kill = false;

    public Main() {
        wallFollowerRightButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(wf==null){
                    JOptionPane.showMessageDialog(form,"Maze not loaded");
                }
                else {
                    reset();
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            walk("Right");
                        }
                    });
                    thread.start();
                }
            }
        });
        loadMazeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    JFileChooser fc = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Maze txt files", "txt");
                    fc.setFileFilter(filter);
                    int result = fc.showOpenDialog(form);
                    if(result == JFileChooser.APPROVE_OPTION) {
                        file = fc.getSelectedFile();
                        mff.createMaze(file);
                        img = mff.getImageMaze();
                        walker = (Graphics2D) img.getGraphics();
                        wf = null;
                        wf = new WallFollower(mff.getMaze());
                        mazeArea.setIcon(new ImageIcon(img.getScaledInstance(img.getWidth(), img.getHeight(), Image.SCALE_DEFAULT)));
                    }
            }

        });
        wallFollowerLeftButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(wf==null){
                    JOptionPane.showMessageDialog(form,"Maze not loaded");
                }
                else {
                    reset();
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            walk("Left");
                        }
                    });
                    thread.start();
                }
            }
        });
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new Main().form);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void walk(String direction){
        if(direction.equals("Right")) {
            ImageIcon icon = new ImageIcon(img);
            while (!kill && !wf.destinationReached()) {
                try {
                    wf.moveRight(walker);
                    mazeArea.setIcon(icon);
                    TimeUnit.MILLISECONDS.sleep(101-speed.getValue());
                    mazeArea.repaint();
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
        else {
            ImageIcon icon = new ImageIcon(img);
            while (!kill && !wf.destinationReached()) {
                try {
                    wf.moveLeft(walker);
                    mazeArea.setIcon(icon);
                        TimeUnit.MILLISECONDS.sleep(101-speed.getValue());
                    mazeArea.repaint();
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
    public void reset(){
        kill = true;
        if(file!=null){
            mff.clearMazeTrace();
            img = mff.getImageMaze();
            walker = (Graphics2D) img.getGraphics();
            wf = null;
            wf = new WallFollower(mff.getMaze());
            mazeArea.setIcon(new ImageIcon(img.getScaledInstance(img.getWidth(), img.getHeight(), Image.SCALE_DEFAULT)));
        }
        try {
            TimeUnit.MILLISECONDS.sleep(101);
        }
        catch (InterruptedException e){
            e.printStackTrace();
        }
        kill = false;
    }

}
