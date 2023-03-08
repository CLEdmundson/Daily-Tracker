import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.Enumeration;
import java.util.Map;

public class TrackerGUI extends JFrame implements ActionListener {
    static TaskList tList;
    JPanel taskPanel, buttonPanel;
    JCheckBox lastClicked;

    public TrackerGUI(String wName) {
        super(wName);
        tList = new TaskList();
        tList.loadTasks();
        taskPanel = setupTaskPanel();
        add(taskPanel, BorderLayout.NORTH);
        buttonPanel = setupButtonPanel(); // Never used outside initial setup
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Makes a Task Panel and adds any loaded tasks to the panel as JCheckBoxes
     *
     * @return The created Task Panel
     */
    public JPanel setupTaskPanel(){
        JPanel tPanel = new JPanel();
        tPanel.setLayout(new BoxLayout(tPanel, BoxLayout.Y_AXIS));
        // In the event any tasks were loaded, adds them to the taskPanel
        if (!tList.isEmpty()) {
            for (JCheckBox cBox : tList.tasks) {
                setStatusFont(cBox);
                cBox.addActionListener(this);
                tPanel.add(cBox);
            }
        } else { updateTaskPanel(); }

        return tPanel;
    }

    /**
     * Makes a Button Panel and adds interaction buttons:
     *  Add Task
     *  Remove Task
     *
     * @return The created Button Panel
     */
    public JPanel setupButtonPanel(){
        JPanel bPanel = new JPanel(new GridLayout(1, 2));

        JButton addTaskButton = new JButton("Add Task");
        addTaskButton.addActionListener(this);
        addTaskButton.setActionCommand("newTask");

        JButton removeTaskButton = new JButton("Remove Task");
        removeTaskButton.addActionListener(this);
        removeTaskButton.setActionCommand("removeTask");

        bPanel.add(addTaskButton);
        bPanel.add(removeTaskButton);

        return bPanel;
    }

    /**
     * Adds filler text to taskPanel in the event there are no tasks in the taskList
     */
    public void updateTaskPanel() {
        if (tList.tasks.size() == 0) {
            JLabel display = new JLabel("There are no tasks yet, try adding one!");
            // Add padding to JLabel to keep taskPanel width consistent
            Border border = display.getBorder();
            Border margin = new EmptyBorder(2, 20, 2, 44);
            display.setBorder(new CompoundBorder(border, margin));
            // Add the filler text to the taskPanel
            taskPanel.add(display, BorderLayout.WEST);
        }
    }

    /**
     * Checks status and changes the font on the JCheckBox to
     *  contain STRIKETHROUGH if selected
     *  remove STRIKETHROUGH if unselected
     *
     * @param cBox JCheckBox to check status and change font
     */
    public void setStatusFont(JCheckBox cBox){
        @SuppressWarnings("unchecked")
        Map<TextAttribute, Boolean> attributes = (Map<TextAttribute, Boolean>) cBox.getFont().getAttributes();
        attributes.put(TextAttribute.STRIKETHROUGH, cBox.isSelected());
        cBox.setFont(new Font(attributes));
        if(cBox.isSelected()) {
            cBox.setForeground(Color.GRAY);
        } else {
            cBox.setForeground(Color.BLACK);
        }
        updateWindow();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() instanceof JCheckBox cBox) {
            if (lastClicked != null)
                lastClicked.setBackground(Color.WHITE);
            lastClicked = cBox;
            lastClicked.setBackground(Color.lightGray);
            setStatusFont(cBox);
        }
        String action = ae.getActionCommand();
        if (action.equals("newTask")) {
            String newTask = JOptionPane.showInputDialog("Task Name: ");
            if(newTask != null) {
                // Make a new task, add it to the window, update the window
                JCheckBox cBox = tList.addTask(newTask);
                setStatusFont(cBox);
                cBox.addActionListener((TrackerGUI) SwingUtilities.windowForComponent(taskPanel));
                taskPanel.add(cBox);
                updateTaskPanel();
                updateWindow();
            } // else the action was canceled
        }
        if (action.equals("removeTask")) {
            // Throw a confirmation dialog to confirm the removal of the last task
            if(lastClicked!=null) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Remove Task: " + lastClicked.getText().trim() + "?",
                        "Remove Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    tList.tasks.remove(lastClicked);
                    taskPanel.remove(lastClicked);
                    updateWindow();
                }
            }
        }
    }

    public void updateWindow() {
        revalidate();
        repaint();
        pack();
    }

    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) UIManager.put(key, f);
        }
    }

    public static void main(String[] args) {
        setUIFont(new javax.swing.plaf.FontUIResource(Font.MONOSPACED, Font.BOLD, 14));

        TrackerGUI frame = new TrackerGUI("Daily Tracker");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showOptionDialog(
                        null, "Close Application?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    tList.saveTasks();
                    System.exit(0);
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }
}
