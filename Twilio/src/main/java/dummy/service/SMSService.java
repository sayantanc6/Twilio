package dummy.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.google.common.util.concurrent.ListenableFuture;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.sdk.Twilio;
import com.twilio.sdk.converter.Promoter;
import com.twilio.base.ResourceSet;
import com.twilio.http.TwilioRestClient;
import com.twilio.type.PhoneNumber;

@Service
public class SMSService {
	
	@Value("${ACCOUNT_SID}")
	public String ACCOUNT_SID;
	
	@Value("${AUTH_TOKEN}")
    public String AUTH_TOKEN;

	@Value("${FROM_NUMBER}")
	private String from_number;
	
	@Value("${TO_NUMBER}")
	private String to_number;

	public void send() {
		TwilioRestClient client = new TwilioRestClient.Builder(ACCOUNT_SID, AUTH_TOKEN).build();
        Message message= Message.creator(
        		new PhoneNumber(to_number),
        		new PhoneNumber(from_number), "Sample Twilio SMS using Java").create(client);
                
	}
	
	public void sendmedia() {
		TwilioRestClient client = new TwilioRestClient.Builder(ACCOUNT_SID, AUTH_TOKEN).build();
		Message message= Message.creator(
        		new PhoneNumber(to_number),
        		new PhoneNumber(from_number), "Sample Twilio SMS media using Java")
				.setMediaUrl(
                        Promoter.listOfOne(
                                URI.create("E:\\sts-workspace\\MailDemo\\src\\main\\resources\\static\\logo-social-sq.png")))
				.create(client);
	}

	public void sendasyncmedia() {
		TwilioRestClient client = new TwilioRestClient.Builder(ACCOUNT_SID, AUTH_TOKEN).build();
		ListenableFuture<ResourceSet<Message>> future = Message.reader().readAsync();
        Futures.addCallback(
                future,
                new FutureCallback<ResourceSet<Message>>() {
                    public void onSuccess(ResourceSet<Message> messages) {
                        for (Message message : messages) {
                            System.out.println(message.getSid() + " : " + message.getStatus());
                        }
                    }

                    public void onFailure(Throwable t) {
                        System.out.println("Failed to get message status: " + t.getMessage());
                    }
                });
	}
	
	public void sendsmsStatus() {
		TwilioRestClient client = new TwilioRestClient.Builder(ACCOUNT_SID, AUTH_TOKEN).build();
		ResourceSet<Message> messages = Message.reader().read();
        for (Message message : messages) {
            System.out.println(message.getSid() + " : " + message.getStatus());
        }
		
	}
	
}
