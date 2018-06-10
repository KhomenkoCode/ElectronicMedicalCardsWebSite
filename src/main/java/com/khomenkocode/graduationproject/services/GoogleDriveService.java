package com.khomenkocode.graduationproject.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class GoogleDriveService {
	private static final String APPLICATION_NAME = "My Project";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FOLDER = ".credentials/"; // Directory to store user credentials.

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved credentials/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CLIENT_SECRET_DIR = "/client_secret_566664926252-c9hdks1dpleaicbfmb6oocc4j5iqjf5o.apps.googleusercontent.com.json";
	
    public static Drive getService(String code) {
    	NetHttpTransport HTTP_TRANSPORT;
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		} catch (GeneralSecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return null;
		}
    	GoogleTokenResponse tokenResponse = null;
    	try {
        	// Load client secrets.
            InputStream in = GoogleDriveService.class.getResourceAsStream(CLIENT_SECRET_DIR);
            GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
             tokenResponse =
                new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    JacksonFactory.getDefaultInstance(),
                    "https://www.googleapis.com/oauth2/v4/token",
                    clientSecrets.getDetails().getClientId(),
                    clientSecrets.getDetails().getClientSecret(),
                    code,
                    "http://localhost:8080/callback")  // Specify the same redirect URI that you use with your web
                                   // app. If you don't have a web version of your app, you can
                                   // specify an empty string.
                    .execute();

        	String accessToken = tokenResponse.getAccessToken();

            // Use access token to call API
            GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
			
			return ( new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
	                .setApplicationName(APPLICATION_NAME)
	                .build() );
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if(tokenResponse == null)
				return null;
			
			String accessToken = tokenResponse.getRefreshToken();

            // Use access token to call API
            GoogleCredential credential = new GoogleCredential().setAccessToken(accessToken);
			
			return ( new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
	                .setApplicationName(APPLICATION_NAME)
	                .build() );
		}
    }

    
    
    private static java.io.File convert(MultipartFile file) throws IOException {
    	java.io.File convFile = new java.io.File(file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
    
    public static String saveImageAndGetLink(MultipartFile multipartFile, Drive service){
    	File fileMetadata = new File();
    	fileMetadata.setName(multipartFile.getOriginalFilename());
    	
    	java.io.File file;
    	File result = null;
		try {
			file = convert(multipartFile);
	    	FileContent mediaContent = new FileContent("image/*", convert(multipartFile));
	    	
	    	result = service.files().create(fileMetadata, mediaContent).setFields("id, webViewLink").execute();
	    	file.delete(); 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "https://drive.google.com/uc?id="+result.getId();
    }
	
}
