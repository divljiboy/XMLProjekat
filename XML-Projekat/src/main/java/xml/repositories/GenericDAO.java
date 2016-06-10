package xml.repositories;

import com.marklogic.client.DatabaseClient;
import com.marklogic.client.document.XMLDocumentManager;
import com.marklogic.client.eval.EvalResult;
import com.marklogic.client.eval.EvalResultIterator;
import com.marklogic.client.eval.ServerEvaluationCall;
import com.marklogic.client.io.DocumentMetadataHandle;
import com.marklogic.client.io.InputStreamHandle;
import database.DatabaseManager;
import database.XMLConverter;
import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.text.Document;
import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dorian on 31.5.2016.
 */
@Repository
public abstract class GenericDAO<T,K extends Serializable> implements IGenericDAO<T,K> {

    //fields
    protected XMLConverter<T> xmlConverter;
    protected DatabaseClient client;
    protected String collection;
    protected String entityName;
    //

    //constructor

    public GenericDAO(String path,String collection,String entityName) throws IOException {
        this.xmlConverter = new XMLConverter<T>(path);
        this.client = DatabaseManager.Client.getClient();
        this.collection = collection;
        this.entityName = entityName;
    }

    @Override
    public void create(T entity, String docId, String colId) throws JAXBException, IOException {
        add(entity,docId,colId);
    }

    @Override
    public void update(T entity, Long id) throws JAXBException {
        throw new NotImplementedException();
    }

    @Override
    public void delete(T entity) throws JAXBException  {
        throw new NotImplementedException();
    }

    @Override
    public List<T> getAll() throws JAXBException, IOException {
        StringBuilder query = new StringBuilder();

        query
                .append("collection(\"")
                .append(collection)
                .append("\")");

        return getByQuery(query.toString());
    }

    @Override
    public T get(Long id) throws JAXBException, IOException {
        StringBuilder query = new StringBuilder();

        query
                .append("collection(\"")
                .append(collection)
                .append("\")/")
                .append(entityName)
                .append("[@Id = ")
                .append(id.toString()+"]");

        ArrayList<T> entities = getByQuery(query.toString());
        if(entities == null) {
            return null;
        }

        return entities.get(0);
    }


    //helper methods
    protected ArrayList<T> getByQuery(String query) throws JAXBException, IOException {

        EvalResultIterator iterator = null;

        ServerEvaluationCall invoker = client.newServerEval();
        invoker.xquery(query);

        iterator = invoker.eval();

        ArrayList<T> list = new ArrayList<>();

        if(iterator.hasNext()){
            for(EvalResult res : iterator){
                list.add((T)xmlConverter.toObject(res.getString()));
            }
        }else {
            return null;
        }
        return list;
    }

    protected  void execQuery(String query) throws IOException{
        ServerEvaluationCall invoker = client.newServerEval();
        invoker.xquery(query);
        invoker.eval();
    }

    protected  void add(T obj,String docId,String colId) throws IOException, JAXBException {
        XMLDocumentManager xmlManager = client.newXMLDocumentManager();
        DocumentMetadataHandle metadataHandle = new DocumentMetadataHandle();
        metadataHandle.getCollections().add(colId);

        InputStreamHandle handle = new InputStreamHandle(new ByteArrayInputStream(xmlConverter.toXML(obj).getBytes(XMLConverter.UTF_8.name())));
        xmlManager.write(docId,metadataHandle,handle);
    }
}
