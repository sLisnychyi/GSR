package com.slis.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.api.client.json.jackson.JacksonFactory;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public class SpredsheetServiceUtil {

    private static final String SERVICE_NAME = "juja_table"; //service name could be any

    private final String serviceAccountId;
    private final List<String> scopes;
    private final String p12FilePath;
    private final HttpTransport httpTransport;
    private final JacksonFactory jsonFactory;

    public SpredsheetServiceUtil(String serviceAccountId, List<String> scopes, String p12FilePath) {
        this.serviceAccountId = serviceAccountId;
        this.scopes = scopes;
        this.p12FilePath = p12FilePath;
        this.httpTransport = new NetHttpTransport();
        this.jsonFactory = new JacksonFactory();

    }

    public GoogleCredential generateCredentials(){
        File p12 = new File(p12FilePath);
        GoogleCredential credential = null;
        try {
            credential = new GoogleCredential.Builder()
                    .setTransport(httpTransport)
                    .setJsonFactory(jsonFactory)
                    .setServiceAccountId(serviceAccountId)
                    .setServiceAccountScopes(scopes)
                    .setServiceAccountPrivateKeyFromP12File(p12)
                    .build();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Can't find .p12 file.");
            e.printStackTrace();
        }
        return credential;
    }

    public SpreadsheetService generateSpreadSheetService(){
        GoogleCredential credentials = generateCredentials();
        SpreadsheetService service = new SpreadsheetService(SERVICE_NAME);
        service.setOAuth2Credentials(credentials);
        return service;
    }

}
