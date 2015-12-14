/*
 * CRungeKutta.cpp
 *
 *  Created on: 21 lis 2015
 *      Author: michal
 */

#include "CRungeKutta.h"

double CRungeKutta::Solve(CEquation &v_oEquation,
            double v_dStep, double v_dTime, double v_dArg)
{
    double dK1, dK2, dK3, dK4;

    dK1 = v_dStep * v_oEquation.RHS(v_dTime, v_dArg);
    dK2 = v_dStep * v_oEquation.RHS(v_dTime + v_dStep / 2, v_dArg + dK1 / 2);
    dK3 = v_dStep * v_oEquation.RHS(v_dTime + v_dStep / 2, v_dArg + dK2 / 2);
    dK4 = v_dStep * v_oEquation.RHS(v_dTime + v_dStep, v_dArg + dK3);

    return v_dArg + (dK1 + 2 * dK2 + 2 * dK3 + dK4) / 6;
}
