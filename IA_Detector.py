#Codigo hecho por el God Giovanni Sandoval
import base64
import numpy as np
from PIL import Image
import io
from tensorflow.keras.applications.mobilenet_v2 import MobileNetV2, preprocess_input, decode_predictions

print("Despertando a la IA...")
modelo = MobileNetV2(weights='imagenet')
print("IA lista")

def clasificar_basura(image_base64):
    if "," in image_base64:
        image_base64 = image_base64.split(",")[1]

    try:
        # Decodificamos y preparamos
        image_data = base64.b64decode(image_base64)
        imagen = Image.open(io.BytesIO(image_data)).convert("RGB")
        imagen = imagen.resize((224, 224))
        
        img_array = np.array(imagen)
        img_array = np.expand_dims(img_array, axis=0)
        img_array = preprocess_input(img_array)

        # Predecimos
        prediccion = modelo.predict(img_array)
        resultados = decode_predictions(prediccion, top=1)[0]
        objeto_detectado = resultados[0][1] 
        
        # Clasificamos
        lista_plastico = ["water_bottle", "pop_bottle", "plastic_bag", "cup"]
        lista_organico = ["banana", "apple", "orange", "strawberry"]
        lista_papel = ["envelope", "carton", "paper_towel"]

        if objeto_detectado in lista_plastico:
            return "Plastico"
        elif objeto_detectado in lista_organico:
            return "Organica"
        elif objeto_detectado in lista_papel:
            return "Papel"
        else:
            return "Desconocido (" + objeto_detectado + ")"

    except Exception as e:
        print("Error en el análisis visual:", e)
        return "Error"