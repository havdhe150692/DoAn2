package com.example.doan2.service;


import com.example.doan2.entity.*;
import com.example.doan2.repository.ToadDataRepository;
import com.example.doan2.repository.ToadIngameRepository;
import com.example.doan2.repository.ToadPoolRepository;
import com.example.doan2.utils.Enum;
import com.example.doan2.utils.randomRarity.RandomRarityGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ToadCreationService {

    @Autowired
    ToadURIService toadURIService;

    @Autowired
    ToadPoolRepository toadPoolRepository;

    @Autowired
    ToadDataRepository toadDataRepository;

    @Autowired
    ToadIngameRepository toadIngameRepository;

    @Autowired
    AdminContractExecutionService adminContractExecutionService;

    @Autowired
    ToadStatusLogicService toadStatusLogicService;


    public ToadData GenerateACommonToad()
    {
        List<ToadData> listToad = toadDataRepository.findAllByRarity(Enum.Rarity.Common);

        Random random = new Random();
        int returnNumber =  random.nextInt(listToad.size());
        System.out.println("----------Entry ");
        System.out.println(listToad.size());
        System.out.println(returnNumber);
        System.out.println("Toad Data " + listToad.get(returnNumber).getName());
        return listToad.get(returnNumber);
    }

    public ToadData GenerateNewToadFromParent(Enum.Rarity pA, Enum.Rarity pB)
    {
        RandomRarityGenerator randomRarityGenerator = new RandomRarityGenerator();
        Enum.Rarity outputRarity = randomRarityGenerator.LookUpRarity(pA, pB);
        List<ToadData> listToad = toadDataRepository.findAllByRarity(outputRarity);
        if(outputRarity != Enum.Rarity.Common)
        {
            List<Integer> remaningAmountList = new ArrayList<>();
            int sum = 0;
            for (int i = 0; i < listToad.size(); i++) {
                ToadData toadData =  listToad.get(i);
                ToadPool t = toadPoolRepository.findByToadData(toadData);
                int totalAmount = t.getTotalAmount();
                int curentIndex = t.getCurrentIndex();
                int remainingAmount = totalAmount - curentIndex;
                remaningAmountList.add(remainingAmount);
                sum += remainingAmount;
            }

            if(sum == 0)
            {
                return GenerateACommonToad();
            }

            Random r = new Random();
            int randomNumber = r.nextInt(sum+1);

            int lookupNumber = 0;
            for (int i = 0; i < remaningAmountList.size(); i++) {
                int number =  remaningAmountList.get(i);
                lookupNumber += number;
                if (randomNumber <= lookupNumber)
                {
                    System.out.print("---------Entry list: ");
                    //test
                    for (int q = 0; q < remaningAmountList.size(); q++)
                    {
                        System.out.print(remaningAmountList.get(q) + "/// ");
                    }
                    System.out.println();
                    //end test
                    System.out.println("RandomNumber = " + randomNumber + ", lookupNumber = "+lookupNumber);
                    System.out.println("Toad Data " + listToad.get(i).getName());
                    return listToad.get(i);
                }
            }
        }
        return GenerateACommonToad();
    }

    public ToadIngame GenerateIngameToadFromToadData(User u, ToadData toadData) throws Exception {
        ToadIngame toadIngame = new ToadIngame();
        toadIngame.setToadData(toadData);
        toadIngame.setOwner(u);
        toadIngame.setDateOfBirth(new Timestamp(System.currentTimeMillis()));
        toadIngame.setTypeCounter(0);
        ToadStatus toadStatus = toadStatusLogicService.StatusGeneration(toadData);
        toadIngame.setToadStatus(toadStatus);
        toadStatus.setToadIngame(toadIngame);

        if(!(toadData.getRarity() == Enum.Rarity.Common))
        {
            var pool = toadPoolRepository.findByToadData(toadData);
            pool.setCurrentIndex(pool.getCurrentIndex() +1);
            toadIngame.setTypeCounter(pool.getCurrentIndex());
            toadIngameRepository.save(toadIngame);

            adminContractExecutionService.MintNFTFromCentral(u, toadIngame);
        }

        toadIngameRepository.save(toadIngame);
        return toadIngame;
    }

    public void ToadBirth(Enum.Rarity pA, Enum.Rarity pB)
    {

    }
    public void Test(int numberOfInput)
    {


        System.out.println("start");
        for(int i = 0; i< numberOfInput; i++)
        {
            Random r = new Random();
            int randomNumberA = r.nextInt(5);
            int randomNumberB = r.nextInt(5);

            GenerateNewToadFromParent(Enum.Rarity.values()[randomNumberA], Enum.Rarity.values()[randomNumberB]);

        }
        System.out.println("end");
    }


}
