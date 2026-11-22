package presentation;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Log {
    private static final String ARCHIVO = "DOPOSoccer.log";
    private static final Logger REGISTRO = Logger.getLogger("DOPOSoccer");

    private Log(){        
    }

    public static void record(Exception e){
        try{
            FileHandler archivo = new FileHandler(ARCHIVO, true);
            archivo.setFormatter(new SimpleFormatter());
            REGISTRO.setUseParentHandlers(false);
            REGISTRO.addHandler(archivo);
            REGISTRO.log(Level.SEVERE, e.getMessage(),e);
            REGISTRO.removeHandler(archivo);
            archivo.close();
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }



}
