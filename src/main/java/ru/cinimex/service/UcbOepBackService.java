package ru.cinimex.service;

import org.springframework.stereotype.Service;
import ru.cinimex.entity.Message;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@Service
public class UcbOepBackService {

    public String getStrXmlUser (String... params) throws JAXBException {
        StringWriter sw = new StringWriter();
        if (params[0] != null & params[1] != null & params[2] != null & params[3] != null) {
            Message user = new Message(params[0], params[1], params[2], params[3]);
            JAXBContext context = JAXBContext.newInstance(Message.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(user, sw);
        }
        return sw.toString();
    }
}
