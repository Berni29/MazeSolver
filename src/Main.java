import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;

public class Main {

    private JPanel form;
    private JTextArea mazeField;
    private JButton GOButton;
    private JButton LOADMAZEButton;
    private MazeFromFile mff = new MazeFromFile();
    private WallFollower wf = null;

    public Main() {
        GOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(wf==null){
                    JOptionPane.showMessageDialog(form,"Maze not loaded");
                }
                else {
                    wf.walkThroughMaze();
                    mazeField.setText(mff.getStringMaze());
                }
            }
        });
        LOADMAZEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.showOpenDialog(form);
                String path = fc.getSelectedFile().getPath();
                int[] size = mff.checkMazeSize(path);
                mff.createMaze(size[0],size[1],path);
                wf = new WallFollower(mff.getMaze());
                mazeField.setText(mff.getStringMaze());
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
