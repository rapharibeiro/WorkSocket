package socket;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.net.Socket;
import javax.swing.JOptionPane;

public class SocketCliente {

        @SuppressWarnings("null")
	public static void main(String[] args) throws IOException {
		
		Socket cliSer = null;
		DataOutputStream dos = null;
		PrintWriter out = null;
                FileOutputStream fos = null;
                boolean chave = true;
		try{
                    // Criando conex�o com o servidor
                    System.out.println("Conectando com Servidor porta 13267");
                    cliSer = new Socket("127.0.0.1", 13267);
                    dos = new DataOutputStream(cliSer.getOutputStream());
                    out = new PrintWriter(cliSer.getOutputStream());
		}catch(IOException e){
                    System.out.println(e.getMessage());
		}
            while(chave){      
                String op = JOptionPane.showInputDialog("Digite o protocolo:");
                out.println(op);
                out.flush();
            
		switch(op){
                    case "/arqMemoria" :
                        String entrada = JOptionPane.showInputDialog("Digite o caminho e o nome do arquivo separado por ;");
                        String[] array = entrada.split(";");
                        for (int i = 0; i < array.length; i++) {
                            System.out.println(array[i]);
                            dos.writeUTF(array[i]);
                            dos.flush();
                        }
                        dos.writeUTF("fechar");
                        dos.flush();
                        break;
                    case  "/arqDisco" :
                        String disco = JOptionPane.showInputDialog("Digite a posição do arquivo e o mls que deseja escrever em disco");
                        String[] array2 = disco.split(";");
                        for (int i = 0; i < array2.length; i++) {
                            System.out.println(array2[i]);
                            dos.writeUTF(array2[i]);
                            dos.flush();
                        }
                        dos.writeUTF("fechar");
                        dos.flush();
                        break;
                    case "/arqLimp" :
                        String sn = JOptionPane.showInputDialog("Deseja limpar a lista, SIM/NAO;");
                        dos.writeUTF(sn);
                        dos.flush();
                        break;
                    case "/arqUp" : 
                        String up = JOptionPane.showInputDialog("Digite o nome do arquivo que deseja enviar;");
                        dos.writeUTF(up); 
                        File file = new File(up);    
                        ObjectOutputStream os = new ObjectOutputStream(dos);    
                        FileInputStream fis = new FileInputStream(file);    
                        byte[] cache = new byte[4096];    

                        while (true) {    
                            int len = fis.read(cache);    
                            if (len == -1) {    
                                break;    
                            }    
                            os.write(cache, 0, len);  
                        }
                        System.out.println("sucesso");
                        dos.writeUTF("fechar");
                        dos.flush();
                        os.flush();    
                        os.close();    
                        break;
                        
                    case "/close":
                        chave = false;
                        break;
                    default:
                       chave = false;
                       out.println("close");     
                }
        }    
                out.flush();
                out.close();
                dos.close();
	}
}