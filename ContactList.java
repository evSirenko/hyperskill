/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperskill.contacts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class ContactList{
    private ArrayList<Contact> contactList = new ArrayList<Contact>();
    
    enum Type{
        PERSON,
        ORGANIZATION
    }
            
    boolean add(Map<String, String> parameters){
       String type = parameters.getOrDefault("type", null);
       if (type == null) {
            return false;
        }
        switch (stringToType(type.toUpperCase())) {
            case ORGANIZATION:
                contactList.add(OrganizationContact.create(parameters));
                return true;
            case PERSON:
                contactList.add(PersonContact.create(parameters));
                return true;
            default:
                return false;
        }
    }
    
    protected Contact selectRecord(int recNum){
        if (recNum < 1 || recNum > contactList.size()){
            return null;
        } else {
            return contactList.get(recNum - 1);
        }
    }

    int remove(int recNum){
        Contact contact = selectRecord(recNum);
        if (contact == null){
            return -1;
        }
        int index = contactList.indexOf(contact);
        if (count() > 0 && index  < contactList.size()){
            contactList.remove(contact);
            return index;
        } else return -1;
    } 
    
        
    int edit(int recNum, String field, String text){    
        int index = contactList.indexOf(selectRecord(recNum));
        if (count() > 0 && index < contactList.size()){
            contactList.get(index).edit(field, text);
            return index;
        } else return -1;
    }
    
    public int count(){
        return contactList.size();
    }
    
    public void countPrint(){
        System.out.println("The phone book has " + count() + " records.");
    }     
    
    public void list(){
        int i = 1;
        for(Contact c: contactList){
            System.out.println(i + ". " + c.getName() + " " +  ((c.getSurname() == null) ? "" : c.getSurname()));
            i++;                  
        }
    }
    
    public void info(int recNum){
        Contact contact = selectRecord(recNum);
        if (contact == null){
            return;
        } else {
            System.out.println(contact.toString());
        }
    }
    
    private Type stringToType(String type){
        try {
            return Type.valueOf(type);
        } catch (IllegalArgumentException e){
            return null;
        }
    }
    
}

class ConactListTest{
    
    public static void main(String args[]){
        Map<String, String> parameters = new HashMap<>();
        parameters.put("type", "person");
        parameters.put("name", "vasja");
        parameters.put("number", "44");
        parameters.put("birth", "12/11/2013");
        ContactList list = new ContactList();
        list.add(parameters);
        list.info(1);
        Map<String, String> parameters2 = new HashMap<>();
        parameters2.put("type", "organization");
        parameters2.put("name", "vasja");
        parameters2.put("number", "44");
        parameters2.put("address", "12/11/2013");
        list.add(parameters2);
        list.info(2);
    }
}