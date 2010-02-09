package com.uprovenance.util.security;

//  /home/lavm/oupi/common/pstructure/src/main/java/com/uprovenance/util/security/GenKeyPair.java created by Lavm on najah on Wed Aug 12 16:02:45 2009 
//  $Id$ 

public class GenKeyPair {
//     //http://www.docjar.com/html/api/gnu/classpath/tools/keytool/SelfCertCmd.java.html

//     /**
//      * Creates a new key pair and self-signed certificate.
//      */
//     public doGenKeyPair(String alias, String dname,
//                         String keyAlgName, int keysize, String sigAlgName)
//         throws Exception {
//         if (keysize == -1) {
//             if ("EC".equalsIgnoreCase(keyAlgName)) {
//                 keysize = 256;
//             } else {
//                 keysize = 1024;
//             }
//         }

//         if (alias == null) {
//             alias = keyAlias;
//         }

//         if (keyStore.containsAlias(alias)) {
//             MessageFormat form = new MessageFormat(
//                                                    rb
//                                                    .getString("Key pair not generated, alias <alias> already exists"));
//             Object[] source = { alias };
//             throw new Exception(form.format(source));
//         }

//         if (sigAlgName == null) {
//             if ("DSA".equalsIgnoreCase(keyAlgName)) {
//                 sigAlgName = "SHA1WithDSA";
//             } else if ("RSA".equalsIgnoreCase(keyAlgName)) {
//                 sigAlgName = "SHA1WithRSA";
//             } else if ("EC".equalsIgnoreCase(keyAlgName)) {
//                 sigAlgName = "SHA1withECDSA";
//             } else {
//                 throw new Exception(rb
//                                     .getString("Cannot derive signature algorithm"));
//             }
//         }
//         CertAndKeyGen keypair = new CertAndKeyGen(keyAlgName,
//                                                   sigAlgName, providerName);

//         // If DN is provided, parse it. Otherwise, prompt the user for it.
//         X500Name x500Name;
//         if (dname == null) {
//             x500Name = getX500Name();
//         } else {
//             x500Name = new X500Name(dname);
//         }

//         keypair.generate(keysize);
//         PrivateKey privKey = keypair.getPrivateKey();

//         X509Certificate[] chain = new X509Certificate[1];
//         chain[0] = keypair.getSelfCertificate(x500Name,
//                                               (long) validity * 24 * 60 * 60);

//         if (verbose) {
//             MessageFormat form = new MessageFormat(
//                                                    rb
//                                                    .getString("Generating keysize bit keyAlgName key pair and self-signed certificate "
//                                                               + "(sigAlgName) with a validity of validality days\n\tfor: x500Name"));
//             Object[] source = { new Integer(keysize),
//                                 privKey.getAlgorithm(), chain[0].getSigAlgName(),
//                                 new Long(validity), x500Name };
//             System.err.println(form.format(source));
//         }

//         if (keyPass == null) {
//             keyPass = promptForKeyPass(alias, null, storePass);
//         }
//         keyStore.setKeyEntry(alias, privKey, keyPass, chain);
//     }


}

//  end of GenKeyPair.java 

