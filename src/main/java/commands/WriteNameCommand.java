package commands;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.pircbotx.hooks.types.GenericMessageEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oscar on 11/28/2016.
 */
public class WriteNameCommand implements ICommand {

    private Sheets service;
    private String spreadsheetId;
    private static final Logger LOGGER= LoggerFactory.getLogger(WriteNameCommand.class);


    public WriteNameCommand(Sheets service, String spreadsheetId){
        this.service=service;
        this.spreadsheetId=spreadsheetId;
    }

    public void execute(GenericMessageEvent event) {
        ValueRange response;
        /*
        try{
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
        List<Object> data1 = new ArrayList<>();
        data1.add(event.getUser().getNick());
        List<List<Object>> data = new ArrayList<>();
        data.add(data1);

        response = new ValueRange();
        response.setValues(data);
        response.setMajorDimension("ROWS");
        try {
            service.spreadsheets().values().update(spreadsheetId, "Sheet1!A1" ,response).setValueInputOption("RAW").execute();
        }catch(IOException e){
            LOGGER.error("Error writing name", e);
        }
    }
}
