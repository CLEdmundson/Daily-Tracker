import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrackerGUI extends JFrame implements ActionListener {
    TaskList tList;
    JPanel taskPanel;

    public TrackerGUI(String wName) {
        super(wName);
        tList = new TaskList();
        taskPanel = new JPanel();
        add(taskPanel, BorderLayout.NORTH);
    }

    public void updateTaskPanel(){
        JLabel display = new JLabel();

        if(tList.taskNames.size() == 0){
            System.out.println("no tasks");
            display.setText("There are now tasks yet, try adding one!");
        }
        else {
            System.out.println("tasks exist");
            StringBuilder toDisplay = new StringBuilder();
            for (int i = 0; i < tList.taskNames.size(); i++) {
                toDisplay.append(tList.taskNames.get(i) + '\n');
                System.out.println("Added task: " + tList.taskNames.get(i));
            }
            display.setText(toDisplay.toString());
        }
        //invalidate();
        //validate();
        //repaint();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        if (action.equals("newTask")) {
            String newTask = JOptionPane.showInputDialog("Task Name: ");
            tList.addTask(newTask);
            updateTaskPanel();
        }
    }

    public static void main(String[] args) {
        TrackerGUI frame = new TrackerGUI("Daily Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.updateTaskPanel();

        JPanel buttonPanel = new JPanel();

        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.addActionListener(frame);
        addTaskButton.setActionCommand("newTask");
        JButton removeTaskButton = new JButton("Remove Task");

        buttonPanel.add(addTaskButton);
        buttonPanel.add(removeTaskButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
}
