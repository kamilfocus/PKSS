/*
 * CRadiator.h
 *
 *  Created on: 22 lis 2015
 *      Author: michal
 */

#ifndef CRADIATOR_H_
#define CRADIATOR_H_

#include "CEquation.h"
#include "CCoefs.h"

class CRadiatorParameters
{
private:
    double m_dWaterInTemp;
    double m_dInsideTemp;

public:
    CRadiatorParameters();
    CRadiatorParameters(double v_dWaterInTemp, double v_dInsideTemp);

    void UpdateParameters(double v_dWaterInTemp, double v_dInsideTemp);

    friend class CRadiator;
};

class CRadiatorConsts
{
private:
    double const m_dWeight;
    double const m_dSpecificHeat;
    double const m_dPenetrationRate;
    double const m_dMaximumWaterFlow;

public:
    CRadiatorConsts(double v_dRadiatorWeight, double v_dRadiatorSpecificHeat,
            double v_dRadiatorToRoomPenetration, double v_dMaximumWaterFlow);

    friend class CRadiator;
};

class CRadiator : public CEquation, CCoefs<CRadiatorParameters, CRadiatorConsts>
{
private:
    double m_dFlow;

public:
    CRadiator(CRadiatorConsts const &v_oConsts);
    double RHS(double v_dTime, double v_dArg);
    void UpdateParameters(double v_dWaterInTemp, double v_dInsideTemp);
    void SetFlow(double v_dFlow);
    double GetFlow();
};

#endif /* CRADIATOR_H_ */
