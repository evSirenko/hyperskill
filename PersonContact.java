/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyperskill.contacts;

import java.util.Map;

class PersonContact extends Contact{
    private static final String DEFAULT_VALUE = "[no_data]";
    
    protected PersonContact (Builder builder){
        super(builder);
    }
    
    public static Builder builder(){
        return new Builder();
    }
    
    public static class Builder extends Contact.Builder<Builder>{
        @Override
        public PersonContact build(){
            return new PersonContact(this);
        }

        @Override
        public Builder self() {
            return this;
        }
    }
    
    public static PersonContact create(Map<String, String> parameters){
        return  PersonContact.builder().name(parameters.getOrDefault("name", DEFAULT_VALUE))
                        .surname(parameters.getOrDefault("surname", DEFAULT_VALUE))
                        .birth(parameters.getOrDefault("birth", DEFAULT_VALUE))
                        .gender(parameters.getOrDefault("gender", DEFAULT_VALUE))
                        .number(parameters.getOrDefault("number", DEFAULT_VALUE))
                        .type("person").build();
    } 
    
    @Override
    public String toString(){
        return String.format("Name: %s \nSurname: %s \nBirth date: %s \nGender: %s \nNumber: %s \nTime created: %s \nTime last edit: %s" 
                            , getName()
                            , getSurname()
                            , getbirthDate()
                            , getGender()
                            , getNumber()
                            , getCreatedDate()
                            , getLastEditDate()
                        );
    }
    
}


