package com.example.eddie.card.MyAdapter;

import com.example.eddie.card.PoJo.UnitNumber;

import java.util.List;

/**
 * Created by eddie on 18/08/2017.
 */

public class UnitList  {

    private List<UnitNumber> unitNumber;

    public UnitList(List<UnitNumber> unitNumber) {
        this.unitNumber = unitNumber;
    }

    public List<UnitNumber> getUnitNumber() {
        return unitNumber;
    }

    public void setUnitNumber(List<UnitNumber> unitNumber) {
        this.unitNumber = unitNumber;
    }
}
