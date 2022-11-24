package com.example.doan2.utils.randomRarity;


import com.example.doan2.utils.Enum;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomRarityGenerator {


    List<RarityLookup> rarityLookupList = new ArrayList<>();

    public RandomRarityGenerator()
    {
        GenerateEntryData();

    }

    private void GenerateEntryData()
    {
        List<ValueEntry> cc = new ArrayList<>();
        cc.add(new ValueEntry(Enum.Rarity.Common, 900));
        cc.add(new ValueEntry(Enum.Rarity.Rare, 100));

        List<ValueEntry> cr = new ArrayList<>();
        cr.add(new ValueEntry(Enum.Rarity.Common, 800));
        cr.add(new ValueEntry(Enum.Rarity.Rare, 200));

        List<ValueEntry> ce = new ArrayList<>();
        ce.add(new ValueEntry(Enum.Rarity.Common, 700));
        ce.add(new ValueEntry(Enum.Rarity.Rare, 295));
        ce.add(new ValueEntry(Enum.Rarity.Epic, 005));

        List<ValueEntry> cm = new ArrayList<>();
        cm.add(new ValueEntry(Enum.Rarity.Common, 650));
        cm.add(new ValueEntry(Enum.Rarity.Rare, 325));
        cm.add(new ValueEntry(Enum.Rarity.Epic, 204));
        cm.add(new ValueEntry(Enum.Rarity.Mythical, 001));

        List<ValueEntry> cl = new ArrayList<>();
        cl.add(new ValueEntry(Enum.Rarity.Common, 600));
        cl.add(new ValueEntry(Enum.Rarity.Rare, 350));
        cl.add(new ValueEntry(Enum.Rarity.Epic, 044));
        cl.add(new ValueEntry(Enum.Rarity.Mythical, 005));
        cl.add(new ValueEntry(Enum.Rarity.Legendary, 001));

        List<ValueEntry> rr = new ArrayList<>();
        rr.add(new ValueEntry(Enum.Rarity.Common, 600));
        rr.add(new ValueEntry(Enum.Rarity.Rare, 350));
        rr.add(new ValueEntry(Enum.Rarity.Epic, 50));

        List<ValueEntry> re = new ArrayList<>();
        re.add(new ValueEntry(Enum.Rarity.Common, 400));
        re.add(new ValueEntry(Enum.Rarity.Rare, 500));
        re.add(new ValueEntry(Enum.Rarity.Epic, 100));

        List<ValueEntry> rm = new ArrayList<>();
        rm.add(new ValueEntry(Enum.Rarity.Common, 300));
        rm.add(new ValueEntry(Enum.Rarity.Rare, 525));
        rm.add(new ValueEntry(Enum.Rarity.Epic, 150));
        rm.add(new ValueEntry(Enum.Rarity.Mythical, 25));

        List<ValueEntry> rl = new ArrayList<>();
        rl.add(new ValueEntry(Enum.Rarity.Common, 250));
        rl.add(new ValueEntry(Enum.Rarity.Rare, 500));
        rl.add(new ValueEntry(Enum.Rarity.Epic, 200));
        rl.add(new ValueEntry(Enum.Rarity.Mythical, 45));
        rl.add(new ValueEntry(Enum.Rarity.Legendary, 5));

        List<ValueEntry> ee = new ArrayList<>();
        ee.add(new ValueEntry(Enum.Rarity.Common, 50));
        ee.add(new ValueEntry(Enum.Rarity.Rare, 750));
        ee.add(new ValueEntry(Enum.Rarity.Epic, 150));
        ee.add(new ValueEntry(Enum.Rarity.Mythical, 50));

        List<ValueEntry> em = new ArrayList<>();
        em.add(new ValueEntry(Enum.Rarity.Common, 100));
        em.add(new ValueEntry(Enum.Rarity.Rare, 650));
        em.add(new ValueEntry(Enum.Rarity.Epic, 200));
        em.add(new ValueEntry(Enum.Rarity.Mythical, 50));

        List<ValueEntry> el = new ArrayList<>();
        el.add(new ValueEntry(Enum.Rarity.Common, 100));
        el.add(new ValueEntry(Enum.Rarity.Rare, 200));
        el.add(new ValueEntry(Enum.Rarity.Epic, 590));
        el.add(new ValueEntry(Enum.Rarity.Mythical, 100));
        el.add(new ValueEntry(Enum.Rarity.Legendary, 10));

        List<ValueEntry> mm = new ArrayList<>();
        mm.add(new ValueEntry(Enum.Rarity.Common, 50));
        mm.add(new ValueEntry(Enum.Rarity.Rare, 100));
        mm.add(new ValueEntry(Enum.Rarity.Epic, 690));
        mm.add(new ValueEntry(Enum.Rarity.Mythical, 150));
        mm.add(new ValueEntry(Enum.Rarity.Legendary, 10));

        List<ValueEntry> ml = new ArrayList<>();
        ml.add(new ValueEntry(Enum.Rarity.Common, 50));
        ml.add(new ValueEntry(Enum.Rarity.Rare, 75));
        ml.add(new ValueEntry(Enum.Rarity.Epic, 650));
        ml.add(new ValueEntry(Enum.Rarity.Mythical, 200));
        ml.add(new ValueEntry(Enum.Rarity.Legendary, 25));

        List<ValueEntry> ll = new ArrayList<>();
        ll.add(new ValueEntry(Enum.Rarity.Common, 25));
        ll.add(new ValueEntry(Enum.Rarity.Rare, 75));
        ll.add(new ValueEntry(Enum.Rarity.Epic, 400));
        ll.add(new ValueEntry(Enum.Rarity.Mythical, 450));
        ll.add(new ValueEntry(Enum.Rarity.Legendary, 50));

        List<List<ValueEntry>> totalList = new ArrayList<>();
        totalList.add(cc);
        totalList.add(cr);
        totalList.add(ce);
        totalList.add(cm);
        totalList.add(cl);
        totalList.add(rr);
        totalList.add(re);
        totalList.add(rm);
        totalList.add(rl);
        totalList.add(ee);
        totalList.add(em);
        totalList.add(el);
        totalList.add(mm);
        totalList.add(ml);
        totalList.add(ll);

        int n =0 ;
        for(int i = 0; i<5; i++)
        {
            for(int j = 0; j<=i; j++)
            {
                RarityLookup lookup = new RarityLookup(
                        Enum.Rarity.values()[i], Enum.Rarity.values()[j], totalList.get(n));
                rarityLookupList.add(lookup);

                n++;
            }
        }

    }


    public Enum.Rarity LookUpRarity(Enum.Rarity rarityA, Enum.Rarity rarityB)
    {
        for(int i = 0; i < rarityLookupList.size(); i++)
        {
            RarityLookup lookup = rarityLookupList.get(i);
            if(((rarityLookupList.get(i).combineA == rarityA) && (rarityLookupList.get(i).combineB == rarityB)) ||
            ((rarityLookupList.get(i).combineB == rarityA) && rarityLookupList.get(i).combineA == rarityB))
            {
                Random r = new Random();
                int randomNumber = r.nextInt(1001);

                int percentageCounter = 0;
                for(int j = 0; j < lookup.entries.size(); j++)
                {
                    percentageCounter += lookup.entries.get(j).value;
                    if (randomNumber <= percentageCounter)
                    {
//                        System.out.print("---------Entry list: ");
//                        //test
//                        for (int q = 0; q < lookup.entries.size(); q++)
//                        {
//                            System.out.print(lookup.entries.get(q).rarityType.toString() + " " +lookup.entries.get(q).value + "/ ");
//                        }
//                        System.out.println();
//                            //end test
//                        System.out.println("------Input rA = " + rarityA + ", Input rB = " + rarityB + ", randomNumber = " + randomNumber + ", percentageCounter = "+percentageCounter +", outputRarity = " + lookup.entries.get(j).rarityType.toString());
                        return lookup.entries.get(j).rarityType;
                    }

                }
            }
        }

        return null;
    }

    public void Test(int numberOfInput)
    {
        System.out.println("start");
        for(int i = 0; i< numberOfInput; i++)
        {
            Random r = new Random();
            int randomNumberA = r.nextInt(5);
            int randomNumberB = r.nextInt(5);

            LookUpRarity(Enum.Rarity.values()[randomNumberA], Enum.Rarity.values()[randomNumberB]);

        }
        System.out.println("end");
    }
}


