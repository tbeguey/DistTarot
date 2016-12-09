package sample.Exception;

import sample.Enumeration.TypeCard;

/**
 * Created by theo on 09/12/16.
 */
public class RemoveRefusedException extends Exception {

    public RemoveRefusedException(int numero, TypeCard color){
        System.out.print("Vous avez selectionner une carte non autorisé");
        System.out.print(" ==> ");
        System.out.print(numero + " ");
        System.out.println(color);
    }
}
