/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cryptex;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Prasith Lakshan 
 * 2015/08
 */
class Cryptex {
    
    

    static String encrypt(String key, String msg) {
        
        if(key.equals("0")) {
            
            Random rand = new Random();
            StringBuilder out = new StringBuilder();
            int r = rand.nextInt(25) + 20;      // generates a random number between 20 and 45 


            for (int i = 0; i < msg.length(); i++){
                int ch = msg.charAt(i);
                out.append(((ch + 4) * r + 1000));

                //for debugging
                //System.out.println(msg.charAt(i));
            }
            //System.out.println(r + "" + out);
            String str = (rand.nextInt(5)) + "" + r + "" + (rand.nextInt(89)+10) +"" + out; // first number 0-5(excluding 5) if default key used
            
            
            return str;
            
            
        } else {
            Random rand = new Random();
            int sum = 0;
            StringBuilder out = new StringBuilder();
            
            for(int i = 0; i < 5; i++) {
                sum += Integer.parseInt(String.valueOf(key.charAt(i)));
            }
            
            if(sum<10) sum+= 20;
            if(sum<20) sum+= 10;
            int r = sum;
            
            for (int i = 0; i < msg.length(); i++){
                int ch = msg.charAt(i);
                out.append(((ch + 4) * r + 1000));

                //for debugging
                //System.out.println(msg.charAt(i));
            }
            //System.out.println(r + "" + out);
            String str = (rand.nextInt(5)+5) + "" + (rand.nextInt(10)+10) + "" + (rand.nextInt(89)+10) +"" + out; // first number between 5-10(excluding 10) if custom key used.
            return str;
            //System.out.println(sum);
            //System.out.println(rand.nextInt(5));
        }
        
    } 
    
    static String decrypt(String key, String msg) {
        int start = 0;
        int end = 5;
        
        if(key.equals("0")) {
            char arr[] = new char[(msg.length() - 5) / 4 ];
            int r = Integer.parseInt(msg.substring(1,3));

            for (int i = 0; i < (msg.length()-2) / 4 ; i++){
                start = end;
                end += 4;

                int val = Integer.parseInt(msg.substring(start, end));

                arr[i] = (char) ((val - 1000) / r -4);            
            }
            
            String out = new String(arr);
            return out;
        } else {
            int sum = 0;
            char arr[] = new char[(msg.length() - 5) / 4 ];
            
            for(int i = 0; i < 5; i++) {
                sum += Integer.parseInt(String.valueOf(key.charAt(i)));
            }
            
            if(sum<10) sum+= 20;
            if(sum<20) sum+= 10;
            int r = sum;

            for (int i = 0; i < (msg.length()-2) / 4 ; i++){
                start = end;
                end += 4;

                int val = Integer.parseInt(msg.substring(start, end));

                arr[i] = (char) ((val - 1000) / r -4);            
            }


            //System.out.println(new String(arr));
            String out = new String(arr);
            return out;
        }
        
        
    } 
    
    static void saveFile(String str) {
        
        if(!str.isEmpty()) {
        
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Save File...");

            int userSelection = fileChooser.showSaveDialog(null);

            if(userSelection == JFileChooser.APPROVE_OPTION) {
                File filetosave = fileChooser.getSelectedFile();
                //System.out.println(filetosave);

                try (Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filetosave), "utf-8")); ) {

                    writer.write(str);
                    writer.close();
                } catch (Exception ex) {
                    Logger.getLogger(Main_Interface.class.getName()).log(Level.SEVERE, null, ex);
                } 
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "Nothing to save!\nEncrypt something first and then try again.");
        }
    }
 
    static String openFile() {
        String st = "";
        
        JFileChooser fileChooser = new JFileChooser();
            FileInputStream fin = null;
            int i;
            StringBuilder sb = new StringBuilder();
            
            fileChooser.setDialogTitle("Open File...");

            int userSelection = fileChooser.showOpenDialog(null);

            if(userSelection == JFileChooser.APPROVE_OPTION) {
                // opening file.
                try {
                    File fileToOpen = fileChooser.getSelectedFile();
                    //System.out.println(filetosave);
                    fin = new FileInputStream(fileToOpen);

                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(null,"Error opening file!");
                    Logger.getLogger(Main_Interface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            // reading file
            try {
                do {
                    i = fin.read();
                    if (i != -1) {
                        sb.append((char)i);
                    } 
                } while (i != -1);
                st = new String(sb);
                //Main_Interface.inputArea.setText(st);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null,"Error Reading File!");
                
            } finally {
                
                try {
                    fin.close();
                } catch (Exception ex) {
                    Logger.getLogger(Main_Interface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            return st;
    }
    
}
