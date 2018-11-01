package ru.cinimex.servlet;

import ru.cinimex.service.UcbOepBackService;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/servlet")
public class UcbOepBackServlet extends HttpServlet {

    private static final long serialVersionUID = -8314035702649252239L;

    @Inject
    private UcbOepBackService service;

    @Resource(mappedName="java:jboss/WSConnectionFactory")
    private ConnectionFactory factory;

    @Resource(mappedName = "java:jboss/MdbQ")
    private Queue queue;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/xml");
        PrintWriter out = resp.getWriter();
        out.write("<h1></h1>");

        try {
            Connection con = factory.createConnection();

            Session session = con.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queue);
            String name = req.getParameter("name");
            String surname = req.getParameter("surname");
            String account = req.getParameter("account");
            String balance = req.getParameter("balance");
            String result;
            if (name != null & surname != null & account != null & balance != null) {
                out.write("<p>Sending messages to <em>" + queue.getQueueName() + "</em></p>");
                result = service.getStrXmlUser(name, surname, account, balance);
                producer.send(session.createTextMessage(result));
                out.write("<p><i>" + result + "</i></p>");
            } else {
                out.write("<p><i>Not all arguments were found</i></p>");
            }
        } catch (JMSException | JAXBException e) {
            e.printStackTrace();
        } finally {
            out.close();
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

}
