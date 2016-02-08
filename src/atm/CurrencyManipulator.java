package atm;

import atm.exception.NotEnoughMoneyException;

import java.util.TreeMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Comparator;


/**
 * Created by Nicki on 18.11.2015.
 */

/**
 * Stores all information about selected currency.
 * <p>
 * This class represents user`s account.
 * It contains all necessary methods for adding, withdrawing money
 * and getting the necessary information.
 */
public class CurrencyManipulator
{
    /**
     * This is a currency code. It consists of three letters in upper case.
     */
    private String currencyCode;
    /**
     * This map is used to keep all money.
     * The first parameter is the currency denomination.
     * The second parameter is the amount of this denomination.
     */
    private Map<Integer, Integer> denominations = new HashMap<>();

    public CurrencyManipulator(String currencyCode)
    {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode()
    {
        return currencyCode;
    }
    /**
     * Adds specified amount of money to the current account.
     *
     * @param denomination  of currency
     * @param count  of denominations
     */
    public void addAmount(int denomination, int count)
    {
        if (denominations.containsKey(denomination))
            denominations.put(denomination, denominations.get(denomination) + count);
        else denominations.put(denomination, count);
    }
    /**
     * Counts and returns total amount of money for current manipulator.
     *
     * @return total amount of money for current account
     */
    public int getTotalAmount()
    {
        int result = 0;
        for (Map.Entry<Integer, Integer> pair : denominations.entrySet())
            result += pair.getValue() * pair.getKey();
        return result;
    }
    /**
     * Checks whether this account has money.
     *
     * @return true if this has money
     */
    public boolean hasMoney()
    {
        return denominations.size() != 0;
    }
    /**
     * Checks whether the user can withdraw entered amount.
     *
     * @param expectedAmount - amount, specified by the user
     * @return true if expectedAmount is lesser than total amount of this account or equal to it
     */
    public boolean isAmountAvailable(int expectedAmount)
    {
        return expectedAmount <= getTotalAmount();
    }
    /**
     * Withdraws specified amount of money.
     *
     * @param expectedAmount to be withdrawn
     * @return map that contains two integers - denomination and it`s amount
     * @throws NotEnoughMoneyException
     */
    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException
    {
        /*
          This method implements greedy algorithm.
        */

        int sum = expectedAmount;
        /*
          Temporary copy of denominations map.
        */
        HashMap<Integer, Integer> temp = new HashMap<>();
        temp.putAll(denominations);
        /*
          This list that contains keys of the map.
          Must be sorted in descending order.
        */
        ArrayList<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> pair : temp.entrySet())
            list.add(pair.getKey());

        Collections.sort(list);
        Collections.reverse(list);
        /*
          Resulting TreeMap, sorted by descending order.
        */
        TreeMap<Integer, Integer> result = new TreeMap<>(new Comparator<Integer>()
        {
            @Override
            public int compare(Integer o1, Integer o2)
            {
                return o2.compareTo(o1);
            }
        });
        /*
          The main logic of the resulting map initialization.
        */
        for (Integer key : list) {
            int denomination = key;
            int count = temp.get(denomination);
            while (true) {
                if (sum < denomination || count <= 0) {
                    temp.put(denomination, count);
                    break;
                }
                sum -= denomination;
                count--;

                if (result.containsKey(denomination))
                    result.put(denomination, result.get(denomination) + 1);
                else
                    result.put(denomination, 1);
            }
        }
        /*
          If current amount of currencies isn`t enough to withdraw specified amount.
        */
        if (sum > 0)
            throw new NotEnoughMoneyException();
        else
        {
            for (Map.Entry<Integer, Integer> pair : result.entrySet())
                ConsoleHelper.writeMessage("\t" + pair.getKey() + " - " + pair.getValue());

            /*
              Updating this account.
            */
            denominations.clear();
            denominations.putAll(temp);

            ConsoleHelper.writeMessage("Transaction was successful!");
        }
        return result;
    }
}

