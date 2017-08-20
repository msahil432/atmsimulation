/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmsimulation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 * @author msahil432
 */
public class ATM {
    Scanner scanner;
    HashMap<Integer, Integer> dominationDetails;
    int totalCash;
    
    public ATM()throws Exception{
        scanner = new Scanner(System.in);
        dominationDetails = new HashMap<>();
        System.out.println("ATM Initialiazation");
        System.out.println("---------------------------------------------");
        System.out.println("(Enter negative note to finish initiliazation)");
        int note=112, quantity=1;
        while(note>0 & quantity>0){
            System.out.println("Enter domination and their quantities: \n Enter Domination: ");
            note = scanner.nextInt();
            System.out.println("Enter number of notes for Rs."+ note);
            quantity = scanner.nextInt();
            if((note>0 & quantity>0))
                dominationDetails.put(note, quantity);
        }
        if(dominationDetails.size()==0){
            System.out.println("----------------------------------------");
            System.out.println("----ATM cannot be initialized empty-----");
            System.out.println("----------------------------------------");
            throw new Exception("ATM Initilized Empty");
        }
        for(Integer i : dominationDetails.keySet()){
            totalCash += dominationDetails.get(i)*i;
        }
        System.out.println("----ATM initialized : Total Amount "+totalCash+"-----");
    }
    public void startWorking() throws Exception{
        int choice =1;
        System.out.println("Choose :\n 1. Withdrawal");
        System.out.println(" Enter Choice: (Negative Values to exit:)");
        while(choice>0){    
            choice = scanner.nextInt();
            try{
                System.out.println(" Enter Amount: ");
                int amount = scanner.nextInt();
                provideOptimal(amount);
            }catch(Exception e){
                System.out.println("Try again with different amount. \n Error Occured: "+e.getMessage());
            }
            System.out.println("Choose :\n 1. Withdrawal");
            System.out.println(" Enter Choice: ");
            choice = scanner.nextInt();
        }
    }
    
    private void provideOptimal(int amount) throws Exception{
        SortedSet<Integer> notes = new TreeSet<>(Collections.reverseOrder());
        notes.addAll(dominationDetails.keySet());
        int maxNote = notes.first(), minNote = notes.last();
        
        int rem = amount%minNote;
        if(rem!=0){
            throw new Exception("Cannot dispense given amount as they are not a multiple of "+minNote);
        }
        if(amount>totalCash){
            throw new Exception("Insufficient Funds in ATM");
        }
        
        int remaining = amount;
        HashMap<Integer, Integer> dispensedCash = new HashMap<>();
        for(Integer note: notes){
            int notesNo= dominationDetails.get(note);
            if(notesNo<1){
                continue;
            }
            int no= remaining/note;
            if(no>notesNo){
                no = notesNo;
            }
            if(no>0){
                dispensedCash.put(note, no);
                dominationDetails.put(note, notesNo-no); 
            }
            remaining-=(no*note);
        }
        if(remaining>0)
            throw new Exception("Amount can't be dispensed.");
        totalCash -= amount;
        printHashMap(dispensedCash);
    }
    
    private void printHashMap(HashMap<Integer, Integer> map){
        System.out.println("\n\n-------------------------------");
        int total=0;
        System.out.println("The notes are:");
        for(Integer i: map.keySet()){
            System.out.println(i+" Rs. Note: "+map.get(i));
            total += i*map.get(i);
        }
        System.out.println("Total Cash:"+total);
        System.out.println("-------------------------------\n\n");
    }
    
    protected void finalise(){
        System.out.println("\n\nATM Work FInished, remaining Amount :"+totalCash);
    }
}
