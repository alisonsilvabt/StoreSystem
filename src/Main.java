import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Inicialize qualquer configuração necessária para o seu sistema aqui

        // Crie uma instância da classe que representa a interface do usuário (StoreModeGUI)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                StoreModeGUI storeMode = new StoreModeGUI();
            }
        });
    }
}
