import javax.swing.*;

public class TrackerGUI extends JFrame {
    TaskList tList = new TaskList();
    public TrackerGUI(String wName) {
        super(wName);
    }

    public JPanel updateTaskPanel(){
        JPanel tPanel = new JPanel();

        if(tList.taskNames.size() == 0){
            JLabel empty = new JLabel("There are now tasks yet, try adding one!");
            tPanel.add(empty);
        }
        else {
            for (int i = 0; i < tList.taskNames.size(); i++) {
                JLabel text = new JLabel(tList.taskNames.get(i));
                tPanel.add(text);
            }
        }
        return tPanel;
    }

    public static void main(String[] args) {
        TrackerGUI frame = new TrackerGUI("Daily Tracker");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel taskPanel = frame.updateTaskPanel();

        JPanel buttonPanel = new JPanel();

        JButton addTaskButton = new JButton("Add Task");
        JButton removeTaskButton = new JButton("Remove Task");

        buttonPanel.add(addTaskButton);
        buttonPanel.add(removeTaskButton);
        //panel.setBackground(Color.GRAY.darker());



        frame.add(taskPanel);
        frame.add(buttonPanel);
        frame.setVisible(true);
    }
}
