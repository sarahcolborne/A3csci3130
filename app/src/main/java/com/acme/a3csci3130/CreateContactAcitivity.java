package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateContactAcitivity extends Activity {

    private Button submitButton;
    private EditText nameField, primaryBusField, bNumField, addressField, provinceField;
    private TextView errorMessage;
    private MyApplicationData appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_acitivity);
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        submitButton = (Button) findViewById(R.id.submitButton);
        nameField = (EditText) findViewById(R.id.name);
        primaryBusField = (EditText) findViewById(R.id.primaryBusField);
        bNumField = (EditText) findViewById(R.id.bNumField);
        addressField = (EditText) findViewById(R.id.addressField);
        provinceField = (EditText) findViewById(R.id.provinceField);
        errorMessage = (TextView) findViewById(R.id.errorMessage);


    }

    /**
     *
     * @param v: given the view
     *
     *         This method creates a new entry with values in Firebase. When the user
     *         presses the submit button, the values entered in the editTexts are verified,
     *         and then sent to be stored in the Firebase.
     *
     *         If not valid, values are not sent and a message appears for the user.
     *
     */
    public void submitInfoButton(View v) {
        boolean valid = false;
        boolean flag = false;

        //Values are retrieved
        String keyID = appState.firebaseReference.push().getKey();
        String name = nameField.getText().toString();
        String primaryBus = primaryBusField.getText().toString();
        String businessNum = bNumField.getText().toString();
        String address = addressField.getText().toString();
        String province = provinceField.getText().toString();

        //Values are verified
        if (!valid){
            if (name.length() > 1 && name.length() < 23){
                //valid
            }
            else { //invalid
                //errorMessage.setText("name");
                flag = true;
            }

            if (primaryBus.equals("Fisher") || primaryBus.equals("Distributor") || primaryBus.equals("Distributor") || primaryBus.equals("Processor") || primaryBus.equals("Fish Monger")){
                //valid
            }
            else { //invalid
                //errorMessage.setText("pBus");
                flag = true;
            }

            if (businessNum.length() == 9 && businessNum.matches("[0-9]+")){
                //valid
            }
            else { //invalid
                //errorMessage.setText("bNum");
                flag = true;
            }

            if (address.length() < 50){
                //valid
            }
            else { //invalid
                //errorMessage.setText("address");
                flag = true;
            }

            if (province.equals("AB") || province.equals("BC") || province.equals("MB") || province.equals("NB") || province.equals("NL") || province.equals("NS") || province.equals("NT") || province.equals("NU") || province.equals("ON") || province.equals("PE") || province.equals("QC") || province.equals("SK") || province.equals("YT") || province.equals("")){
                //valid
            }
            else { //invalid
                //errorMessage.setText("province");
                flag = true;
            }

            //final check
            if (flag == false){
                valid = true;

            }

        }


        //If valid, sent to firebase
        if (valid) {
            Contact person = new Contact(keyID, name, primaryBus, businessNum, address, province);
            appState.firebaseReference.child(keyID).setValue(person);
            finish();
        }
        else {
            errorMessage.setText("One or more values are invalid. Please try again.");

        }



    }
}
