import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.Enumeration;
import java.util.Map;

public class TrackerGUI extends JFrame implements ActionListener, ItemListener {
    static TaskList tList;
    JPanel taskPanel;
    JCheckBox lastClicked;

    public TrackerGUI(String wName) {
        super(wName);
        tList = new TaskList();
        tList.loadTasks();
        taskPanel = new JPanel();
        taskPanel.setLayout(new BoxLayout(taskPanel, BoxLayout.Y_AXIS));
        add(taskPanel, BorderLayout.NORTH);
    }

    public void updateTaskPanel() {
        // Clean the old taskList
        taskPanel.removeAll();

        if (tList.tasks.size() == 0) {
            JLabel display = new JLabel("There are no tasks yet, try adding one!");

            Border border = display.getBorder();
            Border margin = new EmptyBorder(2, 20, 2, 44);
            display.setBorder(new CompoundBorder(border, margin));
            taskPanel.add(display, BorderLayout.WEST);
        } else {
            for (JCheckBox cBox : tList.tasks) {
                setStatusFont(cBox);
                cBox.addActionListener((TrackerGUI) SwingUtilities.windowForComponent(taskPanel));
                taskPanel.add(cBox);
            }
        }

        revalidate();
        repaint();
        pack();
    }

    public void setStatusFont(JCheckBox cBox){
        // TODO: Fix warnings around the attributes Map
        // TODO: Make completed status more visual i.e. font color

        @SuppressWarnings("unchecked")
        Map<TextAttribute, Boolean> attributes = (Map<TextAttribute, Boolean>) cBox.getFont().getAttributes();
        attributes.put(TextAttribute.STRIKETHROUGH, cBox.isSelected());
        cBox.setFont(new Font(attributes));

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
        if (ae.getSource() instanceof JCheckBox cBox) {
            lastClicked = cBox;
            setStatusFont(cBox);
        }
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
                        null, "Are You Sure to Close Application?",
                        "Exit Confirmation", JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE, null, null, null);
                if (confirm == 0) {
                    tList.saveTasks();
                    System.exit(0);
                }
            }
        });


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
