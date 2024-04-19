package com.techbuddy.goldendrop.notification;

import com.techbuddy.goldendrop.model.SaleRecord;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WhatsAppClient implements NotificationClient {
    @Value("${notification.whatsapp.bearer-token}")
    private String bearerToken;
    @Value("${notification.whatsapp.phone-number-id}")
    private String phoneNumberId;
    @Override
    public void sendMessage(SaleRecord saleRecord) {
        String recipientMobileNumber = saleRecord.getUser().getPhoneNumber();

        try {
            HttpRequest request = HttpRequest.newBuilder()
                                             .uri(new URI("https://graph.facebook.com/v13.0/" +phoneNumberId + "/messages"))
                                             .header("Authorization", "Bearer " + bearerToken)
                                             .header("Content-Type", "application/json")
                                             .POST(HttpRequest.BodyPublishers.ofString("{ \"messaging_product\": \"whatsapp\", \"recipient_type\": \"individual\", \"to\":"
                                                                                           +  recipientMobileNumber
                                                                                           + ", \"type\": \"template\", \"template\": { \"name\": \"hello_world\", \"language\": { \"code\": \"en_US\" } } }"))
                                             .build();
            HttpClient http = HttpClient.newHttpClient();
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());

        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
