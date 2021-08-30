/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperskill.contacts;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


interface Editable{
    boolean edit(String fieldName, String fieldValue); 
    <T> T check(Field field, String fieldValue); 
    
    default <T> Field getFieldByName(String fieldName,  T t) {
        Class clss = t.getClass();
        Field field = null;
        while (field == null && clss != null){
            try{
                field = clss.getDeclaredField(fieldName);
            } catch (NoSuchFieldException | SecurityException ex){
                clss = clss.getSuperclass();
            }
        }  
        return field;
    }
    
    default <T> boolean setField(Field field, String fieldValue, T t){
        if (field == null) {
            return false;
        } else {
            try {
                field.setAccessible(true);
                if (check(field, fieldValue) == null){
                    field.set(t, "[no data]");
                } else {                    
                    field.set(t, check(field, fieldValue));
                }
                return true;
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                return false;
            }
        }
    }    
}    

class Contact implements Editable{
    final private static SimpleDateFormat birthDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private String name;
    private String number;
    private String address = "";
    private String surname;
    private Date birth;
    private Gender gender;
    private String type;
    
    enum Gender{
        M,
        F;
    }       
    
    private  LocalDateTime createdDate;
    private  LocalDateTime lastEditDate;
   
    protected Contact(Builder<?> builder){
        this.name = builder.name;
        this.surname = builder.surname;
        this.number = checkPhoneNum(builder.number);
        this.address = builder.address;
        this.birth = parseDate(builder.birth);
        this.gender = genderCheck(builder.gender) ;
        this.createdDate = LocalDateTime.now();
        this.lastEditDate = LocalDateTime.now();
        this.type = builder.type;
    }
    
    static public Builder builder(){
        return new Builder() {
            public Builder self() {
                return this;
            }
        };
    }
    
    public abstract static class Builder <T extends Builder<T>>{
        private String name;
        private String number;
        private String address = "";
        private String surname;
        private String birth;
        private String gender;
        private String type;
        
        public abstract T self();               
        
        public Contact build(){
            return new Contact(this);
        }
        
        public T name(String name){
            this.name = name;
            return self();
        }
        
        public T surname(String surname){
            this.surname = surname;
            return self();
        }
        
        public T number(String number){
            this.number = number;
            return self();
        }
        
        public T birth(String birth){   
            this.birth = birth;
            return self();
        }
        
        public T address(String address){
            this.address = address;
            return self();
        }
        
        public T gender(String gender){
            this.gender = gender;
            return self();
        } 
        
        public T type(String type){
            this.type = type;
            return self();
        } 
        
    }

    public boolean hasNumber(){
        return !(this.number.equals("")); 
    }
    
    protected Date parseDate(String birthDate){
        if (birthDate != null){
            try {
                return birthDateFormat.parse(birthDate);
            } catch (ParseException ex) {
                return null;
            }
        } else{ 
            return null;
        }
        
    }
    
    protected Gender genderCheck(String gender){
        if (gender == null) {
            return null;
        }
        try {
          return Gender.valueOf(gender); 
        } catch(IllegalArgumentException e){
          return null;
        }
    }
        
    private String checkPhoneNum(String text){
        if (text == null) {
            return null;
        }
        String regex = "(\\+?)((?<par>\\({1}\\w{1,}\\){1})\\w?|(\\w*))";
        String delim = "\\s{1}|-{1}";
        
        Pattern pattern = Pattern.compile(delim);
        Matcher matcher; 
        
        String[] groups = pattern.split(text);  
        
        for (int i = 0; i < groups.length; i++){
            if (i > 0 && groups[i].length() < 2){
               return "";
            }
        }
        
        pattern = Pattern.compile(regex);        
        
        int parenthesisCount = 0;
        int findCount = 0;
        
        for(String s: groups){
            matcher = pattern.matcher(s);
            if(matcher.matches()){
               if(matcher.group("par") != null){
                    parenthesisCount++;
                }
                findCount++;
            }
        }

        if (parenthesisCount <= 1 && findCount == groups.length){
            return text;
        } else {
            return "[no data]";
        }
    }
    
    public String getbirthDate(){
        return (birth == null) ? "[no data]" : birthDateFormat.format(birth);
    }   

    public String getType(){
        return this.type;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getSurname(){
        return this.surname;
    }
        
    public String getNumber(){
        return this.number;
    }
    
     public String getAddress(){
        return this.address;
    }
     
    public String getCreatedDate(){
        return this.createdDate.truncatedTo(ChronoUnit.MINUTES).toString();
    } 
    
    public String getLastEditDate(){
        return (this.lastEditDate != null) ? this.lastEditDate.truncatedTo(ChronoUnit.MINUTES).toString() : "";
    } 
     
    public String getGender(){
        if (this.gender == null) {
            return "[no data]";
        }
        switch(this.gender){
            case M: return "M";
            case F: return "F";
            default: return "[no data]";
        }
    } 
    
    public <String, V> Map<String, V> getContactParameters(){
        Field[] contactFields = this.getClass().getDeclaredFields();
        Map<String, V> parameters = new HashMap<>();
        for(Field f: contactFields){
            try {
                parameters.put((String)f.getName(), (V)f.get(this));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                
            }
        }
        return parameters;
    }
    
    @Override
    public boolean edit(String fieldName, String fieldValue) {
        Field field = getFieldByName(fieldName, this);
        if (field == null) {
            return false;
        } else {
            lastEditDate = LocalDateTime.now();
            return setField(field, fieldValue, this);
        }
    } 
    
    @Override
    public <T> T check(Field field, String fieldValue) {
        if (field == null) {
            return null;
        }         
        switch (field.getName()) {
            case "number":
                return (T)checkPhoneNum(fieldValue);
            case "birth":
                return (T)parseDate(fieldValue);
            case "gender":
                return (T)genderCheck(fieldValue);
            default:
                return (T)fieldValue;
        }
    }    
}


class PersonContactTest {   
    
    enum Gender{
        M,
        F;
    }  
    
    public static void main(String[] args) throws ParseException {        
        Contact cc = Contact.builder().name("dfdf").address("dfdfdfdf").build();
        cc.edit("gender", "M");
        cc.edit("phone", "123()()");
        cc.edit("name", "BBBBBBBBBBBBBb");
        cc.edit("birth", "11/01/1934");
        Field field = cc.getFieldByName("gender", cc);
        System.out.println(cc.genderCheck("M") + " " + cc.check(field, "M"));
        System.out.println(cc.getName() + " " + cc.getGender() + " " + cc.getbirthDate());
    }
}