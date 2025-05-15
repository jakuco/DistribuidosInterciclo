"""import paho.mqtt.client as mqtt
import json
import time
import random

client = mqtt.Client()
client.connect("localhost", 1883, 60)

def simulate(sensor_type, region):
    while True:
        message = {
            "type": sensor_type,
            "region": region,
            "magnitude": round(random.uniform(1, 10), 2),
            "timestamp": time.time()
        }
        client.publish(f"sensores/alerta/{region}", json.dumps(message))  # Publiquemos con la región en el topic
        print(f"Publicado en {region}: {message}")
        time.sleep(random.randint(2, 5))

# Simulamos dos regiones
simulate("sismo", "region_norte")
simulate("incendio", "region_sur")"""

topics_list = [
    "sensores/alerta/region_norte",
    "sensores/alerta/region_sur",
]

import paho.mqtt.client as mqtt
import json
import time
import random
import threading
import logging
import os

# Configurar el logger para registrar los eventos importantes
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger()

# Cargar configuración desde el archivo config.json
def load_config():
    """
    Carga la configuración desde un archivo JSON.
    """
    config_path = "config.json"
    if not os.path.exists(config_path):
        logger.error(f"Archivo de configuración {config_path} no encontrado.")
        raise FileNotFoundError("Archivo de configuración no encontrado.")

    with open(config_path, 'r') as f:
        config = json.load(f)

    return config

def on_message(client, userdata, msg):
    payload = msg.payload.decode()
    logger.info(f"Mensaje recibido en {msg.topic}: {payload}")
    
    response_message = topics_list
    response_topic = f"response/{payload}"
    client.publish(response_topic, json.dumps(response_message))
    logger.info(f"Respondido en {response_topic}: {response_message}")

    # Ejemplo básico de comando recibido
    #print(payload)
    
    
    """if payload.startswith("estado region_"):
        region = payload.split(" ")[1]
        response_message = {
            "region": region,
            "status": "operativo",
            "timestamp": time.time()
        }
        # Responder al cliente (ajusta el topic si tienes múltiples clientes)
        response_topic = "response/cliente2"
        client.publish(response_topic, json.dumps(response_message))
        logger.info(f"Respondido en {response_topic}: {response_message}")"""


# Cargar configuración
config = load_config()
BROKER = config["mqtt_broker"]
PORT = config["mqtt_port"]
TOPIC_PREFIX = config["topic_prefix"]

# Inicializamos el cliente MQTT
client = mqtt.Client()
client.connect(BROKER, PORT, 60)

client.on_message = on_message
client.subscribe("ask/topic")

def get_emergency_level(magnitude):
    """
    Determina el nivel de emergencia basado en la magnitud.

    Args:
        magnitude (float): La magnitud del evento.

    Returns:
        str: Nivel de emergencia (Crítica, Alta, Moderada, Baja).
    """
    if magnitude >= 8:
        return "Crítica"
    elif magnitude >= 5:
        return "Alta"
    elif magnitude >= 2:
        return "Moderada"
    else:
        return "Baja"

def simulate(sensor_type, region, stop_event):
    """
    Simula un sensor que publica alertas en un topic específico basado en la región.

    Args:
        sensor_type (str): El tipo de sensor, como 'sismo', 'incendio', etc.
        region (str): La región a la que pertenece el sensor.
        stop_event (threading.Event): Evento de parada para detener el hilo.
    """
    alert_count = 0  # Contador de alertas enviadas
    while not stop_event.is_set():
        # Crear el mensaje con la información del sensor
        magnitude = round(random.uniform(1, 10), 2)
        message = {
            "type": sensor_type,
            "region": region,
            "magnitude": magnitude,
            "timestamp": time.time(),
            "emergency_level": get_emergency_level(magnitude)  # Añadimos el nivel de emergencia
        }

        # Publicamos el mensaje en el topic correspondiente
        topic = f"{TOPIC_PREFIX}/{region}"
        client.publish(topic, json.dumps(message))

        # Incrementar el contador de alertas
        alert_count += 1
        logger.info(f"Publicado en {region} ({sensor_type}): {message}")

        # Imprimir el mensaje enviado en la terminal
        print(f"[{sensor_type}] Publicado en {region}: {message}")

        # Esperar un tiempo aleatorio antes de la siguiente publicación
        time.sleep(random.randint(2, 5))  # Espera entre 2 y 5 segundos

    logger.info(f"Simulación terminada. Total de alertas enviadas: {alert_count}")

if __name__ == "__main__":
    
    
    client.loop_start()  # Iniciar el bucle de red en un hilo separado
    stop_event = threading.Event()

    # Creamos y lanzamos hilos para simular sensores en diferentes regiones
    #thread_norte = threading.Thread(target=simulate, args=("sismo", "region_norte", stop_event))
    #thread_sur = threading.Thread(target=simulate, args=("sismo", "region_sur", stop_event))

    #print("Mensaje recibido:", client.)
    
    # Iniciar los hilos
    #thread_norte.start()
    #thread_sur.start()

    # Detener los hilos después de un tiempo determinado (30 segundos)
    
    
    time.sleep(30)
    stop_event.set()  # Detener los hilos de forma segura

    # Esperar que los hilos terminen
    #thread_norte.join()
    #thread_sur.join()

    
    print("Simulación terminada.")
    client.loop_stop()
    client.disconnect()  # Desconectar del broker MQTT



# Puedes agregar más regiones simplemente creando más hilos
    # thread_este = threading.Thread(target=simulate, args=("inundacion", "region_este"))
    # thread_este.start()