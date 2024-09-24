import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.security.SecureRandom;

public class APIRemoveProject {

    private static final String URL = "http://192.168.1.10:80/jsonrpc.php";
    private static final String AUTHORIZATION_HEADER = "Basic YWRtaW46YWRtaW4=";
    private static final SecureRandom random = new SecureRandom();

    @Test
    @Description("Видалення випадкового проєкту за ID")
    @Step("Запуск тесту на видалення випадкового проєкту")
    public void removeProject() {
        // Генерація випадкового ID проєкту від 20 до 100
        int projectId = generateRandomProjectId();

        // Виклик методу для видалення проєкту
        removeProjectInKanboard(projectId);
    }

    @Step("Видалення проєкту з ID: {projectId}")
    private void removeProjectInKanboard(int projectId) {
        // Створення тіла запиту
        String json = "{\n" +
                "    \"jsonrpc\": \"2.0\",\n" +
                "    \"method\": \"removeProject\",\n" +
                "    \"id\": 1,\n" +
                "    \"params\": {\n" +
                "        \"project_id\": " + projectId + "\n" +
                "    }\n" +
                "}";

        // Відправка запиту та отримання відповіді
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", AUTHORIZATION_HEADER)
                .body(json)
                .post(URL);

        // Виведення статусу та відповіді
        System.out.println("Generated Project ID to Remove: " + projectId);
        System.out.println("Response Status: " + response.getStatusLine());
        System.out.println("Response Body: " + response.asString());
    }

    // Генерація випадкового ID проєкту від 20 до 100
    private int generateRandomProjectId() {
        return 20 + random.nextInt(81); // Випадкове число від 20 до 100
    }
}
