package org.example;
import org.eclipse.paho.client.mqttv3.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws MqttException {
        // Configuración del cliente MQTT
        String broker = "tcp://localhost:1883";  // Broker Mosquitto
        String clientId = "cliente_java";        // ID único para este cliente
        String topic = "sensores/alerta/region_sur";  // Suscripción a una región específica

        MqttClient client = new MqttClient(broker, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);

        // Conectar al broker
        System.out.println("Conectando al broker MQTT...");
        client.connect(options);
        System.out.println("Conectado exitosamente.");

        // Suscribirse al topic
        client.subscribe(topic, (topic1, message) -> {
            // Este código se ejecuta cuando llega un mensaje
            String payload = new String(message.getPayload());
            System.out.println("Mensaje recibido: " + payload);
        });

        System.out.println("Esperando mensajes...");

        // Mantener el cliente activo para seguir recibiendo mensajes
        while (true) {
            // Aquí podrías hacer otras cosas, pero lo importante es mantener el cliente vivo
            // Para este ejemplo, estamos solo esperando mensajes
        }
    }
}