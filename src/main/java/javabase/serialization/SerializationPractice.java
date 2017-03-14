package javabase.serialization;

import java.io.*;

/**
 * Created by gary on 2017/3/14.
 */
public class SerializationPractice {

	public void serializableTest() {
		// Worker worker = new Worker();

		Worker1 worker = new Worker1();
		worker.setAge(30);
		worker.setName("gary");
        worker.setHeight(170);

		System.out.println(worker);

		try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(
				new FileOutputStream("/Users/gary/Documents/serializeTest.txt"))) {
			objectOutputStream.writeObject(worker);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void deserializeTest() {
		try (ObjectInputStream objectInputStream = new ObjectInputStream(
				new FileInputStream("/Users/gary/Documents/serializeTest.txt"))) {
			// Worker worker = (Worker) objectInputStream.readObject();
			Worker1 worker = (Worker1) objectInputStream.readObject();
			System.out.println(worker);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SerializationPractice practice = new SerializationPractice();
		 //practice.serializableTest();

		practice.deserializeTest();
	}

}

class Worker implements Serializable {

	private static final long serialVersionUID = 2L;

	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public String toString() {

		return "name=" + name + " ,age=" + age;
	}
}

class Worker1 implements Externalizable {

	private static final long serialVersionUID = 1L;

	private String name;
	private int age;
    private int height;

	public Worker1() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
	public String toString() {
		return "name=" + name + ",age=" + age + ",height=" + height;
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) {

		try {
			objectOutput.writeObject(name);
			objectOutput.writeInt(age);
            objectOutput.writeInt(height);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void readExternal(ObjectInput objectInput) {
		try {
			name = (String) objectInput.readObject();
			age = objectInput.readInt();
            height = objectInput.readInt();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
