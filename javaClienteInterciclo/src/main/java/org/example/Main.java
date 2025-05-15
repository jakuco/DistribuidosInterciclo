package org.example;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Main {
    public static void main(String[] args) throws MqttException {
        // Configuración del cliente MQTT
        String broker = "tcp://localhost:1883";  // Broker Mosquitto
        String clientId = "cliente_java";        // ID único para este cliente
        String topic1String = "sensores/alerta/region_sur";  // Suscripción a una región específica
        String topic2String = "sensores/alerta/region_norte";  // Suscripción a una región específica


        // Crear el cliente MQTT
        MqttClient client = new MqttClient(broker, clientId);
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);

        //client.p

        /*
        // Conectar al broker
        System.out.println("Conectando al broker MQTT...");
        client.connect(options);
        System.out.println("Conectado exitosamente al broker.");

        // Suscripción al topic
        /*client.subscribe(topic1String, (topic1, message) -> {
            // Este código se ejecuta cuando llega un mensaje
            String payload = new String(message.getPayload());
            System.out.println("[ALERTA] Mensaje recibido: " + payload);
        });

        /*client.subscribe(topic2String, (topic1, message) -> {
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
        client.disconnect();*/
        System.out.println("Desconectado del broker.");

        /*Client cliente1 = new Client(
                "cliente1",
                new String[]{"sensores/alerta/region_sur", "sensores/alerta/region_norte"},
                broker);

        cliente1.connect();*/
        // Comodines; + es para una debajo, # es para todos los que están debajo
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
