/**
 * This code originally came from this site:
 *
 *      https://developers.redhat.com/blog/2019/08/16/manipulating-emojis-in-java-or-what-is-%F0%9F%90%BB-1/#disqus_thread
 */
package org.onebeartoe.tours.emojis;

public class MysteryAnimal
{
    public static void main(String[] args)
    {
//        String bear = "🐻";
        
        // If the previous line doesn't show up in your editor,
        // you can comment it out and use this declaration instead: 
        String bear = "\ud83d\udc3b";
        
        System.out.println("going from: " + bear + " to:");
        
        int bearCodepoint = bear.codePointAt(bear.offsetByCodePoints(0, 0));
        int mysteryAnimalCodepoint = bearCodepoint + 1;

        char mysteryAnimal[] =
        {
            Character.highSurrogate(mysteryAnimalCodepoint),
            Character.lowSurrogate(mysteryAnimalCodepoint)
        };
        
        System.out.println("The Coderland Zoo's latest attraction: "
                + String.valueOf(mysteryAnimal));
    }
}
