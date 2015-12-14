/*
 * CWater.h
 *
 *  Created on: 22 lis 2015
 *      Author: michal
 */

#ifndef CWATER_H_
#define CWATER_H_

class CWater
{
private:
    static double const m_dWaterDensity = 1000;
    static double const m_dWaterSpecificHeat = 4200;

    CWater() {}
    CWater(CWater const &) {}
    ~CWater() {}

public:
    static double WaterDensity() {return m_dWaterDensity;}
    static double WaterSpecificHeat() {return m_dWaterSpecificHeat;}
};

#endif /* CWATER_H_ */
