package com.logtool.servlet;

import com.logtool.service.LogService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    LogService ls = new LogService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response){

        try{

            request.setAttribute("levels",ls.getLevelStats());
            request.setAttribute("services", ls.getServiceErrors());
            request.setAttribute("errors", ls.recentErrors());

            request.getRequestDispatcher("dashboard.jsp").forward(request,response);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
