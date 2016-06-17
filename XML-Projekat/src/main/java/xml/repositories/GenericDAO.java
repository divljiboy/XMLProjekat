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
import org.w3c.dom.Document;
import security.SecurityManager;
import security.SignEnveloped;
import xml.Constants;
import xml.model.Korisnik;
import xml.model.PravniAkt;

import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
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
    protected String namespace;
    //

    //constructor

    public GenericDAO(String path,String collection,String entityName,String namespace) throws IOException {
        this.xmlConverter = new XMLConverter<T>(path);
        this.client = DatabaseManager.Client.getClient();
        this.collection = collection;
        this.entityName = entityName;
        this.namespace = namespace;
    }

    @Override
    public void create(T entity, String docId, String colId) throws JAXBException, IOException {
       /*
        SignEnveloped se = new SignEnveloped();
        XMLConverter<PravniAkt> converter = new XMLConverter<PravniAkt>("./src/main/schema/akt.xsd");
        String xml = converter.toXML((PravniAkt) entity);

        File file = new File("data/glupost.xml");


        try (FileOutputStream fop = new FileOutputStream(file)) {

            // if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }

            // get the content in bytes
            byte[] contentInBytes = xml.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean da = singXml(null,user);*/
        add(entity,docId,colId);
    }

    @Override
    public void update(T entity, Long id) throws JAXBException {

        //throw new NotImplementedException();
    }

    @Override
    public void delete(Long id, String constant) throws JAXBException  {
        //document-delete("PravniAkt10")
        StringBuilder query = new StringBuilder();

        query
                .append("xdmp:document-delete(\"")
                .append(constant)
                .append(id.toString())
                .append("\")");

        System.out.print(query.toString());

        try {
            execQuery(query.toString());
        } catch (IOException e) {
            System.out.print("Cannot delete entity");
        }

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
    public T get(Long id,String colId) throws JAXBException, IOException {
        StringBuilder query = new StringBuilder();

        /*
        query
                .append("collection(\"")
                .append(collection)
                .append("\")/")
                .append(entityName)
                .append("[@Id = ")
                .append(id.toString()+"]");
        */
        query
                .append("declare namespace ns = \"")
                .append(namespace)
                .append("\";\n")
                .append("for $x in collection(\"");
        if(colId != null)
            query.append(colId);
        else
            query.append(collection);
        query
                .append("\")\n")
                .append("return $x/ns:")
                .append(entityName)
                .append("[@Id = ")
                .append(id.toString()+"]");

        System.out.print(query.toString());

        ArrayList<T> entities = getByQuery(query.toString());
        if(entities == null) {
            return null;
        }

        return entities.get(0);
    }


    @Override
    public T getEntityWithMaxId(String colId, String ns, String entity) throws JAXBException, IOException {
        StringBuilder query = new StringBuilder();

            query
                    .append("declare namespace ns = \"")
                    .append(ns)
                    .append("\";\n")
                    .append("let $id := ")
                    .append("max(collection(\"")
                    .append(colId)
                    .append("\")/ns:")
                    .append(entity)
                    .append("/@Id)\n")
                    .append("return collection(\"")
                    .append(colId)
                    .append("\")/ns:")
                    .append(entity)
                    .append("[@Id = $id]");



        EvalResultIterator iterator = null;

        ServerEvaluationCall invoker = client.newServerEval();
        invoker.xquery(query.toString());

        iterator = invoker.eval();

        ArrayList<T> list = new ArrayList<>();

        if(iterator.hasNext()){
            for(EvalResult res : iterator){
                list.add((T) xmlConverter.toObject(res.getString()));
            }
        }else
            return null;

        return list.get(0);
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
        }

        return list;
    }

    protected String getStringByQuery(String query){
        EvalResultIterator iterator = null;

        ServerEvaluationCall invoker = client.newServerEval();
        invoker.xquery(query);

        iterator = invoker.eval();

        StringBuilder result = new StringBuilder();

        ArrayList<String> list = new ArrayList<>();

        if(iterator.hasNext()){
            for(EvalResult res : iterator){
                list.add(res.getString());
                result.append(res.getString());
            }
        }else {
            return null;
        }
        return result.toString();
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


    public boolean singXml(String filePath, Korisnik user){

        boolean ret = false;

        try{
            SecurityManager sm = new SecurityManager();
            SignEnveloped signEnveloped = new SignEnveloped();
            Document document;
            if (filePath == null) {
                document  = signEnveloped.loadDocument("data/glupost.xml");
            }  else {
                document = signEnveloped.loadDocument(filePath);
            }
            KeyStore ks = sm.loadKeyStore("data/sgns.jks","sgns");
            PrivateKey pk = sm.getPK(ks,user.getUsername(),user.getPassword().toCharArray());
            Certificate cert = sm.readCertificate(ks,user.getUsername(),user.getPassword().toCharArray());
            document = signEnveloped.signDocument(document,pk,cert);
            signEnveloped.saveDocument(document,"data/glupost2.xml");
            ret = true;

        } catch (Exception e){

        } finally {
            return  ret;
        }
    }



}
