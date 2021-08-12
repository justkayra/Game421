package dev.kaira.service;

import dev.kaira.model.constant.ItemType;
import dev.kaira.util.EnumUtil;

public class TossAdviserService {


    public ItemType getRandomItem()  {
        return EnumUtil.getRndElement(ItemType.values());
    }

}
