package br.ufpe.cin.triggermq.distribution;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Marshaller {

	public byte[] marshall(Object msgToBeMarshalled) throws IOException,
			InterruptedException {

		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
		objectStream.writeObject(msgToBeMarshalled);

		return byteStream.toByteArray();
	}

	public Object unmarshall(byte[] msgToBeUnmarshalled) {

		ByteArrayInputStream byteStream = new ByteArrayInputStream(
				msgToBeUnmarshalled);
		ObjectInputStream objectStream = null;
		try {
			objectStream = new ObjectInputStream(byteStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			return (Object) objectStream.readObject();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}