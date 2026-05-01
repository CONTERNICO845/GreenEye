//Codigo hecho por el God Giovanni Sandoval
import java.io.*;
import java.net.*;

public class PythonConector{

    public static String sendImageBase64(String base64Image){
        try {
            URL url = new URL("http://127.0.0.1:5000/image");
            HttpURLConnection conection = (HttpURLConnection) url.openConnection();
            conection.setRequestMethod("POST");
            conection.setRequestProperty("Content-Type", "application/json");
            conection.setDoOutput(true);

            String jsonPackage = "{\"image\": \"" + base64Image + "\"}";

            try(OutputStream os = conection.getOutputStream()) {
                byte[] input = jsonPackage.getBytes("utf-8");
                os.write(input, 0, input.length);
            } 

            BufferedReader br = new BufferedReader(new InputStreamReader(conection.getInputStream(), "utf-8"));
            StringBuilder answer = new StringBuilder();
            String linea;
            while ((linea = br.readLine()) != null) {
                answer.append(linea.trim());

                System.out.println(answer); //wefcsadvsaf 
            }

            return answer.toString();

        }catch(Exception e){
            System.out.println("Error en el mensajero Java-Python: " + e.getMessage());
            return "Error de conexión";
        }
    }
}