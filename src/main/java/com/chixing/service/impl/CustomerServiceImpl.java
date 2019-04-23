package com.chixing.service.impl;

import com.chixing.dao.CustomerDao;
import com.chixing.entity.Customer;
import com.chixing.dao.example.CustomerExample;
import com.chixing.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    @Override
    public Customer getByTelno(String telno) {
        CustomerExample example =   new CustomerExample(); //select * from customer where  telno =? and pwd = ?
        example. createCriteria().andCustTelnoEqualTo(telno);
        List<Customer> customerList = customerDao.selectByExample(example);

        if(customerList ==null || customerList.size()==0){
            return null;
        }else{
            return customerList.get(0);
        }

    }

    @Override
    public boolean save(Customer customer) {
        return customerDao.insert(customer) >0;

    }
}
