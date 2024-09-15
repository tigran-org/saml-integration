package com.org.saml.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class CertificateUtils {

    public static X509Certificate getX509CertificateFromPem(String pemCertificate) throws Exception {
        // Remove the first and last lines (BEGIN/END CERTIFICATE) and any extra whitespace
        String cleanedPem = pemCertificate
                .replaceAll("-----BEGIN CERTIFICATE-----", "")
                .replaceAll("-----END CERTIFICATE-----", "")
                .replaceAll("\\s", "");

        // Decode the base64 encoded certificate
        byte[] decodedCert = Base64.getDecoder().decode(cleanedPem);

        // Generate certificate from decoded byte array
        CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
        try (InputStream certStream = new ByteArrayInputStream(decodedCert)) {
            return (X509Certificate) certFactory.generateCertificate(certStream);
        }
    }
}
