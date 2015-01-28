package woordenapplicatie.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author frankcoenen
 */
public class WoordenController implements Initializable {
    
   private static final String DEFAULT_TEXT =   "Een, twee, drie, vier\n" +
                                                "Hoedje van, hoedje van\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van papier\n" +
                                                "\n" +
                                                "Heb je dan geen hoedje meer\n" +
                                                "Maak er één van bordpapier\n" +
                                                "Eén, twee, drie, vier\n" +
                                                "Hoedje van papier\n" +
                                                "\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van, hoedje van\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van papier\n" +
                                                "\n" +
                                                "En als het hoedje dan niet past\n" +
                                                "Zetten we 't in de glazenkas\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van papier";
    
    @FXML
    private Button btAantal;
    @FXML
    private TextArea taInput;
    @FXML
    private Button btSorteer;
    @FXML
    private Button btFrequentie;
    @FXML
    private Button btConcordantie;
    @FXML
    private TextArea taOutput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taInput.setText(DEFAULT_TEXT);
    }
    
    Set<String> woorden;
    List<String> woordenorder;
    
    @FXML
    private void aantalAction(ActionEvent event) 
    {       
        System.out.println("aantalAction");
        //Deze word gevuld met het globaal aantal woorden
        int woordcount = 0;
        
        //Deze word gevuld met het aantal "Unieke" woorden
        int dubbelwoord = 0;
        
        //Een grote string uit het text veld
        String test = taInput.getText();
        String testlower = test.toLowerCase();
                        
        //Array van Strings gehaalt uit de test string
        //en daarop de split methode om alles los te trekken
        String[] test2 = testlower.split("[^a-zA-Z]+");
                               
        Collection<String> woorden = new HashSet<String>();
        for(String s : test2)
        {
            woorden.add(s);
        }
        
        System.out.println("Totaal aantal woorden: " + test2.length);
        System.out.println("Aantal verschillende woorden: " + woorden.size());
    }
    
    @FXML
    private void sorteerAction(ActionEvent event) 
    {
        System.out.println("sorteerAction");
        //ArrayList maken van de woorden TreeSet
        List<String> order = new ArrayList<String>(woorden);
        
        //Comparator voor reverseorder maken
        Comparator cmp = Collections.reverseOrder();
        Collections.sort(order, cmp);
        
        for(String s : order)
        {
            System.out.println(s);
        }
    }

    @FXML
    private void frequentieAction(ActionEvent event) 
    {
        System.out.println("frequentieAction");
        //Een grote string uit het text veld
        String test = taInput.getText();
        String testlower = test.toLowerCase();
        testlower = Normalizer.normalize(testlower, Normalizer.Form.NFD);
        testlower = testlower.replaceAll("\\p{M}", "");
        
        //Array van Strings gehaalt uit de test string
        //en daarop de split methode om alles los te trekken
        String[] test2 = testlower.split("[^a-zA-Z]+");

        Map<String, Integer> counter = new HashMap<String, Integer>();
        for (String str : test2)
        {
            counter.put(str, 1 + (counter.containsKey(str) ? counter.get(str) : 0));
        }
        List<String> list = new ArrayList<String>(counter.keySet());
        Collections.sort(list, new Comparator<String>() 
        {
            @Override
            public int compare(String x, String y) {
                return counter.get(y) - counter.get(x);
            }
        });
        list.toArray(new String[list.size()]);
        for (String str : list) 
        {
            int frequency = counter.get(str);
            System.out.println(str + ": " + frequency);
        }
    }


    @FXML
    private void concordatieAction(ActionEvent event) 
    {
        TreeMap<String, LinkedList<Integer>> concordinatie = new TreeMap<String, LinkedList<Integer>>();
        
         //Een grote string uit het text veld
        String test = taInput.getText();
        String testlower = test.toLowerCase();
                        
        //Array van Strings gehaalt uit de test string
        //en daarop de split methode om alles los te trekken
        String[] test2 = testlower.split("[^a-zA-Z]+");
        
        //Loop voor de lengte van de test2 array
        for (int i = 0; i < test2.length; i++) 
        {
            //Kijken naar de string die zit in de Array op plek i
            for (String s : test2[i].split("[^a-zA-Z]+")) 
            {
                if (!concordinatie.containsKey(s) || concordinatie.get(s) == null) 
                {
                    LinkedList ll = new LinkedList<Integer>();
                    ll.add(i + 1);
                    concordinatie.put(s, ll);
                } 
                else 
                {
                    LinkedList ll = concordinatie.get(s);
                    ll.add(i + 1);
                }
            }
        }
        
        //Alles naar de system printen
        for (Map.Entry<String, LinkedList<Integer>> e : concordinatie.entrySet())
        {
            System.out.println(String.format("%-20s\t%s\n", e.getKey() + ":", e.getValue().toString()));
        }
    }
}
