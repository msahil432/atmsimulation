/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmsimulation;

import java.util.*;

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
        printNotes(dispensedCash);
    }

    private void provideOptimal2(int amount) throws Exception{
        SortedSet<Integer> notes = new TreeSet<>(Collections.reverseOrder());
        notes.addAll(dominationDetails.keySet());
        int maxNote = notes.first(), minNote = notes.last();

        if(amount>totalCash){
            throw new Exception("Insufficient Funds in ATM");
        }
        if(amount<minNote){
            throw new Exception("Amount less than Minimum Domination");
        }

        int length = 2, t = maxNote;
        while(t/10>0){
            length++;
            t=t%10;
        }

        int[] remain = new int[length-1];
        for(int i=0; i<length-1; i++){
            remain[i] = (int) (amount%Math.pow(10,i));
        }
        /**
            This would make data like this:
                    [no of notes][2][0][0][0]
                    [no of notes][0][5][0][0]
         **/
        int[][] mNotes = new int[length][notes.size()];
        Iterator iterator = notes.iterator();
        int i =0;
        while(iterator.hasNext()){
            int temp = (Integer)iterator.next();
            i++;
            for(int j=0; j<length; j++){
                mNotes[j][i] = (int)(temp%Math.pow(10, j));
            }
            mNotes[length-1][i]= dominationDetails.get(temp);
        }

        for(i=0; i<notes.size(); i++){

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
        printNotes(dispensedCash);
    }
    
    private void printNotes(HashMap<Integer, Integer> map){
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
