package pacman.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Helper class, used for reading files. We use it for loading a map (world).
 * 
 * @author uross
 */

public class Utils {

    public static String loadFileAsString(String path) {
        StringBuilder builder = new StringBuilder();

        try {
            InputStream inputStream = Utils.class.getResourceAsStream(path);
            if (inputStream == null) {  // invalid file path
                return null;
            }
            
            InputStreamReader inputReader = new InputStreamReader(inputStream);
            
            BufferedReader br = new BufferedReader(inputReader);
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line + '\n');
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }
    
    public static String loadExternalFileAsString(String path) {
        StringBuilder builder = new StringBuilder();

        try {
            File f = new File(path);
            if (!f.exists()) {
                return null;
            }
            FileReader fr = new FileReader(f);
            if (fr == null) {
                return null;
            }
            BufferedReader br = new BufferedReader(fr);
            if (br == null) {
                return null;
            }
            String line;
            while ((line = br.readLine()) != null) {
                builder.append(line + '\n');
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    public static int parseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
