import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Enumeration;

public class TrackerGUI extends JFrame implements ActionListener, ItemListener {
    TaskList tList;
    JPanel taskPanel;
    JCheckBox lastClicked;


    public TrackerGUI(String wName) {
        super(wName);
        tList = new TaskList();
        taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        add(taskPanel, BorderLayout.NORTH);
    }

    public void updateTaskPanel() {
        // Clean the unupdated tasklist
        taskPanel.removeAll();

        JLabel display = new JLabel("");

        if (tList.tasks.size() == 0) {
            display.setText("There are no tasks yet, try adding one!");

            Border border = display.getBorder();
            Border margin = new EmptyBorder(2, 20, 2, 44);
            display.setBorder(new CompoundBorder(border, margin));
            taskPanel.add(display, BorderLayout.WEST);
        } else {
            for (JCheckBox cBox: tList.tasks) {
                cBox.addActionListener((TrackerGUI) SwingUtilities.windowForComponent(taskPanel));
                taskPanel.add(cBox);
                System.out.println("Added task: " + cBox.getText());
            }
        }

        revalidate();
        repaint();
        pack();
    }

    @Override
    public void itemStateChanged(ItemEvent ie) {
        System.out.println(ie.getSource());
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() instanceof JCheckBox)
            lastClicked = (JCheckBox) ae.getSource();
        String action = ae.getActionCommand();
        if (action.equals("newTask")) {
            String newTask = JOptionPane.showInputDialog("Task Name: ");
            tList.addTask(newTask);
            updateTaskPanel();
        }
        if (action.equals("removeTask")) {
            tList.tasks.remove(lastClicked);
            updateTaskPanel();
        }
    }

    public static void setUIFont(javax.swing.plaf.FontUIResource f)
    {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while(keys.hasMoreElements())
        {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if(value instanceof javax.swing.plaf.FontUIResource) UIManager.put(key, f);
        }
    }

    public static void main(String[] args) {
        TrackerGUI frame = new TrackerGUI("Daily Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUIFont(new javax.swing.plaf.FontUIResource(Font.MONOSPACED, Font.BOLD, 14));

        frame.updateTaskPanel();

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));

        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.addActionListener(frame);
        addTaskButton.setActionCommand("newTask");

        JButton removeTaskButton = new JButton("Remove Task");
        removeTaskButton.addActionListener(frame);
        removeTaskButton.setActionCommand("removeTask");

        buttonPanel.add(addTaskButton);
        buttonPanel.add(removeTaskButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.pack();
        frame.setVisible(true);
    }
}
