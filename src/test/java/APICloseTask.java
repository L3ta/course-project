import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.security.SecureRandom;

public class APICloseTask {

    private static final String URL = "http://localhost:80/jsonrpc.php";
    private static final String AUTHORIZATION_HEADER = "Basic YWRtaW46YWRtaW4=";
    private static final SecureRandom random = new SecureRandom();

    @Test
    @Description("Тест на закриття задачі")
    @Step("Запуск тесту на закриття задачі")
    public void closeTask() {
        // Генерація випадкового ID задачі в діапазоні від 20 до 100
        int taskId = generateRandomTaskId();

        // Виведення ID задачі, що закривається
        System.out.println("Task ID to close: " + taskId);

        // Виклик методу для закриття задачі
        closeTaskInKanboard(taskId);
    }

    @Step("Генерація випадкового ID задачі")
    private int generateRandomTaskId() {
        return 20 + random.nextInt(81); // Випадкове число від 20 до 100
    }

    @Step("Закриття задачі в Kanboard")
    private void closeTaskInKanboard(int taskId) {
        // Створення тіла запиту
        String json = "{\n" +
                "    \"jsonrpc\": \"2.0\",\n" +
                "    \"method\": \"closeTask\",\n" +
                "    \"id\": 1,\n" +
                "    \"params\": {\n" +
                "        \"task_id\": " + taskId + "\n" +
                "    }\n" +
                "}";

        // Відправка запиту та отримання відповіді
        Response response = sendCloseTaskRequest(json);

        // Виведення статусу та відповіді
        System.out.println("Response Status: " + response.getStatusLine());
        System.out.println("Response Body: " + response.asString());
    }

    @Step("Відправка запиту на закриття задачі")
    private Response sendCloseTaskRequest(String json) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", AUTHORIZATION_HEADER)
                .body(json)
                .post(URL);
    }
}
