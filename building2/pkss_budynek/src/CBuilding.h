/*
 * CBuilding.h
 *
 *  Created on: 22 lis 2015
 *      Author: michal
 */

#ifndef CBUILDING_H_
#define CBUILDING_H_

#include "CEquation.h"
#include "CCoefs.h"

class CBuildingParameters
{
private:
    double m_dWaterOutTemp;
    double m_dOutsideTemp;

public:
    CBuildingParameters();
    CBuildingParameters(double v_dWaterOutTemp, double v_dOutsideTemp);

    void UpdateParameters(double v_dWaterOutTemp, double v_dOutsideTemp);

    friend class CBuilding;
};

class CBuildingConsts
{
private:
    double const m_dAirWeight;
    double const m_dSpecificHeat;
    double const m_dHeatLossRate;
    double const m_dPenetrationRate;

public:
    CBuildingConsts(double v_dAirWeight, double v_dSpecificHeat,
            double v_dHeatLossRate, double v_dPenetrationRate);

    friend class CBuilding;
};

class CBuilding : public CEquation, CCoefs<CBuildingParameters, CBuildingConsts>
{
public:
    CBuilding(CBuildingConsts const &v_oConsts);
    double RHS(double v_dTime, double v_dArg);
    void UpdateParameters(double v_dWaterOutTemp, double v_dOutsideTemp);
};

#endif /* CBUILDING_H_ */
