package org.example;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public String clientId = "";
    public ArrayList<String> topics;
    public String broker;
    MqttClient client;
    MqttConnectOptions options;

    public Client(String clientId, String[] topics, String broker) throws MqttException {
        this.clientId = clientId;
        this.topics = new ArrayList<>(List.of(topics));
        this.broker = broker;

        this.client = new MqttClient(broker, clientId);
        this.options = new MqttConnectOptions();

        options.setCleanSession(true);
    }

    public void connect() throws MqttException {
        this.client.connect(this.options);
        for (String topic: this.topics) {
            client.subscribe(topic, (topic1, message) -> {
                String payload = new String(message.getPayload());
                System.out.println("[CLIENTE]:"+this.clientId+"|[ALERTA] Mensaje recibido: " + payload);
                //System.out.println("[ALERTA] Mensaje recibido: " + payload);
            });
        }
    }

    public void add_topid(String topic){
        this.topics.add(topic);
    }

    public void remove_topic(String topic){
        this.topics.remove(this.topics.indexOf(topic));
    }

    public void load_subscribe() throws MqttException {
        this.client.disconnect();
        this.connect();
    }

    public void ask_topics() throws MqttException {
        String topicFilter = "response/"+this.clientId;

        client.subscribe(topicFilter, (topic1, message) -> {
            String payload = new String(message.getPayload());
            System.out.println("[RESPUESTA RECIBIDA] " + payload);
        });

        String mensaje = "estado region_sur";
        this.client.publish("ask/topic", new MqttMessage(mensaje.getBytes()));


    }
}
