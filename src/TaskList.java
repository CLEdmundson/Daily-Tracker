import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TaskList {
    final int Max_Task_Length = 30;
    final String FILENAME = "Saved_Tasks";
    ArrayList<JCheckBox> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    //Adds a new task, assumes the task is incomplete by default
    public void addTask(String tn, boolean check) {
        int bufferLength = Max_Task_Length - tn.length();
        String bufferedtn = tn + (new String(new char[bufferLength]).replace('\0', ' '));
        JCheckBox cBox = new JCheckBox(bufferedtn, check);
        cBox.setHorizontalTextPosition(SwingConstants.LEFT);
        cBox.setMargin(new Insets(0, 20, 0, 100));
        tasks.add(cBox);
    }

    public void addTask(String tn) {
        addTask(tn, false);
    }

    /**
     * Checks if TaskList contains a task with name tn
     *
     * @param tn name of the task
     * @return boolean value
     */
    public boolean isTask(String tn) {
        boolean ans = false;
        for (JCheckBox cBox : tasks)
            if (cBox.getText().equals(tn))
                ans = true;
        if (!ans)
            System.out.println("No task with such a name exists.");
        return ans;
    }

    /**
     * Edit a preexisting task in the TaskList
     *
     * @param tn     name of the task to be edited
     * @param action action to be performed on the task
     *               1: remove
     *               2: finish
     *               3: reset
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

    public void printTaskList() {
        for (JCheckBox cBox : tasks)
            System.out.println("Task: " + cBox.getText() + ", Complete: " + cBox.isSelected());
    }

    public void createSaveFile() {
        System.out.println("File does not exist, creating file...");
        File f = new File(FILENAME);
        try {
            //noinspection ResultOfMethodCallIgnored
            f.createNewFile();
            System.out.println("File created: " + f.getName());
        } catch (IOException e) {
            System.out.println("Error Creating File...");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void loadTasks() {
        // TODO: Check if day has passes and reset complete status if so
        File f = new File(FILENAME);
        try {
            Scanner myReader = new Scanner(f);
            while (myReader.hasNextLine()) {
                String[] data = myReader.nextLine().split(",");
                addTask(data[0], Boolean.parseBoolean(data[1]));
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            createSaveFile();
        }
    }

    public void saveTasks() {
        try {
            FileWriter myWriter = new FileWriter(FILENAME);

            for (JCheckBox cBox: tasks){
                myWriter.write(cBox.getText() + "," + cBox.isSelected() + "\n");
            }
            myWriter.close();
            System.out.println("Successfully saved to file.");
        } catch (IOException e) {
            System.out.println("Error saving to file...");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // TODO: Add method and inline comments
        TaskList tList = new TaskList();

        // Add Tasks
        tList.addTask("Task 1");
        tList.addTask("Task 2");
        tList.addTask("Task 2");
        tList.addTask("Task 3");
        tList.addTask("Task 4");

        // Test Functions
        tList.printTaskList();
        tList.editTask("Task 2", 2);
        tList.editTask("Task 3", 2);
        tList.printTaskList();
        tList.editTask("Task 3", 1);
        tList.editTask("Fake Task", 1);

        tList.printTaskList();
    }
}
