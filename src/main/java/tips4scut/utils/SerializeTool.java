package tips4scut.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.*;

public class SerializeTool
{
    public static String object2String(Object obj){
		String objBody = null;
		ByteArrayOutputStream baops = null;
		ObjectOutputStream oos = null;

		try{
			baops = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baops);
			oos.writeObject(obj);
			byte[] bytes = baops.toByteArray();
			objBody = new String(bytes);
		} catch (IOException e){
            e.printStackTrace();
		} finally{
			try{
				if (oos != null)
					oos.close();
				if (baops != null)
					baops.close();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		return objBody;
	}

	public static <T extends Serializable> T getObjectFromString (String objBody, Class<T> clazz){
		byte[] bytes = objBody.getBytes();
		ObjectInputStream ois = null;
		T obj = null;
		try{
			ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
			obj = (T) ois.readObject();
		} catch (IOException e){
            e.printStackTrace();
		} catch (ClassNotFoundException e){
			e.printStackTrace();
		} finally{
			try{
				if (ois != null)
					ois.close();
			} catch (IOException e){
				e.printStackTrace();
			}
		}
		return obj;
	}

    private static  BASE64Decoder base64Decoder = new BASE64Decoder();
    private static  BASE64Encoder base64Encoder = new BASE64Encoder();

    /** Read the object from Base64 string. */
    public static Object fromString( String s ) throws IOException ,
            ClassNotFoundException {
        byte [] data =   base64Decoder.decodeBuffer(s);
        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(  data ) );
        Object o  = ois.readObject();
        ois.close();
        return o;
    }

    /** Write the object to a Base64 string. */
    public static String toString( Serializable o ){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream( baos );
            oos.writeObject( o );
            oos.close();

        }catch (Exception e){
            e.printStackTrace();
        }
        return new String( base64Encoder.encodeBuffer(baos.toByteArray()));
    }

}