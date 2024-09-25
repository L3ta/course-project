import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.security.SecureRandom;
import java.util.Base64;

public class APIcreateUser {

    private static final SecureRandom random = new SecureRandom();

    // Генерація випадкового рядка
    private static String generateRandomString(int length) {
        byte[] bytes = new byte[length];
        random.nextBytes(bytes);
        return Base64.getEncoder().withoutPadding().encodeToString(bytes).substring(0, length);
    }

    @Test
    @Description("Тест на створення випадкового користувача")
    @Step("Запуск тесту на створення користувача")
    public void createUserTest() {
        // URL API
        String url = "https://192.168.10.173/jsonrpc.php";

        // Генерація випадкових логіна та пароля
        String username = generateRandomString(8);
        String password = generateRandomString(12);

        // Виведення логіна та пароля
        System.out.println("Generated Username: " + username);
        System.out.println("Generated Password: " + password);

        // Створення тіла запиту
        String json = "{\n" +
                "    \"jsonrpc\": \"2.0\",\n" +
                "    \"method\": \"createUser\",\n" +
                "    \"id\": 2,\n" +
                "    \"params\": {\n" +
                "        \"username\": \"" + username + "\",\n" +
                "        \"password\": \"" + password + "\"\n" +
                "    }\n" +
                "}";

        // Відправка запиту та отримання відповіді
        Response response = sendCreateUserRequest(url, json);

        // Виведення статусу та відповіді
        System.out.println("Response Status: " + response.getStatusLine());
        System.out.println("Response Body: " + response.asString());
    }

    @Step("Відправка запиту на створення користувача")
    private Response sendCreateUserRequest(String url, String json) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .header("Authorization", "Basic YWRtaW46YWRtaW4=")
                .body(json)
                .post(url);
    }
}
