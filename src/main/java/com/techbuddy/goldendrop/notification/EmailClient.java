package com.techbuddy.goldendrop.notification;

import static com.techbuddy.goldendrop.constant.Constants.ORG_NAME;
import static com.techbuddy.goldendrop.notification.MessageTemplate.saleSubmitTemplate;

import com.techbuddy.goldendrop.model.SaleRecord;
import java.text.SimpleDateFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
public class EmailClient implements NotificationClient {

    private final JavaMailSender javaMailSender;

    public void sendMessage(SaleRecord saleRecord) {
        SimpleMailMessage msg = new SimpleMailMessage();
        // TODO - FETCH EMAIL FROM USER IN SALE RECORD
        msg.setTo("manoharkrishna102@gmail.com", "manoranjanisrisai@gmail.com", "lskumarkollati721@gmail.com");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(saleRecord.createdDate);
        msg.setSubject("Sales Report -" + ORG_NAME + " - " + formattedDate);
        String formattedMessage = String.format(
                saleSubmitTemplate,
                formattedDate,
                saleRecord.getSaleAmount(),
                saleRecord.getDigitalAmount(),
                saleRecord.getOnlineAmount(),
                saleRecord.getSaleAmount(),
                saleRecord.getExpenses(),
                saleRecord.updatedDate,
                ORG_NAME);
        msg.setText(formattedMessage);

        javaMailSender.send(msg);
    }
}
