import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class APICreateProject {

    private static final String URL = "http://192.168.1.10:80/jsonrpc.php";
    private static final String AUTHORIZATION_HEADER = "Basic YWRtaW46YWRtaW4=";

    @Test
    @Description("Тест на створення нового проекту")
    @Step("Запуск тесту на створення проекту")
    public void createProject() {
        // Дані для створення проєкту
        String projectName = "New Project";

        // Виклик методу для створення проєкту
        createProjectInKanboard(projectName, 1); // 1 - owner_id
    }

    @Step("Створення проекту в Kanboard")
    private void createProjectInKanboard(String projectName, int ownerId) {
        // Створення тіла запиту
        String json = "{\n" +
                "    \"jsonrpc\": \"2.0\",\n" +
                "    \"method\": \"createProject\",\n" +
                "    \"id\": 1,\n" +
                "    \"params\": {\n" +
                "        \"name\": \"" + projectName + "\",\n" +
                "        \"owner_id\": " + ownerId + "\n" +
                "    }\n" +
                "}";

        // Відправка запиту та отримання відповіді
        Response response = sendCreateProjectRequest(json);

        // Виведення статусу та відповіді
        System.out.println("Response Status: " + response.getStatusLine());
        System.out.println("Response Body: " + response.asString());
    }

    @Step("Відправка запиту на створення проекту")
    private Response sendCreateProjectRequest(String json) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", AUTHORIZATION_HEADER)
                .body(json)
                .post(URL);
    }
}
