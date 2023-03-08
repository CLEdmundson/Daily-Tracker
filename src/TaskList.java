import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class TaskList {
    final int Max_Task_Length = 30;
    final String FILENAME = "Saved_Tasks"; // "Task_Name,Task_Status\n"
    ArrayList<JCheckBox> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Adds a JCheckBox task to the tasks variable
     *
     * @param tn name of the task to be added
     * @param status (optional because of method overload) boolean
     *               value for current isSelected status of task,
     *               assumed to be false by default
     */
    public JCheckBox addTask(String tn, boolean status) {
        int bufferLength = Max_Task_Length - tn.length();
        String bufferedtn = tn + (new String(new char[bufferLength]).replace('\0', ' '));
        JCheckBox cBox = new JCheckBox(bufferedtn, status);
        cBox.setHorizontalTextPosition(SwingConstants.LEFT);
        cBox.setMargin(new Insets(0, 20, 0, 100));
        tasks.add(cBox);
        return(cBox);
    }

    public JCheckBox addTask(String tn) {
        return(addTask(tn, false));
    }

    /**
     * Checks if TaskList contains a task with given name tn
     *
     * @param tn name of task to be checked
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

    public boolean isEmpty(){
        return (tasks.size()==0);
    }

    public String getDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    /**
     * Checks the current date against a given date and checks if a day has passed
     *
     * @param fileDate the date obtained from the save file
     * @return if it is a new day
     */
    public boolean isNewDay(String fileDate) {
        if(fileDate != null) {
            // Could potentially be optimized to just checking if dates are different as time in linear
            String[] fileArray = fileDate.split("/");
            String[] currArray = getDate().split("/");

            for (int i = 0; i < fileArray.length; i++) {
                if (parseInt(fileArray[i]) < parseInt(currArray[i]))
                    return true;
            }
        }
        return false;
    }

    // Checks if the file specified by FILENAME exists, and creates the file if not
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

    // Loads the tasks from file specified by FILENAME into the tasks variable
    public void loadTasks() {
        File f = new File(FILENAME);
        try {
            Scanner myReader = new Scanner(f);
            try {
                boolean newDay = isNewDay(myReader.nextLine());
                while (myReader.hasNextLine()) {
                    String[] data = myReader.nextLine().split(",");
                    boolean status = Boolean.parseBoolean(data[1]);
                    if(newDay)
                        status = false;
                    addTask(data[0], status);
                }
                myReader.close();
            } catch (NoSuchElementException e) {
                // The file exists but was empty
            }
        } catch (FileNotFoundException e) {
            createSaveFile();
        }
    }

    // Saves the tasks located in tasks variable to file specified by FILENAME variable
    public void saveTasks() {
        try {
            FileWriter myWriter = new FileWriter(FILENAME);
            myWriter.write(getDate() + "\n");
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
}
