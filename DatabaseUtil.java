package org.example;
import com.mongodb.client.*;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.apache.log4j.Logger;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DatabaseUtil{
    public static MongoClient mClient;
    public static MongoDatabase database;
    public static MongoCollection userCollection;
    public static Logger logger = Logger.getLogger(DatabaseUtil.class);

    private DatabaseUtil(){
    }

    static{
        String uri = "mongodb+srv://egemenguler06:Gz2McAGVd7ybw3iE@eternalsemestercluster0.qkhgpwi.mongodb.net/";
        mClient = MongoClients.create(uri);
        database = mClient.getDatabase("EternalSemester");
        userCollection = database.getCollection("Users");
    }

    public static boolean createUser(String email, String username, String password){
        if(!userSignUpInfoValidity(email, username)) return false;
        Document document = new Document();
        document.put("email", email);
        document.put("username", username);
        document.put("password", password);
        document.put("playWithMouse", false);
        document.put("levelOneKillCount", 0);
        document.put("levelTwoKillCount", 0);
        document.put("levelThreeKillCount", 0);
        document.put("levelFourKillCount", 0);
        document.put("MoveUp", 51);
        document.put("UseSpecial", 62);
        document.put("MoveDown", 47);
        document.put("MoveLeft", 29);
        document.put("MoveRight", 32);
        document.put("Gold",0);
        document.put("HealthBoost",0);
        document.put("AttackBoost",0);
        document.put("SpeedBoost",0);
        document.put("CooldownBoost", 0);
        document.put("XPBoost", 0);
        userCollection.insertOne(document);
        return true;
    }

    public static void printUsers(){
        FindIterable<Document> documentCursor = userCollection.find();
        for (Document doc : documentCursor) {
            logger.info(doc.toJson());
        }
    }
    public static Document findUser(String username){
        FindIterable<Document> documentCursor = userCollection.find();
        for (Document doc : documentCursor) {
            if(doc.getString("username").equals(username)) return doc;
        }
        return null;
    }
    public static boolean userLogin(String email, String username, String password){
        if(email == null || username == null ) return false;
        FindIterable<Document> documentCursor = userCollection.find();
        for (Document doc : documentCursor) {
            if(doc.getString("email").equals(email) && doc.getString("username").equals(username) & doc.getString("password").equals(password)) return true;
        }
        return false;
    }
    private static boolean userSignUpInfoValidity(String email, String username){
        if(email == null || username == null || username == "" || email == "") return false;
        FindIterable<Document> documentCursor = userCollection.find();
        for (Document doc : documentCursor) {
            if(doc.getString("email").equals(email) && doc.getString("username").equals(username)) return false;
        }
        return true;
    }
    private static boolean isValidEmailAddress(String email) {
        final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.matches();
    }
    public static void updateKillCount(String username, int level, int addedKills){
        Document document = findUser(username);
        String[] nums = {"One","Two","Three","Four"};
        UpdateResult updateResult = userCollection.updateOne(document, Updates.set("level" + nums[level - 1] + "KillCount", (Integer)document.get("level" + nums[level - 1] + "KillCount") + addedKills));
    }
    public static void changeKeyBinding(String username, String changedKey, int newPreference){
        Document document = findUser(username);
        if(changedKey.equals("playWithMouse")){
            UpdateResult updateResult = userCollection.updateOne(document, Updates.set(changedKey, !((Boolean)document.getBoolean(changedKey))));
        }
        else{
            UpdateResult updateResult = userCollection.updateOne(document, Updates.set(changedKey, newPreference));
        }
    }
    public static void updateGold(String username, int gold){
        Document document = findUser(username);
        UpdateResult updateResult = userCollection.updateOne(document, Updates.set("Gold", (Integer)document.get("Gold") + gold));
    }

    public static void boost(String username, String stat){
        Document document = findUser(username);
        UpdateResult updateResult = userCollection.updateOne(document, Updates.set(stat + "Boost", (Integer)document.get(stat + "Boost") + 1));
    }
    public static int[] getKeyBindings(String username){
        Document document = findUser(username);
        final int KEY_COUNT = 5;
        int[] keyBindings = new int[KEY_COUNT];
        String[] keys = {"MoveUp", "UseSpecial", "MoveDown", "MoveLeft", "MoveRight"};
        for (int i = 0; i < KEY_COUNT; i++) {
            keyBindings[i] = (Integer)document.get(keys[i]);
        }
        return keyBindings;
    }
    public static int getLevelKillCount(String username, int level){
        Document document = findUser(username);
        String[] nums = {"One","Two","Three","Four"};
        return (Integer)document.get("level" + nums[level - 1] + "KillCount");
    }
    public static int getBoost(String username, String boostType){
        Document document = findUser(username);
        return (Integer)document.get(boostType + "Boost");
    }
    public static int getGold(String username){
        Document document = findUser(username);
        return (Integer)document.get("Gold");
    }
}
