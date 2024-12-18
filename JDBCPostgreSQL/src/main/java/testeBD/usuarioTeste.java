package testeBD;

import java.sql.*;
import java.util.*;
import javax.swing.*;

public class usuarioTeste {

    public static void main(String[] args) {
        try {
            // Listar supervisores
            List<String> supervisores = listarSupervisores();
            // Permitir a seleção do supervisor
            String supervisorEscolhido = (String) JOptionPane.showInputDialog(null, "Selecione um supervisor:", "Supervisores", JOptionPane.PLAIN_MESSAGE, null, supervisores.toArray(), supervisores.get(0));
            int usuarioId = Integer.parseInt(supervisorEscolhido.split(" - ")[0]);
            // Mostrar informações do supervisor e suas obras
            mostrarInformacoesSupervisor(usuarioId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static List<String> listarSupervisores() throws SQLException {
        List<String> supervisores = new ArrayList<>();
        String sql = "SELECT usuarioid, nome FROM usuario WHERE usuarioid IN (SELECT usuarioid FROM supervisor)";
        try (Connection conn = testeBD.conection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("usuarioid");
                String nome = rs.getString("nome");
                supervisores.add(id + " - " + nome);
            }
        }
        return supervisores;
    }

    private static void mostrarInformacoesSupervisor(int usuarioId) throws SQLException {
        String query = """
SELECT 
    usuario.usuarioID,
    usuario.Nome,
    usuario.Email,
    usuario.Telefone,
    status_usuario.status,
    obra.nome AS obra
FROM 
    usuario
 JOIN status_usuario ON usuario.statusUsuarioID = status_usuario.statusUsuarioID
 JOIN supervisor ON usuario.usuarioID = supervisor.usuarioID
 JOIN obra ON supervisor.obraid = obra.obraid
WHERE usuario.usuarioID = ?;
                    """;
        try (Connection conn = testeBD.conection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String nomeUsuario = rs.getString("nome");
                String email = rs.getString("email");
                String telefone = rs.getString("telefone");
                String status = rs.getString("status");
                System.out.printf("Nome: %s\nEmail: %s\nTelefone: %s\nStatus: %s\nObras:\n", nomeUsuario, email, telefone, status);
                do {
                    String nomeObra = rs.getString("obra");
                    System.out.println("  - " + nomeObra);
                } while (rs.next());
            } else {
                System.out.println("Supervisor não encontrado.");
            }
        }
    }
}
