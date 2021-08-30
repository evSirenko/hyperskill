/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperskill.contacts;

import java.util.HashMap;
import java.util.Map;

public class OrganizationContact extends Contact{
    private static final String DEFAULT_VALUE = "[no_data]";

    
    protected OrganizationContact (Builder builder){
        super(builder);
    }
    
    public static Builder builder(){
        return new Builder();
    }
    
    public static class Builder extends Contact.Builder<Builder>{
        @Override
        public OrganizationContact build(){
            return new OrganizationContact(this);
        }

        @Override
        public Builder self() {
            return this;
        }
    }
    
    public static OrganizationContact create(Map<String, String> parameters){
        return  OrganizationContact.builder().name(parameters.getOrDefault("name", DEFAULT_VALUE))
                        .address(parameters.getOrDefault("address", DEFAULT_VALUE))
                        .number(parameters.getOrDefault("number", DEFAULT_VALUE))
                        .type("organization").build();
    }     
    
    @Override
    public String toString(){
        return String.format("Organization name: %s \nAddress: %s \nNumber: %s \nTime created: %s \nTime last edit: %s" 
                            , getName()
                            , getAddress()
                            , getNumber()
                            , getCreatedDate()
                            , getLastEditDate()
                        );
    }
    
    public static void main(String[] args) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("type", "organization");
        parameters.put("name", "vasja");
        parameters.put("number", "5555555-6699");
        parameters.put("address", "12/11/2013");
        
        OrganizationContact contact = OrganizationContact.create(parameters);
        
        System.out.println(contact.toString());
        
    }

}
