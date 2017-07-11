package com.acme.a3csci3130;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that defines how the data will be stored in the
 * Firebase databse. This is converted to a JSON format
 */

public class Contact implements Serializable {

    public  String keyID;
    public  String name;
    public  String primaryBus;
    public  String bNum;
    public  String address;
    public  String province;

    public Contact() {
        // Default constructor required for calls to DataSnapshot.getValue
    }

    public Contact(String keyID, String name, String primaryBus, String bNum, String address, String province){
        this.keyID = keyID;
        this.name = name;
        this.primaryBus = primaryBus;
        this.bNum = bNum;
        this.address = address;
        this.province = province;
    }

    @Exclude
    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("keyID", keyID);
        result.put("name", name);
        result.put("primaryBus", primaryBus);
        result.put("bNUM", bNum);
        result.put("address", address);
        result.put("province", province);

        return result;
    }
}
