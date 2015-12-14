/*
 * CModel.h
 *
 *  Created on: 22 lis 2015
 *      Author: michal
 */

#ifndef CMODEL_H_
#define CMODEL_H_

#include <vector>

class CModel
{
private:
    std::vector<double> m_oState;

public:
    CModel(std::vector<double> v_oStartState);
};

#endif /* CMODEL_H_ */
