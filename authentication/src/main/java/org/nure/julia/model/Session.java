package org.nure.julia.model;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

public class Session implements IdentifiedDataSerializable, Serializable {
    private String token;
    private Date expirationDate;

    public Session(String token, long lifeTime) {
        this.token = token;
        this.expirationDate = new Date(System.currentTimeMillis() + lifeTime);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    @Override
    public int getFactoryId() {
        return 0;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeUTF(token);
        objectDataOutput.writeLong(getExpirationDate().getTime());
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        token = objectDataInput.readUTF();
        expirationDate = new Date(objectDataInput.readLong());
    }
}
