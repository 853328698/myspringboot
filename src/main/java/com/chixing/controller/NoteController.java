package com.chixing.controller;


import com.chixing.common.JsonResult;
import com.chixing.entity.Note;
import com.chixing.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/note")
public class NoteController {
    @Autowired
    private NoteService noteService;

    @RequestMapping("goToAddNote")
    public String gotoadd(){
        System.out.println("=============gotoadd==============");
        return "note/addnote";
    }


    @RequestMapping("headImgUpload")
    @ResponseBody
    //文件上传操作
    public JsonResult headupload(HttpServletRequest request,MultipartFile upload){
        String path=request.getServletContext().getRealPath("/upload");
        File file =new File(path);//通过给定的字符串路径创建一个文件
        // 文件对象创建后，指定的文件或目录不一定物理上存在
        if(!file.exists()){
            file.mkdirs();
            System.out.println("创建文件夹");
        }else {
            System.out.println("已有该文件夹");
        }
        //如果文件夹不存在则创建一个
        String fileName=upload.getOriginalFilename();
        String uuid=UUID.randomUUID().toString();
        fileName=uuid+"_"+fileName;
        System.out.println("============================");


        try {
            File uploadFile=new File(file,fileName);
            System.out.println(uploadFile);
            upload.transferTo(uploadFile);
            //返回JSON,当前上传文件在tomcat服务器中的相对路径
            Map<String,Object> data=new HashMap<>();
            data.put("filePath","upload/"+fileName);
            return JsonResult.createSuccessJsonResult(data);
        } catch (IOException e) {
            e.printStackTrace();
            return JsonResult.createFailJsonResult(null);
        }




    }

    //添加游记
    @RequestMapping("add")
    @ResponseBody
    public JsonResult save(Note note,HttpServletRequest request){
        //注意添加游记要关联作者id,即当前登录用户的id,还有当前游记创建的时间
        System.out.println("添加游记");
        int custId = (int)  request.getSession().getAttribute("customerId");
        note.setCustId(custId);
        note.setNoteCreateTime(new Date());

        if( this.noteService.save(note)){
            return JsonResult.createSuccessJsonResult(null);
        }else{
            Map<String,Object> data = new HashMap<>();
            data.put("reason","添加游记失败");
            return JsonResult.createFailJsonResult(data);
        }
    }

    @RequestMapping("goToDetail")
    public String goToDetail(){
        return "note/detail";
        // 跳转到游记的详情页中  http://localhost:8080/ssm_demo/note/detail.html
    }
    //查看我最近的游记（刚写的游记）
    @RequestMapping("myLastNote")
    @ResponseBody
    public  JsonResult  getMyLastNote(HttpServletRequest request ){
        int custId = (int)  request.getSession().getAttribute("customerId");
        Note myLastNote =  this.noteService.getMyLastNote(custId);
        System.out.println(myLastNote);
        Map<String,Object> data = new HashMap<>();
        data.put("myLastNote",myLastNote);
        return   JsonResult.createSuccessJsonResult(data);

    }





}
