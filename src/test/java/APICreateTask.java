import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class APICreateTask {

    private static final String URL = "https://192.168.10.173/jsonrpc.php";
    private static final String AUTHORIZATION_HEADER = "Basic YWRtaW46YWRtaW4=";

    @Test
    @Description("Тест на створення нової задачі")
    @Step("Запуск тесту на створення задачі")
    public void createTask() {
        // Дані для створення задачі
        String taskTitle = "New Task";
        int ownerId = 1; // Власник задачі
        int projectId = 11; // ID проекту

        // Виклик методу для створення задачі
        createTaskInKanboard(taskTitle, ownerId, projectId);
    }

    @Step("Створення задачі в Kanboard")
    private void createTaskInKanboard(String taskTitle, int ownerId, int projectId) {
        // Створення тіла запиту
        String json = "{\n" +
                "    \"jsonrpc\": \"2.0\",\n" +
                "    \"method\": \"createTask\",\n" +
                "    \"id\": 1,\n" +
                "    \"params\": {\n" +
                "        \"title\": \"" + taskTitle + "\",\n" +
                "        \"owner_id\": " + ownerId + ",\n" +
                "        \"project_id\": " + projectId + "\n" +
                "    }\n" +
                "}";

        // Відправка запиту та отримання відповіді
        Response response = sendCreateTaskRequest(json);

        // Виведення статусу та відповіді
        System.out.println("Response Status: " + response.getStatusLine());
        System.out.println("Response Body: " + response.asString());
    }

    @Step("Відправка запиту на створення задачі")
    private Response sendCreateTaskRequest(String json) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", AUTHORIZATION_HEADER)
                .body(json)
                .post(URL);
    }
}
