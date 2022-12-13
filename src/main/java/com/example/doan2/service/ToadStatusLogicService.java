package com.example.doan2.service;


import com.example.doan2.entity.ToadData;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.ToadStatus;
import jnr.posix.Times;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class ToadStatusLogicService {

    public int MATURE_TIME_HOUR_COMMON = 0;
    public int MATURE_TIME_MINUTE_COMMON = 5;
    public int MATURE_TIME_HOUR_RARE= 0;
    public int MATURE_TIME_MINUTE_RARE = 30;
    public int MATURE_TIME_HOUR_EPIC = 5;
    public int MATURE_TIME_MINUTE_EPIC = 0;
    public int MATURE_TIME_HOUR_MYTHICAL = 10;
    public int MATURE_TIME_MINUTE_MYTHICAL = 0;
    public int MATURE_TIME_HOUR_LEGENDARY = 24;
    public int MATURE_TIME_MINUTE_LEGENDARY = 0;

    public int COLLECT_TIME_HOUR_COMMON = 0;
    public int COLLECT_TIME_MINUTE_COMMON = 5;
    public int COLLECT_TIME_HOUR_RARE= 1;
    public int COLLECT_TIME_MINUTE_RARE = 30;
    public int COLLECT_TIME_HOUR_EPIC = 3;
    public int COLLECT_TIME_MINUTE_EPIC = 0;
    public int COLLECT_TIME_HOUR_MYTHICAL = 6;
    public int COLLECT_TIME_MINUTE_MYTHICAL = 30;
    public int COLLECT_TIME_HOUR_LEGENDARY = 13;
    public int COLLECT_TIME_MINUTE_LEGENDARY = 0;

    public int BREED_TIME_HOUR_COMMON = 2;
    public int BREED_TIME_MINUTE_COMMON = 30;
    public int BREED_TIME_HOUR_RARE= 2;
    public int BREED_TIME_MINUTE_RARE = 30;
    public int BREED_TIME_HOUR_EPIC = 2;
    public int BREED_TIME_MINUTE_EPIC = 30;
    public int BREED_TIME_HOUR_MYTHICAL = 2;
    public int BREED_TIME_MINUTE_MYTHICAL = 30;
    public int BREED_TIME_HOUR_LEGENDARY = 2;
    public int BREED_TIME_MINUTE_LEGENDARY = 30;

    public int FEED_TIME_HOUR_COMMON = 2;
    public int FEED_TIME_MINUTE_COMMON = 30;
    public int FEED_TIME_HOUR_RARE= 2;
    public int FEED_TIME_MINUTE_RARE = 30;
    public int FEED_TIME_HOUR_EPIC = 2;
    public int FEED_TIME_MINUTE_EPIC = 30;
    public int FEED_TIME_HOUR_MYTHICAL = 2;
    public int FEED_TIME_MINUTE_MYTHICAL = 30;
    public int FEED_TIME_HOUR_LEGENDARY = 2;
    public int FEED_TIME_MINUTE_LEGENDARY = 30;

    public ToadStatus PerformMatured(ToadStatus t)
    {
        Instant now = Instant.now();
        if(now.compareTo(t.getExpectedMature().toInstant()) >= 0)
        {
            t.setExpectedMature(null);
            t.setExpectedBreed(Timestamp.from(now));
        }
        return t;
    }


    public JSONObject StatusCheck(ToadStatus t) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        Instant now = Instant.now();

        jsonObject.put("id", t.getId());
        jsonObject.put("isMature", (now.compareTo(t.getExpectedMature().toInstant()) >= 0));
        jsonObject.put("isToBreed", (now.compareTo(t.getExpectedBreed().toInstant()) >= 0));
        jsonObject.put("isToFeed", (now.compareTo(t.getExpectedCollect().toInstant()) >= 0));
        jsonObject.put("isToCollect", (now.compareTo(t.getExpectedHungry().toInstant()) >= 0));


        if((t.getExpectedMature() != null) && (now.compareTo(t.getExpectedMature().toInstant()) >= 0))
        {
            t.setExpectedMature(null);
            t.setExpectedBreed(Timestamp.from(now));
        }


        return jsonObject;
    }

    public ToadStatus StatusGeneration(ToadData t)
    {
        ToadStatus toadStatus = new ToadStatus();
        Instant now = Instant.now();
        Timestamp timestampNow = Timestamp.from(now);
        int mature_hour = 0;
        int mature_minute = 0;
        int collect_hour = 0;
        int collect_minute = 0;
        switch (t.getRarity())
        {
            case Common -> {
                mature_minute = MATURE_TIME_MINUTE_COMMON;
                mature_hour = MATURE_TIME_HOUR_COMMON;
                collect_minute = COLLECT_TIME_MINUTE_COMMON;
                collect_hour = COLLECT_TIME_HOUR_COMMON;
            }
            case Rare -> {
                mature_minute = MATURE_TIME_MINUTE_RARE;
                mature_hour = MATURE_TIME_HOUR_RARE;
                collect_minute = COLLECT_TIME_MINUTE_RARE;
                collect_hour = COLLECT_TIME_HOUR_RARE;
            }
            case Epic -> {
                mature_minute = MATURE_TIME_MINUTE_EPIC;
                mature_hour = MATURE_TIME_HOUR_EPIC;
                collect_minute = COLLECT_TIME_MINUTE_EPIC;
                collect_hour = COLLECT_TIME_HOUR_EPIC;
            }
            case Mythical -> {
                mature_minute = MATURE_TIME_MINUTE_MYTHICAL;
                mature_hour = MATURE_TIME_HOUR_MYTHICAL;
                collect_minute = COLLECT_TIME_MINUTE_MYTHICAL;
                collect_hour = COLLECT_TIME_HOUR_MYTHICAL;
            }
            case Legendary -> {
                mature_minute = MATURE_TIME_MINUTE_LEGENDARY;
                mature_hour = MATURE_TIME_HOUR_LEGENDARY;
                collect_minute = COLLECT_TIME_MINUTE_LEGENDARY;
                collect_hour = COLLECT_TIME_HOUR_LEGENDARY;
            }

        }
        Instant tMature = now.plus(mature_hour, ChronoUnit.HOURS).plus(mature_minute, ChronoUnit.MINUTES);
        Instant tCollect = now.plus(collect_hour, ChronoUnit.HOURS).plus(collect_minute, ChronoUnit.MINUTES);
        Instant tFeed = now;
        Instant tBreed =  now.plus(1000000, ChronoUnit.DAYS);

        toadStatus.setExpectedMature(Timestamp.from(tMature));
        toadStatus.setExpectedBreed(Timestamp.from(tBreed));
        toadStatus.setExpectedCollect(Timestamp.from(tCollect));
        toadStatus.setExpectedHungry(Timestamp.from(tFeed));

        return toadStatus;
    }

    public void ToadFeedWithType(ToadStatus toadStatus, int foodType)
    {
        var tMature = toadStatus.getExpectedMature().toInstant();
        var tBreed = toadStatus.getExpectedBreed().toInstant();
        var tFeed = toadStatus.getExpectedHungry().toInstant();
        var tCollect = toadStatus.getExpectedCollect().toInstant();

        switch (foodType)
        {
            case 1 -> {


            }

            case 2 -> {

            }

            case 3 -> {

            }

        }
    }

    public ToadIngame ToadBreed(ToadIngame toadIngame)
    {
        Instant now = Instant.now();
        var toadStatus = toadIngame.getToadStatus();
        var breedTime = toadStatus.getExpectedBreed().toInstant();
        switch (toadIngame.getToadData().getRarity())
        {
            case Common -> {
                breedTime = now.plus(BREED_TIME_HOUR_COMMON, ChronoUnit.HOURS).plus(BREED_TIME_MINUTE_COMMON, ChronoUnit.MINUTES);

            }
            case Rare -> {
                breedTime = now.plus(BREED_TIME_HOUR_RARE, ChronoUnit.HOURS).plus(BREED_TIME_MINUTE_RARE, ChronoUnit.MINUTES);

            }
            case Epic -> {
                breedTime = now.plus(BREED_TIME_HOUR_EPIC, ChronoUnit.HOURS).plus(BREED_TIME_MINUTE_EPIC, ChronoUnit.MINUTES);
            }
            case Mythical -> {
                breedTime = now.plus(BREED_TIME_HOUR_MYTHICAL, ChronoUnit.HOURS).plus(BREED_TIME_MINUTE_MYTHICAL, ChronoUnit.MINUTES);
            }
            case Legendary -> {
                breedTime = now.plus(BREED_TIME_HOUR_LEGENDARY, ChronoUnit.HOURS).plus(BREED_TIME_MINUTE_LEGENDARY, ChronoUnit.MINUTES);
            }
        }
        toadStatus.setExpectedBreed(Timestamp.from(breedTime));
        toadIngame.setToadStatus(toadStatus);

        return toadIngame;
    }

    public ToadIngame ToadFeed(ToadIngame toadIngame)
    {
        Instant now = Instant.now();

        var toadStatus = toadIngame.getToadStatus();
        var feedTime = toadStatus.getExpectedHungry().toInstant();
        switch (toadIngame.getToadData().getRarity())
        {
            case Common -> {
                feedTime = now.plus(FEED_TIME_HOUR_COMMON, ChronoUnit.HOURS).plus(FEED_TIME_MINUTE_COMMON, ChronoUnit.MINUTES);

            }
            case Rare -> {
                feedTime = now.plus(FEED_TIME_HOUR_RARE, ChronoUnit.HOURS).plus(FEED_TIME_MINUTE_RARE, ChronoUnit.MINUTES);

            }
            case Epic -> {
                feedTime = now.plus(FEED_TIME_HOUR_EPIC, ChronoUnit.HOURS).plus(FEED_TIME_MINUTE_EPIC, ChronoUnit.MINUTES);
            }
            case Mythical -> {
                feedTime = now.plus(FEED_TIME_HOUR_MYTHICAL, ChronoUnit.HOURS).plus(FEED_TIME_MINUTE_MYTHICAL, ChronoUnit.MINUTES);
            }
            case Legendary -> {
                feedTime = now.plus(FEED_TIME_HOUR_LEGENDARY, ChronoUnit.HOURS).plus(FEED_TIME_MINUTE_LEGENDARY, ChronoUnit.MINUTES);
            }
        }
        toadStatus.setExpectedHungry(Timestamp.from(feedTime));
        toadIngame.setToadStatus(toadStatus);

        return toadIngame;
    }

    public ToadIngame ToadCollect(ToadIngame toadIngame)
    {
        Instant now = Instant.now();

        var toadStatus = toadIngame.getToadStatus();
        var collectTime = toadStatus.getExpectedCollect().toInstant();
        switch (toadIngame.getToadData().getRarity())
        {
            case Common -> {
                collectTime = now.plus(COLLECT_TIME_HOUR_COMMON, ChronoUnit.HOURS).plus(COLLECT_TIME_MINUTE_COMMON, ChronoUnit.MINUTES);

            }
            case Rare -> {
                collectTime = now.plus(COLLECT_TIME_HOUR_RARE, ChronoUnit.HOURS).plus(COLLECT_TIME_MINUTE_RARE, ChronoUnit.MINUTES);

            }
            case Epic -> {
                collectTime = now.plus(COLLECT_TIME_HOUR_EPIC, ChronoUnit.HOURS).plus(COLLECT_TIME_MINUTE_EPIC, ChronoUnit.MINUTES);
            }
            case Mythical -> {
                collectTime = now.plus(COLLECT_TIME_HOUR_MYTHICAL, ChronoUnit.HOURS).plus(COLLECT_TIME_MINUTE_MYTHICAL, ChronoUnit.MINUTES);
            }
            case Legendary -> {
                collectTime = now.plus(COLLECT_TIME_HOUR_LEGENDARY, ChronoUnit.HOURS).plus(COLLECT_TIME_MINUTE_LEGENDARY, ChronoUnit.MINUTES);
            }
        }
        toadStatus.setExpectedCollect(Timestamp.from(collectTime));
        toadIngame.setToadStatus(toadStatus);

        return toadIngame;
    }

}
