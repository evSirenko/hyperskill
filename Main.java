/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
In this stage, you will write a program that can store not only information about people but also organizations.

In the real app, you can also store phone numbers of different companies, like your favorite pizza shop or your bank. 
These organizations don't have a name or a surname; they have an organization name and an address.

Additionally, a person can have a birth date and gender, but a company can't have that.

Let's use inheritance to solve this issue. There should be a base class containing information relevant to both to an organization and a person, like a phone number. And there should be two classes that inherit this base class: the class that represents an organization and a class that represents a person.

The list of records should contain both people and organizations. You can accomplish that if the list with records contains elements of the base class, not the specific classes.

There is one problem with that: how can you edit the fields that correspond to specific classes, like the address of an organization? One of the solutions is to create a final Boolean field isPerson in the base class. After that, when editing the record, first check this field, then cast to the corresponding class and then edit the field. This is a bad, workaround solution, but we will write a more general solution in the next stage.

Also, in this stage, you can improve the base class so that it has more than one field. You should implement fields that store the date of this record creation and the date of the last edit.
s
 */

package hyperskill.contacts;

public class Main {
    
    public static void main(String[] args) {
        ContactListMenu c = new ContactListMenu();
        c.run();
    }
}
