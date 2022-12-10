package IR.controller;

import IR.entity.RawTweet;
import IR.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    TestService ts;

    @GetMapping("news/{query}/{publisherList}")
    public String returnTweets(@PathVariable String query,@PathVariable String publisherList, Model model) throws Exception {
        //An example shows how to accept parameter and return JSON
        List<String> idList=new ArrayList<>();
        idList.add("1597696747442212865");
        idList.add("1596462520562245636");
        idList.add("1584869313458077697");
        List<RawTweet> rtList=new ArrayList<>();
        for(String id:idList){
            RawTweet tmpRt=ts.getTweetFromTwitterById(id);
            rtList.add(tmpRt);
        }
        model.addAttribute("tweets",rtList);
        return "Blog";
    }
}
