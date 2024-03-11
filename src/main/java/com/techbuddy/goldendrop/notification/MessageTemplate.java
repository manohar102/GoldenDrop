package com.techbuddy.goldendrop.notification;

public class MessageTemplate {

    public static String saleSubmitTemplate =
            """
    Dear user,\s

    Sales report dated %s has been submitted successfully with the following details.\s
    SaleAmount - %s\s
    DigitalAmount - %s\s
    OnlineAmount - %s\s
    Expenses - %s\s
    Comments = %s\s




    Thanks&Regards,
    %s""";
}
