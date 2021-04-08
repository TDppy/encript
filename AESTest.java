package corejava;

import java.io.*;
import java.security.*;
import javax.crypto.*;

public class AESTest {
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, ShortBufferException, IllegalBlockSizeException {
     if(args[0].equals("-genkey")){
         KeyGenerator keygen = KeyGenerator.getInstance("AES");
         SecureRandom random = new SecureRandom();
         keygen.init(random);
         //����һ����Կ
         SecretKey key = keygen.generateKey();
         try (ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(args[1])))
         {
             //��ָ���Ķ���д��ObjectOutputStream
             out.writeObject(key);
         }
     }else{
         int mode;
         //����ģʽ
         if(args[0].equals("-encrypt")) mode= Cipher.ENCRYPT_MODE;
         //����ģʽ
         else mode=Cipher.DECRYPT_MODE;
         try(ObjectInputStream keyIn=new ObjectInputStream(new FileInputStream(args[3]));
             FileInputStream in=new FileInputStream(args[1]);
             FileOutputStream out=new FileOutputStream(args[2]))
         {
             Key key=(Key)keyIn.readObject();
             //����ʵ�� Cipher����
             Cipher cipher = Cipher.getInstance("AES");
             //����Կ��ʼ��������
             cipher.init(mode,key);
             Util.crypt(in,out,cipher);
         }
     }
    }
}
