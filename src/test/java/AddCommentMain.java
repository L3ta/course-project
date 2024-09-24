import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.example.AddComment;
import org.example.KanboardApiAuth;
import org.example.ProjectManager;
import org.example.UserManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AddCommentMain {

    private UserManager userManager;

    @BeforeClass
    @Step("Ініціалізація користувача")
    public void setUp() {
        userManager = new UserManager();
    }

    @Test
    @Description("Тест на додавання коментаря до задачі")
    @Step("Запуск тесту на додавання коментаря")
    public void runTestsInBrowsers() {
        // Running tests in Chrome
        executeFlowInBrowser("chrome");

        // Running tests in Firefox
        executeFlowInBrowser("firefox");
    }

    @Step("Виконання тесту в браузері: {browser}")
    private void executeFlowInBrowser(String browser) {
        String ownerId = createUser(); // Create user and get userId

        if (ownerId != null) {
            String projectId = createProject(ownerId); // Create project and get projectId

            if (projectId != null) {
                AddComment taskManager = new AddComment();

                // Log in to Kanboard in the selected browser (Chrome or Firefox)
                taskManager.loginToKanboard(userManager.getUsername(), userManager.getPassword(), browser);
                taskManager.goToProject();
                taskManager.createTask();
                String taskId = taskManager.getCreatedTaskId();

                // Delete task via API
                if (taskId != null) {
                    System.out.println("Task created in " + browser + " with ID: " + taskId);
                    KanboardApiAuth apiAuth = new KanboardApiAuth(userManager.getUsername(), userManager.getPassword());
                    deleteTask(apiAuth, taskId);
                    deleteProject(apiAuth, projectId);
                } else {
                    System.out.println("Task creation failed in " + browser);
                }

                // Close the browser
                taskManager.quit();
            } else {
                System.out.println("Project creation failed in " + browser);
            }

            // Delete user after all actions are complete
            deleteUser();
        } else {
            System.out.println("User creation failed in " + browser + ". Aborting.");
        }
    }

    @Step("Створення користувача")
    private String createUser() {
        return userManager.createUser(); // Create user and get userId
    }

    @Step("Створення проекту з ідентифікатором власника: {ownerId}")
    private String createProject(String ownerId) {
        ProjectManager projectManager = new ProjectManager();
        return projectManager.createProject(ownerId); // Create project and get projectId
    }

    @Step("Видалення задачі з ідентифікатором: {taskId}")
    private void deleteTask(KanboardApiAuth apiAuth, String taskId) {
        apiAuth.deleteTask(taskId); // Delete task
    }

    @Step("Видалення проекту з ідентифікатором: {projectId}")
    private void deleteProject(KanboardApiAuth apiAuth, String projectId) {
        apiAuth.deleteProject(projectId); // Delete project
    }

    @Step("Видалення користувача")
    private void deleteUser() {
        userManager.deleteUser(); // Delete user
    }

    @AfterClass
    @Step("Очистка після тесту")
    public void tearDown() {
        // Any cleanup code if needed
    }
}
