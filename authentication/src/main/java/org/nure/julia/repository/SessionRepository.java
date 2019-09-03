package org.nure.julia.repository;

import com.hazelcast.core.HazelcastInstance;
import org.nure.julia.model.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;

@Repository
public class SessionRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionRepository.class);

    private final HazelcastInstance hazelcastInstance;

    @Autowired
    public SessionRepository(HazelcastInstance hazelcastInstance) {
        this.hazelcastInstance = hazelcastInstance;
    }

    public void save(Session session) {
        try {
            getContext().put(session.getToken(), writeSession(session));
        } catch (IOException e) {
            LOGGER.error("Unable to save a session", e);
        }
    }

    public void delete(Session session) {
        try {
            getContext().remove(session.getToken(), writeSession(session));
        } catch (IOException e) {
            LOGGER.error("Unable to delete a session", e);
        }
    }

    public Optional<Session> findById(String id) {
        if (getContext().containsKey(id)) {
            try {
                return Optional.of(readSession(getContext().get(id)));
            } catch (IOException | ClassNotFoundException e) {
                LOGGER.error("Unable to find a session", e);
            }
        }
        return Optional.empty();
    }

    private Map<String, String> getContext() {
        return hazelcastInstance.getMap("sessionsDB");
    }

    private Session readSession(String s) throws IOException, ClassNotFoundException {
        byte[] data = Base64.getDecoder().decode(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
        Object o = ois.readObject();
        ois.close();
        return (Session) o;
    }

    private String writeSession(Session o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(o);
        oos.close();
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }


}
