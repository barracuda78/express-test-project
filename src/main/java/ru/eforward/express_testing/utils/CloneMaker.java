package ru.eforward.express_testing.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class CloneMaker {
    private static final Logger LOGGER = LoggerFactory.getLogger(CloneMaker.class);

    public static <T>T getClone(T original){
        T clone = null;
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(original);
            oos.close();

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            clone = (T)ois.readObject();

        } catch (IOException e) {
            LOGGER.error("IOException. Can not clone this object: " + original);
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            LOGGER.error("ClassNotFoundException. Can not clone this object: " + original);
            e.printStackTrace();
        }
        return clone;
    }
}