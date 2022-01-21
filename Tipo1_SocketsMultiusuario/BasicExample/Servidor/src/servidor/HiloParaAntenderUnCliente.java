/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.Socket;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tibur
 */
public class HiloParaAntenderUnCliente extends Thread{
    Socket sk;
    Random randomNumber = new Random(100);
    private static String[] words = {"hola", "prueba", "funciona"};


    public HiloParaAntenderUnCliente(Socket sk){
        this.sk = sk;
    }

    @Override
    public void run() {
        InputStream is = null;
        OutputStream os = null;

        try {

            is = sk.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
            os = sk.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            
            Inet4Address ip = (Inet4Address) sk.getInetAddress();
            String laIP = ip.getHostAddress();
            
            while(true){
                System.out.println("Esperando algo");
                String linea = br.readLine();
                System.out.println(laIP +": " + linea);
                String word = random();
                System.out.println("La palabra es: " + word);
                if (linea.equals(word)) {
                    bw.write("Correcto, felicidades, la palabra era " + "'" + word + "'");
                } else {
                    bw.write("Incorrecto, vuelva a intentarlo. La palabra tiene " + word.length() + " letras");
                }

                bw.newLine();
                bw.flush();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(HiloParaAntenderUnCliente.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if(is != null) is.close();
            } catch (IOException ex) {
                Logger.getLogger(HiloParaAntenderUnCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

    private String random () {
        String word = words[(int) (Math.random() * words.length - 1) ];
        return word;

    }

    
    
}
