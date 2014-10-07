package socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JOptionPane;

public class SocketCliente {

        @SuppressWarnings("null")
	public static void main(String[] args) throws IOException {
		
		Socket cliSer = null;
		DataOutputStream dos = null;
		PrintWriter out = null;
                
		try{
                    // Criando conex�o com o servidor
                    System.out.println("Conectando com Servidor porta 13267");
                    cliSer = new Socket("127.0.0.1", 13267);
                    dos = new DataOutputStream(cliSer.getOutputStream());
                    out = new PrintWriter(cliSer.getOutputStream());
		}catch(IOException e){
                    System.out.println(e.getMessage());
		}
		
                out.println("/arq");
                out.flush();
                
		String entrada = JOptionPane.showInputDialog("Digite o caminho e o nome do arquivo separado por ;");
		int qtd = returnQuantidade(entrada);
		System.out.println(qtd);
		String[] array = new String[qtd];
		array = entrada.split(";");
                for (int i = 0; i < array.length; i++) {
                    System.out.println(array[i]);
                    dos.writeUTF(array[i]);
                    dos.flush();
		}
                dos.writeUTF("fechar");
                dos.flush();
                out.println("/rea");
                out.flush();
                String disco = JOptionPane.showInputDialog("Digite por ordem os nomes dos arquivos para escrever disco"
                        + "separado por ;");
                //int qtd2 = returnQuantidade(disco);
		//System.out.println("qtd2"+qtd2);
		String[] array2 = disco.split(";");
		//array2 = 
                System.out.println("ar2"+array2.length);
                for (int i = 0; i < array2.length; i++) {
                    System.out.println("n"+array2[i]);
                    dos.writeUTF(array2[i]);
                    dos.flush();
		}
                dos.writeUTF("fechar");
                dos.flush();
                out.println("/limp");
                out.flush();
                String op = JOptionPane.showInputDialog("Deseja limpar a lista, SIM/NAO;");
                dos.writeUTF(op);
                dos.flush();
                out.write("/close");
                dos.flush();
                out.close();
                dos.close();
                
	}
	
	public static int returnQuantidade(String arq){
		int cont = 0;
		char regra = ';';
		
		for (int i = 0; i < arq.length() ;i++) {
                    if(arq.charAt(i) == regra)
			cont++;
		}
		return cont;
	}
}