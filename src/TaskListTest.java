import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskListTest {

    TaskList tList;

    @org.junit.jupiter.api.BeforeEach
    public void init() {
        tList = new TaskList();
    }

    @org.junit.jupiter.api.Test
    void isTask() {
        tList.addTask("Task 1");

        // Needs extra spaces do to max length setting, may need to change eventually
        assertTrue(tList.isTask("Task 1                        "));
        assertFalse(tList.isTask("Fake Task"));
    }

    @org.junit.jupiter.api.Test
    void loadTasks() {
    }
}