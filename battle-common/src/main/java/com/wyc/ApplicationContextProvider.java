package com.wyc;
import org.springframework.context.ApplicationContext;


public class ApplicationContextProvider{
    private static ApplicationContext context;

    public ApplicationContextProvider() {
	}

    public static ApplicationContext getApplicationContext() {
        return context;
    }
//    @Override
//    public void setApplicationContext(ApplicationContext ctx) {
//        context = ctx;
//    }
}
