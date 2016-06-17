package xml.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;
import xml.Constants;
import xml.model.PravniAkt;
import xml.repositories.ActDAO;
import xml.repositories.IActDAO;

import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Daniel on 6/15/2016.
 */
public class SendToArchive {

    private static final String ASPDOTNETURLISTORIJSKAARHIVA = "http://localhost:4303/archive/save";
    private static final String TEST_URL_ASPDOTNET = "http://localhost:4303/archive/get";

    @Autowired
    private TaskScheduler scheduler;

    @Autowired
    private IActDAO aktDao;

    private ScheduledExecutorService localExecutor;

    public void init() {
        if (this.scheduler  == null) {
            this.localExecutor = Executors.newSingleThreadScheduledExecutor();
            this.scheduler = new ConcurrentTaskScheduler(this.localExecutor);
        }
        if(this.aktDao == null){
            try {
                this.aktDao = new ActDAO();
            } catch (IOException e) {
                System.out.println("can't init ActDao");
            }
        }
        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                send();
            }
        }, new Date(), 1000 * 60 * 5); //na 5 minuta
    }

    public void send() {
        //ping archive app
        if(!testUrl(TEST_URL_ASPDOTNET)) {
            System.out.println("archive app is not reachable");
            return;
        }

        //zip files
        ByteArrayOutputStream zipFile = makeZip();
        if(zipFile == null){
            System.out.println("error in making zip file...");
            return;
        }

        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        final String filename="usvojeniAkati.zip";
        map.add("name", filename);
        map.add("filename", filename);
        ByteArrayResource contentsAsResource = new ByteArrayResource(zipFile.toByteArray()){
            @Override
            public String getFilename(){
                return filename;
            }
        };
        map.add("file", contentsAsResource);
        //

        ResponseEntity<String> response = restTemplate.postForEntity(ASPDOTNETURLISTORIJSKAARHIVA,map,String.class);
        System.out.println(response.getStatusCode());
    }

    //make zip from collection usvojeni akati
    private ByteArrayOutputStream makeZip(){
        try {
            ByteArrayOutputStream zipResult = new ByteArrayOutputStream();
            ZipOutputStream zos = new ZipOutputStream(zipResult);
            ByteArrayOutputStream baos = null;

            ArrayList<PravniAkt> akati = aktDao.getAdoptedActs();
            for(PravniAkt akt : akati){
                baos = aktDao.getPdf(akt.getId(), Constants.ActCollection);
                zos.putNextEntry(new ZipEntry("Akt"+akt.getId()+".pdf"));
                zos.write(baos.toByteArray());
            }
            zos.close();
            baos.close();
            zipResult.close();
            return zipResult;
        } catch (JAXBException e) {
            return null;
        } catch (IOException e) {
            return null;
        } catch (SAXException e) {
            return null;
        } catch (TransformerException e) {
            return null;
        }
    }

    private boolean testUrl(String url){
            try {
                URL siteURL = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) siteURL
                        .openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int code = connection.getResponseCode();
                if (code == 200) {
                    return true;
                }
            } catch (Exception e) {
                return false;
            }
            return false;
    }

}