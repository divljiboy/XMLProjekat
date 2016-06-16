package xml.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by Daniel on 6/15/2016.
 */
public class SendToArchive {

    private static final String ASPDOTNETURLISTORIJSKAARHIVA = "http://localhost:4303/archive/save";

    @Autowired
    private TaskScheduler scheduler;

    private ScheduledExecutorService localExecutor;

    public void init() {
        if (this.scheduler  == null) {
            this.localExecutor = Executors.newSingleThreadScheduledExecutor();
            this.scheduler = new ConcurrentTaskScheduler(this.localExecutor);
        }
        scheduler.scheduleAtFixedRate(new Runnable() {
            public void run() {
                send();
            }
        }, new Date(), 1000 * 60 * 5); //na 5 minuta
    }

    public void send() {
        RestTemplate restTemplate = new RestTemplate();
        //
        MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
        final String filename="somefile.txt";
        map.add("name", filename);
        map.add("filename", filename);
        ByteArrayResource contentsAsResource = new ByteArrayResource(new byte[5]){ /*bajtovi pdf-a*/
            @Override
            public String getFilename(){
                return filename;
            }
        };
        map.add("file", contentsAsResource);
        //

        ResponseEntity<String> response = restTemplate.postForEntity(ASPDOTNETURLISTORIJSKAARHIVA,map,String.class);
        System.out.println(response);
    }
}