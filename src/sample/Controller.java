package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller {

    public static final String ACCOUNT_SID = "ACe9972b266ecdfb6951a8dff630024fac";
    public static final String AUTH_TOKEN = "70d9ba15889fc62ac1d0e147b68e9d42";

    public ToggleGroup phonebook;
    public TextArea phoneNumberField;
    public Label phoneNumberLabel;
    public ChoiceBox phonebookChoice;
    public Label chooseFromPhonebook;
    public TextArea bodyField;
    public Button contactAdd;


    public void optionOne(ActionEvent actionEvent) {
        //Select from phonebook
        phoneNumberField.setVisible(false);
        phoneNumberLabel.setVisible(false);
        contactAdd.setVisible(false);
        phonebookChoice.setVisible(true);
        chooseFromPhonebook.setVisible(true);
        getContacts();
    }

    public void optionTwo(ActionEvent actionEvent) {
        //Manualy input number
        phoneNumberField.setVisible(true);
        phoneNumberLabel.setVisible(true);
        contactAdd.setVisible(true);
        phonebookChoice.setVisible(false);
        chooseFromPhonebook.setVisible(false);
    }

    public void message(String body, String number) {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber(number), //THIS IS THE TO NUMBER
                new PhoneNumber("+18339883385"), //THIS IS THE TWILIO NUMBER
                body).create();

        System.out.println(message.getSid());
    }

    public void getContacts() {
        List<String> Contacts = new ArrayList<String>();
        try {
            File file = new File("resources/Contacts");
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                Contacts.add(input.nextLine());
            }
        } catch (java.io.FileNotFoundException fileName) {
            System.out.println("FILE NOT FOUND");
        }

        System.out.println(Contacts);

        for (String item : Contacts) phonebookChoice.getItems().add(item);
    }

    public void sendText(ActionEvent actionEvent) {
        String body = bodyField.getText();
        String number = phonebookChoice.getValue().toString();
        message(body, number);
    }

    public void addContact(ActionEvent actionEvent) {
        String newContact = phoneNumberField.getText();
        if (newContact.length() < 10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Phone Number");
            alert.setHeaderText(null);
            alert.setContentText("Phone number is too short, please input a valid number");
            alert.showAndWait();
        } else {
            BufferedWriter bw = null;

            try {
                // APPEND MODE SET HERE
                bw = new BufferedWriter(new FileWriter("resources/Contacts", true));
                bw.newLine();
                bw.write(newContact);
                bw.flush();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {                       // always close the file
                if (bw != null) try {
                    bw.close();
                } catch (IOException ioe2) {
                    // just ignore it
                }
            }
        }
    }
}