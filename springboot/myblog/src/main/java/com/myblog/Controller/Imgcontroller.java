package com.myblog.Controller;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonFactory;
import com.myblog.Dao.userphotoDao;
import com.sun.org.apache.bcel.internal.util.ClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.jdbc.core.JdbcTemplate;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class Imgcontroller {
    @Autowired
    JdbcTemplate jdbcTemplate = null;

    @Autowired
    userphotoDao userphotoDao;
    @Value("${file.upload.url}")
    private String uploadFilePath;

    private int imginonepage = 16;
    @ResponseBody
    @PostMapping("/uploadheadimg")
    public JSONObject uploadheadimg(HttpServletRequest request, @RequestParam("file") MultipartFile files[]) {

        JSONObject jsonObject = new JSONObject();
        String[] urls = new String[files.length];
        int sum = 0;
        for (int i = 0; i < files.length; i++) {
            //String fileName = files[i].getOriginalFilename();// 文件名
            //System.out.println(fileName);
            sum = imgsum();
            sum++;
            String fileName = sum+".jpg";
            File dest = new File(uploadFilePath + '/' + fileName);
            urls[i] = "http://localhost:8080/images/" + fileName;
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                files[i].transferTo(dest);

            } catch (Exception e) {
                System.out.println("flase:" + e.toString());
                System.out.println("filename is:" + fileName);
                System.out.println("程序错误，请重新上传");
                return jsonObject;
            }
        }

        request.setAttribute("success", "1");
        System.out.println("success");
        System.out.println("result,文件上传成功");
        addone();
        jsonObject.put("errno", 0);
        jsonObject.put("data", urls);

        System.out.println(jsonObject.toString());
        return jsonObject;
    }

    @ResponseBody
    @PostMapping("/uploadimg")
    public JSONObject uploadimg(HttpServletRequest request, @RequestParam("myFile") MultipartFile[] file) throws IOException {
        System.out.println("in uploadimg");
        JSONObject jsonObject = new JSONObject();
        System.out.println("request :"+request.getAttribute(""));
        String url = new String();
        System.out.println("in uploadimg1");
        if(file == null||file.length == 0){
            jsonObject.put("errno",-1);
            return jsonObject;
        }
        for (int i = 0; i < file.length; i++) {
            String fileName = file[i].getOriginalFilename();  // 文件名
            System.out.println("in uploadimg2");
            File dest = new File(uploadFilePath + '/' + fileName);
            url = "http://localhost:8080/images/" + fileName;
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                file[i].transferTo(dest);

            } catch (Exception e) {
                System.out.println("flase:" + e.toString());
                System.out.println("filename is:" + fileName);
                System.out.println("程序错误，请重新上传");
                return jsonObject;
            }
        }
        System.out.println("success");
        System.out.println("result,文件上传成功");

        jsonObject.put("errno", 0);
        jsonObject.put("data", url);

        System.out.println(jsonObject.toString());
        return jsonObject;
    }

    @ResponseBody
    @PostMapping("/uploadphoto")
    public JSONObject uploadpersonphoto(HttpServletRequest request, @RequestParam("file") MultipartFile files[]){

        JSONObject jsonObject = new JSONObject();
        String[] urls = new String[files.length];
        int[] imgnames = new int[files.length];
        int sum = 0;
        for (int i = 0; i < files.length; i++) {
            //String fileName = files[i].getOriginalFilename();// 文件名
            //System.out.println(fileName);
            sum = imgsum();
            sum++;
            imgnames[i]=sum;
            String fileName = sum+".jpg";
            File dest = new File(uploadFilePath + '/' + fileName);
            urls[i] = "http://localhost:8080/images/" + fileName;
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                files[i].transferTo(dest);
            } catch (Exception e) {
                System.out.println("flase:" + e.toString());
                System.out.println("filename is:" + fileName);
                System.out.println("程序错误，请重新上传");
                return jsonObject;
            }
        }
        int id = (int)request.getSession().getAttribute("id");

        for (int i=0;i<imgnames.length;i++){
            System.out.println("imgnames :" + imgnames[i]);
            System.out.println("userid is:" + id);
            userphotoDao.addphoto(id,imgnames[i]);
        }

        request.setAttribute("success", "1");
        System.out.println("success");
        System.out.println("result,文件上传成功");
        addone();
        jsonObject.put("errno", 0);
        jsonObject.put("data", urls);

        System.out.println(jsonObject.toString());
        return jsonObject;
    }

    @PostMapping("delphoto")
    public String delphoto(@RequestParam("userid")int userid,@RequestParam("imgid")int imgid){

        userphotoDao.delphoto(userid,imgid);
        return "/personalphoto";
    }
    @GetMapping("/myphoto")
    public String tomyphoto(HttpServletRequest request, Model model){
        int userid = (int)request.getSession().getAttribute("id");
        List<Integer> imgs = new ArrayList<Integer>();
        if(userphotoDao.getallphotobyid(userid)==null){
            System.out.println("无图片");
        }else {
            imgs = userphotoDao.getallphotobyid(userid);
        }
        model.addAttribute("myphotos",imgs);
        model.addAttribute("imginonepage",imginonepage);
        model.addAttribute("imgcount",imgs.size());
        return "/personalphoto";
    }
    public int imgsum(){

        String sql = "select sum from imgsum";
        int sum = 0;
        Map map = null;
        try {
            map = jdbcTemplate.queryForMap(sql);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return 0;
        }
        sum = (int)map.get("sum");
        return sum;
    }
    public void addone(){
        int sum = imgsum();
        sum++;
        String sql ="update imgsum set sum = "+sum;
        System.out.println(sql);
        jdbcTemplate.update(sql);
    }
    /*@RequestMapping("/upload")
    public Map upload(HttpServletRequest request,@RequestParam(value="myFileName")MultipartFile file) throws IllegalStateException, IOException {
        String pathRoot = request.getSession().getServletContext().getRealPath("upload");
        String fileName = file.getOriginalFilename();//获取图片名称
        String path = pathRoot+"\\"+fileName;
        System.out.println(path);
        File dest = new File(path);
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();// 新建文件夹
        }
        file.transferTo(dest);// 文件写入

        String path2="D:\\aim\\upload"+"\\"+fileName;
        UtilTools.copyFile(path, path2);
        Map map=new HashMap();
        map.put("errno", 0);
        path="http://localhost:8069/upload/"+fileName+"";
        List list=new ArrayList();
        list.add(path);
        map.put("data", list);



        return map;

    }*/
}

    /*@ResponseBody
    @PostMapping("/upload-img")
    public JSONObject uploadimg(@RequestParam("file") MultipartFile files[]){
        System.out.println("upload-img");
        JSONObject jsonObject =new JSONObject();
        String[] urls = new String[files.length];
        for(int i=0;i<files.length;i++){
            String fileName = files[i].getOriginalFilename();  // 文件名
            File dest = new File(uploadFilePath +'/'+ fileName);
            urls[i] = "http://localhost:8080/images/"+fileName;
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                files[i].transferTo(dest);
            } catch (Exception e) {
                System.out.println("success");
                System.out.println("程序错误，请重新上传");
                return jsonObject;
            }
        }
        System.out.println("success");
        System.out.println("result,文件上传成功");
        jsonObject.put("errno",0);
        jsonObject.put("data",urls);
        return jsonObject;
    }

    }*/

