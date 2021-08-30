/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperskill.contacts;

import java.util.HashMap;
import java.util.Scanner;

class ContactListMenu {
    private final Scanner input = new Scanner(System.in);
    private final ContactList book = new ContactList();        
    
    private String getSringInput() {
        return this.input.nextLine();
    }
    
    private void finish() {        
        input.close();
    }
    
    public void run() {
       for(;;) { 
            System.out.print("Enter action (add, remove, edit, count, info, exit): ");
            String selectedAction = getSringInput();
            if (selectedAction.equals("exit")) {
                finish();
                break; 
            }
            switch (selectedAction) {
                    case "add":     add();
                                    break;
                    case "remove":  if (!remove()){
                                        continue;
                                    }
                                    break;
                    case "edit":    edit();
                                    break;
                    case "count":   book.countPrint();
                                    break;
                    case "info":    info();
                                    break;
                    default:        break;
            }
            System.out.println();
        } 
    }
    
    
    private boolean remove(){
        if (book.count() == 0) {
            System.out.println("No records to remove!");
            return false;
        }
        System.out.print("Select a record: ");
        if (book.remove(Integer.parseInt(getSringInput())) != -1){
            System.out.println("The record removed!");
            return true;
        } else {
            System.out.println("No such record");
            return false;
        }
    }
    
    private boolean edit(){
        if (book.count() == 0) {
            System.out.println("No records to edit!");
            return false;
        }
        System.out.print("Select a record: ");
        int index = Integer.parseInt(getSringInput());
        System.out.print("Select a field: ");
        String field =  getSringInput();
        System.out.print("Enter " + field +": ");
        String text = getSringInput();
        if (book.edit(index,  field, text) != -1) {
            System.out.println("The record updated!.");
            return true;
        } else {
            System.out.println("No such record");
            return false;
        }
    }
    
    private boolean info(){
        book.list();
        System.out.print("Select a record: ");
        var rec = Integer.parseInt(getSringInput()) ;
        Contact contact = book.selectRecord(rec);
        if (contact != null) {
            book.info(rec);
            return true;
        } else {
            return false;
        }
    }
    
    private boolean add(){
        HashMap<String, String> parameters = new HashMap<>();
        System.out.print("Enter the type (person, organization): ");
        parameters.put("type", getSringInput());
        if (parameters.get("type").equalsIgnoreCase("organization")){
            System.out.print("Enter the name: ");
            parameters.put("name", getSringInput());
            System.out.print("Enter the address: ");
            parameters.put("address", getSringInput());
            System.out.print("Enter the number: ");
            parameters.put("number", getSringInput());
            if (book.add(parameters)){
                System.out.println("The record added.");
                return true;
            } else {
                return false;
            }
        } else if (parameters.get("type").equalsIgnoreCase("person")) {
            System.out.print("Enter the name: ");
            parameters.put("name", getSringInput());
            System.out.print("Enter the surname: ");
            parameters.put("surname", getSringInput());
            System.out.print("Enter the birth: ");
            parameters.put("birth", getSringInput());
            if(parameters.get("birth") == null || parameters.get("birth").equals("")) {
                System.out.println("Bad birth date!");
            }
            System.out.print("Enter the gender: ");
            parameters.put("gender", getSringInput());
            if(!parameters.get("gender").equals("F") || !parameters.get("gender").equals("M")) {
                System.out.println("Bad gender!");
            }
            System.out.print("Enter the number: ");
            parameters.put("number", getSringInput());
            if (book.add(parameters)){
                System.out.println("The record added.");
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }      
}
