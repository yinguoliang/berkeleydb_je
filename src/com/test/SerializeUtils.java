package com.test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class SerializeUtils {
    public static byte[] serial(Object obj) throws IOException {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(); 
             ObjectOutput out = new ObjectOutputStream(bos);) {
            out.writeObject(obj);
            out.flush();
            byte[] ret = bos.toByteArray();
            return ret;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T deserial(byte[] input) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream bis = new ByteArrayInputStream(input);
            ObjectInput in = new ObjectInputStream(bis)){
            Object o = in.readObject();
            return (T)o;
        }
    }
}