import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class IA_Conector {

    // CAMBIO 1: Usamos el endpoint de chat que es más estable para instrucciones "system"
    private final String urlApi = "http://192.168.1.75:11434/api/chat";

    public String clasificarImagen(String base64Imagen) throws Exception {
        String base64Limpio = base64Imagen.replaceAll("\\r|\\n", "");

        URL url = new URL(urlApi);
        HttpURLConnection conexion = (HttpURLConnection) url.openConnection();

        conexion.setRequestMethod("POST");
        conexion.setRequestProperty("Content-Type", "application/json; utf-8");
        conexion.setRequestProperty("Accept", "application/json");
        conexion.setDoOutput(true);

        // CAMBIO 2: Aumentamos el tiempo de lectura. 
        // Las imágenes en GPU tardan en procesar el primer "token".
        conexion.setConnectTimeout(15000);
        conexion.setReadTimeout(120000); // 2 minutos reales

        String jsonInputString = "{"
        + "\"model\": \"qwen3-vl\","
        + "\"messages\": ["
        + "  { \"role\": \"system\", \"content\": \"Eres un experto en clasificación de residuos en México. "
        + "Analiza la imagen y detecta objetos de desecho o reciclables. Ignora personas o fondos. "
        + "Responde EXCLUSIVAMENTE con un arreglo JSON. Prohibido incluir pensamientos o etiquetas <thought>. "
        + "Categorías válidas: "
        + "(PLASTICO): botellas, envases, bolsas. "
        + "(PAPEL/CARTON): hojas, cajas, sobres. "
        + "(METAL): latas de refresco, papel aluminio, grapas. "
        + "(ORGANICO): cáscaras, restos de comida, servilletas usadas. "
        + "(VIDRIO): frascos, botellas de vidrio. "
        + "(DIFICIL RECICLAJE): colillas, cerámica, unicel. "
        + "(PILAS): pilas. "
        + "(NO ES BASURA): si el objeto no es basura. "
        + "Ejemplos de salida: "
        + "[{\\\"item\\\": \\\"Botella de PET transparente\\\", \\\"categoria\\\": \\\"(PLASTICO)\\\"},"
        + "{\\\"item\\\": \\\"Cáscara de plátano oxidada\\\", \\\"categoria\\\": \\\"(ORGANICO)\\\"},"
        + "{\\\"item\\\": \\\"Lata de aluminio aplastada\\\", \\\"categoria\\\": \\\"(METAL)\\\"}]. "
        + "Si no hay basura, devuelve un resultado como este ejemplo: [{\\\"item\\\": \\\"nombre del objeto\\\", \\\"categoria\\\": \\\"No es basura\\\"}]\" },"
        + "  { \"role\": \"user\", \"content\": \"Analiza detalladamente esta imagen y clasifica el desecho.\", \"images\": [\"" + base64Limpio + "\"] }"
        + "],"
        + "\"stream\": false,"
        + "\"options\": {"
        + "  \"temperature\": 0.1,"
        + "  \"num_predict\": 2000,"
        + "  \"top_k\": 20,"
        + "  \"top_p\": 0.9"
        + "}"
        + "}";

        try (OutputStream os = conexion.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        StringBuilder response = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(conexion.getInputStream(), StandardCharsets.UTF_8))) {
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        }
        
        System.out.println("Respuesta Raw: " + response.toString());
        return extraerRespuestaChat(response.toString());
    }

    private String extraerRespuestaChat(String jsonCompleto) {
        try {
            // En /api/chat con stream:false, la respuesta viene en message -> content
            String clave = "\"content\":\"";
            int inicio = jsonCompleto.indexOf(clave) + clave.length();
            // Buscamos el final del contenido del mensaje
            int fin = jsonCompleto.indexOf("\"},\"done\"");

            if (inicio > clave.length() && fin > inicio) {
                String resultado = jsonCompleto.substring(inicio, fin);
                
                // Limpieza de caracteres de escape que mete Ollama en el JSON
                resultado = resultado.replace("\\n", "").replace("\\\"", "\"").trim();
                
                // Si el modelo aún así mete basura antes del JSON, buscamos los corchetes
                int corcheteInicio = resultado.indexOf("[");
                int corcheteFin = resultado.lastIndexOf("]");
                if(corcheteInicio != -1 && corcheteFin != -1) {
                    return resultado.substring(corcheteInicio, corcheteFin + 1);
                }
                return resultado;
            }
        } catch (Exception e) {
            return "[]";
        }
        return "[]";
    }
}