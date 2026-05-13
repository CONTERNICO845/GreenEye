from flask import Flask, request, jsonify

app = Flask(__name__)
# Aumentamos el límite para que Flask no rechace la imagen por peso
app.config['MAX_CONTENT_LENGTH'] = 32 * 1024 * 1024 

@app.route('/clasificar', methods=['POST'])
def clasificar():
    # Imprimimos un mensaje indicando que se ha recibido una petición
    print("\n--- ¡PETICIÓN RECIBIDA! ---")
    try:
        # Intentamos capturar el JSON de la solicitud
        data = request.get_json(force=True)
        
        # Verificamos si el JSON está vacío
        if not data:
            # Si el JSON está vacío, imprimimos un mensaje de error y devolvemos una respuesta con código 400 (Bad Request)
            print("Error: Se recibió una petición, pero el JSON está vacío.")
            return jsonify({"resultado": "error_json_vacio"}), 400

        # Verificamos si existe la clave 'image' en el JSON
        if 'image' in data:
            # Si existe la clave 'image', imprimimos un mensaje de éxito y mostramos el tamaño de la cadena Base64
            tamano = len(data['image'])
            print(f"Éxito: Imagen recibida en Python.")
            print(f"Tamaño de la cadena Base64: {tamano} caracteres.")
            
            # Respondemos algo para confirmar que Python está vivo
            return jsonify({"resultado": "imagen_recibida_ok"})
        else:
            # Si el JSON no tiene el campo 'image', imprimimos un mensaje de error y devolvemos una respuesta con código 400 (Bad Request)
            print("Error: El JSON no tiene el campo 'image'.")
            return jsonify({"resultado": "sin_campo_image"}), 400

    except Exception as e:
        # Si ocurre algún error durante el procesamiento, imprimimos un mensaje de error crítico y devolvemos una respuesta con código 500 (Internal Server Error)
        print(f"Error crítico en el servidor: {e}")
        return jsonify({"resultado": str(e)}), 500

if __name__ == '__main__':
    # Imprimimos un mensaje indicando que el servidor de diagnóstico está corriendo
    print("Servidor de diagnóstico corriendo en http://127.0.0.1:5005")
    app.run(host='127.0.0.1', port=5005, debug=False)