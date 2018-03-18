import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    private JPanel form;
    private JButton GOButton;
    private JButton LOADMAZEButton;
    private JLabel mazeArea;
    private MazeFromFile mff = new MazeFromFile();
    private WallFollower wf = null;
    private BufferedImage img;
    private Graphics2D walker;

    public Main() {
        GOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(wf==null){
                    JOptionPane.showMessageDialog(form,"Maze not loaded");
                }
                else {
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            if(wf!=null) {
                                ImageIcon icon = new ImageIcon(img);
                                while (!wf.destinationReached()) {
                                    try {
                                        wf.move(walker);
                                        mazeArea.setIcon(icon);
                                        TimeUnit.MILLISECONDS.sleep(1);
                                        mazeArea.repaint();
                                    }
                                    catch (InterruptedException e) {
                                        System.err.println(e.getMessage());
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
        LOADMAZEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    JFileChooser fc = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Maze txt files", "txt");
                    fc.setFileFilter(filter);
                    int result = fc.showOpenDialog(form);
                    if(result == JFileChooser.APPROVE_OPTION) {
                        mff.createMaze(fc.getSelectedFile());
                        img = mff.getImageMaze();
                        walker = (Graphics2D) img.getGraphics();
                        wf = null;
                        wf = new WallFollower(mff.getMaze());
                        mazeArea.setIcon(new ImageIcon(img.getScaledInstance(img.getWidth(), img.getHeight(), Image.SCALE_DEFAULT)));
                    }
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

}
