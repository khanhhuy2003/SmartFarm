package com.example.demo_iot_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.github.angads25.toggle.model.ToggleableView;
import com.github.angads25.toggle.widget.LabeledSwitch;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.w3c.dom.Text;

import java.nio.charset.Charset;
public class MainActivity extends AppCompatActivity {

    MQTTHelper mqttHelper;
    CardView MixerButton;

//    TextView txtTemperature, txtHumidity;
//    LabeledSwitch StartButton;
//    Button MixerButton;
//    Button CycleButton;
//    Button AreaButton;
//    Button PumpinButton;
//    Button PumpoutButton;
//    EditText inputCycle;
//    EditText inputArea;
//    EditText inputPumpin;
//    EditText inputPumpout;
//
//    Button Mixer1Button;
//    Button Mixer2Button;
//    Button Mixer3Button;
//
//    EditText inputMixer1Button;
//    EditText inputMixer2Button;
//    EditText inputMixer3Button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        txtTemperature = findViewById(R.id.textTemperature);
//        txtHumidity = findViewById(R.id.textHumidity);
//
//        StartButton = findViewById(R.id.StartButton);
          MixerButton = findViewById(R.id.MixerButton);
//
//        CycleButton = findViewById(R.id.CycleButton);
//        PumpinButton = findViewById(R.id.PumpinButton);
//        PumpoutButton = findViewById(R.id.PumpoutButton);
//        AreaButton = findViewById(R.id.AreaButton);
//
//        inputCycle = findViewById(R.id.inputCycle);
//        inputArea = findViewById(R.id.inputArea);
//        inputPumpin = findViewById(R.id.inputPumpin);
//        inputPumpout = findViewById(R.id.inputPumpout);



//        Mixer1Button = findViewById(R.id.Mixer1Button);
//        Mixer2Button = findViewById(R.id.Mixer2Button);
//        Mixer3Button = findViewById(R.id.Mixer3Button);
//
//        inputMixer1Button = findViewById(R.id.inputMixer1Button);
//        inputMixer2Button = findViewById(R.id.inputMixer2Button);
//        inputMixer3Button = findViewById(R.id.inputMixer3Button);

//        StartButton.setOnToggledListener(new OnToggledListener() {
//            @Override
//            public void onSwitched(ToggleableView toggleableView, boolean isOn) {
//                if(isOn == true){
//                    sendDataMQTT( "huytran1305/feeds/assignment.active","1");
//
//                }
//                else{
//                    sendDataMQTT( "huytran1305/feeds/assignment.active","0");
//
//                }
//            }
//        });
//



        MixerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);


            }
        });

//        CycleButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Get the entered number from EditText
//                String numberStr = inputCycle.getText().toString();
//
//                if (!numberStr.isEmpty()) {
//                    // Convert the input to a number
//
//                    String topic = "huytran1305/feeds/assignment.next-cycle"; // Replace with your Adafruit username and feed name
//                    sendDataMQTT(topic, numberStr);
//
//                    // Do something with the number, for example, display it
//
//                } else {
//                    // If EditText is empty, show a toast
//                    Toast.makeText(MainActivity.this, "Please enter a number", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        AreaButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Get the entered number from EditText
//                String numberStr = inputArea.getText().toString();
//
//                if (!numberStr.isEmpty()) {
//                    // Convert the input to a number
//
//                    String topic = "huytran1305/feeds/assignment.selector"; // Replace with your Adafruit username and feed name
//                    sendDataMQTT(topic, numberStr);
//
//                    // Do something with the number, for example, display it
//
//                } else {
//                    // If EditText is empty, show a toast
//                    Toast.makeText(MainActivity.this, "Please enter a number", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        PumpinButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Get the entered number from EditText
//                String numberStr = inputPumpin.getText().toString();
//
//                if (!numberStr.isEmpty()) {
//                    // Convert the input to a number
//
//                    String topic = "huytran1305/feeds/assignment.pump-in"; // Replace with your Adafruit username and feed name
//                    sendDataMQTT(topic, numberStr);
//
//                    // Do something with the number, for example, display it
//
//                } else {
//                    // If EditText is empty, show a toast
//                    Toast.makeText(MainActivity.this, "Please enter a number", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

//        PumpoutButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Get the entered number from EditText
//                String numberStr = inputPumpout.getText().toString();
//
//                if (!numberStr.isEmpty()) {
//                    // Convert the input to a number
//
//                    String topic = "huytran1305/feeds/assignment.pump-out"; // Replace with your Adafruit username and feed name
//                    sendDataMQTT(topic, numberStr);
//
//                    // Do something with the number, for example, display it
//
//                } else {
//                    // If EditText is empty, show a toast
//                    Toast.makeText(MainActivity.this, "Please enter a number", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        startMQTT();
    }
    public void sendDataMQTT(String topic, String value){
        MqttMessage msg = new MqttMessage();
        msg.setId(1234);
        msg.setQos(0);
        msg.setRetained(false);

        byte[] b = value.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);

        try {
            mqttHelper.mqttAndroidClient.publish(topic, msg);
        }catch (MqttException e){
        }
    }

    public void startMQTT(){
        mqttHelper = new MQTTHelper(this);
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override


            public void connectComplete(boolean reconnect, String serverURI) {
                Log.d("TEST", "Subcribed");
            }

            @Override
            public void connectionLost(Throwable cause) {


            }
            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("TEST", topic + "***" + message.toString());
//                if(topic.contains("huytran1305/feeds/assignment.temperature")){
//                    txtTemperature.setText(message.toString() + "*C");
//                }
//                else if(topic.contains("huytran1305/feeds/assignment.humidity")){
//                    txtHumidity.setText(message.toString() + "%");
//                }

//                else if(topic.contains("nutnhan2")){
//                    if(message.toString().equals("1")){
//                        btnLED2.setOn(true);
//                    }
//                    else{
//                        btnLED2.setOn(false);
//                    }
//                }
            }
            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

}
