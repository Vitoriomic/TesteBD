package testeBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioComStatus {
    public static void main(String[] args) {
        String query = """
            SELECT 
                USUARIO.usuarioID,
                USUARIO.nome,
                USUARIO.email,
                statusUsuario.status AS status
            FROM 
                USUARIO
            JOIN 
                statusUsuario ON USUARIO.statusUsuarioID = statusUsuario.statusUsuarioID;
        """;

        try (Connection conn = conection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int usuarioID = rs.getInt("usuarioID");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String status = rs.getString("status");

                System.out.printf("ID: %d | Nome: %s | Email: %s | Status: %s%n",
                        usuarioID, nome, email, status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
