package dummy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.sdk.Twilio;

import dummy.service.SMSService;

@RestController
public class SMSController {
	
	@Value("${ACCOUNT_SID}")
	public String ACCOUNT_SID;
	
	@Value("${AUTH_TOKEN}")
    public String AUTH_TOKEN;

	@Autowired
    private SMSService service;    
    
    @PutMapping("/sms")
    public void smsSubmit() {
           service.send();        
    }
    
    @PostMapping(value = "/sms/media",consumes = MediaType.APPLICATION_JSON_VALUE,
    		produces = MediaType.APPLICATION_JSON_VALUE)
    public void smsMediaSubmit() {

           service.sendmedia();        
    }
    
    @PutMapping("/sms/mediaasync")
    public void smsAsyncSubmit() {
           service.sendasyncmedia();        
    }
    
    @PutMapping("/sms/status")
    public void smsstatus() {
           service.sendsmsStatus();;        
    }
   
}
