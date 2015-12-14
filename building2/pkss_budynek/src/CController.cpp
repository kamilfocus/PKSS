/*
 * CController.cpp
 *
 *  Created on: 5 gru 2015
 *      Author: michal
 */

#include "CController.h"

CController::CController() :
        m_dSP(0), m_dKp(0), m_dKi(0), m_dKd(0), m_dIntegral(0), m_dPrevError(0)
{
}

CController::~CController()
{
}

void CController::SetParameters(double v_dKp, double v_dKi, double v_dKd)
{
    m_dKp = v_dKp;
    m_dKi = v_dKi;
    m_dKd = v_dKd;

    if(v_dKi == 0)
    {
        m_dIntegral = 0;
    }
}

void CController::SetSP(double v_dSP)
{
    m_dSP = v_dSP;
}

double CController::Control(double v_dPV, double v_dStep)
{
    double dError, dDerivative, dOutput;

    dError = m_dSP - v_dPV;
    m_dIntegral += dError * v_dStep;
    dDerivative = (dError - m_dPrevError) / v_dStep;
    dOutput = m_dKp * dError + m_dKi * m_dIntegral + m_dKd * dDerivative;
    m_dPrevError = dError;

    return dOutput;
}
