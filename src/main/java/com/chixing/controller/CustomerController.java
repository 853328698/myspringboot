package com.chixing.controller;


import com.chixing.common.JsonResult;
import com.chixing.entity.Customer;
import com.chixing.service.CustomerService;
import com.chixing.util.GetMessageCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    public CustomerController(){
        System.out.println("CustomerController>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }


    //获得手机验证码
    @RequestMapping("getCode")
    @ResponseBody
    public String sendSMS(String phone){
        //手机验证码
        //  String code = GetMessageCode.getCode(phone);
        String code="123456";
        System.out.println("code:"+code);
        Map<String,Object> data = new HashMap<>();
        data.put("code",code);
        return code;
    }


    //手机登录
    @RequestMapping("loginByPhone")
    @ResponseBody
    public  JsonResult loginByPhone(String telno, HttpServletRequest request){
        //1. 查看该用户是否存在过，若存在过，查询出来绑定在session
        Customer customer =  customerService.getByTelno(telno);
        if(customer!=null){
            request.getSession().setAttribute("customerTelno",customer.getCustTelno());
            request.getSession().setAttribute("customerId",customer.getCustId());


            return JsonResult.createSuccessJsonResult(null);


        }else{ //2. 若用户没有存在过，注册一个（添加一个新账户），绑定在session
            Customer newCustomer =    new Customer();
            newCustomer.setCustTelno(telno);
            boolean flag = customerService.save(newCustomer);
            if(flag){
                //添加成功，登录成功,查询出来绑定在session
                customer =  customerService.getByTelno(telno);
                request.getSession().setAttribute("customerTelno",customer.getCustTelno());
                request.getSession().setAttribute("customerId",customer.getCustId());
                return JsonResult.createSuccessJsonResult(null);

            }else{
                //添加失败
                return JsonResult.createFailJsonResult(null);

            }

        }

    }

    @RequestMapping("loginByUsername")
    public  JsonResult loginByUsername(){
        return null;
    }



    //获得登录用户的手机信息
    @RequestMapping("getLoginTelno")
    @ResponseBody
    public JsonResult getLoginTelno( HttpServletRequest request){
        System.out.println("getLoginTelno.....................................");
        Object result =  request.getSession().getAttribute("customerTelno");
        if(result!=null) {
            String customerTelno = (String) result;
            Map<String, Object> data = new HashMap<>();
            data.put("customerTelno", customerTelno);
            return JsonResult.hasLoginJsonResult(data);

            /* {
                "status": "SUCCESS",
                    "data": {
                        "customerTelno":"18493489349";
                     },
                "message": "操作成功"
            }

            */

        }else{
            return JsonResult.needLoginJsonResult(null);
        }
    }





    //用户退出
    @RequestMapping("logout")
    @ResponseBody
    public JsonResult logout( HttpServletRequest request){
        request.getSession().invalidate();
        return JsonResult.createSuccessJsonResult(null);
    }



    //根据手机号查询用户信息
    @RequestMapping("info")
    @ResponseBody
    public JsonResult getInfoByTelno(String telno){
        System.out.println("getInfoByTelno>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        Customer customer = customerService.getByTelno(telno);
        Map<String,Object> data = new HashMap<>();
        data.put("customer",customer);
        return JsonResult.createSuccessJsonResult(data);
    }


    //跳转到登录页面
    @RequestMapping("goToLogin")
    public String goToLogin(){
        return "customer/login";
    }


    //跳转到用户个人中心页面
    @RequestMapping("goToInfo")
    public String goToInfo(){
        return "customer/info";
    }

    public String goToRegist(){
        return "customer/regist";
    }

}
