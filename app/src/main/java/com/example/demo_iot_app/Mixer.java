package com.example.demo_iot_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
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

public class Mixer extends AppCompatActivity {
    MQTTHelper mqttHelper;
    CardView mixer1;
    CardView mixer2;
    CardView mixer3;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mixer);
        mixer1 = findViewById(R.id.mixer1);
        mixer2 = findViewById(R.id.mixer2);
        mixer3 = findViewById(R.id.mixer3);

        mixer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Mixer.this, mixer1.class);
                startActivity(intent);
            }
        });
        mixer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Mixer.this, mixer2.class);
                startActivity(intent);
            }
        });
        mixer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Mixer.this, mixer3.class);
                startActivity(intent);
            }
        });
        startMQTT();
    }


}