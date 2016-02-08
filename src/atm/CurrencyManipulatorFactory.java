package atm;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Nicki on 18.11.2015.
 */

/**
 * Creates and stores all currency manipulators.
 */
public class CurrencyManipulatorFactory
{
    /**
     * Stores all instances of CurrencyManipulator.
     */
    private static HashMap<String, CurrencyManipulator> manipulatorHashMap = new HashMap<>();
    /**
     * Creates and returns the CurrencyManipulator according to the parameter.
     *
     * @param currencyCode - what manipulator to select
     * @return CurrencyManipulator specified by the parameter
     */
    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode)
    {
        if(!manipulatorHashMap.containsKey(currencyCode))
        {
            CurrencyManipulator manipulator = new CurrencyManipulator(currencyCode);
            manipulatorHashMap.put(currencyCode, manipulator);
            return manipulator;
        }
        else return manipulatorHashMap.get(currencyCode);
    }
    /**
     * We don`t need to instantiate this class as we use only static method.
     */
    private CurrencyManipulatorFactory(){}
    /**
     * Returns all accounts that manipulatorHashMap stores.
     *
     * @return all currency manipulators of this factory
     */
    public static Collection<CurrencyManipulator> getAllCurrencyManipulators(){
        return manipulatorHashMap.values();
    }
}
