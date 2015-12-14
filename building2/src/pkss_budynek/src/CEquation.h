/*
 * CEquation.h
 *
 *  Created on: 22 lis 2015
 *      Author: michal
 */

#ifndef CEQUATION_H_
#define CEQUATION_H_

class CEquation
{
protected:
    virtual ~CEquation() {}

public:
    virtual double RHS(double v_dTime, double v_dArg) = 0;
};

#endif /* CEQUATION_H_ */
