package orn.nure.julia;

import org.apache.activemq.Message;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;
import java.io.Serializable;
import java.util.Optional;
import java.util.function.Function;

public final class CombinedTemplate {

    private CombinedTemplate() {}

    public static <T extends Serializable, R extends Serializable> Optional<R> receiveObjectAndSend(final Message message, Function<T, R> f)
            throws JMSException {

        R response = null;
        if (message instanceof ObjectMessage) {
            T t = (T)((ObjectMessage) message).getObject();
            if (t != null) {
                response = f.apply(t);
            }
        }
        return Optional.ofNullable(response);
    }

    public static <R extends Serializable> Optional<R> receiveTextAndSend(final Message message, Function<String, R> f)
            throws JMSException {

        R response = null;
        if (message instanceof ObjectMessage) {
            String text = ((TextMessage) message).getText();
            if (text != null) {
                response = f.apply(text);
            }
        }
        return Optional.ofNullable(response);
    }

}
