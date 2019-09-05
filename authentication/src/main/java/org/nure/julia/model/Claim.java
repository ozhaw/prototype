package org.nure.julia.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;

import java.io.IOException;
import java.io.Serializable;

public class Claim implements IdentifiedDataSerializable, Serializable {
    private String identifier;
    private String claimKey;
    private Session session;

    @Override
    public int getFactoryId() {
        return 0;
    }

    @Override
    public int getId() {
        return 1;
    }

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeUTF(identifier);
        objectDataOutput.writeUTF(claimKey);
        objectDataOutput.writeObject(session);
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        identifier = objectDataInput.readUTF();
        claimKey = objectDataInput.readUTF();
        session = objectDataInput.readObject();
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getClaimKey() {
        return claimKey;
    }

    public void setClaimKey(String claimKey) {
        this.claimKey = claimKey;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
