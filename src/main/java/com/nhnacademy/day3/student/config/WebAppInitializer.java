package com.nhnacademy.day3.student.config;


import com.nhnacademy.day3.student.factory.ControllerFactory;
import jakarta.servlet.ServletContainerInitializer;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.HandlesTypes;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

@Slf4j
@HandlesTypes(
        value = {
                com.nhnacademy.day3.student.controller.Command.class
        }
)

public class WebAppInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> c, ServletContext ctx) throws ServletException {


        ControllerFactory controllerFactory = new ControllerFactory();
        controllerFactory.init(c);
        ctx.setAttribute("controllerFactory", controllerFactory);
    }
}
