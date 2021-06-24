package com.myTwilioProject.twilio;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("twilio")
public class TwilioSmsSender implements SmsSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(TwilioSmsSender.class);

    private final TwilioConfiguration twilioConfiguration;

    @Autowired
    public TwilioSmsSender(TwilioConfiguration twilioConfiguration) {
        this.twilioConfiguration = twilioConfiguration;
    }

    @Override
    public void sendSms(SmsRequest smsRequest) {
        String[] phoneNumbers=smsRequest.getPhoneNumber();
        for (String phoneNumber : phoneNumbers) {
            if (isPhoneNumberValid(phoneNumber)) {
                PhoneNumber to = new PhoneNumber("whatsapp:+91"+phoneNumber);
                PhoneNumber from = new PhoneNumber(twilioConfiguration.getTrialNumber());
                String message = smsRequest.getMessage();
                MessageCreator creator = Message.creator(to, from, message);
                creator.create();
                LOGGER.info("Send sms {}", smsRequest);
            } else {
                throw new IllegalArgumentException(
                        "Phone number [" + phoneNumber + "] is not a valid number"
                );
            }
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        // TODO: Implement phone number validator
            //return phoneNumber.contains("+91") && phoneNumber.length() == 22;
            return phoneNumber.length() == 10;
    }
}
