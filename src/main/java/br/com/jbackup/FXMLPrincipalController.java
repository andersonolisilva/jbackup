/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jbackup;

import br.com.andersonoliveirasilva.jbackup.utils.Compactador;
import br.com.andersonoliveirasilva.jbackup.utils.JavaMail;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author anderson
 */
public class FXMLPrincipalController implements Initializable {

    @FXML
    private TextField arquivoSelecionado;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void selecionarArquivo() {
        FileChooser fc = new FileChooser();
        File arquivo = fc.showOpenDialog(null);

        if (arquivo.exists()) {
            arquivoSelecionado.setText(arquivo.getPath());
        } else {
            JOptionPane.showMessageDialog(null, "Nenhum arquivo selecionado.");
        }
    }

    public void enviarArquivo() throws IOException {

        File arquivo = new File(arquivoSelecionado.getText());
        File arquivoCompactado = new File(arquivoSelecionado.getText() + ".zip");
        Compactador.compress(arquivo, arquivoCompactado);

        List<File> arquivosAnexos = new ArrayList();
        arquivosAnexos.add(arquivoCompactado);

        String destinatario  = "ande1710@hotmail.com";
        String assuntoEmail  = "Backup de arquivo compactado";
        String textoMensagem = "Texto da mensagem";

        JavaMail java = new JavaMail(destinatario, assuntoEmail, textoMensagem);
        java.enviarEmail(arquivosAnexos);

        JOptionPane.showMessageDialog(null, "Backup realizado e arquivo enviado para o email " + destinatario);
    }

}
