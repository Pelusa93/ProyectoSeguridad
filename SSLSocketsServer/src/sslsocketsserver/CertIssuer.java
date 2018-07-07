package sslsocketsserver;

/**
 *
 * 
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.InvalidKeyException;
import java.security.KeyStore;

import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.security.cert.Certificate;
//import java.security.Certificate;


/**
 * Este es el metodo que genera el certificado al usuario
 * @author elberg
 */

public class CertIssuer {
   private String certAlias="serverSocketDom";

   public void generar() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidKeyException, CertificateException, SignatureException, IOException, KeyStoreException{ 
   KeyStore keyStore =  KeyStore.getInstance("JKS");
   InputStream st= new FileInputStream(System.getProperty("javax.net.ssl.keyStore"));
   
   
   keyStore.load(st,"PreguntA1234".toCharArray());

   
// generate the certificate
// first parameter  = Algorithm
// second parameter = signrature algorithm
// third parameter  = the provider to use to generate the keys (may be null or
//                    use the constructor without provider)
CertAndKeyGen certGen = new CertAndKeyGen("RSA", "SHA256WithRSA", null);
// generate it with 2048 bits
certGen.generate(2048);

// prepare the validity of the certificate
long validSecs = (long) 365 * 24 * 60 * 60; // valid for one year
// add the certificate information, currently only valid for one year.
X509Certificate cert = certGen.getSelfCertificate(
   // enter your details according to your application
   new X500Name("CN=My Application,O=My Organisation,L=My City,C=DE"), validSecs);

// set the certificate and the key in the keystore
keyStore.setKeyEntry(certAlias, certGen.getPrivateKey(), "fresa*1984".toCharArray(), 
                        new X509Certificate[] { cert });

    String alias = certAlias;
    Certificate cert1 = keyStore.getCertificate(alias);

    File file = new File("src//sslsocketsserver//certs//nuevos_cert//cert1.crt");
    byte[] buf = cert1.getEncoded();

    FileOutputStream os = new FileOutputStream(file);
    os.write(buf);
    os.close();

    Writer wr = new OutputStreamWriter(os, Charset.forName("UTF-8"));
    wr.write(new sun.misc.BASE64Encoder().encode(buf));
    //wr.flush();



   /*CertificateFactory certFactory = CertificateFactory.getInstance("X.509");

    FileInputStream fis = new FileInputStream("src//sslsocketsserver//certs//server//cert1.crt");

    Certificate cert1 = certFactory.generateCertificate(fis);
    fis.close();

    System.out.println(cert1);*/
    }
}
