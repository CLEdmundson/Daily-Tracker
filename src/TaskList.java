import java.util.ArrayList;

public class TaskList {
    ArrayList<String> taskNames;
    ArrayList<Boolean> taskComplete;

    public TaskList() {
        taskNames = new ArrayList<String>();
        taskComplete = new ArrayList<Boolean>();
    }

    //Adds a new task, assumes the task is incomplete by default
    public void addTask(String tn) {
        taskNames.add(tn);
        taskComplete.add(false);
    }

    /**
     * Checks if TaskList contains a task with name tn
     * @param tn        name of the task
     * @return boolean value
     */
    public boolean isTask(String tn) {
        boolean ans = taskNames.contains(tn);
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
     * @return boolean value - if edit operation was performed
     */
    public boolean editTask(String tn, int action) {
        boolean ans = isTask(tn);
        if (ans) {
            int taskIndex = taskNames.indexOf(tn);
            switch (action) {
                case 1: //remove
                    taskNames.remove(taskIndex);
                    taskComplete.remove(taskIndex);
                    break;
                case 2: //finish
                    taskComplete.set(taskIndex, true);
                    break;
                case 3: //reset
                    taskComplete.set(taskIndex, false);
            }
        }
        return ans;
    }

    public void printTaskList(){
        for (int i=0; i<taskNames.size(); i++){
            System.out.println("Task: " + taskNames.get(i) + ", Complete: " + taskComplete.get(i));
        }
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
