#Codigo hecho por el God Giovanni Sandoval
from flask import Flask, jsonify, request
import base64
import IA_Detector

app = Flask(__name__)

@app.route("/")
def root():
    return "root"

print("servidor iniciado correctamente \n"
"Jarvis, traeme una coca")

#Ruta para recibir la imagen desde Java
@app.route("/image", methods=["POST"])
def image():
    data = request.get_json()

    image_base64 = data.get("image")
    print("Python server recibio una imagen")

    if "," in image_base64:
        image_base64 = image_base64.split(",")[1]

    typeTrash = IA_Detector.clasificador_basura(image_base64)

    #Devuelve la informacion
    return jsonify({
        "Hola": typeTrash
    })

if __name__ == "__main__":
    app.run(debug=True)