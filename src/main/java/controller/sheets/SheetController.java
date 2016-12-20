package controller.sheets;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.services.sheets.v4.model.ValueRange;
import controller.listener.ChatListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by Oscar on 11/17/2016.
 */
public class SheetController {
    private static final String APPLICATION_NAME = "NiceBot Sheets Controller";
    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials"+File.separator+"NiceBot");
    private static FileDataStoreFactory DATA_STORE_FACTORY;
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static HttpTransport HTTP_TRANSPORT;

    //scopes
    private static final List<String> SCOPES =
            Arrays.asList(SheetsScopes.SPREADSHEETS);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }

    //Creates authorized Credential object
    private static Credential authorize() throws IOException {
        Credential credential=null;
        try {
            URI filePath = new URI("client_secret.json");
            FileInputStream in = new FileInputStream(filePath.toString());
            GoogleClientSecrets clientSecrets =
                    GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
            // Build flow and trigger user authorization request.
            GoogleAuthorizationCodeFlow flow =
                    new GoogleAuthorizationCodeFlow.Builder(
                            HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                            .setDataStoreFactory(DATA_STORE_FACTORY)
                            .setAccessType("offline")
                            .build();
            credential = new AuthorizationCodeInstalledApp(
                    flow, new LocalServerReceiver()).authorize("user");
            System.out.println(
                    "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
            return credential;
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        //Horrible below this line. Remember to fix later.
        if(credential == null){
            System.exit(1);
        }
        return null;
    }

    private static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

    }

    private String spreadsheetId;
    private Sheets service;
    private static final Logger LOGGER= LoggerFactory.getLogger(ChatListener.class);

    public SheetController(){
        //initializes strings from properties file
        initConfigSettings();
        try{
            service=getSheetsService();
        }catch(IOException e){
            LOGGER.error("Error getting sheets service", e);
        }
        /*
        ValueRange response;
        try {
            service = getSheetsService();
            String range = "Sheet1!A2:E";
            response = service.spreadsheets().values().get(spreadsheetId, range).execute();
            List<List<Object>> values = response.getValues();
            if (values == null || values.size() == 0) {
                System.out.println("No data found.");
            } else {
                System.out.println("Number, School");
                for (List row : values) {
                    // Print columns A and E, which correspond to indices 0 and 4.
                    System.out.printf("%s, %s\n", row.get(0), row.get(4));
                }
            }
        } catch(IOException e){
            e.printStackTrace();
            System.exit(0);
        }
        */
    }

    //Gets values from config file
    private void initConfigSettings(){
        //Setup Properties file
        Properties properties = new Properties();
        String path = "."+ File.separator+"config.properties";
        try {
            FileInputStream file = new FileInputStream(path);
            properties.load(file);
            file.close();
        }catch(FileNotFoundException e){
            LOGGER.error("Properties File Not Found!", e);
            System.exit(0);
        }catch(IOException e){
            LOGGER.error("Properties Load Error!", e);
            System.exit(0);
        }
        //Get String values from properties file
        spreadsheetId=properties.getProperty("spreadsheetId");
    }

    public Sheets getService(){
        return service;
    }

    public String getSpreadsheetId(){
        return spreadsheetId;
    }
}
