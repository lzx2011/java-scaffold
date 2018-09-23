package fun;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName: StatisticCharacter <br/>
 * Function: 统计英文名著中字数和重复率<br/>
 *
 * @author gary.liu
 * @date 03/06/2018
 */
public class StatisticCharacter {

    public static double totalCount = 0;
    public static double diffWord = 0;
    public static Map<String, Integer> characterMap = new HashMap<String, Integer>();
    public static ArrayList<Map.Entry<String, Integer>> sortWordList;

    public static void main(String[] args) throws IOException {
        String fileName = "/Users/gary/Documents/JaneEyre.txt";
        StringBuilder words = new StringBuilder();
        File file = new File(fileName);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String str;
        while ((str = bufferedReader.readLine()) != null) {
            //sumChineseWordNum(str);
            //sumEnglishWordNum(str);
            
        }
        bufferedReader.close();
        System.out.println("总字数------" + totalCount);
        System.out.println("不同的字数------" + diffWord);
        sort(characterMap);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter("/Users/gary/Documents/Study/output.txt");
            for (Map.Entry<String, Integer> entry : sortWordList) {
//              System.out.println(entry.getKey() + "------" + entry.getValue());
                words.append(entry.getKey()).append("------").append(entry.getValue()).append("\r\n");
            }
            fileWriter.write(words.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                fileWriter.close();
            }
        }
        //System.out.println("重复率-----" + ((totalCount-diffWord)/totalCount));
    }

    /**
     * 统计英文总字数和每个字出现的次数
     *
     * 大写转小写，过滤非因为字符
     *
     * @param str
     */
    public static void sumEnglishWordNum(String str) {
        if(StringUtils.isBlank(str)){
            return;
        }
        int num;
        String[] strArr = str.toLowerCase().split(" ", -1);
        if(strArr.length < 1){
            return;
        }
        for (int i = 0; i < strArr.length; i++) {
            if (StringUtils.isBlank(strArr[i])) {
                continue;
            }
            char lastCharacter = strArr[i].charAt(strArr[i].length() - 1);
            if(!(lastCharacter >='a' && lastCharacter <= 'z')){
                strArr[i] = strArr[i].substring(0, strArr[i].length() - 1);
            }
            if (StringUtils.isBlank(strArr[i])) {
                continue;
            }
            totalCount++;
            if (!characterMap.containsKey(strArr[i])) {
                num = 1;
                diffWord++;
                characterMap.put(strArr[i], num);
            } else {
                num = characterMap.get(strArr[i]) + 1;
                characterMap.put(strArr[i], num);
            }
        }
    }

    /**
     * 统计中文总字数和每个字出现的次数
     *
     * @param str
     */
    public static void sumChineseWordNum(String str) {
        int num;
        Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]");
        Matcher matcher = p.matcher(str);
        while (matcher.find()) {
            totalCount++;
            if (!characterMap.containsKey(matcher.group())) {
                num = 1;
                diffWord++;
                characterMap.put(matcher.group(), num);
            } else {
                num = characterMap.get(matcher.group()) + 1;
                characterMap.put(matcher.group(), num);
            }
        }
    }
    /**
     * 按value排序hashmap，统计单词的频率从高到低
     * @param map
     */
    public static void sort(Map<String, Integer> map){
        sortWordList  = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
        Collections.sort(sortWordList,new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> map1 , Map.Entry<String, Integer> map2){
                return (map2.getValue() - map1.getValue());
            }
        });
    }



}
