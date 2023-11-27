package ermakov.onlinebanking.currency;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class СurrencyAPI {
    public static void main(String[] args) {
        String apiUrl = "https://belarusbank.by/api/kursExchange";

        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet(apiUrl);
            HttpResponse response = client.execute(request);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            JsonArray jsonArray = JsonParser.parseString(result.toString()).getAsJsonArray();

            for (JsonElement element : jsonArray) {
                JsonObject jsonObject = element.getAsJsonObject();
                if (jsonObject.has("USD") && jsonObject.get("USD").isJsonObject()) {
                    JsonObject usdObject = jsonObject.getAsJsonObject("USD");
                    String usdIn = usdObject.has("in") ? usdObject.get("in").getAsString() : "N/A";
                    String usdOut = usdObject.has("out") ? usdObject.get("out").getAsString() : "N/A";

                    System.out.println("USD_in: " + usdIn);
                    System.out.println("USD_out: " + usdOut);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
