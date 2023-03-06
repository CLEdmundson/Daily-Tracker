import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class TaskList {
    final int Max_Task_Length = 30;
    ArrayList<JCheckBox> tasks;

    public TaskList(){
        tasks = new ArrayList<>();
    }

    //Adds a new task, assumes the task is incomplete by default
    public void addTask(String tn) {
        int bufferLength = Max_Task_Length - tn.length();
        String bufferedtn = tn + (new String(new char[bufferLength]).replace('\0', ' '));
        JCheckBox cBox = new JCheckBox(bufferedtn, false);
        cBox.setHorizontalTextPosition(SwingConstants.LEFT);
        cBox.setMargin(new Insets(0, 20, 0, 100));
        tasks.add(cBox);
    }

    /**
     * Checks if TaskList contains a task with name tn
     * @param tn        name of the task
     * @return boolean value
     */
    public boolean isTask(String tn) {
        boolean ans = false;
        for (JCheckBox cBox: tasks)
            if(cBox.getText().equals(tn))
                ans = true;
        if(!ans)
            System.out.println("No task with such a name exists.");
        return ans;
    }

    /**
     * Edit a preexisting task in the TaskList
     * @param tn        name of the task to be edited
     * @param action    action to be performed on the task
     *                  1: remove
     *                  2: finish
     *                  3: reset
     */
    public void editTask(String tn, int action) {
        if (isTask(tn)) {
            int taskIndex = -1;
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getText().equals(tn))
                    taskIndex = i;
            }
            switch (action) {
                case 1 -> tasks.remove(taskIndex); //remove
                case 2 -> tasks.get(taskIndex).setSelected(true); //finish
                case 3 -> tasks.get(taskIndex).setSelected(false); //reset
            }
        }
    }

    public void printTaskList(){
        for (JCheckBox cBox: tasks)
            System.out.println("Task: " + cBox.getText() + ", Complete: " + cBox.isSelected());
    }

    public static void main(String[] args) {
        TaskList tList = new TaskList();

        // Add Tasks
        tList.addTask("Brush Teeth (Morning)");
        tList.addTask("Take Meds");
        tList.addTask("Take Meds");
        tList.addTask("Take Meds (dupe)");
        tList.addTask("Brush Teeth (Night)");

        // Test Functions
        tList.printTaskList();
        tList.editTask("Take Meds", 2);
        tList.editTask("Take Meds (dupe)", 2);
        tList.printTaskList();
        tList.editTask("Take Meds (dupe)", 1);
        tList.editTask("f", 1);
        tList.printTaskList();
    }
}
