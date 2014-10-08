package socket;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
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
            int cont = 1;
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
	
	public static void main(String[] args) throws IOException, Exception {
		
		ServerSocket server = null;
		BufferedReader in = null;
                boolean chave = true;
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
                    
                while(chave){
                    switch(op){
                        case "/arqMemoria" :
                            arqs = dis.readUTF();
                            arqMemoria(arqs);
                            break;
                        case  "/arqDisco" :
                            arqs = dis.readUTF();
                            arqDisco(arqs);         
                            break;
                        case "/arqLimp" :
                            arqs = dis.readUTF();
                            arqLimp(arqs);          
                            break;
                        case "/arqUp" : 
                            arqs = dis.readUTF();
                            byte[] cache = new byte[4096];
                            
                            FileOutputStream fos = new FileOutputStream(arqs);    
                            while(!arqs.equalsIgnoreCase("fechar")){
                                while (true) {    
                                    int len = dis.read(cache);
                                    if (len == -1) {    
                                        break;    
                                    }    
                                        fos.write(cache, 0, len);    
                                }
                                listByte.add(cache);
                                fos.flush();    
                                fos.close();
                                System.out.println("Sucesso");
                                System.out.println(op);
                            }
                            break;
                        default:    
                            System.out.println("Encerrando transmição");
                            chave = false;
                    }
                    op = in.readLine();
                    arqs = "";
                }
                    
//                    
//                
//                    while(!op.equalsIgnoreCase("/close")){        
//                            if(op.equalsIgnoreCase("/arqMemoria")){
//                                arqs = dis.readUTF();
//                                arqMemoria(arqs);
//                            }
//                                if(op.equalsIgnoreCase("/arqDisco")){
//                                    arqs = dis.readUTF();
//                                    arqDisco(arqs);
//                                }   
//                                    if(op.equalsIgnoreCase("/arqLimp")){
//                                        arqs = dis.readUTF();
//                                        arqLimp(arqs);
//                                    }if(op.equalsIgnoreCase("/arqUp")){
//                                    try{    arqs = dis.readUTF();
//                                    byte[] cache = new byte[4096];    
//
//                                    ObjectInputStream ois = new ObjectInputStream(dis);    
//
//                                    FileOutputStream fos = new FileOutputStream(arqs);    
//                                    while (true) {    
//                                        int len = ois.read(cache);    
//                                        if (len == -1) {    
//                                            break;    
//                                        }    
//                                        fos.write(cache, 0, len);    
//                                    }    
//                                    fos.flush();    
//                                    fos.close();    
//                                } catch (IOException e) {    
//                                    System.out.println(e);    
//                                }    
//                                        
//                                    }
//                        op = in.readLine();
//                        System.out.println(op);
//                    }
                 
                    
                    dis.close();
                    sock.close();
                    server.close();
                    in.close();
                }
    
        public static void arqMemoria(String arq) throws IOException{            
                    while(!arq.equalsIgnoreCase("fechar")){
                        servidor.carregaArquivoMemoria(arq);
                        arq = dis.readUTF();
                     }
            } 
       public static void arqDisco(String disc)throws IOException{ 
                    String[] aux =disc.split(",");; 
                    while(!disc.equalsIgnoreCase("fechar")){
                        aux = disc.split(",");
                        servidor.escreverArquivoDisco(aux[0], aux[1]);
                        System.out.println("Sucesso");
                        disc = dis.readUTF();
                    }
       }
       
       public static void arqLimp(String op)throws IOException{
                    if(op.equalsIgnoreCase("sim")){
                        servidor.limparLista();
                    }else{
                       System.out.println("Lista mantida");
            }
       }
}
