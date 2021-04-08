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
         //生成一个密钥
         SecretKey key = keygen.generateKey();
         try (ObjectOutputStream out=new ObjectOutputStream(new FileOutputStream(args[1])))
         {
             //将指定的对象写入ObjectOutputStream
             out.writeObject(key);
         }
     }else{
         int mode;
         //加密模式
         if(args[0].equals("-encrypt")) mode= Cipher.ENCRYPT_MODE;
         //解密模式
         else mode=Cipher.DECRYPT_MODE;
         try(ObjectInputStream keyIn=new ObjectInputStream(new FileInputStream(args[3]));
             FileInputStream in=new FileInputStream(args[1]);
             FileOutputStream out=new FileOutputStream(args[2]))
         {
             Key key=(Key)keyIn.readObject();
             //返回实现 Cipher对象。
             Cipher cipher = Cipher.getInstance("AES");
             //用密钥初始化此密码
             cipher.init(mode,key);
             Util.crypt(in,out,cipher);
         }
     }
    }
}
