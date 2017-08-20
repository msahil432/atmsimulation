/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package atmsimulation;

/**
 *
 * @author msahil432
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            ATM atm = new ATM();
            atm.startWorking();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
}