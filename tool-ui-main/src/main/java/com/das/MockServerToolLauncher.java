package com.das;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;

import com.das.splash.SplashHandler;
import com.drishlent.dlite.annotation.Application;
import com.drishlent.dlite.annotation.EnableSplash;
import com.drishlent.dlite.annotation.EnableUI;
import com.drishlent.dlite.enumm.ApplicationType;
import com.drishlent.dlite.launcher.DliteApplication;

@Application(value=ApplicationType.SWING)
@EnableUI
//@EnableSplash(action=SplashHandler.class)
public class MockServerToolLauncher {
	static final Logger mLogger = Logger.getLogger(MockServerToolLauncher.class);
	
	public static void main(String[] args) throws Exception {
		mLogger.info("MST Application statered on date : "+new Date());
		FileInputStream is = null;
		try {

	        File file = new File("conf/test13.jks");
	        is = new FileInputStream(file);
	        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
	        String password = "pask.sansar";
	        keystore.load(is, password.toCharArray());


	        Enumeration enumeration = keystore.aliases();
	        while(enumeration.hasMoreElements()) {
	            String alias = (String)enumeration.nextElement();
	            System.out.println("alias name: " + alias);
	            Certificate certificate = keystore.getCertificate(alias);
	            System.out.println(certificate.toString());
	            PublicKey publicKey = certificate.getPublicKey();
	            System.out.println("pub : "+publicKey.toString());
	            
	            X509Certificate xx = (X509Certificate)certificate;
	            System.out.println("XX : "+xx.getNotAfter());
	            //xx.checkValidity();
	            DliteApplication.run(MockServerToolLauncher.class, args);
	        }

	    } catch (java.security.cert.CertificateException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Certificate has been expired", "Expire",
	                JOptionPane.ERROR_MESSAGE);
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (FileNotFoundException e) {
	        e.printStackTrace();
	    } catch (KeyStoreException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }finally {
	        if(null != is)
	            try {
	                is.close();
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	    }   

		
		
	}

}
