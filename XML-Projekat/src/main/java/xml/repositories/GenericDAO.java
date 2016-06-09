package xml.repositories;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.DatabaseClientFactory;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import database.DatabaseConfig;
import database.DatabaseManager;
import database.XMLConverter;
import xml.model.Korisnik;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dorian on 31.5.2016.
 */
public class GenericDAO<T,K> {

    protected XMLConverter<T> xmlConverter;
    protected static DatabaseClient client;
    private static final String USER_SCHEMA_PATH = "./src/main/schema/korisnici.xsd";

    public GenericDAO(String path) throws IOException {
        xmlConverter = new XMLConverter<T>(path);
        client = DatabaseManager.Client.getClient();
    }

    protected ArrayList<T> getByQuery(String query) throws FileNotFoundException, IOException {

        EvalResultIterator iterator = null;

        ServerEvaluationCall invoker = client.newServerEval();
        invoker.xquery(query);

        iterator = invoker.eval();

        ArrayList<T> list = new ArrayList<>();

        if(iterator.hasNext()) {
            for (EvalResult res : iterator) {
                if (xmlConverter.stringToFile(res.getString())) {
                    T item = (T)xmlConverter.fromXMLtoObject();
                    list.add(item);
                }
            }
        }else{
            return null;
        }

        return list;
    }

    protected  void execQuery(String query) throws IOException{

        EvalResultIterator iterator = null;

        ServerEvaluationCall invoker = client.newServerEval();
        invoker.xquery(query);

        iterator = invoker.eval();
    }

    protected  void add(T obj,String docId,String colId) throws IOException{
        if (xmlConverter.fromObjectToXML(obj)){
            //write to Database
            XMLDocumentManager xmlManager = client.newXMLDocumentManager();

            DocumentMetadataHandle metadataHandle = new DocumentMetadataHandle();
            metadataHandle.getCollections().add(colId);

            InputStreamHandle handle = new InputStreamHandle(new FileInputStream("./src/main/resources/temp.xml"));
            xmlManager.write(docId,metadataHandle, handle);

            System.out.print("Upisao uspesno u bazu");

        }else{
            //Error
            System.out.print("Cannot convert to xml file.");
        }
    }

}
