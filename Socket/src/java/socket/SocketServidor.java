package socket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketServidor {

	private static final ArrayList<byte[]> listByte = new ArrayList<>();
	private static DataInputStream dis;
	static SocketServidor servidor = new SocketServidor();
        
	public void carregaArquivoMemoria(String nomeArquivo){
		
            File f = new File(nomeArquivo);
            DataInputStream dis = null;
            byte[] arrayByte = new byte[(int) f.length()];
            try {
                dis = new DataInputStream(new FileInputStream(nomeArquivo));
                dis.read(arrayByte);
            } catch (FileNotFoundException ex) {
		Logger.getLogger(SocketServidor.class.getName()).log(Level.SEVERE, null, ex);
            }catch (IOException e) {
            	Logger.getLogger(SocketServidor.class.getName()).log(Level.SEVERE, null, e);
            }
            System.out.println("Sucesso");
            listByte.add(arrayByte);
	}
	
	public void escreverArquivoDisco(String nomeArquivo, String mls){
            int ms = Integer.parseInt(mls);
            int nArq = Integer.parseInt(nomeArquivo);
            
            byte[] arquivo = listByte.get(nArq);
            int cont = 0;
            for (int i = 0; i < ms; i++) {
                try {
                    try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(new File("C:/Temp/"+"arquivo_pos_"+nArq+"_"+cont+".bin")))) {
                        dos.write(arquivo);
                        dos.flush();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(SocketServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
                    cont++;
                    System.out.println(nomeArquivo+" "+i+" milisegundos");
            }
	}
	
	public void limparLista() {
            listByte.clear();
	}
	
	public void enviarArquivo(File f) {
		byte[] arquivo = new byte[(int) f.length()];
		try {
			dis = new DataInputStream(new FileInputStream(f));
			dis.read(arquivo);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(SocketServidor.class.getName()).log(Level.SEVERE, null, ex);
		}catch (IOException ex) {
			Logger.getLogger(SocketServidor.class.getName()).log(Level.SEVERE, null, ex);
		}
		listByte.add(arquivo);
	}
	
	public static void main(String[] args) throws IOException {
		
		ServerSocket server = null;
		//DataInputStream dis = null;
		BufferedReader in = null;
              
                    // Abrindo porta para conexao de clients
                    server = new ServerSocket(13267);
                    System.out.println("Porta de conexao aberta 13267");
                    // Cliente conectado
                    Socket sock = server.accept();
                    System.out.println("Conexao recebida pelo cliente");
                    // Criando tamanho de leitura
                    
                    in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
                    dis = new DataInputStream(sock.getInputStream());
                    
                    String op = in.readLine();
                    String arqs = "";
                    System.out.println(op);
                
            while(!op.equalsIgnoreCase("/close")){        
                    if(op.equalsIgnoreCase("/arq")){
                        arqs = dis.readUTF();
                        System.out.println(arqs);
                        arq(arqs);
                    }
                    System.out.println(op);
                        if(op.equalsIgnoreCase("/rea")){
                            arqs = dis.readUTF();
                            System.out.println(arqs);
                            rea(arqs);
                        }   
                            if(op.equalsIgnoreCase("/limp")){
                                arqs = dis.readUTF();
                                limp(arqs);
                            }
                       op = in.readLine();
//                    dis = new DataInputStream(sock.getInputStream());
            }
                    dis.close();
                    sock.close();
                    server.close();
                    in.close();
		
	}
    
        public static void arq(String arq) throws IOException{            
                    while(!arq.equalsIgnoreCase("fechar")){
                        System.out.println(arq);
                        servidor.carregaArquivoMemoria(arq);
                        arq = dis.readUTF();
                     }
            } 
       public static void rea(String disc)throws IOException{ 
   
                    String[] aux = new String[2]; 
                    aux = disc.split(",");
                    System.out.println(aux[0]);
                    while(!disc.equalsIgnoreCase("fechar")){
                        System.out.println(disc);
                        aux = disc.split(",");
//                        for (int i = 0; i < aux.length; i++) {
//                            System.out.println(aux[i]+aux[i+1]);
                            servidor.escreverArquivoDisco(aux[0], aux[1]);
//                            break;
//                        }
                        System.out.println("Sucesso");
                        disc = dis.readUTF();
                    }
       }
       
       public static void limp(String op)throws IOException{
                    op = dis.readUTF();
                    if(op.equalsIgnoreCase("sim")){
                        servidor.limparLista();
                    }else{
                       System.out.println("Lista mantida");
            }
       }
}
