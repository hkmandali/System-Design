package src.MachineCoding.DesignaT9KeyPad;
// we need to implement a t9 keypad

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;

// assumptions - we'd be considering only english for the time being
// when the user enters any value , we need to display all the strings starting with that string corresponding to number
// we can use a trie for the look up as we'd be dealing with lot of strings
// so when a number of entered we'd loop through all the combinations and display the possible strings
class Trie
{
    Trie[] children;
    boolean isvalidword;
    Trie()
    {
        children = new Trie[26]; // no of valid characters
        isvalidword = false;
        for(int i=0;i<26;++i)
        {
            children[i] = null;
        }
    }

    void insertString(String input, Trie Head)
    {
        int len = input.length();
        Trie curr = Head;
        int i=0;
        while(i< len)
        {
            char current = input.charAt(i);
            int index = current -'a';
            Trie next = curr.children[index];
            if(next == null)
            {
                curr.children[index] =  new Trie();
            }
           curr = curr.children[index];
            i++;
        }
        curr.isvalidword = true;
    }

    boolean findword(String input,final Trie Head)
    {
        int len = input.length();
        Trie curr= Head;
        for(int i=0;i<len;++i)
        {
            char current = input.charAt(i);
            int index = current - 'a';
            if(curr.children[index] == null)
                return false;
            else
                curr= curr.children[index];
        }
        if(curr != null && curr.isvalidword)
            return true;
        return false;
    }



}

public class T9Implementation {

    static boolean findpartial(final Trie Head, String input)
    {

        int len = input.length();
        Trie curr = Head;
        for(int i=0;i<len;++i)
        {
            char current = input.charAt(i);
            int index = current-'a';
            if(curr.children[index] == null)
                return false;
            curr = curr.children[index];
        }
        return true;
    }

    static int reversDigits(int num)
    {
        int rev_num = 0;
        while(num > 0)
        {
            rev_num = rev_num*10 + num%10;
            num = num/10;
        }
        return rev_num;
    }

    static void returnallvalidhelper(int number, Trie Head, HashMap<Integer,ArrayList<Character>> lookup, String resulttopass, ArrayList<String> tosendback)
    {
        //String res = new String();
        //System.out.println("string passed is "+resulttopass+" curr number is "+number);
        int currentdigit = number %10;
        //System.out.println("current digit is "+currentdigit);
        ArrayList<Character> currpossiblechars = lookup.get(currentdigit);
        Iterator<Character> itr = currpossiblechars.iterator();
        if(number > 1 && number < 10) // this means it is the last digit can be returned to the user
        {
            while(itr.hasNext()) // checking for all the possible
            {
                char curr = itr.next();
                String topass = resulttopass + curr;
                if (findpartial(Head, topass)) {
                    tosendback.add(topass);
                }
            }
        }
        else // there are more than 2 digits , so keep iterating
        {
            while(itr.hasNext())  // this returns  a, b ,c in first case
            {
                char curr = itr.next(); // now we need to check if this is a valid string to be passed i.e we chcek if theres a string with these numbers
                String topass = resulttopass + curr;
                //System.out.println("current char is "+curr + "   topass is "+topass);
                if(findpartial(Head,topass))
                {
                   // System.out.println("find partial is true for "+topass);
                    returnallvalidhelper(number/10,Head,lookup,topass,tosendback);
                }
            }
        }
        return ;

    }
    // need to write these two methods to print all the valid strings
    /*
    static void getNext(Trie Head,String prefix,ArrayList<String> res) // by this point we have a confirmation that the prefix is valid annd we need to generate strings from them
    {
        int size = prefix.length();
        Trie curr = Head;
        for(int i=0;i<size;++i)
        {
            char current = prefix.charAt(i);
            int index = current-'a';
            curr = curr.children[index];
        }
        if(curr.isvalidword)
            res.add(prefix);
        for(int i=0;i<26;++i)
        {
            if(curr.children[i] != null)
                curr = curr.children[i];
        }
    }

    static void addtofinalresult(Trie Head,ArrayList<String> prefixes)
    {
        int size = prefixes.size();
        for(int i=0;i<size;++i) // we need to iterate over one by one prefix
        {
            String curr = prefixes.get(i);

        }
    }
*/
    static ArrayList<String> returnallvalid(int number, Trie Head, HashMap<Integer,ArrayList<Character>> lookup)
    {
        ArrayList<String> result = new ArrayList<>();
        int reverse = reversDigits(number); // we are reversing so that we can directly them from return as we need to go from first to last
        String resulttopass = new String();
        returnallvalidhelper(reverse,Head,lookup,resulttopass,result);
        // now in the result we have the list of all prefixes which are valid
        // we need to generate all the string starting with this prefix
        // we need to call another helper function over herewhich is in line 135
        return result;
    }

    static void fillLookupTable(HashMap<Integer,ArrayList<Character>> lookup)
    {

        lookup.put(2,new ArrayList<>(Arrays.asList('a','b','c')));
        lookup.put(3,new ArrayList<>(Arrays.asList('d','e','f')));
        lookup.put(4,new ArrayList<>(Arrays.asList('g','h','i')));
        lookup.put(5,new ArrayList<>(Arrays.asList('j','k','l')));
        lookup.put(6,new ArrayList<>(Arrays.asList('m','n','o')));
        lookup.put(7,new ArrayList<>(Arrays.asList('p','q','r','s')));
        lookup.put(8,new ArrayList<>(Arrays.asList('t','u','v')));
        lookup.put(9,new ArrayList<>(Arrays.asList('w','x','y','z')));
        return;
    }
    public static void main(String[] args) {
        Trie Head = new Trie();
        String[] input = {"book","robo","hemu","teja","test","kaba","applica","apple","bmn","anm","con"};
        for(int i=0;i<input.length;++i)
        {
            Head.insertString(input[i],Head);
        }

        System.out.println("find word for hem is "+ Head.findword("hema",Head));
        System.out.println("find word for book is "+ Head.findword("book",Head));
        HashMap<Integer, ArrayList<Character>> lookup;
        lookup = new HashMap<>();
        fillLookupTable(lookup);

        ArrayList<String> ret = returnallvalid(266,Head,lookup);
        System.out.println("All te valid for 266 "+Arrays.toString(ret.toArray()));


    }

}
