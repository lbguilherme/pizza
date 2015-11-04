package pizzasystem;

import java.util.ArrayList;
import newpackage.businesslogic.Pizzaria;
import pizzasystem.data.Client;

import newpackage.businesslogic.Pizzaria;

public class Main {

    public static void main(String[] args) {
        
        //test para ui
        Client testclient = new Client();
        testclient.setName("bisnaga");
        testclient.setCep("464654");
        testclient.setAddress("rua da bazinga");
        testclient.setPhoneNumber("123123");
        ArrayList<Client> newclients = Pizzaria.getClients(); 
        newclients.add(testclient);
        Pizzaria.setClients(newclients);;
        
        
        pizzasystem.ui.MainFrame.main(null);
    }
    
}
