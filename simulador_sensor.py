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
import paho.mqtt.client as mqtt
import json
import time
import random
import threading

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
        client.publish(f"sensores/alerta/{region}", json.dumps(message))  # Publicamos con la región en el topic
        print(f"Publicado en {region}: {message}")
        time.sleep(random.randint(2, 5))

if __name__ == "__main__":
    # Creamos y ejecutamos los hilos para cada región
    thread_norte = threading.Thread(target=simulate, args=("sismo", "region_norte"))
    thread_sur = threading.Thread(target=simulate, args=("incendio", "region_sur"))

    thread_norte.start()
    thread_sur.start()

    # Puedes agregar más regiones simplemente creando más hilos
    # thread_este = threading.Thread(target=simulate, args=("inundacion", "region_este"))
    # thread_este.start()