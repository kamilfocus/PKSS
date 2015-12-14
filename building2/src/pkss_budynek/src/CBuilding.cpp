/*
 * CBuilding.cpp
 *
 *  Created on: 22 lis 2015
 *      Author: michal
 */

#include "CBuilding.h"

CBuildingParameters::CBuildingParameters() :
        m_dWaterOutTemp(0),
        m_dOutsideTemp(0)
{
}

CBuildingParameters::CBuildingParameters(
        double v_dWaterOutTemp,
        double v_dOutsideTemp) :
        m_dWaterOutTemp(v_dWaterOutTemp),
        m_dOutsideTemp(v_dOutsideTemp)
{
}

void CBuildingParameters::UpdateParameters(
        double v_dWaterOutTemp,
        double v_dOutsideTemp)
{
    m_dWaterOutTemp = v_dWaterOutTemp;
    m_dOutsideTemp = v_dOutsideTemp;
}

CBuildingConsts::CBuildingConsts(
        double v_dAirWeight,
        double v_dSpecificHeat,
        double v_dHeatLossRate,
        double v_dPenetrationRate) :
        m_dAirWeight(v_dAirWeight),
        m_dSpecificHeat(v_dSpecificHeat),
        m_dHeatLossRate(v_dHeatLossRate),
        m_dPenetrationRate(v_dPenetrationRate)
{
}

CBuilding::CBuilding(CBuildingConsts const &v_oConsts) :
        CCoefs(v_oConsts)
{
}

double CBuilding::RHS(double v_dTime, double v_dArg)
{
    double dResult;

    dResult = m_oConsts.m_dPenetrationRate * (m_oParameters.m_dWaterOutTemp - v_dArg);
    dResult -= m_oConsts.m_dHeatLossRate * (v_dArg - m_oParameters.m_dOutsideTemp);
    dResult /= (m_oConsts.m_dAirWeight * m_oConsts.m_dSpecificHeat);

    return dResult;
}

void CBuilding::UpdateParameters(double v_dWaterOutTemp, double v_dOutsideTemp)
{
    m_oParameters.UpdateParameters(v_dWaterOutTemp, v_dOutsideTemp);
}
