package sslsocketsserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.Date;

import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.CertificateIssuerName;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateSubjectName;
import sun.security.x509.CertificateValidity;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;
import sun.security.x509.X509CertInfo;

public class GenCert {
  public void Generar() throws Exception {
      
    System.out.println("GENCERTTTTTT");
    String keystoreFile =System.getProperty("javax.net.ssl.keyStore");
    String caAlias = "serverKay";
    String certToSignAlias = "PARAFIRMAR";
    String newAlias = "newAlias";

    char[] password = new char[]{'a','b','c','d','e','f','g','h'};
    char[] caPassword = new char[]{'a','b','c','d','e','f','g','h'};
    char[] certPassword = new char[]{'a','b','c','d','e','f','g','h'};

    //Propiedades KeyStore
    KeyStore keyStore =  KeyStore.getInstance("JKS");
    InputStream st= new FileInputStream(System.getProperty("javax.net.ssl.keyStore"));
    keyStore.load(st,"PreguntA1234".toCharArray());
    st.close();

    PrivateKey caPrivateKey = (PrivateKey) keyStore.getKey(caAlias, caPassword);
    java.security.cert.Certificate caCert = keyStore.getCertificate("serverKay");

    byte[] encoded = caCert.getEncoded();
    X509CertImpl caCertImpl = new X509CertImpl(encoded);

    X509CertInfo caCertInfo = (X509CertInfo) caCertImpl.get(X509CertImpl.NAME + "."
        + X509CertImpl.INFO);

    X500Name issuer = (X500Name) caCertInfo.get(X509CertInfo.SUBJECT + "."
        + CertificateIssuerName.DN_NAME);

    java.security.cert.Certificate cert = keyStore.getCertificate("serverKay");
    PrivateKey privateKey = (PrivateKey) keyStore.getKey("serverKey", "fresa*1984".toCharArray());
    encoded = cert.getEncoded();
    X509CertImpl certImpl = new X509CertImpl(encoded);
    X509CertInfo certInfo = (X509CertInfo) certImpl
        .get(X509CertImpl.NAME + "." + X509CertImpl.INFO);

    Date firstDate = new Date();
    Date lastDate = new Date(firstDate.getTime() + 365 * 24 * 60 * 60 * 1000L);
    CertificateValidity interval = new CertificateValidity(firstDate, lastDate);

    certInfo.set(X509CertInfo.VALIDITY, interval);

    certInfo.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(
        (int) (firstDate.getTime() / 1000)));

    certInfo.set(X509CertInfo.ISSUER + "." + CertificateSubjectName.DN_NAME, issuer);

    AlgorithmId algorithm = new AlgorithmId(AlgorithmId.md5WithRSAEncryption_oid);
    certInfo.set(CertificateAlgorithmId.NAME + "." + CertificateAlgorithmId.ALGORITHM, algorithm);
    X509CertImpl newCert = new X509CertImpl(certInfo);

    newCert.sign(caPrivateKey, "MD5WithRSA");

    keyStore.setKeyEntry(newAlias, privateKey, certPassword,
        new java.security.cert.Certificate[] { newCert });

    FileOutputStream output = new FileOutputStream(keystoreFile);
    keyStore.store(output, password);
    output.close();
    
    
    Certificate cert1 = keyStore.getCertificate("PARAFIRMAR");

    System.out.println(cert1);
    File file = new File("src//sslsocketsserver//certs//server//new//cert1.crt");
    
    if (!file.exists()) 
        file.createNewFile();
    
    byte[] buf = newCert.getEncoded();
    

    FileOutputStream os = new FileOutputStream(file);
    os.write(buf);
    os.close();

    Writer wr = new OutputStreamWriter(os, Charset.forName("UTF-8"));
    wr.write(new sun.misc.BASE64Encoder().encode(buf));
    //wr.flush();
 
  }
}