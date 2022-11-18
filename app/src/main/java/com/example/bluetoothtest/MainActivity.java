package com.example.bluetoothtest;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    Button On,Off,Visible,List;
    BluetoothAdapter BA;
    Set<BluetoothDevice> pairedDevices;
    ListView LV;

    ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode()== Activity.RESULT_OK){
                    Intent data=result.getData();
                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        On = findViewById(R.id.buttonOn);
        Off =findViewById(R.id.buttonOff);
        Visible = findViewById(R.id.buttonVisible);
        List = findViewById(R.id.buttonList);

        LV = findViewById(R.id.listView1);

        BA = BluetoothAdapter.getDefaultAdapter();
    }
    public void on(View view){
        if (!BA.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
           // startActivityForResult(intent, 0);

            activityResultLauncher.launch(intent);
        }
        else{
            Toast.makeText(getApplicationContext(),"Bluetooth is already on",
                    Toast.LENGTH_SHORT).show();
        }
    }
    public void list(View view){
        try {
            pairedDevices = BA.getBondedDevices();
        }catch(SecurityException e) {
        }

        ArrayList list = new ArrayList();
        for(BluetoothDevice bt : pairedDevices)
            try{
                list.add(bt.getName());
            }catch(SecurityException e){

            }

        Toast.makeText(getApplicationContext(),"Showing Paired Devices",
                Toast.LENGTH_SHORT).show();
        final ArrayAdapter adapter = new ArrayAdapter
                (this,android.R.layout.simple_list_item_1, list);
        LV.setAdapter(adapter);

    }
    public void off(View view){
        try{
            BA.disable();
        }catch(SecurityException e){

        }
        Toast.makeText(getApplicationContext(),"Turned off" ,
                Toast.LENGTH_LONG).show();
    }
    public void visible(View view){
        try {
            Intent getVisible = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            try {
                startActivityForResult(getVisible, 0);
            } catch (SecurityException e) {
            }
        }catch(Exception e){

        }
    }
}