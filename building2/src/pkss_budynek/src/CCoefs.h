/*
 * CCoefs.h
 *
 *  Created on: 23 lis 2015
 *      Author: michal
 */

#ifndef CCOEFS_H_
#define CCOEFS_H_

template<typename Parameters, typename Consts>
class CCoefs
{
protected:
    Parameters m_oParameters;
    Consts m_oConsts;

    CCoefs(Consts v_oConsts) :
        m_oParameters(Parameters()),
        m_oConsts(v_oConsts) {}
    virtual ~CCoefs() {}

public:
    void SetParameters(Parameters &v_oParameters) {m_oParameters = v_oParameters;}
};

#endif /* CCOEFS_H_ */
