package IR.controller;

import IR.commen.Path;
import IR.entity.RawTweet;
import IR.service.RawDocumentService;
import com.alibaba.fastjson.JSON;
import com.sun.xml.internal.stream.util.ThreadLocalBufferAllocator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CorpusController {

    @Autowired
    private RawDocumentService rds;

    @Autowired
    private RestTemplate restTemplate;

    private Path path=new Path();

    private List<RawTweet> rtList = new ArrayList<>();

    //max rawtweets in list
    public int cntMax=100;

    //global cnt of tweets in list
    public int glbInListCnt=0;

    public String publisherId;
    Map params = new HashMap();;// store uriVariable
    String nextPageToken="";
    String oldestId="";
    boolean haveNextPage=false;
    @GetMapping("startLoading")
    public void getFromTwitter()throws Exception{
        //get tweets JSON from tw server;
        //fill up the rawtweets' list
        String urlPrefix=path.getTwUrlPrefix() ;
        String rurlSurfix="/tweets";
        String bearer= path.getTwKey();
        HttpHeaders headers = new HttpHeaders();
        String url = urlPrefix + publisherId +  rurlSurfix;
        headers.set("Authorization", bearer);


        while(glbInListCnt<cntMax){
            try {
                String tmpUrl = "";
                if (!nextPageToken.equals("")) {
                    tmpUrl = url + "?pagination_token={pagination_token}";
                } else tmpUrl = url;


                ResponseEntity<String> responseEntity = restTemplate.exchange(
                        tmpUrl, HttpMethod.GET, new HttpEntity((Object) null, headers), String.class, params);
                Map body = (Map) JSON.parse(responseEntity.getBody());
                //get rawtweets list from responded JSON and add them to glblist
                List<RawTweet> tmpTweets = JSON.parseArray(body.get("data").toString(), RawTweet.class);
                rtList.addAll(tmpTweets);
                glbInListCnt+=tmpTweets.size();
                for(RawTweet tmp:rtList){
                    if(tmp.getId().equals(oldestId)) haveNextPage=false;
                }

                //get meta from responded JSON to load next page
                Map meta=(Map) JSON.parse(body.get("meta").toString());
                nextPageToken=meta.get("next_token").toString();
                params.put("pagination_token",nextPageToken);
                oldestId=meta.get("oldest_id").toString();
                haveNextPage=true;

            }catch (Exception e){
                //retry


                Thread.sleep(5000);
                String tmpUrl = "";
                if (!nextPageToken.equals("")) {
                    tmpUrl = url + "?pagination_token={pagination_token}";
                } else tmpUrl = url;


                ResponseEntity<String> responseEntity = restTemplate.exchange(
                        tmpUrl, HttpMethod.GET, new HttpEntity((Object) null, headers), String.class, params);
                Map body = (Map) JSON.parse(responseEntity.getBody());
                //get rawtweets list from responded JSON and add them to glblist
                List<RawTweet> tmpTweets = JSON.parseArray(body.get("data").toString(), RawTweet.class);
                rtList.addAll(tmpTweets);
                glbInListCnt+=tmpTweets.size();
                for(RawTweet tmp:rtList){
                    if(tmp.getId().equals(oldestId)) haveNextPage=false;
                }
                //get meta from responded JSON to load next page
                Map meta=(Map) JSON.parse(body.get("meta").toString());
                nextPageToken=meta.get("next_token").toString();
                params.put("pagination_token",nextPageToken);
                haveNextPage=true;
            }
        }
    }

    @GetMapping("load")
    public void loadRawDocument(@RequestParam("publisher") String publisher) throws Exception {
        //invoke methode to fill up the document list and invoke service

        publisherId=path.getPublisherIds().get(publisher)+"";
        nextPageToken="";
        oldestId="";
        haveNextPage=true;
        this.getFromTwitter();
        rds.processRawTweets(this);



    }

    public RawTweet nextRawDocument()throws Exception{
        //pop one document from the list and process it.
        RawTweet tmpTweet=rtList.get(0);
        rtList.remove(0);
        glbInListCnt--;
        if(glbInListCnt<=0&&haveNextPage) {
            this.getFromTwitter();
        }
        return tmpTweet;
    }


    @GetMapping("example")
    public Map returnExampleJson(@RequestParam("param") String param) throws Exception {
        //An example shows how to accept parameter and return JSON
        Map map0=new HashMap();
        Map map1=new HashMap();
        map0.put("name","Alex");
        map0.put("age","26");
        map1.put("name","Bob");
        map1.put("age","21");

        if(param.equals("stu0")) return map0;
        else return map1;
    }




}
