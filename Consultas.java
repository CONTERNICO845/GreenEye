import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class Consultas {

    public static int loggedUserId = -1;

    // Metodo para inicar secion en el login
    public boolean validateUser(String email, String password) {

        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return false;

            String query = "SELECT * FROM users WHERE email = '" + email + "' AND password = '" + password + "'";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) { // Si encuentra el usuario, devuelve true

                loggedUserId = rs.getInt("id");
                return true;

            }

            return false;

        } catch (Exception e) {

            System.out.println("Error en consulta: " + e.getMessage());
            return false;

        }
    }

    // Método para registrar usario en la base de datos
    public void registerUser(String email, String password, String userName, String randomString) {

        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return;

            // 1. Obtenemos la fecha de hoy automáticamente
            String creationDate = LocalDate.now().toString();

            // 2. Inyectamos correo, password y la fecha en la tabla
            String query = "INSERT INTO users (user_Name,email, password, creation_date, user_photo) VALUES ('"
                    + userName + "','"
                    + email + "', '" + password + "', '" + creationDate + "' , '" + randomString + "')";

            stmt.executeUpdate(query);
            System.out.println("✅ Usuario registrado con éxito el día: " + creationDate);

        } catch (Exception e) {

            System.out.println("❌ Error al registrar: " + e.getMessage());

        }

    }

    // Metodo para cambiar la contraseña :)
    public void updatePassword(String newPassword) {

        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return;

            String query = "UPDATE users SET password = '" + newPassword + "' WHERE id = " + loggedUserId;

            int user = stmt.executeUpdate(query);

            if (user > 0) {

                System.out.println("Cambio de contraseña exitoso");

            }

        } catch (Exception e) {

            System.out.println("Error al cambiar la contraseña: " + e.getMessage());

        }

    }
    //Usuario
    public String getUserName() {

        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return "Usuario"; // Valor por defecto en caso de no haber conexión

            String query = "SELECT user_Name FROM users WHERE id = " + loggedUserId;
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                String userName = rs.getString("user_Name");
                return userName;

            }

        } catch (Exception e) {

            System.out.println("Error al consultar el nombre de usuario: " + e.getMessage());

        }

        return "Usuario"; // Valor por defecto si falla la consulta
    }

    // Metodo para consultar los puntos del usario
    public int getPoints() {

        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return 0;

            String query = "SELECT points FROM users WHERE id = " + loggedUserId;
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                int points = rs.getInt("points");
                return points;

            }

        } catch (Exception e) {

            System.out.println("Error al consultar los puntos: " + e.getMessage());

        }

        return 0;
    }

    // Metodo para consultar los nivel del usario
    public int getNivel() {

        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return 0;

            String query = "SELECT nivel FROM users WHERE id = " + loggedUserId;
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                int nivel = rs.getInt("nivel");
                return nivel;

            }

        } catch (Exception e) {

            System.out.println("Error al consultar los puntos: " + e.getMessage());

        }

        return 0;
    }

    // Consulta los puntos de Vidrios
    public int getGlassPoints() {

        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return 0;

            String query = "SELECT glass FROM users WHERE id = " + loggedUserId;
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                int glassPoints = rs.getInt("glass");
                return glassPoints;

            }

        } catch (Exception e) {

            System.out.println("Error al consultar los putnos de vidrios: " + e.getMessage());

        }

        return 0;

    }

    // Consulta los puntos de Platico
    public int getPlasticPoints() {

        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return 0;

            String query = "SELECT plastic FROM users WHERE id = " + loggedUserId;
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                int plasticPoints = rs.getInt("plastic");
                return plasticPoints;

            }

        } catch (Exception e) {

            System.out.println("Error al consultar los puntos del Platico: " + e.getMessage());

        }

        return 0;

    }

    // Consulta los puntos del Metal
    public int getMetalPoints() {

        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return 0;

            String query = "SELECT metal FROM users WHERE id = " + loggedUserId;
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                int metalPoints = rs.getInt("metal");
                return metalPoints;

            }

        } catch (Exception e) {
            System.out.println("Error al consultar los puntos del Metal: " + e.getMessage());
        }

        return 0;

    }

    public int getHardToRecyclePoints() {

        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return 0;

            String query = "SELECT hard_to_recycle FROM users WHERE id = " + loggedUserId;
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                int hardPoints = rs.getInt("hard_to_recycle");
                return hardPoints;

            }

        } catch (Exception e) {

            System.out.println("Error al consultar los puntos del Metal: " + e.getMessage());
        }

        return 0;

    }

    public int getPaper() {

        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return 0;

            String query = "SELECT paper FROM users WHERE id = " + loggedUserId;
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                return rs.getInt("paper");

            }

        } catch (Exception e) {
            System.out.println("Error al consultar el papel: " + e.getMessage());
        }
        return 0;
    }

    public int getOrganic() {

        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return 0;

            String query = "SELECT organic FROM users WHERE id = " + loggedUserId;
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                return rs.getInt("organic");

            }

        } catch (Exception e) {
            System.out.println("Error al consultar lo organico: " + e.getMessage());
        }
        return 0;
    }

    // Consulta el numero de foto del usuario actual
    public static String getPhoto() {

        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return null;

            String query = "SELECT user_photo FROM users WHERE id = " + loggedUserId;
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {

                return rs.getString("user_photo");

            }

        } catch (Exception e) {
            System.out.println("Error al consultar la foto: " + e.getMessage());
        }
        return null;
    }

    // Agregamos Puntos al usuario
    public void updatePoints(int pointsToAdd) {

        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return;

            String query = "UPDATE users SET points = points + " + pointsToAdd + " WHERE id = " + loggedUserId;

            int rowsAffected = stmt.executeUpdate(query);

            if (rowsAffected > 0) {

                int totalCurrentPoints = getPoints();
                System.out.println("Se agregaron " + pointsToAdd + " puntos. Total actual: " + totalCurrentPoints);

            }

        } catch (Exception e) {

            System.out.println("Error al agregar los puntos: " + e.getMessage());

        }

    }

    // Agregamos Niveles al usuario
    public void setNivel(int nuevoNivel) {
        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return;

            String query = "UPDATE users SET nivel = " + nuevoNivel + " WHERE id = " + loggedUserId;
            int rowsAffected = stmt.executeUpdate(query);

            if (rowsAffected > 0) {
                System.out.println("Nivel actualizado correctamente a: " + nuevoNivel);
            }

        } catch (Exception e) {
            System.out.println("Error al actualizar el nivel: " + e.getMessage());
        }
    }

    // Update para Vidrio
    public void updateGlassPoints(int pointsToAdd) {

        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return;

            String query = "UPDATE users SET glass = glass + " + pointsToAdd + " WHERE id = " + loggedUserId;
            int rowsAffected = stmt.executeUpdate(query);

            if (rowsAffected > 0) {

                int total = getGlassPoints();
                System.out.println("Se agregaron " + pointsToAdd + " puntos de Vidrio. Total: " + total);

            }

        } catch (Exception e) {

            System.out.println("Error al agregar puntos de vidrio: " + e.getMessage());

        }
    }

    // Update para Plástico
    public void updatePlasticPoints(int pointsToAdd) {
        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {
            if (conn == null)
                return;

            String query = "UPDATE users SET plastic = plastic + " + pointsToAdd + " WHERE id = " + loggedUserId;
            int rowsAffected = stmt.executeUpdate(query);

            if (rowsAffected > 0) {

                int total = getPlasticPoints();
                System.out.println("Se agregaron " + pointsToAdd + " puntos de Plástico. Total: " + total);

            }

        } catch (Exception e) {

            System.out.println("Error al agregar puntos de plástico: " + e.getMessage());

        }
    }

    // Update para Metal
    public void updateMetalPoints(int pointsToAdd) {
        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {
            if (conn == null)
                return;

            String query = "UPDATE users SET metal = metal + " + pointsToAdd + " WHERE id = " + loggedUserId;
            int rowsAffected = stmt.executeUpdate(query);

            if (rowsAffected > 0) {

                int total = getMetalPoints();
                System.out.println("Se agregaron " + pointsToAdd + " puntos de Metal. Total: " + total);
            }

        } catch (Exception e) {

            System.out.println("Error al agregar puntos de metal: " + e.getMessage());

        }
    }

    // Update para Reciclaje Difícil
    public void updateHardToRecyclePoints(int pointsToAdd) {
        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {
            if (conn == null)
                return;

            String query = "UPDATE users SET hard_to_recycle = hard_to_recycle + " + pointsToAdd + " WHERE id = "
                    + loggedUserId;
            int rowsAffected = stmt.executeUpdate(query);

            if (rowsAffected > 0) {

                int total = getHardToRecyclePoints();
                System.out.println("Se agregaron " + pointsToAdd + " puntos... Total: " + total);
            }

        } catch (Exception e) {

            System.out.println("Error al agregar puntos de reciclaje difícil: " + e.getMessage());

        }
    }

    public void updatePaperPoints(int pointsToAdd) {
        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {
            if (conn == null)
                return;

            String query = "UPDATE users SET paper = paper + " + pointsToAdd + " WHERE id = "
                    + loggedUserId;
            int rowsAffected = stmt.executeUpdate(query);

            if (rowsAffected > 0) {

                int total = getHardToRecyclePoints();
                System.out.println("Se agregaron " + pointsToAdd + " puntos de papel. Total: " + total);

            }

        } catch (Exception e) {

            System.out.println("Error al agregar puntos de papel: " + e.getMessage());

        }
    }

    public void updateOrganicPoints(int pointsToAdd) {
        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {
            if (conn == null)
                return;

            String query = "UPDATE users SET organic = organic + " + pointsToAdd + " WHERE id = "
                    + loggedUserId;
            int rowsAffected = stmt.executeUpdate(query);

            if (rowsAffected > 0) {

                int total = getHardToRecyclePoints();
                System.out.println("Se agregaron " + pointsToAdd + " puntos de organico. Total: " + total);

            }

        } catch (Exception e) {

            System.out.println("Error al agregar puntos de organico: " + e.getMessage());

        }
    }

    // Obtener el total de vidrio de TODOS los usuarios
    public int getTotalPoints() {
        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return 0;

            String query = "SELECT SUM(points) FROM users";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error al consultar el total global de Puntos: " + e.getMessage());
        }
        return 0;
    }

    // Obtener el total de vidrio de TODOS los usuarios
    public int getTotalNivel() {
        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return 0;

            String query = "SELECT SUM(nivel) FROM users";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error al consultar el total global de Nivel: " + e.getMessage());
        }
        return 0;
    }

    // Obtener el total de vidrio de TODOS los usuarios
    public int getTotalGlass() {
        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return 0;

            String query = "SELECT SUM(glass) FROM users";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error al consultar el total global de vidrio: " + e.getMessage());
        }
        return 0;
    }

    // Obtener el total de plástico de TODOS los usuarios
    public int getTotalPlastic() {
        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return 0;

            String query = "SELECT SUM(plastic) FROM users";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error al consultar el total global de plástico: " + e.getMessage());
        }
        return 0;
    }

    // Obtener el total de metal de TODOS los usuarios
    public int getTotalMetal() {
        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return 0;

            String query = "SELECT SUM(metal) FROM users";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error al consultar el total global de metal: " + e.getMessage());
        }
        return 0;
    }

    // Obtener el total de difícil reciclaje de TODOS los usuarios
    public int getTotalHardToRecycle() {
        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return 0;

            String query = "SELECT SUM(hard_to_recycle) FROM users";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error al consultar el total global de reciclaje difícil: " + e.getMessage());
        }
        return 0;
    }

    public int getTotalPaper() {
        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return 0;

            String query = "SELECT SUM(paper) FROM users";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error al consultar el total global de papel: " + e.getMessage());
        }
        return 0;
    }

    public int getTotalOrganic() {
        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn == null)
                return 0;

            String query = "SELECT SUM(organic) FROM users";
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
            System.out.println("Error al consultar el total global de organico: " + e.getMessage());
        }
        return 0;
    }

    //Borrar la cuenta XD (Ya sirve y esta God)
    public static int deleteAccount() {
       
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DatabaseConnection.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (conn == null)
                return 0;

            // Establecemos el ID del usuario en la consulta
            pstmt.setInt(1, loggedUserId);

            // executeUpdate devuelve el número de filas afectadas
            int filasBorradas = pstmt.executeUpdate();

            if (filasBorradas > 0) {
                System.out.println("Cuenta eliminada exitosamente.");
                // Opcional: Reiniciar el ID de sesión tras borrar la cuenta
                loggedUserId = -1;
            }

            return filasBorradas;

        } catch (Exception e) {
            System.out.println("Error al intentar eliminar la cuenta: " + e.getMessage());
            return 0;
        }
    }

    // Consulta del top 10 usarios con mas puntos
    // "1% yo alias CONTENRICO y 99% IA alias Gemini"
    public static String[][] getTopTenUsers() {
        // Creamos una matriz de 10 filas por 2 columnas (Nombre y Puntos)
        String[][] top10 = new String[10][2];

        try (Connection conn = DatabaseConnection.conectar();
                Statement stmt = conn.createStatement()) {

            if (conn != null) {
                String query = "SELECT user_Name, points FROM users ORDER BY points DESC LIMIT 10";
                ResultSet rs = stmt.executeQuery(query);

                int index = 0;
                // CAMBIO 5: Eliminé los System.out.println.
                // Ahora guardamos los valores directo en nuestra matriz 'top10'
                while (rs.next() && index < 10) {
                    top10[index][0] = rs.getString("user_Name");
                    top10[index][1] = String.valueOf(rs.getInt("points"));
                    index++;
                }
            }
        } catch (Exception e) {
            System.out.println("Error al consultar el top 10: " + e.getMessage());
        }

        // CAMBIO 6: Regresamos la información para que el constructor la use
        return top10;
    }

}