package org.example;
import org.eclipse.paho.client.mqttv3.*;

public class Main {
    public static void main(String[] args) throws MqttException {
        // Configuración del cliente MQTT
        String broker = "tcp://localhost:1883";  // Broker Mosquitto
        String clientId = "cliente_java";        // ID único para este cliente
        String topic = "sensores/alerta/region_sur";  // Suscripción a una región específica

        // Crear el cliente MQTT
        MqttClient client = new MqttClient(broker, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);

        // Conectar al broker
        System.out.println("Conectando al broker MQTT...");
        client.connect(options);
        System.out.println("Conectado exitosamente al broker.");

        // Suscripción al topic
        client.subscribe(topic, (topic1, message) -> {
            // Este código se ejecuta cuando llega un mensaje
            String payload = new String(message.getPayload());
            System.out.println("[ALERTA] Mensaje recibido: " + payload);
        });

        System.out.println("Esperando mensajes...");

        // Mantener el cliente activo por un tiempo determinado (30 segundos)
        try {
            Thread.sleep(30000); // Escuchar durante 30 segundos
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Desconectar después del tiempo de espera
        client.disconnect();
        System.out.println("Desconectado del broker.");
    }
}
