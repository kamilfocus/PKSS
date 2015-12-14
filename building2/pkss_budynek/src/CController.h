/*
 * CController.h
 *
 *  Created on: 5 gru 2015
 *      Author: michal
 */

#ifndef CCONTROLLER_H_
#define CCONTROLLER_H_

class CController
{
private:
    double m_dSP;

    double m_dKp;
    double m_dKi;
    double m_dKd;

    double m_dIntegral;
    double m_dPrevError;

public:
    CController();
    virtual ~CController();

    void SetParameters(double v_dKp, double v_dKi, double v_dKd);
    void SetSP(double v_dSP);
    double Control(double v_dPV, double v_dStep);
};

#endif /* CCONTROLLER_H_ */
