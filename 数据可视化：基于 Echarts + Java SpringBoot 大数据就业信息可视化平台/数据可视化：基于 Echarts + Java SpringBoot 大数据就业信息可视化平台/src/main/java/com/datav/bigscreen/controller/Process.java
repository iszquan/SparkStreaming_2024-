package com.datav.bigscreen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.*;


/**
 * 返回json格式
 * @author user
 *
 */
@RestController
@RequestMapping(value = {"/map_china_map", "/line_area_chart", "/pie_source"})
public class Process {

    @RequestMapping("/{filename}.json")
    public String json(@PathVariable("filename") String filename) throws Exception {
        UriComponentsBuilder builder = ServletUriComponentsBuilder.fromCurrentRequest();
        String requestedValue = builder.buildAndExpand().getPath();
        System.out.println(requestedValue);


        System.out.println(filename);
        ChangeJSON("./src/main/resources/static" + requestedValue);

        String jsonStr = readJSON("./src/main/resources/static" + requestedValue);
        System.out.println(jsonStr);
        return jsonStr;
    }

    public void ChangeJSON(String filename) throws Exception {
        System.out.println("正在访问:" + filename);
        if (filename.contains("map_china_map.json"))
        {
            int max=800, min=300;
            String objectStr = String.format("[{\"name\": \"上海\", \"confirmAdd\": %d, \"confirm\": %d, \"nowConfirm\": %d}, {\"name\": \"云南\", \"confirmAdd\": %d, \"confirm\": %d, \"nowConfirm\": %d}, {\"name\": \"内蒙古\", \"confirmAdd\": %d, \"confirm\": %d, \"nowConfirm\": %d}, {\"name\": \"北京\", \"confirmAdd\": 373, \"confirm\": 568, \"nowConfirm\": 600}, {\"name\": \"吉林\", \"confirmAdd\": 646, \"confirm\": 396, \"nowConfirm\": 512}, {\"name\": \"四川\", \"confirmAdd\": 749, \"confirm\": 455, \"nowConfirm\": 146}, {\"name\": \"天津\", \"confirmAdd\": 233, \"confirm\": 596, \"nowConfirm\": 878}, {\"name\": \"安徽\", \"confirmAdd\": 595, \"confirm\": 637, \"nowConfirm\": 746}, {\"name\": \"山东\", \"confirmAdd\": 148, \"confirm\": 201, \"nowConfirm\": 850}, {\"name\": \"山西\", \"confirmAdd\": 211, \"confirm\": 481, \"nowConfirm\": 228}, {\"name\": \"广东\", \"confirmAdd\": 136, \"confirm\": 360, \"nowConfirm\": 191}, {\"name\": \"广西\", \"confirmAdd\": 387, \"confirm\": 319, \"nowConfirm\": 324}, {\"name\": \"江苏\", \"confirmAdd\": 870, \"confirm\": 573, \"nowConfirm\": 435}, {\"name\": \"江西\", \"confirmAdd\": 576, \"confirm\": 323, \"nowConfirm\": 124}, {\"name\": \"河北\", \"confirmAdd\": 405, \"confirm\": 542, \"nowConfirm\": 878}, {\"name\": \"河南\", \"confirmAdd\": 210, \"confirm\": 550, \"nowConfirm\": 188}, {\"name\": \"浙江\", \"confirmAdd\": 336, \"confirm\": 549, \"nowConfirm\": 764}, {\"name\": \"海南\", \"confirmAdd\": 758, \"confirm\": 411, \"nowConfirm\": 251}, {\"name\": \"湖北\", \"confirmAdd\": 814, \"confirm\": 264, \"nowConfirm\": 113}, {\"name\": \"湖南\", \"confirmAdd\": 872, \"confirm\": 337, \"nowConfirm\": 181}, {\"name\": \"福建\", \"confirmAdd\": 378, \"confirm\": 749, \"nowConfirm\": 741}, {\"name\": \"贵州\", \"confirmAdd\": 823, \"confirm\": 100, \"nowConfirm\": 603}, {\"name\": \"辽宁\", \"confirmAdd\": 173, \"confirm\": 140, \"nowConfirm\": 589}, {\"name\": \"陕西\", \"confirmAdd\": 546, \"confirm\": 206, \"nowConfirm\": 855}, {\"name\": \"青海\", \"confirmAdd\": 688, \"confirm\": 614, \"nowConfirm\": 480}, {\"name\": \"黑龙江\", \"confirmAdd\": 193, \"confirm\": 220, \"nowConfirm\": 666}, {\"name\": \"新疆\", \"confirmAdd\": 514, \"confirm\": 164, \"nowConfirm\": 113}, {\"name\": \"西藏\", \"confirmAdd\": 214, \"confirm\": 64, \"nowConfirm\": 13}, {\"name\": \"甘肃\", \"confirmAdd\": 414, \"confirm\": 174, \"nowConfirm\": 133}]",
                    (int) (Math.random()*(max-min)+min),
                    (int) (Math.random()*(max-min)+min),
                    (int) (Math.random()*(max-min)+min),
                    (int) (Math.random()*(max-min)+min),
                    (int) (Math.random()*(max-min)+min),
                    (int) (Math.random()*(max-min)+min),
                    (int) (Math.random()*(max-min)+min),
                    (int) (Math.random()*(max-min)+min),
                    (int) (Math.random()*(max-min)+min));
            System.out.println(objectStr);
            StringWrite(filename, objectStr);
        }else if (filename.contains("line_area_chart.json"))
        {
            int max=1000, min=100;
            String objectStr = String.format("{\"xAxis\": {\"data\": [\"企业服务\", \"数据服务\", \"电子商务\", \"培训机构\", \"生活服务\", \"半导体\"]}, \"series\": [{\"data\": [%d, %d, %d, %d, %d, %d]}]}",
                    (int)(Math.random()*(max-min)+min),
                    (int)(Math.random()*(max-min)+min),
                    (int)(Math.random()*(max-min)+min),
                    (int)(Math.random()*(max-min)+min),
                    (int)(Math.random()*(max-min)+min),
                    (int)(Math.random()*(max-min)+min),
                    (int)(Math.random()*(max-min)+min),
                    (int)(Math.random()*(max-min)+min),
                    (int)(Math.random()*(max-min)+min),
                    (int)(Math.random()*(max-min)+min),
                    (int)(Math.random()*(max-min)+min),
                    (int)(Math.random()*(max-min)+min));
            System.out.println(objectStr);
            StringWrite(filename, objectStr);
        }else if (filename.contains("pie_source.json"))
        {
            int max=1000, min=100;
            String objectStr = String.format("[{\"name\": \"1-3年\", \"value\": %d}, {\"name\": \"经验不限\", \"value\": %d}, {\"name\": \"7年以上\", \"value\": %d}, {\"name\": \"5-7年\", \"value\": %d}]",
                    (int)(Math.random()*(max-min)+min),
                    (int)(Math.random()*(max-min)+min),
                    (int)(Math.random()*(max-min)+min),
                    (int)(Math.random()*(max-min)+min));
            System.out.println(objectStr);
            StringWrite(filename, objectStr);
        }
        //其它json请自行增加相关代码
    }

    public void StringWrite(String filename, String content) throws Exception {
        File jsonFile = new File(filename);
        FileOutputStream outputStream = new FileOutputStream(jsonFile);
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bw.write("");
            bw.write(content);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readJSON(String fileName) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));

        StringBuilder sb = new StringBuilder();
        String line = "";
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        String result = sb.toString().replaceAll("\r\n", "").replaceAll(" +", "");

        return result;
    }
}

