package module3_week4;

import java.util.*;
import edu.duke.*;
import java.io.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder sb = new StringBuilder(message);
        String result = "";
        for(int i=whichSlice;i<sb.length();i+=totalSlices){
            char next = sb.charAt(i);
            result = result + next;
        }
        return result;
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) { //5  [5,10,15,20,25] // ejoty
        int[] key = new int[klength];
        CaesarCracker ck = new CaesarCracker(mostCommon);
        for(int i=0; i<klength;i++) {
            String sliced = sliceString(encrypted, i, klength);
            //System.out.println(sliced);
            //for each sliced, get the key
            int keys = ck.getKey(sliced);
            key[i]= keys;
        }
        //System.out.println(Arrays.toString(key));
        return key;
    }

    public void breakVigenere () { //call this in tester
        FileResource fr = new FileResource("/Users/anjali/Desktop/3. JP-Arrays, " +
                "Lists & Structured Data/Week 4/SecretData/secretmessage4.txt"); //this is the encrypted string
        String encrypted = fr.asString();

//        for when you know thr key length :
//        int[] key = tryKeyLength(encrypted,4,'e');
//        VigenereCipher vc = new VigenereCipher(key);
//        String decryptedMessage = vc.decrypt(encrypted);
//        System.out.println(decryptedMessage);

//        for when you don't know the key length :
//          FileResource frDictionary = new FileResource("/Users/anjali/Desktop/3. JP-Arrays, Lists & Structured Data/Week 4/VigenereProgram/dictionaries/English");
//          HashSet<String> dictionary = readDictionary(frDictionary);
//          breakForLanguage(encrypted,dictionary); // call this when the language is known
//
//       for when you don't know the language:
        HashMap<String, HashSet<String>> myMap = new HashMap<String, HashSet<String>>();
        DirectoryResource dr = new DirectoryResource();
        for(File f : dr.selectedFiles()){
            FileResource frDictionary = new FileResource("/Users/anjali/Desktop/3. JP-Arrays, Lists & Structured Data/Week 4/VigenereProgram/dictionaries/" + f.getName());
            myMap.put(f.getName(),readDictionary(frDictionary));
        }
        breakForAllLangs(encrypted,myMap);
    }

    public HashSet<String> readDictionary(FileResource fr){
        HashSet<String> dictionary = new HashSet<String>();
        for (String word : fr.lines()){
            word = word.toLowerCase();
            dictionary.add(word);
        }
        return dictionary;
    }

    public int countWords(String message, HashSet<String> dictionary){
        message = message.toLowerCase();
        String[] words = new String[message.length()];
        words = message.split("\\W+");
        //System.out.println(Arrays.toString(words));
        int count = 0;
        for(int i=0;i<words.length;i++){
            String myWord = words[i];
            if(dictionary.contains(myWord)){
                count++;
            }
        }
        return count;
    }

    public String breakForLanguage(String encrypted,HashSet<String> dictionary){ //we don't know the keyLength here
        int maxNumber = 0;
        String correctMessage = "";

        List<int[]> list = new ArrayList<int[]>();
        char mostCommon = mostCommonCharIn(dictionary);

        for(int i=1;i<=100;i++){
            int[] key = tryKeyLength(encrypted,i,mostCommon);
            VigenereCipher vc = new VigenereCipher(key);
            String decryptedMessage = vc.decrypt(encrypted);
            // if dictionary contains words from the decrypted message
            //increase the count
            //return thr message with the largest count

            int currCount = countWords(decryptedMessage,dictionary);
            //System.out.println("Key length is: "+ i);
            //System.out.println("Valid words are: "+currCount);
            if(currCount>maxNumber){
                maxNumber = currCount;
                correctMessage=decryptedMessage;
                list = Collections.singletonList(key);
            }
        }
        //System.out.println("Valid words are: "+maxNumber);
        System.out.println("The key is: " + Arrays.toString(list.get(0)));
        System.out.println("The key length is: " + list.get(0).length);
        //System.out.println("Message is: "+ "\n" + correctMessage);
        return correctMessage;
    }

    public char mostCommonCharIn(HashSet<String> dictionary){
        Map<Character, Integer> charCount = new HashMap<Character, Integer>();
        int maxCount = 0;
        char mostCommonChar =' ';
        for(String s : dictionary){
            s = s.toLowerCase();
            StringBuilder sb = new StringBuilder(s);
            for(int i=0;i<sb.length();i++){
                char currChar = sb.charAt(i);
                if(!charCount.containsKey(currChar)){
                    charCount.put(currChar,1);
                }
                else{
                    charCount.put(currChar,charCount.get(currChar)+1);
                }
            }
        }
        for(char c : charCount.keySet()){
            int currCount = charCount.get(c);
            if(currCount>maxCount){
                maxCount=currCount;
                mostCommonChar = c;
            }
        }
        //System.out.println("Most common char is: "+ mostCommonChar);
        return mostCommonChar;
    }

    public void breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> myMap){
        //create the hashmap
        //get the most common char for all languages
        //call breakForLanguage for all languages
        int maxCount =0;
        String correctMessage = "";
        String correctLang = "";

        for(String s : myMap.keySet()){
            HashSet<String> dictionary = myMap.get(s);
            char mostCommon = mostCommonCharIn(dictionary);
            String decryptedMessage = breakForLanguage(encrypted,dictionary);
            int currCount = countWords(decryptedMessage,dictionary);
            if(currCount>maxCount){
                maxCount=currCount;
                correctMessage = decryptedMessage;
                correctLang = s;
            }
        }
        System.out.println("Valid words are: "+ maxCount);
        System.out.println("Correct Message is: " + "\n" + correctMessage);
        System.out.println("Correct language is: " + correctLang);
    }
}
    

