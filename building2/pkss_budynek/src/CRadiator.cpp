/*
 * CRadiator.cpp
 *
 *  Created on: 22 lis 2015
 *      Author: michal
 */

#include "CRadiator.h"
#include "CWater.h"

CRadiatorParameters::CRadiatorParameters() :
        m_dWaterInTemp(0),
        m_dInsideTemp(0)
{
}

CRadiatorParameters::CRadiatorParameters(
        double v_dWaterInTemp,
        double v_dInsideTemp) :
        m_dWaterInTemp(v_dWaterInTemp),
        m_dInsideTemp(v_dInsideTemp)
{
}

void CRadiatorParameters::UpdateParameters(
        double v_dWaterInTemp,
        double v_dInsideTemp)
{
    m_dWaterInTemp = v_dWaterInTemp;
    m_dInsideTemp = v_dInsideTemp;
}

CRadiatorConsts::CRadiatorConsts(double v_dRadiatorWeight,
        double v_dRadiatorSpecificHeat,
        double v_dRadiatorToRoomPenetration,
        double v_dMaximumWaterFlow) :
        m_dWeight(v_dRadiatorWeight),
        m_dSpecificHeat(v_dRadiatorSpecificHeat),
        m_dPenetrationRate(v_dRadiatorToRoomPenetration),
        m_dMaximumWaterFlow(v_dMaximumWaterFlow)
{
}

CRadiator::CRadiator(CRadiatorConsts const &v_oConsts) :
        CCoefs(v_oConsts), m_dFlow(0)
{
}

double CRadiator::RHS(double v_dTime, double v_dArg)
{
    double dResult;

    dResult = CWater::WaterDensity() * CWater::WaterSpecificHeat();
    dResult *= m_oConsts.m_dMaximumWaterFlow / 3600;
    dResult *= m_dFlow;
    dResult *= (m_oParameters.m_dWaterInTemp - v_dArg);
    dResult -= m_oConsts.m_dPenetrationRate * (v_dArg - m_oParameters.m_dInsideTemp);
    dResult /= (m_oConsts.m_dWeight * m_oConsts.m_dSpecificHeat);

    return dResult;
}

void CRadiator::UpdateParameters(double v_dWaterInTemp, double v_dInsideTemp)
{
    m_oParameters.UpdateParameters(v_dWaterInTemp, v_dInsideTemp);
}

void CRadiator::SetFlow(double v_dFlow)
{
    m_dFlow = (v_dFlow < 0) ? 0 : ((v_dFlow > 1) ? 1 : v_dFlow);
}

double CRadiator::GetFlow()
{
    return m_dFlow;
}
