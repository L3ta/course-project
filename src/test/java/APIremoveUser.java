import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.security.SecureRandom;

public class APIremoveUser {

    private static final String URL = "https://192.168.10.173/jsonrpc.php";
    private static final String AUTHORIZATION_HEADER = "Basic YWRtaW46YWRtaW4=";
    private static final SecureRandom random = new SecureRandom();

    // Генерація випадкового ID користувача в діапазоні від 2 до 100
    private static int generateRandomUserId() {
        return 2 + random.nextInt(99); // Випадкове число від 2 до 100
    }

    @Test
    @Description("Видалення випадкового користувача за ID")
    @Step("Запуск тесту на видалення випадкового користувача")
    public void removeRandomUser() {
        // Генерація випадкового ID користувача
        int userIdToRemove = generateRandomUserId();

        // Видалення користувача
        removeUser(userIdToRemove);
    }

    @Step("Видалення користувача з ID: {userId}")
    private void removeUser(int userId) {
        // Створення тіла запиту
        String json = "{\n" +
                "    \"jsonrpc\": \"2.0\",\n" +
                "    \"method\": \"removeUser\",\n" +
                "    \"id\": 2,\n" +
                "    \"params\": {\n" +
                "        \"user_id\": " + userId + "\n" +
                "    }\n" +
                "}";

        // Відправка запиту та отримання відповіді
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", AUTHORIZATION_HEADER)
                .body(json)
                .post(URL);

        // Виведення статусу та відповіді
        System.out.println("Generated User ID to Remove: " + userId);
        System.out.println("Response Status: " + response.getStatusLine());
        System.out.println("Response Body: " + response.asString());
    }
}
