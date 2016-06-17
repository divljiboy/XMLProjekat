package security;

import java.io.FileOutputStream;

/**
 * Created by David on 6/17/2016.
 */
public class SecurityManager {



    public SecurityManager(){

    }



    public boolean writeStringToFile(String fileContent, String filePath)
    {
        boolean ret = false;
        try
        {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath + ".xml");
            fileOutputStream.write(fileContent.getBytes());
            fileOutputStream.close();
            ret = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }






}
