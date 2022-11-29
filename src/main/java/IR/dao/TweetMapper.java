package IR.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TweetMapper {

    @Autowired
    private MongoTemplate mongoTemplate;

    //insert a processed tweet into mongodb
    public void insertADocumentToCorpus(String id,String content,String publisher){
        Map tmp=new HashMap<String,String>();
        tmp.put("id",id);
        tmp.put("content",content);
        try {
            mongoTemplate.save(tmp,"corpus_"+publisher);
        }catch (Exception e){
            System.out.println("here");
        }
    }
}
