package org.example;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Main {
    public static void main(String[] args) throws MqttException {
        // Configuración del cliente MQTT
        String broker = "tcp://localhost:1883";  // Broker Mosquitto

        /*Client cliente1 = new Client(
                "cliente1",
                new String[]{"sensores/alerta/region_sur", "sensores/alerta/region_norte"},
                broker);

        cliente1.connect();

        cliente1.ask_topics();*/


        // Comodines; + es para una debajo, # es para todos los que están debajo
        // Los comodines van en los tópicos

        Client cliente2 = new Client(
                "cliente2",
                new String[]{"sensores/#"},
                broker);

        cliente2.connect();

        cliente2.ask_topics();
        try {
            Thread.sleep(5000);  // Esperar 5 segundos para recibir la respuesta
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("final");
    }
}
