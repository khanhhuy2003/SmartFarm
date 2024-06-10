package com.example.demo_iot_app;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LineChart2 extends AppCompatActivity {
    private MQTTHelper mqttHelper;
    private LineChart lineChart;
    private LineData lineData;
    private LineDataSet lineDataSet;
    private List<Entry> lineEntries;
    private long startTime;

    public void sendDataMQTT(String topic, String value) {
        MqttMessage msg = new MqttMessage();
        msg.setId(1234);
        msg.setQos(0);
        msg.setRetained(false);
        byte[] b = value.getBytes(Charset.forName("UTF-8"));
        msg.setPayload(b);

        try {
            mqttHelper.mqttAndroidClient.publish(topic, msg);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void startMQTT() {
        mqttHelper = new MQTTHelper(this);
        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                Log.d("TEST", "Subscribed");
            }

            @Override
            public void connectionLost(Throwable cause) {
                // Handle connection lost
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                Log.d("TEST", topic + "***" + message.toString());
                if (topic.contains("khanhhuy03/feeds/humidity")) {
                    try {
                        float humidity = Float.parseFloat(message.toString());
                        runOnUiThread(() -> addEntry(humidity));
                    } catch (NumberFormatException e) {
                        Log.e("LineChart2", "Invalid temperature format", e);
                    }
                }
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                // Handle delivery complete
            }
        });
    }

    private void addEntry(float temperature) {
        // Use relative time to ensure continuous line visualization
        long elapsedTime = (System.currentTimeMillis() - startTime) / 1000;
        lineEntries.add(new Entry(elapsedTime, temperature));
        lineDataSet.notifyDataSetChanged();
        lineData.notifyDataChanged();
        lineChart.notifyDataSetChanged();
        lineChart.setVisibleXRangeMaximum(60); // Show last 60 seconds of data
        lineChart.moveViewToX(lineData.getEntryCount());
        lineChart.invalidate();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart2);

        lineChart = findViewById(R.id.lineChart2);
        lineEntries = new ArrayList<>();
        lineDataSet = new LineDataSet(lineEntries, "Humidity");
        lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        lineDataSet.setValueTextColor(android.R.color.black);
        lineDataSet.setValueTextSize(10f);
        lineDataSet.setCircleRadius(6f);  // Increase the circle radius
        //lineDataSet.setCircleHoleRadius(3f); // Increase the hole radius
        lineDataSet.setDrawValues(false);
        lineDataSet.setLineWidth(3f);

        lineData = new LineData(lineDataSet);
        lineChart.setData(lineData);

        // Set X-axis configuration
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                // Show time in HH:mm:ss format
                long millis = startTime + ((long) value * 1000);
                return new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date(millis));
            }
        });

        // Set Y-axis configuration
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(100f);
        leftAxis.setLabelCount(10, true);

        lineChart.getAxisRight().setEnabled(false); // Disable right Y-axis

        // Set description
        Description description = new Description();
        description.setText("Humidity over Time");
        lineChart.setDescription(description);

        lineChart.invalidate(); // Ensure the chart is drawn initially

        startTime = System.currentTimeMillis(); // Initialize start time
        startMQTT();
    }
}
