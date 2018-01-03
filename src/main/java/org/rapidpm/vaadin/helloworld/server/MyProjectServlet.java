package org.rapidpm.vaadin.helloworld.server;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinServlet;

/**
 *
 */
@WebServlet("/*")
@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
public class MyProjectServlet extends VaadinServlet {}
