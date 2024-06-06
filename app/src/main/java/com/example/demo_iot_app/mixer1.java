package com.example.demo_iot_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.example.demo_iot_app.MQTTHelper;

import java.nio.charset.Charset;
import android.os.Bundle;

public class mixer1 extends AppCompatActivity {
    MQTTHelper mqttHelper;
    EditText mixer1;
    Button mixer1Button;

    public void sendDataMQTT(String topic, String value){
        MqttMessage msg = new MqttMessage();
        msg.setId(1234);
        msg.setQos(0);
        msg.setRetained(false);

        byte[] b = value.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);

        try {
            mqttHelper.mqttAndroidClient.publish(topic, msg);
        } catch (MqttException e) {
        }
    }

    public void startMQTT() {
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
//                    if(topic.contains("cambien1")){
//                        txtTemperature.setText(message.toString() + "*C");
//                    }
//                    else if(topic.contains("cambien2")){
//                        txtHumidity.setText(message.toString() + "%");
//                    }
//                else if(topic.contains("nutnhan1")){
//                    if(message.toString().equals("1")){
//                        btnLED1.setOn(true);
//                    }
//                    else{
//                        btnLED1.setOn(false);
//                    }
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
    private boolean isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mixer1);

        mixer1 = findViewById(R.id.mixer1);
        mixer1Button = findViewById(R.id.mixer1Button);
        mixer1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isInternetConnected()) {
                    Toast.makeText(mixer1.this, "No internet connection", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Get the entered number from EditText
                String numberStr = mixer1.getText().toString();

                if (!numberStr.isEmpty()) {
                    // Convert the input to a number

                    String topic = "khanhhuy03/feeds/mixer1"; // Replace with your Adafruit username and feed name
                    sendDataMQTT(topic, numberStr);

                    // Do something with the number, for example, display it

                } else {
                    // If EditText is empty, show a toast
                    Toast.makeText(mixer1.this, "Please enter a number", Toast.LENGTH_SHORT).show();
                }
            }


        });
        startMQTT();
    }


}