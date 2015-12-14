/*
 * CRungeKutta.h
 *
 *  Created on: 21 lis 2015
 *      Author: michal
 */

#ifndef CRUNGEKUTTA_H_
#define CRUNGEKUTTA_H_

#include "CEquation.h"

class CRungeKutta
{
private:
    CRungeKutta();
    CRungeKutta(CRungeKutta const &);

public:
    static double Solve(CEquation &v_oEquation,
            double v_dStep, double v_dTime, double v_dArg);
};

#endif /* CRUNGEKUTTA_H_ */
