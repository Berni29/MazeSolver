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
    private JButton loadMazeButton;
    private JLabel mazeArea;
    private JButton resetButton;
    private JSlider speed;
    private JComboBox sizeBox;
    private JButton startButton;
    private JComboBox methodBox;

    private MazeFromFile mff;
    private BufferedImage img;
    private File file;
    private Solver solver;

    public Main() {

        loadMazeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    JFileChooser fc = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Maze txt files", "txt");
                    fc.setFileFilter(filter);
                    int result = fc.showOpenDialog(form);
                    if(result == JFileChooser.APPROVE_OPTION) {
                        if(solver!=null){
                            solver.reset();
                        }
                        file = fc.getSelectedFile();
                        mff = new MazeFromFile();
                        mff.createMaze(file);
                        img = mff.getImageMaze();
                        mazeArea.setIcon(new ImageIcon(img));
                    }
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(solver!=null)
                    solver.reset();
            }
        });

        sizeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cell.setCellSize(Integer.parseInt(sizeBox.getSelectedItem().toString()));
                if(file!=null) {
                    if(solver!=null)
                        solver.reset();
                    img = mff.getImageMaze();
                    mazeArea.setIcon(new ImageIcon(img));
                    mazeArea.repaint();
                }
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(file==null){
                    JOptionPane.showMessageDialog(form,"Maze not loaded");
                }
                else {
                    if(solver!=null)
                        solver.reset();
                    switch (methodBox.getSelectedIndex()){
                        case 0:
                            solver = new WallFollowerRight(mff,mazeArea,speed);
                            break;
                        case 1:
                            solver = new WallFollowerLeft(mff,mazeArea,speed);
                            break;
                        default :
                            solver = null;
                    }
                    solver.walkThrough();
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Maze Solver");
        frame.setContentPane(new Main().form);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
