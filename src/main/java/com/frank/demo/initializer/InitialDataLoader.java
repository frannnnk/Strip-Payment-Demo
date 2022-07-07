package com.frank.demo.initializer;


import com.frank.demo.util.CommonUtil;
import com.frank.demo.util.SpringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class InitialDataLoader implements
        ApplicationListener<ContextRefreshedEvent> {
 
    boolean alreadySetup = false;


    @Autowired
    Environment env;

    @Autowired
    ApplicationContext ctx;
  
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        SpringUtil.setApplicationContext(ctx);
        CommonUtil.env = env;
        alreadySetup = true;
    }
 

}