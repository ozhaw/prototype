package orn.nure.julia;

import org.apache.activemq.Message;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import java.io.Serializable;
import java.util.Optional;

public final class ListenerTemplate {

    public static <T extends Serializable> Optional<T> receiveObject(final Message message) throws JMSException {
        return message instanceof ObjectMessage
            ? Optional.ofNullable((T)((ObjectMessage) message).getObject())
            : Optional.empty();
    }

    public static Optional<String> receiveText(final Message message) throws JMSException {
        return message instanceof TextMessage
                ? Optional.ofNullable(((TextMessage) message).getText())
                : Optional.empty();
    }

}
