package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class Controller {

    public static final String ACCOUNT_SID = "ACe9972b266ecdfb6951a8dff630024fac";
    public static final String AUTH_TOKEN = "70d9ba15889fc62ac1d0e147b68e9d42";

    public ToggleGroup phonebook;
    public TextArea phoneNumberField;
    public Label phoneNumberLabel;
    public ChoiceBox phonebookChoice;
    public Label chooseFromPhonebook;
    public TextArea bodyField;

    public void optionOne(ActionEvent actionEvent) {
        //Select from phonebook
        phoneNumberField.setVisible(false);
        phoneNumberLabel.setVisible(false);
        phonebookChoice.setVisible(true);
        chooseFromPhonebook.setVisible(true);
    }

    public void optionTwo(ActionEvent actionEvent) {
        //Manualy input number
        phoneNumberField.setVisible(true);
        phoneNumberLabel.setVisible(true);
        phonebookChoice.setVisible(false);
        chooseFromPhonebook.setVisible(false);
    }

    public void message(String body,String number){

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber(number), //THIS IS THE TO NUMBER
                new PhoneNumber("+18339883385"), //THIS IS THE TWILIO NUMBER
                body).create();

        System.out.println(message.getSid());
    }

    public void sendText(ActionEvent actionEvent) {
        String body = bodyField.getText();
        String number = phoneNumberField.getText();
        message(body,number);
    }
}
