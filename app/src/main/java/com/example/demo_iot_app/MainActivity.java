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
    CardView environment;
    CardView pumping;
    CardView activate;
    CardView mixer;
    CardView time;
    CardView area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        environment = findViewById(R.id.environment);
        pumping = findViewById(R.id.pumping);
        activate =findViewById(R.id.activate);
        mixer = findViewById(R.id.MixerButton);
        time = findViewById(R.id.time);
        area = findViewById(R.id.area);

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
        environment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);


            }
        });
        pumping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, MainActivity5.class);
                startActivity(intent);
            }
        });
        mixer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, Mixer.class);
                startActivity(intent);
            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, time.class);
                startActivity(intent);
            }
        });
        area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, area.class);
                startActivity(intent);
            }
        });

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
