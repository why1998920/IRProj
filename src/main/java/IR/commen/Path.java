package IR.commen;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Data
public class Path {
    private String twKey="Bearer AAAAAAAAAAAAAAAAAAAAAEUVhgEAAAAAIT%2BTrvakGDLy27ogbe7UDST6gq4%3DLwNOHLPHASMtVySMg1hM7Ana4v51AEZYRU65INgAKB225awxgI";
    private String twUrlPrefix="https://api.twitter.com/2/users/";
    private Map<String,Integer> PublisherIds=new HashMap<String,Integer>(){{
        put("BBC",19701628);
        put("CNN",759251);
        put("The New York Times",807095);
        put("Bloomberg",34713362);
        put("The Associated Press",51241574);
        put("Reuters",1652541);
        put("AFP News Agency",380648579); //https://www.proexpo.cc/a/203.html
        put("UPI.com",16666806);//United Press International
        put("WSJ",3108351);// wall street new
        put("HuffPost",14511951); //Huffington post
    }};
    private String StopwordDir="E:\\Java_Project\\IRFinalProj\\src\\main\\resources\\stopword.txt";//????????????
}
