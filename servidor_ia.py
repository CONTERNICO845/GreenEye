from flask import Flask, request, jsonify
import base64
from io import BytesIO
from PIL import Image
import google.generativeai as genai
import json

app = Flask(__name__)

app.config['JSON_AS_ASCII'] = False
if hasattr(app, 'json'):
    app.json.ensure_ascii = False


# 🔴🔴🔴 PEGA TU LLAVE EXACTAMENTE AQUÍ ADENTRO DE LAS COMILLAS 🔴🔴🔴
API_KEY = "AIzaSyDBkXIwoKxY4GMSBtqjYIf3HaIYG76rGpU"
genai.configure(api_key=API_KEY)

# Configuramos el modelo visual de Gemini para que nos responda en formato JSON estricto
modelo_ia = genai.GenerativeModel('gemini-2.5-flash',
    generation_config={"response_mime_type": "application/json"})

COLORES_HTML = {
    "AMARILLO (PLASTICO)": "#B8860B", 
    "AZUL (PAPEL/CARTON)": "#0000FF",
    "GRIS (METAL)": "#696969",
    "VERDE (ORGANICO)": "#228B22",
    "VERDE CLARO (VIDRIO)": "#32CD32",
    "NEGRO (RECHAZO)": "#000000",
    "ROJO (PELIGROSO)": "#FF0000"
}

# Aquí le damos las instrucciones exactas a la mente de la IA
INSTRUCCIONES = """
Eres un experto en clasificación de residuos en México. Analiza la imagen y detecta todos los objetos que sean basura o reciclables. Ignora a las personas o fondos.
Devuelve una lista en formato JSON. Cada elemento debe tener 'item' (el nombre descriptivo del objeto) y 'categoria'.
Las categorías estrictamente deben ser una de estas:
- AMARILLO (PLASTICO)
- AZUL (PAPEL/CARTON)
- GRIS (METAL)
- VERDE (ORGANICO)
- VERDE CLARO (VIDRIO)
- NEGRO (RECHAZO)
- ROJO (PELIGROSO)

Ejemplo de respuesta:
[
  {"item": "Rollo de papel higiénico blanco", "categoria": "AZUL (PAPEL/CARTON)"},
  {"item": "Lata de aerosol roja Old Spice", "categoria": "ROJO (PELIGROSO)"}
]
Si no hay basura, devuelve [].
"""

@app.route('/clasificar', methods=['POST'])
def clasificar_imagen():
    try:
        datos = request.get_json()
        if 'imagen' not in datos:
            return jsonify({'error': 'No se envió ninguna imagen'}), 400

        # Decodificar imagen para pasársela a Gemini
        datos_imagen = base64.b64decode(datos['imagen'])
        imagen = Image.open(BytesIO(datos_imagen))

        # --- MANDAMOS LA FOTO A LA NUBE ---
        print("Enviando foto a Gemini...")
        respuesta = modelo_ia.generate_content([INSTRUCCIONES, imagen])
        
        # Leemos la lista estructurada que nos mandó la IA
        lista_basura = json.loads(respuesta.text)

        if len(lista_basura) > 0:
            botes_agrupados = {}
            # Agrupamos por contenedor
            for residuo in lista_basura:
                obj = residuo.get("item", "Desconocido")
                bote = residuo.get("categoria", "BASURA GENERAL (GRIS)")
                
                if bote not in botes_agrupados:
                    botes_agrupados[bote] = set()
                botes_agrupados[bote].add(obj)

            objeto_detectado = "Clasificación exitosa"
            lineas_html = []
            
            # Construimos la interfaz de Java igual que antes
            for bote, objs in botes_agrupados.items():
                color = COLORES_HTML.get(bote, "#000000")
                items_juntos = "<br>".join([f"&nbsp;&nbsp;&nbsp;&nbsp;{obj.upper()}" for obj in objs])
                lineas_html.append(f"<span style='color:{color}; font-family: monospace; font-size: 14px;'>{bote}</span><br><span style='color: black; font-family: monospace; font-size: 14px;'>{items_juntos}</span>")
            
            bote_recomendado = "<br><br>".join(lineas_html)
        else:
            objeto_detectado = "Completado"
            bote_recomendado = "<span style='color: gray; font-family: monospace;'>BASURA GENERAL (GRIS)</span><br><span style='color: black; font-family: monospace;'>&nbsp;&nbsp;&nbsp;&nbsp;NADA DETECTADO</span>"

        return jsonify({
            'objeto': objeto_detectado,
            'bote': bote_recomendado
        })

    except Exception as e:
        print("ERROR:", e)
        return jsonify({'error': str(e)}), 500

if __name__ == '__main__':
    # CAMBIAMOS A PUERTO 5007
    app.run(port=5009)