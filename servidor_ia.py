from flask import Flask, request, jsonify

app = Flask(__name__)
# Aumentamos el límite para que Flask no rechace la imagen por peso
app.config['MAX_CONTENT_LENGTH'] = 32 * 1024 * 1024 

@app.route('/clasificar', methods=['POST'])
def clasificar():
    print("\n--- ¡PETICIÓN RECIBIDA! ---")
    try:
        # 1. Intentar capturar el JSON
        data = request.get_json(force=True)
        
        if not data:
            print("Error: Se recibió una petición, pero el JSON está vacío.")
            return jsonify({"resultado": "error_json_vacio"}), 400

        # 2. Verificar si existe la clave 'image'
        if 'image' in data:
            tamano = len(data['image'])
            print(f"Éxito: Imagen recibida en Python.")
            print(f"Tamaño de la cadena Base64: {tamano} caracteres.")
            
            # Respondemos algo para confirmar que Python está vivo
            return jsonify({"resultado": "imagen_recibida_ok"})
        else:
            print("Error: El JSON no tiene el campo 'image'.")
            return jsonify({"resultado": "sin_campo_image"}), 400

    except Exception as e:
        print(f"Error crítico en el servidor: {e}")
        return jsonify({"resultado": str(e)}), 500

if __name__ == '__main__':
    print("Servidor de diagnóstico corriendo en http://127.0.0.1:5005")
    app.run(host='0.0.0.0', port=5005, debug=False)