package module3_week4;

import edu.duke.DirectoryResource;
import edu.duke.FileResource;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;

public class tester {

    public String fileToString(){
        FileResource fr = new FileResource(
                "/Users/anjali/Documents/3. JP-Arrays, Lists & Structured Data/Week 4/VigenereTestData/titus-small.txt");
//        String message = "";
//        for(String s : fr.lines()){
//            message = message + "\n" + s;
//        }
//       return message;
        String message = fr.asString();
        return message;
    }

    public static void main(String[] args) {
        tester tester = new tester();
        String message = tester.fileToString();
        //System.out.println(message);

//        CaesarCipher cc = new CaesarCipher(17);
//        String encryptedMessage = cc.encrypt(message);
//        System.out.println(encryptedMessage);

//        CaesarCracker ck = new CaesarCracker();
//        String decryptedMessage = ck.decrypt(message);
//        System.out.println(decryptedMessage);

//        int [] key1 = {17,14,12,4};
//        VigenereCipher vc = new VigenereCipher(key1);
//        String vcEncrypted = vc.encrypt(message);
//        System.out.println(vcEncrypted);
//        String vcDecrypted = vc.decrypt(vcEncrypted);
//        System.out.println(vcDecrypted);

//        VigenereBreaker vb = new VigenereBreaker();
//        String sliced1 = vb.sliceString("abcdefghijklm", 2, 4);
//        System.out.println(sliced1);

//          VigenereBreaker vb = new VigenereBreaker();
//          vb.breakVigenere();

//           VigenereBreaker vb = new VigenereBreaker();
//           FileResource fr = new FileResource("/Users/anjali/Desktop/3. JP-Arrays, Lists & Structured Data/Week 4/VigenereProgram/dictionaries/English");
//           HashSet<String> dictionary = vb.readDictionary(fr);
//           vb.mostCommonCharIn(dictionary);

        VigenereBreaker vb = new VigenereBreaker();
        vb.breakVigenere();

    }
}
