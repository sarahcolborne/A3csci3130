package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.*;

public class DetailViewActivity extends Activity {

    private EditText nameField, primaryBusField, bNumField, addressField, provinceField;
    private TextView errorMessage;

    Contact receivedPersonInfo;
    private MyApplicationData appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        appState = ((MyApplicationData) getApplicationContext());
        receivedPersonInfo = (Contact)getIntent().getSerializableExtra("Contact");

        nameField = (EditText) findViewById(R.id.name);
        primaryBusField = (EditText) findViewById(R.id.primaryBusField);
        bNumField = (EditText) findViewById(R.id.bNumField);
        addressField = (EditText) findViewById(R.id.addressField);
        provinceField = (EditText) findViewById(R.id.provinceField);
        errorMessage = (TextView) findViewById(R.id.errorMessage);

        if(receivedPersonInfo != null){
            nameField.setText(receivedPersonInfo.name);
            primaryBusField.setText(receivedPersonInfo.primaryBus);
            bNumField.setText(receivedPersonInfo.bNum);
            addressField.setText(receivedPersonInfo.address);
            provinceField.setText(receivedPersonInfo.province);
        }
    }

    /**
     *
     * @param v: given the current view
     *
     *         This method updates Firebase values for a particular contact (business in the
     *         seafood marketplace). When the user presses the update button, the values in
     *         the editTexts are retrieved, verified, and then sent to firebase.
     *
     *         If invalid, values are not pushed and a message is displayed.
     *
     */
    public void updateContact(View v){
        boolean valid = false;
        boolean flag = false;

        //Get values from editText areas
        String newName = nameField.getText().toString();
        String newPrimaryBus = primaryBusField.getText().toString();
        String newBNum = bNumField.getText().toString();
        String newAddress = addressField.getText().toString();
        String newProvince = provinceField.getText().toString();

        //Values are verified
        //Note: if statements were left in for debugging
        if (!valid){
            if (newName.length() > 1 && newName.length() < 23){
                //valid
            }
            else { //invalid
                errorMessage.setText("name");
                flag = true;
            }

            if (newPrimaryBus.equals("Fisher") || newPrimaryBus.equals("Distributor") || newPrimaryBus.equals("Distributor") || newPrimaryBus.equals("Processor") || newPrimaryBus.equals("Fish Monger")){
                //valid
            }
            else { //invalid
                errorMessage.setText("pBus");
                flag = true;
            }

            if (newBNum.length() == 9 && newBNum.matches("[0-9]+")){
                //valid
            }
            else { //invalid
                errorMessage.setText("bNum");
                flag = true;
            }

            if (newAddress.length() < 50){
                //valid
            }
            else { //invalid
                errorMessage.setText("address");
                flag = true;
            }

            if (newProvince.equals("AB") || newProvince.equals("BC") || newProvince.equals("MB") || newProvince.equals("NB") || newProvince.equals("NL") || newProvince.equals("NS") || newProvince.equals("NT") || newProvince.equals("NU") || newProvince.equals("ON") || newProvince.equals("PE") || newProvince.equals("QC") || newProvince.equals("SK") || newProvince.equals("YT") || newProvince.equals("")){
                //valid
            }
            else { //invalid
                errorMessage.setText("province");
                flag = true;
            }

            //final check
            if (flag == false){
                valid = true;

            }

        }


        //If valid, sent to firebase
        if (valid) {
            //Using values retreived from editText areas, replace Firebase values
            String taskID = receivedPersonInfo.keyID;
            appState.firebaseReference.child(taskID).child("name").setValue(newName);
            appState.firebaseReference.child(taskID).child("primaryBus").setValue(newPrimaryBus);
            appState.firebaseReference.child(taskID).child("bNum").setValue(newBNum);
            appState.firebaseReference.child(taskID).child("address").setValue(newAddress);
            appState.firebaseReference.child(taskID).child("province").setValue(newProvince);
            //finish();
        }
        else {
            errorMessage.setText("One or more values are invalid. Please try again.");

        }

    }

    /**
     *
     * @param v: given the current view
     *
     *         This method deletes the Firebase entry for this contact (business in the
     *         seafood marketplace). When the user presses the erase button, the entry in
     *         firebase which matches the keyID is removed.
     */
    public void eraseContact(View v)
    {
        //This erases the child with the matching keyID
        String taskID = receivedPersonInfo.keyID;
        appState.firebaseReference.child(taskID).removeValue();
    }
}
